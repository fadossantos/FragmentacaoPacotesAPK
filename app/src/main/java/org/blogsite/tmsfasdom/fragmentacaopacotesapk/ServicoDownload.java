package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicoDownload extends Service {
    //String numeroIMEI;
    //String numeroIP;

    public ServicoDownload() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //TODO obter imei para passar nas comunicacoes com o servidor

        Log.d("DEBUG", "onCreate: Servico Criado com sucesso");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DEBUG", "onStartCommand: Servico startado");
        try {
            new Thread() {
                @Override
                public void run() {

                    try {
                        executar();
                        stopSelf();
                    } catch (Exception e) {
                        e.printStackTrace();
                        stopSelf();
                    }
                    super.run();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DEBUG", "onStartCommand: Deu merda e parou");
            Log.d("DEBUG", "Chamou stopSelf()");
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void executar() throws Exception {
        Log.d("DEBUG", "onHandleIntent: dentro do svc");
        //Verifica se tem download em andamento na base de dados
        List<InformacoesArquivo> listDownloadAndamento = retornaDownloadEmAndamento();
        String[] listStr;
        try {
            listStr = FragmentacaoPacotesWCF.RetornaListaArquivos();
        } catch (IOException e) {
            Log.d("DEBUG", "Erro na chamada para RetornaListaArquivos");
            throw e;
        }
        //Caso lista de downloads em andamento esteja vazia iniciar processo de verificacao de arquivos disponiveis
        if (listDownloadAndamento.isEmpty()) {
            //Obtem lista de arquivos disponiveis

            List<InformacoesArquivo> arquivosParaBaixar = new ArrayList<>();

            //para cada arquivo disponivel para download busca informacoes do arquivo
            if (listStr != null) {
                for (String str : listStr) {

                    try {
                        //TODO obter IP para passar nas comunicacoes com o servidor
                        InformacoesArquivo informacoesArquivo = FragmentacaoPacotesWCF.RetornaInformacoesArquivo(str);
                        if (informacoesArquivo != null) {
                            //Verifica se o arquivo ja nao foi concluido na base
                            if (!verificaConclusao(informacoesArquivo)) {
                                arquivosParaBaixar.add(informacoesArquivo);
                            }
                        }

                    } catch (IOException e) {

                        Log.d("DEBUG", "executar: erro na requisicao RetornaInformacoesArquivo");
                        throw e;

                    }
                }
            }
            //para cada item na lista arquivos para baixar realiza o download de pacotes
            for (InformacoesArquivo infArq : arquivosParaBaixar) {
                downloadPacotes(infArq);

            }
        }
        //Se a lista de downloads iniciados nao esta vazia, continuar cada um dos downloads
        else {
            boolean arquivoDisponivel = false;

            for (InformacoesArquivo infArq : listDownloadAndamento) {
                for (String str : listStr)
                {
                    if(str.contentEquals(infArq.nomearquivo)){
                        arquivoDisponivel = true;
                        try {
                            //TODO obter IP para passar nas comunicacoes com o servidor
                            InformacoesArquivo informacoesArquivo = FragmentacaoPacotesWCF.RetornaInformacoesArquivo(infArq.nomearquivo);
                            if (informacoesArquivo != null) {
                                //Se versao da base eh diferente da versao disponivel apagar registros da base para recomecar download na prox execucao.
                                if (!infArq.versao.contentEquals(informacoesArquivo.versao)) {
                                    apagarArquivo(infArq);
                                    Log.d("DEBUG", "executar: Versões diferentes - Banco: " + infArq.nomearquivo + " Versão " + infArq.versao + " Servico: " + informacoesArquivo.nomearquivo + " Versão: " + informacoesArquivo.versao);
                                    throw new Exception("DEBUG - Versoes Diferentes");
                                }
                            } else {
                                Log.d("DEBUG", "executar: Arquivo não encontrado no servidor. Apagando conteudo da base de dados.");
                                apagarArquivo(infArq);
                                throw new Exception("DEBUG - Arquivo nao encontrado, servidor retornou nulo ou vazio para: " + infArq.nomearquivo);
                            }

                        } catch (Exception e) {
                            Log.d("DEBUG", "Parando devido à exceção");
                            throw e;
                        }
                        downloadPacotes(infArq);
                    }
                }
                if (!arquivoDisponivel){
                    apagarArquivo(infArq);
                }
                arquivoDisponivel = false;
            }
        }
        Log.d("DEBUG", "executar: Terminou a execução com sucesso.");
    }

    private void apagarArquivo(InformacoesArquivo infarq) {

        TabelaArquivosBaixados tabelaArquivosBaixados = new TabelaArquivosBaixados(this);
        tabelaArquivosBaixados.deletar(infarq);
        TabelaPacote tabelaPacote = new TabelaPacote(this);
        tabelaPacote.deletarTodos(infarq);
    }


    public boolean verificaConclusao(InformacoesArquivo infArq) {
        TabelaArquivosBaixados tabelaArquivosBaixados = new TabelaArquivosBaixados(this);
        String sql = TabelaArquivosBaixados.LISTACAMPOS + " where id_arquivo = ? and versao = ?";
        String[] parametros = new String[]{infArq.id_arquivo, infArq.versao};
        List<InformacoesArquivo> arquivoList = tabelaArquivosBaixados.buscar(sql, parametros);
        if (arquivoList.isEmpty()) {
            return false;
        }
        else {
            return arquivoList.get(0).status != 0;
        }
    }

    public int downloadPacotes(InformacoesArquivo infArq) throws Exception {
        int numPctConcluidos;
        numPctConcluidos = retornaNumPctConcluidos(infArq);

        try {
            for (int i = numPctConcluidos; i <= infArq.quantidadepacotes; i++) {
                Pacote pct = FragmentacaoPacotesWCF.RetornaPacote(infArq.id_arquivo, infArq.versao, i);
                if (!(infArq.versao.contentEquals(pct.versao))) {
                    apagarArquivo(infArq);
                    Log.d("DEBUG", "downloadPacotes: Versao do pacote diferente da versao do arquivo baixado em andamento.");
                    Log.d("DEBUG", "Chamou stopSelf()");
                    throw new Exception("DownloadPacotes: Erro no download de pacote");
                }
                Log.d("Debug", "String recebida: " + pct.nomearquivo + " - Pacote: " + pct.id_pct);
                pct.caminho = infArq.caminhoarquivo + "_" + pct.id_pct;
                TabelaPacote TbPct = new TabelaPacote(this);
                TbPct.inserir(pct);
            }
        } catch (Exception e) {
            Log.d("DEBUG", "downloadPacotes: erro no download do pacote " + infArq.nomearquivo);
            throw e;
        }
        //juntar pacotes e colocar na lista para instalacao
        juntarPacotes(infArq);
        //Marcar arquivo como concluido na tabela
        TabelaArquivosBaixados tabelaArquivosBaixados = new TabelaArquivosBaixados(this);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        infArq.datafim = dateFormat.format(date);
        infArq.status = 1;
        tabelaArquivosBaixados.atualizar(infArq);
        //Deletar os pacotes da tabela
        TabelaPacote TbPct = new TabelaPacote(this);
        TbPct.deletarTodos(infArq);
        Log.d("DEBUG", "downloadPacotes: terminou download e juncao de pacotes com sucesso: " + infArq.nomearquivo);
        return 1;
    }

    private List<InformacoesArquivo> retornaDownloadEmAndamento() {
        TabelaArquivosBaixados tabelaArquivosBaixados = new TabelaArquivosBaixados(this);
        String sql = "Select _id, id_arquivo, nomearquivo, versao, status, datainicio, datafim, caminhoarquivo, quantidadepacotes, tamanhoarquivo from " + tabelaArquivosBaixados.TABELA + " where status = 0";
        List<InformacoesArquivo> arquivoList = tabelaArquivosBaixados.buscar(sql, null);
        return arquivoList;
    }

    private int retornaNumPctConcluidos(InformacoesArquivo infArq) {
        TabelaArquivosBaixados tabelaArquivosBaixados = new TabelaArquivosBaixados(this);
        String sql = TabelaArquivosBaixados.LISTACAMPOS + " where id_arquivo = ? and versao = ?";
        String[] parametros = new String[]{infArq.id_arquivo, infArq.versao};
        List<InformacoesArquivo> arquivoList = tabelaArquivosBaixados.buscar(sql, parametros);
        if (arquivoList.isEmpty()) {
            infArq.status = 0;
            infArq.caminhoarquivo = (new FileUtils(this)).retornaCaminhoArquivoParaPersistir(infArq);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            infArq.datainicio = dateFormat.format(date);
            int a = tabelaArquivosBaixados.inserir(infArq);
            infArq._id = a;
        }
        TabelaPacote tabelaPacote = new TabelaPacote(this);
        return tabelaPacote.getCount(infArq);

    }

    public void juntarPacotes(InformacoesArquivo infArq) throws Exception {

        File file = new File(infArq.caminhoarquivo);
        file.delete();
        for (int i = 0; i <= infArq.quantidadepacotes; i++) {
            TabelaPacote TbPct = new TabelaPacote(this);
            Pacote pct = TbPct.buscarPacote(infArq.id_arquivo, infArq.versao, i);
            if (pct != null) {
                FileUtils fileUtils = new FileUtils(this);
                if (!(fileUtils.persistirPacoteNoArquivo(infArq.caminhoarquivo, pct.pctbase64))) {
                    Log.d("DEBUG", "Não salvou arquivo direito" + infArq.nomearquivo + " - Pacote: " + i);
                    throw new Exception("Erro ao salvar arquivo!");
                }
                TbPct.deletar(pct);
            }
        }
    }
}
