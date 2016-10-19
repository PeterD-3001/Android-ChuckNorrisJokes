package be.teknyske.chucknorrisjokes.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import be.teknyske.chucknorrisjokes.R;
import be.teknyske.chucknorrisjokes.app.AppController;
import be.teknyske.chucknorrisjokes.app.AppPreferences;
import be.teknyske.chucknorrisjokes.app.ConnectionDetector;

import static be.teknyske.chucknorrisjokes.R.id.joke;
import static be.teknyske.chucknorrisjokes.R.id.text;
import static be.teknyske.chucknorrisjokes.R.id.textView;
import static com.android.volley.Request.Method.GET;

public class JokeActivity extends AppCompatActivity
{
    private AppPreferences preferences;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        preferences = new AppPreferences(this);
        if (preferences.isFirstTime())
            {
            Toast.makeText(this, "Hi " + preferences.getUserName(), Toast.LENGTH_LONG).show();
            preferences.setFirstTime(true);
            }
        else
            {
            Toast.makeText(this, "Welcome back " + preferences.getUserName() + "!", Toast.LENGTH_LONG).show();
            }

        textView = (TextView) findViewById(R.id.joke);
        ConnectionDetector detector = new ConnectionDetector(this);
        if (detector.isConnected())
            {
            getOnlineJoke();
            }
        else
            {
            readFromFile();
            }
        }


    public void volgendeMop(View view)
        {
        ConnectionDetector detector = new ConnectionDetector(this);
        if (detector.isConnected())
            {
            getOnlineJoke();
            }
        else
            {
            readFromFile();
            }
        }

    private void getOnlineJoke()
        {
            textView.setText("Loading....");
            JsonObjectRequest request = new JsonObjectRequest
                    (GET,
                    "http://api.icndb.com/jokes/random?exclude=[EXPLICIT]",
                    new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                                {
                                String joke;
                                try
                                    {
                                    joke = response.getJSONObject("value").getString("joke");
                                    }
                                catch (JSONException e)
                                    {
                                    Toast.makeText(JokeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e("ChuckNorris", "Error during online joke fetching !", e);
                                    joke = "Error !";
                                    //e.printStackTrace();
                                    }
                                textView.setText(joke);
                                writeToFile(joke);
                                }

                        },
                    new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                                {
                                Toast.makeText(JokeActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("ChuckNorris", "Error : " + error.getMessage());
                                // error.printStackTrace();
                                }
                        }

                    );
            AppController.getInstance().addToRequestQueue(request);
        }

    private void writeToFile(String joke)
        {
            try
                {
                OutputStreamWriter out = new OutputStreamWriter(openFileOutput("joke.json", Context.MODE_PRIVATE));
                out.write(joke);
                out.close();
                }
            catch (IOException e)
                {
                e.printStackTrace();
                Log.e("ChuckNorris","Error saving joke to storage !",e);
                }

        }

    private void readFromFile()
        {
        StringBuilder builder = new StringBuilder();
        try
            {
            InputStreamReader in = new InputStreamReader(openFileInput("joke.json"));
            BufferedReader buf = new BufferedReader(in);
            String input = "";
            while ( (input = buf.readLine()) != null )
                {
                 builder.append(input);
                }
            in.close();
            }
        catch (IOException e)
            {
            e.printStackTrace();
            }

            // TODO Replace
        textView.setText(builder.toString());
        }

}
