package btest.bapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import btest.bapplication.pojo.Link;


public class BMainActivity extends AppCompatActivity {

    final static String TAG = "BMainActivity_TAG";
    public static final String COLUMN_LINK = "LINK";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_STATUS = "STATUS";

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

                            }else {
                                link.setStatus(Link.STATUS_LOADED);

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


//    public void delete(Link item) {
//        db = dbHelper.getWritableDatabase();
//        int cnt = db.delete(dbHelper.getTableName(), selection, selectionArgs);
//        getContext().getContentResolver().notifyChange(uri, null);
//    }
//
//
//    public void update(Link item) {
//        db = dbHelper.getWritableDatabase();
//        int cnt = db.update(dbHelper.getTableName(), values, selection, selectionArgs);
//        getContext().getContentResolver().notifyChange(uri, null);
//    }
}
