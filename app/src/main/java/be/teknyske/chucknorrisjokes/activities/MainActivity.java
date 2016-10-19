package be.teknyske.chucknorrisjokes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import be.teknyske.chucknorrisjokes.R;
import be.teknyske.chucknorrisjokes.app.AppPreferences;

public class MainActivity extends AppCompatActivity
{

    private AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new AppPreferences(this);
        if (!preferences.isFirstTime())
            {
            // TODO go to next activity
            nextActivity();
            }
        }

    public void saveUserName (View view)
        {

        EditText editText = (EditText) findViewById(R.id.edit_text);
        preferences.setUserName(editText.getText().toString().trim());
        // TODO goto next activity
        nextActivity();
        }

    public void nextActivity()
        {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        }
}