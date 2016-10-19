package be.teknyske.chucknorrisjokes.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by cerseilannister on 17/10/16.
 */

public class ConnectionDetector
{
    private Context context;


    public ConnectionDetector(Context context)
        {
        this.context = context;
        }

    public boolean isConnected()
        {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        // TODO: replace
        return (activeNetworkInfo != null) && (activeNetworkInfo.isConnected()) ;
        }
}
