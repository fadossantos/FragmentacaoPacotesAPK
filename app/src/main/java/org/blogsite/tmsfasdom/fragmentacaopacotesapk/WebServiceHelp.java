package org.blogsite.tmsfasdom.fragmentacaopacotesapk;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Denize on 12/02/2016.
 */
public class WebServiceHelp {


    public static String ChamadaGet(String urlToService) throws IOException {
        Log.d("Info", "onHandleIntent: dentro da ChamadaGet para " + urlToService);
        InputStream inpStr;
        URL url = null;
        url = new URL(urlToService);

        HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
        conn.setDoInput(true);
            conn.connect();
        int responseCod = 0;
            responseCod = conn.getResponseCode();
            Log.d("Info", "ChamadaGet: Chamada http retornou Http " + responseCod);
        InputStreamReader input;
        BufferedReader inputreader = null;
        StringBuffer output = new StringBuffer();
            inpStr = conn.getInputStream();
            input = new InputStreamReader(inpStr, "UTF-8");
            inputreader = new BufferedReader(input);
            String line;
            while ((line = inputreader.readLine()) != null) {
                output.append(line);
            }
            inputreader.close();
            input.close();

        return output.toString();

    }


}
