package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denize on 15/02/2016.
 */
public class TabelaPacote {

    private static final String TABELA = "pacotes";
    public BaseDados bd;

    public TabelaPacote(Context ctx) {
        bd = new BaseDados(ctx);
    }


    public void inserir(Pacote pacote) {
        ContentValues valores = new ContentValues();
        valores.put("id_arquivo", pacote.id_arquivo);
        valores.put("id_pct", pacote.id_pct);
        valores.put("nomearquivo", pacote.nomearquivo);
        valores.put("versao", pacote.versao);
        valores.put("caminho", pacote.caminho);
        valores.put("dados", pacote.pctbase64);

        bd.insert(TABELA,valores);
    }


    public void atualizar(Pacote pacote) {
        ContentValues valores = new ContentValues();

        if (!(pacote.id_arquivo == null) || !(pacote.id_arquivo.trim() == "")) {
            valores.put("id_arquivo", pacote.id_arquivo);
        }

        if (pacote.id_pct != 0) {
            valores.put("id_pct", pacote.id_pct);
        }

        if (!(pacote.versao == null) || !(pacote.versao.trim() == "")) {
            valores.put("versao", pacote.versao);
        }

        if (!(pacote.nomearquivo == null) || !(pacote.nomearquivo.trim() == "")) {
            valores.put("nomearquivo", pacote.nomearquivo);
        }

        if (!(pacote.caminho == null) || !(pacote.caminho.trim() == "")) {
            valores.put("caminho", pacote.caminho);
        }

        if (pacote.pctbase64 != null) {
            valores.put("dados", pacote.pctbase64);
        }

        bd.update(TABELA, valores, " _id = " + pacote._id, null);
    }

    public void deletar(Pacote pacote) {
        bd.delete(TABELA, "_id = " + pacote._id, null);
    }

    public void deletarTodos()
    {
        bd.deleteAll(TABELA);
    }

    public List<Pacote> buscarTodos() {
        List<Pacote> list = new ArrayList<Pacote>();
        String sql = "Select _id, id_arquivo, versao, id_pct, nomearquivo, caminho, pctbase64 from pacotes";

        Cursor cursor = bd.select(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Pacote p = new Pacote(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));

                list.add(p);

            } while (cursor.moveToNext());
        }

        return list;
    }
}
