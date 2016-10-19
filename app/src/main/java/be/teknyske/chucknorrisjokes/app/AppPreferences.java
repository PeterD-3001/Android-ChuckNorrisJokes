package be.teknyske.chucknorrisjokes.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cerseilannister on 17/10/16.
 */

public class AppPreferences
{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Context context;

    // Constructor
    public AppPreferences(Context context)
        {
        this.context = context;
        preferences = this.context.getSharedPreferences(AppController.class.getName(), Context.MODE_PRIVATE);
        editor = preferences.edit();
        }

    public boolean isFirstTime()
        {
        return preferences.getBoolean("firsttime", true);
        }

    public void setFirstTime(boolean first)
        {
        if (first)
            {
            editor.putBoolean("firsttime", false);
            editor.commit();
            }
        }

    public String getUserName()
        {
        return preferences.getString("userName", "");
        }

    public void setUserName(String userName)
        {
        editor.putString("userName", userName);
        editor.commit();
        }
}
