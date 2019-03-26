package rdproject.fileloader.util;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FileLoader extends AsyncTask<String, String, String> {
    public static final String TAG = "FileLoaderUtility";
    private OnSuccessListener onSuccessListener;
    private String incomingURI;

    public void addOnEventListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        int count;
        incomingURI = strings[0];
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            String fileName = strings[0].substring(strings[0].lastIndexOf('/') + 1);

            //External directory path to save file
            String folder = Environment.getExternalStorageDirectory() + File.separator + "filestorage/";
            File directory = new File(folder);

            if (!directory.exists()) {
                directory.mkdirs();
            }
            OutputStream output = new FileOutputStream(folder + fileName);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            ((HttpURLConnection) connection).disconnect();

            return folder + fileName;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return "Download Failed";
    }

    @Override
    protected void onPostExecute(String message) {
        if (onSuccessListener != null) {
            if (message.equals("Download Failed")){
                onSuccessListener.onError(incomingURI);
            }
            else {
                onSuccessListener.onSuccess(message);
            }
        }

    }
}
