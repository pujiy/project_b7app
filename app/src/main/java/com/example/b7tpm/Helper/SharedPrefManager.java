package com.example.b7tpm.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.b7tpm.Model.User;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "b7sharedpref";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_USERNAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_NIK = "keyusernik";
    private static final String KEY_USER_ISUSER = "keyuserisuser";
    private static final String KEY_USER_ISSPV = "keyuserisspv";
    private static final String KEY_USER_ISADMIN = "keyuserisadmin";
    private static final String KEY_USER_ISVERIFIED = "keyuserisverified";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_USERNAME, user.getUsername());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_NIK, user.getNik());
        editor.putInt(KEY_USER_ISUSER, user.getIsuser());
        editor.putInt(KEY_USER_ISSPV, user.getIsspv());
        editor.putInt(KEY_USER_ISADMIN, user.getIsadmin());
        editor.putInt(KEY_USER_ISVERIFIED, user.getIsverified());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL, null) !=null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_USERNAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_NIK, null),
                sharedPreferences.getInt(KEY_USER_ISUSER, 0),
                sharedPreferences.getInt(KEY_USER_ISSPV, 0),
                sharedPreferences.getInt(KEY_USER_ISADMIN, 0),
                sharedPreferences.getInt(KEY_USER_ISVERIFIED, 0)

        );
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
