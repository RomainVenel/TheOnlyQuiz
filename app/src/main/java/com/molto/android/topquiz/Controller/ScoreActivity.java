package com.molto.android.topquiz.Controller;

        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.TextView;

        import com.molto.android.topquiz.R;


/**
 * Created by Romain on 07/06/2018.
 */

public class ScoreActivity extends AppCompatActivity {

    private TextView mbestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        final SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);

        Integer bestScore = prefs.getInt("bestscore",
                0);

        mbestScore = (TextView) findViewById(R.id.activity_score_txt);

        mbestScore.setText(String.valueOf(bestScore));
    }

}
