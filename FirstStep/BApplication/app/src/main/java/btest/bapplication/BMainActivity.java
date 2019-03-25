package btest.bapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import btest.bapplication.pojo.Link;


public class BMainActivity extends AppCompatActivity {

    final static String TAG = "BMainActivity_TAG";
    public static final String COLUMN_LINK = "LINK";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_STATUS = "STATUS";
    int REQUEST_WRITE_EXTERNAL_STORAGE=1;

    final Uri LINKS_URI = Uri.parse("content://atest.aapplication.LinksList/links");

    private TextView textView;
    private TextView textTimer;
    private ImageView imageView;
    private Link link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmain);

        textView = findViewById(R.id.text_view);
        textTimer = findViewById(R.id.text_timer);
        imageView = findViewById(R.id.image_view);



        Intent intent = getIntent();
        String from = getIntent().getStringExtra("from");
        if (from == null) {
            closeIfLaunchedNotFromA();
        }else {

            Cursor cursor = getContentResolver().query(LINKS_URI, null, null,
                    null, null);
            startManagingCursor(cursor);

            final String linkToPic = intent.getStringExtra("linkToPic");
            final int status = intent.getIntExtra("status", Link.STATUS_UNKNOWN);
            final long date = intent.getLongExtra("date", System.currentTimeMillis());

            link = new Link(linkToPic, date, status);

            if (from.equals("button")){
                Picasso.get().load(linkToPic).into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        link.setStatus(Link.STATUS_LOADED);
                        insert(link);
                    }
                    @Override
                    public void onError(Exception ex) {
                        link.setStatus(Link.STATUS_ERROR);
                        insert(link);
                        textView.setText(R.string.oops_cannot_load);
                    }
                });

            }else {
                if (from.equals("list")){
                    Picasso.get().load(linkToPic).into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (status == Link.STATUS_LOADED){

                                final String saveResult = saveImageToStorage(imageView, link);
                                if (saveResult.equals(getString(R.string.success))) {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            delete(link);
                                            Toast.makeText(BMainActivity.this, saveResult, Toast.LENGTH_LONG).show();
                                        }
                                    }, 15000);

                                }else {
                                    Toast.makeText(getApplicationContext(), saveResult, Toast.LENGTH_LONG).show();
                                }

                            }else {
                                link.setStatus(Link.STATUS_LOADED);
                                update(link);
                            }
                        }
                        @Override
                        public void onError(Exception ex) {
                            link.setStatus(Link.STATUS_ERROR);
                            textView.setText(R.string.oops_cannot_load);
                        }
                    });
                }

            }
        }

    }

    private String saveImageToStorage(ImageView imageView, Link link) {

        if (!checkWriteExternalPermission()){
            Log.d(TAG, getResources().getString(R.string.no_permission));

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIGDIG/test/B/");
        if (!dir.exists()) {dir.mkdirs();}

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File image = new File(dir.getAbsolutePath() + File.separator + link.getDateMills() + ".png");

        boolean success = false;


        try (FileOutputStream outStream = new FileOutputStream(image)){
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, image.getPath() + " Error   " + e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            return getString(R.string.success);
        } else {
            return getString(R.string.false_saving);
        }
    }

    private void closeIfLaunchedNotFromA() {
        textView.setText(R.string.after_10_sec);
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                String message = getString(R.string.time_left)
                        + millisUntilFinished / 1000;
                textTimer.setText(message);
            }
            public void onFinish() {
                finish();
            }
        }.start();
    }

    public void insert(Link item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LINK, item.getLink());
        cv.put(COLUMN_DATE, item.getDateMills());
        cv.put(COLUMN_STATUS, item.getStatus());
        Uri newUri = getContentResolver().insert(LINKS_URI, cv);
    }


    public void delete(Link item) {
        String [] arguments = new String[1];
        arguments[0] = item.getLink();
        String selectionclause = COLUMN_LINK +" = ?";
        getContentResolver().delete(LINKS_URI, selectionclause, arguments);
    }


    public void update(Link item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, item.getStatus());
        String [] arguments = new String[1];
        arguments[0] = item.getLink();
        String selectionclause = COLUMN_LINK +" = ?";
        getContentResolver().update(LINKS_URI, cv, selectionclause, arguments);

    }

    private boolean checkWriteExternalPermission()
    {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
