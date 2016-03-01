package org.blogsite.tmsfasdom.fragmentacaopacotesapk;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExecutarServico extends BroadcastReceiver {
    public ExecutarServico() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!(isMyServiceRunning(ServicoDownload.class, context))) {
            Intent serviceIntent = new Intent(context, ServicoDownload.class);
            context.startService(serviceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Fernando", "Servico ja esta rodando");
                return true;
            }
        }
        Log.i("Fernando", "Servico nao esta rodando");
        return false;
    }

}
