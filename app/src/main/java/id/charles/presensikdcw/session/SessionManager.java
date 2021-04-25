package id.charles.presensikdcw.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SessionManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID = "0";
    public static final String NAME = "Sname";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void CreatLoginSession(String name, String id) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(NAME, name);
        editor.putString(ID, id);
        editor.commit();
    }

    public HashMap<String, String> getDataCommit() {
        HashMap<String, String> data = new HashMap<>();
        data.put(NAME, sharedPreferences.getString(NAME, null));
        data.put(ID, sharedPreferences.getString(ID, null));
        return data;
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void logOut() {
        editor.clear();
        editor.commit();
    }

}
