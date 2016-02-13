package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



public class TelaPrincipal extends AppCompatActivity {
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

    }

    public void chamaServico(View v)
    {

        // declare the dialog as a member field of your activity
        progressDialog = new ProgressDialog(TelaPrincipal.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();


        Log.d("informacao", "mostrou a msg");
        Intent intent = new Intent(this, DownloadJson.class);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        startService(intent);
        Log.d("informacao", "chamou o servico");

    }
    private class DownloadReceiver extends ResultReceiver {

        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadJson.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                progressDialog.setProgress(progress);

            }
            if (resultCode == DownloadJson.FINISH_PROGRESS)
            {
                progressDialog.dismiss();
            }
        }
    }

}
