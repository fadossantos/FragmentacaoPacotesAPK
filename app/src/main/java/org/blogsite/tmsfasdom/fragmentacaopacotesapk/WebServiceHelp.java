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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Denize on 12/02/2016.
 */
public class WebServiceHelp {


    public static String ChamadaGet(String urlToService) {
        Log.d("Info", "onHandleIntent: dentro da ChamadaGet para " + urlToService);
        InputStream inpStr;
        URL url = null;
        try {
            url = new URL(urlToService);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error";
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "Error";
        }
        conn.setDoInput(true);
        try {
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
        int responseCod = 0;
        try {
            responseCod = conn.getResponseCode();
            Log.d("Info", "ChamadaGet: Chamada http retornou" + responseCod);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
        InputStreamReader input;
        BufferedReader inputreader = null;
        StringBuffer output = new StringBuffer();
        try {
            inpStr = conn.getInputStream();
            input = new InputStreamReader(inpStr, "UTF-8");
            inputreader = new BufferedReader(input);
            String line;
            while ((line = inputreader.readLine()) != null) {
                output.append(line);
            }
            inputreader.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
        return output.toString();

    }


}
