package com.molto.android.topquiz.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.molto.android.topquiz.Model.Question;
import com.molto.android.topquiz.Model.QuestionBank;
import com.molto.android.topquiz.R;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionText;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private QuestionBank mQuestionBank;
    private int mNbQuestions;
    private Question mCurrentQuestion;
    private TextView mScoreText;
    private int mScore;
    private boolean mEnableTouchEvents;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String firstname = getPreferences(MODE_PRIVATE).getString("firstname", null);

        mEnableTouchEvents = true;

        mNbQuestions = 4;
        mScore = 0;

        mQuestionText = (TextView) findViewById(R.id.activity_game_question_text);
        mScoreText = (TextView) findViewById(R.id.activity_game_score_text);
        mButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mButton4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        mQuestionBank = this.generateQuestions();

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        mButton1.setTag(0);
        mButton2.setTag(1);
        mButton3.setTag(2);
        mButton4.setTag(3);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
        this.displayScore(mScore);

    }

    public QuestionBank generateQuestions() {
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3));
    }

    private void displayQuestion(final Question question) {

        mQuestionText.setText(question.getQuestion());

        mButton1.setText(question.getChoiceList().get(0));

        mButton2.setText(question.getChoiceList().get(1));

        mButton3.setText(question.getChoiceList().get(2));

        mButton4.setText(question.getChoiceList().get(3));

    }

    private void displayScore(final int score) {

        mScoreText.setText(String.valueOf(score));
    }

    @Override
    public void onClick(View v) {

        mEnableTouchEvents = false;

        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            mScore = mScore + 1;
        }else{
            Toast.makeText(GameActivity.this, "False!", Toast.LENGTH_SHORT).show();
        }

        this.displayScore(mScore);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                if (--mNbQuestions == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

                    builder.setTitle("Well done!")
                            .setMessage("Your score is " + mScore)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            })
                            .create()
                            .show();

                }else{
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    GameActivity.this.displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long

    }

    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {

        return mEnableTouchEvents && super.dispatchTouchEvent(ev);

    }
}
