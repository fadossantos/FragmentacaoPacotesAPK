package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.util.Base64;

/**
 * Created by Denize on 12/02/2016.
 */
public class Pacote {
    transient int _id;
    String id_arquivo;
    int id_pct;
    String nomearquivo;
    String pctbase64;
    transient String caminho;
    String versao;



    public Pacote(int _id_,
                       String _id_Arquivo, 
                       String _versao, 
                       int _id_Pct, 
                       String _nomeArquivo, 
                       String _caminho, 
                       String _pctBase64) 
    {
        this._id = _id_;
        this.id_arquivo = _id_Arquivo;
        this.versao = _versao;
        this.id_pct = _id_Pct;
        this.nomearquivo = _nomeArquivo;
        this.caminho = _caminho;
        this.pctbase64 = _pctBase64;

    }


}
