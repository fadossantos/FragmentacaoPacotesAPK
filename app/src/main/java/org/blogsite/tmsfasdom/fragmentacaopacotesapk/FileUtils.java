package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

    Context context;

    FileUtils(Context context_)

    {
        this.context = context_;
    }

    public boolean persistirPacoteNoArquivo(String filename, String pctbase64) {

        try {
            OutputStream outputFile = new FileOutputStream(new File(filename), true);
            outputFile.write(Base64.decode(pctbase64, Base64.DEFAULT));
            outputFile.flush();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Erro", "PersistirSD: erro ao salvar arquivo");
            return false;
        }
        Log.d("Info", "PersistirSD: Arquivo salvo em: " + filename);
        return true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public String retornaCaminhoArquivoParaPersistir(InformacoesArquivo informacoesArquivo) {
        if (isExternalStorageWritable()) {
            File dir = context.getExternalFilesDir(null);
            //File dir = new File(sdcard, context.getPackageName() + File.separator + "Files");
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            File arquivofinal = new File(dir.getAbsolutePath() + File.separator + informacoesArquivo.versao + "_" + informacoesArquivo.nomearquivo);
            arquivofinal.delete();
            return arquivofinal.getAbsolutePath();
        } else {
            return null;
        }
    }
}
