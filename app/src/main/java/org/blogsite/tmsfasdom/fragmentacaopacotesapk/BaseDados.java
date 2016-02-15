package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denize on 14/02/2016.
 */
public class BaseDados {

    SQLiteDatabase bd;
    Context ctx;


    public BaseDados(Context context) {
        BaseDadosCore auxBd = new BaseDadosCore(context);
        bd = auxBd.getWritableDatabase();
        ctx = context;
    }

    public void update(String table, ContentValues values, String whereClause, String[] whereArgs)
    {
        if(!(bd.isOpen())) {
            BaseDadosCore auxBd = new BaseDadosCore(ctx);
            bd = auxBd.getWritableDatabase();
        }
        bd.update(table,values,whereClause,whereArgs);
        bd.close();
    }

    public Cursor select(String sql, String[] args)
    {
        if(!(bd.isOpen())) {
            BaseDadosCore auxBd = new BaseDadosCore(ctx);
            bd = auxBd.getWritableDatabase();
            }
        Cursor cursor = bd.rawQuery(sql, args);
        bd.close();
        return cursor;
    }

    public void insert(String tabela, ContentValues valores)
    {

        if(!(bd.isOpen())) {
            BaseDadosCore auxBd = new BaseDadosCore(ctx);
            bd = auxBd.getWritableDatabase();
        }
        bd.insert(tabela, null, valores);
        bd.close();
    }

    public void deleteAll(String tabela)
    {

        if(!(bd.isOpen())) {
            BaseDadosCore auxBd = new BaseDadosCore(ctx);
            bd = auxBd.getWritableDatabase();
        }
        bd.delete(tabela, null, null);
        bd.close();
    }

    public void delete(String tabela, String where, String[] args)
    {
        if(!(bd.isOpen())) {
            BaseDadosCore auxBd = new BaseDadosCore(ctx);
            bd = auxBd.getWritableDatabase();
        }
        bd.delete(tabela, where, args);
        bd.close();
    }


}



