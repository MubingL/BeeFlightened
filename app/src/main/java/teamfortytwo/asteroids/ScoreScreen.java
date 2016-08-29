package teamfortytwo.asteroids;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScoreScreen extends Activity implements OnClickListener {
    private static final String THE_SCORE = "theScore";

    private ImageButton backButton;
    private TextView scoreboard;
    private TextView myscore;
    private int theScore;
    private int[] HighScores = new int[5];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);

        backButton = (ImageButton) findViewById(R.id.backbutton);
        backButton.setOnClickListener(this);

        scoreboard = (TextView) findViewById(R.id.scorelist);
        myscore = (TextView) findViewById(R.id.myscore);
        for (int i = 0; i < 5; i++) {
            SharedPreferences prefs = this.getSharedPreferences(getResources().getString(R.string.high_scores) + i, Context.MODE_PRIVATE);
            HighScores[i] = prefs.getInt(getResources().getString(R.string.high_scores) + i, 0); //loads previous high scores 1-5
        }

        Bundle extras = getIntent().getExtras(); // gets bundle package
        if (extras != null) {
            theScore = extras.getInt(THE_SCORE); //gets the score from bundle
            String scoreText = String.format(getResources().getString(R.string.survive_text), theScore);
            myscore.setText(scoreText);
        }

        for (int i = 0; i < 5; i++) {
            if (theScore >= HighScores[i]) {        //Checks if score fits into highscores
                HighScores[i] = theScore;
                int temp;
                for (int j = i + 1; j < 4; j++) {
                    temp = HighScores[j + 1];       //shifts scores down
                    HighScores[j + 1] = HighScores[j];
                }
                break;
            }
        }

        for (int i = 0; i < 5; i++) {
            SharedPreferences prefs = this.getSharedPreferences(getResources().getString(R.string.high_scores) + i, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(getResources().getString(R.string.high_scores) + i, HighScores[i]); //saves new set of scores
            editor.apply();
        }

        //displays new scores in the text box
        String highScoreBoardMsg = String.format(getResources().getString(R.string.high_score_board), HighScores[0], HighScores[1], HighScores[2], HighScores[3], HighScores[4]);
        scoreboard.setText(highScoreBoardMsg);
        System.gc();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbutton: {
                finish();
                break;
            }
            default:
                break;
        }
    }
}
