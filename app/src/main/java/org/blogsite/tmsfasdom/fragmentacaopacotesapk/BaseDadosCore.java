package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Denize on 14/02/2016.
 */
public class BaseDadosCore extends SQLiteOpenHelper {
        private static final String NOME_BD = "FragmentacaoBD";
        private static final int VERSAO_BD = 1;
    Context ctx;


        public BaseDadosCore(Context ctx_){
            super(ctx_, NOME_BD, null, VERSAO_BD);
            ctx = ctx_;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            readAndExecuteSQLScript(db, ctx, R.raw.createdatabase);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            readAndExecuteSQLScript(db, ctx, R.raw.droptables);
            onCreate(db);
        }


    private void readAndExecuteSQLScript(SQLiteDatabase db, Context ctx, Integer sqlScriptResId) {

        Resources res = ctx.getResources();

        try {
            InputStream is = res.openRawResource(sqlScriptResId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            executeSQLScript(db, reader);

            reader.close();
            isr.close();
            is.close();

        } catch (IOException e) {
            throw new RuntimeException("Unable to read SQL script", e);
        }
    }

    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader)
            throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                db.execSQL(statement.toString());
                statement = new StringBuilder();
            }
        }
    }

    }


