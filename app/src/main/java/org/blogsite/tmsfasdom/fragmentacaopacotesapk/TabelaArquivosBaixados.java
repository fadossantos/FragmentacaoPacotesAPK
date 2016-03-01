package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TabelaArquivosBaixados {

    /**
     * Created by Denize on 15/02/2016.
     */

    public static final String TABELA = "arquivosbaixados";
    public static final String LISTACAMPOS = "Select _id, id_arquivo, nomearquivo, versao, status, datainicio, datafim, caminhoarquivo, quantidadepacotes, tamanhoarquivo from " + TABELA;
    private BaseDados bd;

    public TabelaArquivosBaixados(Context ctx) {
        bd = new BaseDados(ctx);
    }


    public int inserir(InformacoesArquivo informacoesArquivo) {
        ContentValues valores = new ContentValues();
        valores.put("id_arquivo", informacoesArquivo.id_arquivo);
        valores.put("nomearquivo", informacoesArquivo.nomearquivo);
        valores.put("versao", informacoesArquivo.versao);
        valores.put("status", informacoesArquivo.status);
        valores.put("datainicio", informacoesArquivo.datainicio);
        valores.put("datafim", informacoesArquivo.datafim);
        valores.put("caminhoarquivo", informacoesArquivo.caminhoarquivo);
        valores.put("quantidadepacotes", informacoesArquivo.quantidadepacotes);
        valores.put("tamanhoarquivo", informacoesArquivo.tamanhoarquivo);
        return bd.insert(TABELA, valores);
    }


    public void atualizar(InformacoesArquivo informacoesArquivo) {
        ContentValues valores = new ContentValues();
        if (informacoesArquivo.id_arquivo != null && informacoesArquivo.id_arquivo != "")
            valores.put("id_arquivo", informacoesArquivo.id_arquivo);
        if (informacoesArquivo.nomearquivo != null && informacoesArquivo.nomearquivo != "")
            valores.put("nomearquivo", informacoesArquivo.nomearquivo);
        if (informacoesArquivo.versao != null && informacoesArquivo.versao != "")
            valores.put("versao", informacoesArquivo.versao);
        if (informacoesArquivo.status != 0)
            valores.put("status", informacoesArquivo.status);
        if (informacoesArquivo.datainicio != null && informacoesArquivo.datainicio != "")
            valores.put("datainicio", informacoesArquivo.datainicio);
        if (informacoesArquivo.datafim != null && informacoesArquivo.datafim != "")
            valores.put("datafim", informacoesArquivo.datafim);
        if (informacoesArquivo.caminhoarquivo != null && informacoesArquivo.caminhoarquivo != "")
            valores.put("caminhoarquivo", informacoesArquivo.caminhoarquivo);
        if (informacoesArquivo.quantidadepacotes != 0)
            valores.put("quantidadepacotes", informacoesArquivo.quantidadepacotes);
        if (informacoesArquivo.tamanhoarquivo != 0)
            valores.put("tamanhoarquivo", informacoesArquivo.tamanhoarquivo);
        bd.update(TABELA, valores, " _id = " + informacoesArquivo._id, null);
    }

    public void deletar(InformacoesArquivo informacoesArquivo) {
        bd.delete(TABELA, "_id = " + informacoesArquivo._id, null);
    }

    public void deletarTodos() {
        bd.deleteAll(TABELA);
    }

    public List<InformacoesArquivo> buscarTodos() {
        List<InformacoesArquivo> list = new ArrayList<>();
        String sql = "Select _id, id_arquivo, nomearquivo, versao, status, datainicio, datafim, caminhoarquivo, quantidadepacotes, tamanhoarquivo from " + TABELA;
        Cursor cursor = bd.select(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                InformacoesArquivo infArq = new InformacoesArquivo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9)
                );
                list.add(infArq);

            } while (cursor.moveToNext());
        }
        bd.close();
        return list;

    }

    public List<InformacoesArquivo> buscar(String sql, String[] parametros) {
        List<InformacoesArquivo> list = new ArrayList<>();
        Cursor cursor = bd.select(sql, parametros);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                InformacoesArquivo infArq = new InformacoesArquivo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9)
                );
                list.add(infArq);

            } while (cursor.moveToNext());
        }
        bd.close();
        return list;
    }
}


