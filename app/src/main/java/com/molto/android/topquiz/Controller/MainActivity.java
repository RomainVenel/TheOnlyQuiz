package com.molto.android.topquiz.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.molto.android.topquiz.R;
import com.molto.android.topquiz.Model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private ImageButton mPlayButton;
    private Button mScoreButton;
    private User mUser;
    private MediaPlayer player;

    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            editor.putInt("lastscore", score);
            editor.apply();

            greetUser();

            player= MediaPlayer.create(MainActivity.this,R.raw.home);

            player.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        player= MediaPlayer.create(MainActivity.this,R.raw.home);

        player.start();

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (ImageButton) findViewById(R.id.activity_main_play_btn);
        mPlayButton.setEnabled(false);
        mScoreButton = (Button) findViewById(R.id.activity_main_score_btn);
        mUser = new User();

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/home.TTF");

        mGreetingText.setTypeface(custom_font);

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mNameInput.getText().toString();
                mUser.setFirstName(firstName);
                Toast.makeText(MainActivity.this, "Bienvenue " + firstName + "!", Toast.LENGTH_SHORT).show();

                editor.putString("firstname", firstName);
                editor.apply();

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
                player.stop();
            }
        });

        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreActivity = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(scoreActivity);
            }
        });
    }

    private void greetUser() {

        final SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);

        String firstName = prefs.getString("firstname", null);
        Integer lastScore = prefs.getInt("lastscore", 0);

        if (null != firstName) {

            String fulltext = "Welcome back, " + firstName

                    + "!\nYour last score was " + lastScore

                    + ", will you do better this time?";

            mGreetingText.setText(fulltext);

            mNameInput.setText(firstName);

            mNameInput.setSelection(firstName.length());

            mPlayButton.setEnabled(true);

        }

    }

}
