package rd.fordewindcompanytesttask.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rd.fordewindcompanytesttask.pojo.User;

import static rd.fordewindcompanytesttask.dao.DBHelper.*;

public class DataListHandler {
    private Context context;
    private SQLiteDatabase personListDB;
    private String tableName;
    private DBHelper dbHelper;

    public DataListHandler(Context context, String tableName) {
        this.context = context;
        this.tableName = tableName;
        dbHelper = new DBHelper(context);
        dbHelper.setTableName(tableName);
        personListDB = dbHelper.getWritableDatabase();
    }

    public long insert(User user) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LOGIN, user.getLogin());
        cv.put(COLUMN_USER_ID, user.getId());
        cv.put(COLUMN_AVATAR_URL, user.getAvatarUrl());
        cv.put(COLUMN_FOLLOWERS_URL, user.getFollowersUrl());
        cv.put(COLUMN_HTML_URL,user.getHtmlUrl());
        cv.put(COLUMN_COMMENT, user.getComment());
        cv.put(COLUMN_FAVORITE, user.getFavorite());
        return personListDB.insert(tableName, null, cv);
    }

    public void insertAll(List<User> list) {
        personListDB.beginTransaction();
        try {
            for (User user: list){
                insert(user);
            }
            personListDB.setTransactionSuccessful();
        }
        finally {
            personListDB.endTransaction();
        }
    }

    public int deleteAll() {
        return personListDB.delete(tableName, null, null);
    }

    public void delete(User user) {
        personListDB.delete(tableName, COLUMN_LOGIN + " = ?", new String[] { String.valueOf(user.getLogin()) });
    }

    public ArrayList<User> getFavoriteList() {
        ArrayList<User> arr = new ArrayList<>();

        Cursor mCursor = personListDB.rawQuery("SELECT * FROM " + tableName, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                User user = new User();
                String login = mCursor.getString(1);
                int userID = mCursor.getInt(2);
                String avatarURL = mCursor.getString(3);
                String followersURL = mCursor.getString(4);
                String htmlURL = mCursor.getString(5);
                String comment = mCursor.getString(6);
                int favorite = mCursor.getInt(7);

                user.setLogin(login);
                user.setId(userID);
                user.setAvatarUrl(avatarURL);
                user.setFollowersUrl(followersURL);
                user.setHtmlUrl(htmlURL);
                user.setComment(comment);
                user.setFavorite(favorite);

                arr.add(user);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;

    }

    public void close() {
        personListDB.close();
    }
}
