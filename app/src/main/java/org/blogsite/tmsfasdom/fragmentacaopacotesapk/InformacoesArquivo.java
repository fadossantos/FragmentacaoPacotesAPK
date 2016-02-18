package org.blogsite.tmsfasdom.fragmentacaopacotesapk;


import java.sql.Date;

/**
 * Created by Denize on 12/02/2016.
 */
public class InformacoesArquivo {
    transient int _id;
    String id_arquivo;
    String nomearquivo;
    String versao;
    transient String status;
    transient String datainicio;
    transient String datafim;
    transient String caminhoarquivo;
    int quantidadepacotes;
    int tamanhoarquivo;




    public InformacoesArquivo(
            int      _id_
            ,String id_arquivo_
            ,String nomearquivo_
            ,String versao_
            ,String status_
            ,String datainicio_
            ,String datafim_
            ,String caminhoarquivo_
            ,int    quantidadepacotes_
            ,int    tamanhoarquivo_
    )
    {
        this._id = _id_;
        this.id_arquivo = id_arquivo_;
        this.nomearquivo = nomearquivo_;
        this.versao = versao_;
        this.status = status_;
        this.datainicio = datainicio_;
        this.datafim = datafim_;
        this.caminhoarquivo = caminhoarquivo_;
        this.quantidadepacotes = quantidadepacotes_;
        this.tamanhoarquivo = tamanhoarquivo_;

    }


}
