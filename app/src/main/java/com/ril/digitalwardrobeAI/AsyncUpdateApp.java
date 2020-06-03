package com.ril.digitalwardrobeAI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;

public class AsyncUpdateApp extends AsyncTask{
    Context context;
    public AsyncUpdateApp(Context context){
        this.context=context;

    }

    public  String getApkStoragePath(){
        String path = context.getFilesDir()+"/myapks/dw.apk";
        Log.d(LOGTAG," Generated path for apk "+path);
        return path;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.d(LOGTAG, String.valueOf(objects[0]));
        //String path = Environment.getExternalStorageDirectory()+"/dw.apk";
         String path = getApkStoragePath();
        //download the apk from your server and save to sdk card here
        try{
            URL url = new URL((String) objects[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(path);
            //OutputStream output = context.openFileOutput(path,Context.MODE_WORLD_READABLE);
            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1)
            {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        }catch(Exception e){}

        return path;
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.d(LOGTAG,"Download apk "+o.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Uri uri = Uri.fromFile(new File(getApkStoragePath()));
        //intent.setDataAndType(uri, "application/vnd.android.package-archive");

        File file=new File(getApkStoragePath());

        Uri uri = FileProvider.getUriForFile(context, "com.ril.digitalwardrobe.GenericFileProvider",file );
        Log.d(LOGTAG,"Update apk location "+uri.getPath());
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        //Uri uri=FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + "com.ril.digitalwardrobe.provider", new File(Environment.getExternalStorageDirectory()+ "/dw.apk"));
       // intent.setDataAndType(uri,"application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Log.d(LOGTAG,"Update activity started");
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
       // super.onProgressUpdate(values);
        Log.d(LOGTAG,"values "+ values.toString());

    }
}

