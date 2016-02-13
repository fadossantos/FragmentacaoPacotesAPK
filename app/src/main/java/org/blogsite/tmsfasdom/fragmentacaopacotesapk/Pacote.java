package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

/**
 * Created by Denize on 12/02/2016.
 */
public class Pacote {
    int id_Arquivo;
    int id_Pct;
    String nomeArquivo;
    String pctBase64;

    public void Pacote(int _id_Arquivo, int _id_Pct, String _nomeArquivo, String _pctBase64) {
        this.id_Arquivo = _id_Arquivo;
        this.id_Pct = _id_Pct;
        this.nomeArquivo = _nomeArquivo;
        this.pctBase64 = _pctBase64;
    }
}
