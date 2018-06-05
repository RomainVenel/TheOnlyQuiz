package com.molto.android.topquiz.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.molto.android.topquiz.R;
import com.molto.android.topquiz.Model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    private MediaPlayer player;

    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            preferences.edit().putInt("bestscore", score).apply();

            greetUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player= MediaPlayer.create(MainActivity.this,R.raw.accueil);

        player.start();

        LinearLayout constraintLayout = (LinearLayout) findViewById(R.id.root_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);

        animationDrawable.setExitFadeDuration(4000);

        animationDrawable.start();

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mPlayButton.setEnabled(false);
        mUser = new User();

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

                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putString("firstname", mUser.getFirstName()).apply();

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void greetUser() {


        String firstname = getPreferences(MODE_PRIVATE).getString("firstname", null);
        Integer bestScore = getPreferences(MODE_PRIVATE).getInt("bestscore", 0);

        if (null != firstname) {

            String fulltext = "Welcome back, " + firstname

                    + "!\nYour last score was " + bestScore

                    + ", will you do better this time?";

            mGreetingText.setText(fulltext);

            mNameInput.setText(firstname);

            mNameInput.setSelection(firstname.length());

            mPlayButton.setEnabled(true);

        }

    }
}
