package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Denize on 13/02/2016.
 */
public class FileUtils {

    Context context;

    FileUtils(Context context_)
    {
        this.context = context_;
    }

    public boolean PersistirSD(String filename, String dados){

        File diretorioBase = getDirFromSDCard();
        File file;
        String path;

        if (diretorioBase != null) {
            file = new File(diretorioBase, filename);
            file.delete();
            path = file.getAbsolutePath();

        } else {
            file = new File(context.getExternalFilesDir(null), filename);
            file.delete();
            path = file.getAbsolutePath();
        }

        try {
            OutputStream outputFile = new FileOutputStream(file, true);
            outputFile.write(dados.getBytes());
            outputFile.flush();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Erro", "PersistirSD: erro ao salvar arquivo");
            return false;
        }
        Log.d("Info", "PersistirSD: Arquivo salvo em: " + path);
        return true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private File getDirFromSDCard() {
        if (isExternalStorageWritable()) {
            File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File dir = new File(sdcard, context.getPackageName() + File.separator + "Files");
            if (!dir.exists())
                dir.mkdirs();
            return dir;
        } else {
            return null;
        }
    }


}
