package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DownloadJson extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    public static final int FINISH_PROGRESS = 8345;
    public static final int DOWNLOAD_CONCLUIDO = 1;
    private static final int DOWNLOAD_INTERROMPIDO = 470;

    public DownloadJson() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("informacao", "onHandleIntent: dentro do svc");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle resultData = new Bundle();
        String[] listStr = null;
        try {
            listStr = FragmentacaoPacotesWCF.RetornaListaArquivos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<InformacoesArquivo> inf = new ArrayList<InformacoesArquivo>();
        for (String str : listStr) {

            try {
                inf.add(FragmentacaoPacotesWCF.RetornaInformacoesArquivo(str));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (InformacoesArquivo infarq : inf) {

            //resultData.putInt("progress", (int) (i * 100 / infarq.quantidadePacotes));
            //receiver.send(UPDATE_PROGRESS, resultData);


        }
        //Log.d("Debug", "String recebida: " + dados);
        //FileUtils futil = new FileUtils(this);
        //futil.PersistirSD("Retorno.txt", dados.toString());
        resultData.putInt("progress", 100);
        receiver.send(FINISH_PROGRESS, resultData);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("informacao", "start command");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("informacao", "on create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("informacao", "ondestroy");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("informacao", "on start");
    }

    public int downloadPacotes(InformacoesArquivo infArq) {
        int numPctConcluidos = 0;

        if (downloadIniciado(infArq)) {
            numPctConcluidos = retornaNumPctConcluidos(infArq);
        }
        try {
            for (int i = numPctConcluidos; i < infArq.quantidadepacotes; i++) {
                Pacote pct = FragmentacaoPacotesWCF.RetornaPacote(infArq.id_arquivo, i);
                Log.d("Debug", "String recebida: " + pct.nomearquivo + "\n" + pct.id_pct);
                StringBuilder caminho = new StringBuilder(Environment.getExternalStorageDirectory().getAbsoluteFile().getAbsolutePath());
                caminho.append(File.separator).append(pct.nomearquivo).append("_Pacote_").append(pct.id_pct);
                pct.caminho =  caminho.toString();
                TabelaPacote TbPct = new TabelaPacote(this);
                TbPct.inserir(pct);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Debug", "downloadPacotes: " + e.getMessage());
            return DOWNLOAD_INTERROMPIDO;
        }
        return DOWNLOAD_CONCLUIDO;
    }

    private boolean downloadIniciado(InformacoesArquivo infArq) {

        return true;
    }

    private int retornaNumPctConcluidos(InformacoesArquivo infArq) {

        return 0;
    }

}
