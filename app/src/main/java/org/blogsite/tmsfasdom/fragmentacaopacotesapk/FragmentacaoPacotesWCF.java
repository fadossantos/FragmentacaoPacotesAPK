package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import com.google.gson.Gson;

/**
 * Created by Denize on 12/02/2016.
 */
public class FragmentacaoPacotesWCF {
    final static private String urlMetodoRetornaPacote = "http://tmsfasdom.com.br/Pacotes/SVC_DownloadArquivo.svc/rest/RetornaPacote";
    final static private String urlMetodoRetornaListaArquivos = "http://tmsfasdom.com.br/Pacotes/SVC_DownloadArquivo.svc/rest/retornalistaarquivos";
    final static private String urlMetodoRetornaInfoArquivo = "http://tmsfasdom.com.br/Pacotes/SVC_DownloadArquivo.svc/rest/retornainformacoesarquivo";

    public static Pacote RetornaPacote(int id_Arquivo, int id_Pacote){
        StringBuilder url = new StringBuilder(urlMetodoRetornaPacote);
        url.append("/").append(id_Arquivo).append("/").append(id_Pacote);
        String retorno = WebServiceHelp.ChamadaGet(url.toString());
        Gson json = new Gson();
        return json.fromJson(retorno, Pacote.class);
    }

    public static String[]  RetornaListaArquivos(){
        String retorno = WebServiceHelp.ChamadaGet(urlMetodoRetornaListaArquivos);
        Gson json = new Gson();
        return json.fromJson(retorno, String[].class);
    }

    public static InformacoesArquivo RetornaInformacoesArquivo(String nomeArquivo)
    {
        StringBuilder url = new StringBuilder(urlMetodoRetornaInfoArquivo);
        url.append("/").append(nomeArquivo);
        String retorno = WebServiceHelp.ChamadaGet(url.toString());
        Gson json = new Gson();
        return json.fromJson(retorno, InformacoesArquivo.class);
    }
}
