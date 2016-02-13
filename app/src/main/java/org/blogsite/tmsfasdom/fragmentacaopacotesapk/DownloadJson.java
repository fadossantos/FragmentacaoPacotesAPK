package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Denize on 11/02/2016.
 */
public class DownloadJson extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    public static final int FINISH_PROGRESS = 8345;


    public DownloadJson() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("informacao", "onHandleIntent: dentro do svc");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        Bundle resultData = new Bundle();
        InformacoesArquivo pct;
        pct = FragmentacaoPacotesWCF.RetornaInformacoesArquivo("tmd_pmesp-6.2.2.0.apk");
        resultData.putInt("progress", 50);
        receiver.send(UPDATE_PROGRESS, resultData);
        StringBuilder dados = new StringBuilder();
        dados.append(pct.indexArquivo);
        dados.append("/n");
        dados.append(pct.nomeArquivo);
        dados.append("/n");
        dados.append(pct.quantidadePacotes);
        dados.append("/n");
        dados.append(pct.tamanhoArquivo);

        Log.d("Debug", "String recebida: " + dados);
        String path = "";
        File file;
        if (isExternalStorageWritable()) {
            file = new File(getExternalFilesDir(null), "Retorno.txt");
            path = file.getAbsolutePath();
            Toast.makeText(this, path + " true", Toast.LENGTH_LONG);
        } else {
            file = new File(getFilesDir(), "Retorno.txt");
            path = file.getAbsolutePath();
            Toast.makeText(this, path + " false", Toast.LENGTH_LONG);
        }
        Log.d("Debug", "Caminho Salvo: " + path);
        try {
            OutputStream outputFile = new FileOutputStream(file, true);

            outputFile.write(dados.toString().getBytes());
            outputFile.flush();
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;


    }
}