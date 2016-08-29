package teamfortytwo.asteroids;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MainScreen extends ActionBarActivity implements OnClickListener {
    private ImageButton startButton, scoreButton, tutButton;
    public GameScreen game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (ImageButton) findViewById(R.id.start);
        startButton.setOnClickListener(this);

        scoreButton = (ImageButton) findViewById(R.id.score);
        scoreButton.setOnClickListener(this);

        tutButton = (ImageButton) findViewById(R.id.tutorial);
        tutButton.setOnClickListener(this);

        System.gc();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start: { //If the button clicked was the start button
                startGame(); // Start the game screen
                break;
            }
            case R.id.score: {
                startScore();
                break;
            }
            case R.id.tutorial: {
                startTutorial();
                break;
            }
            default: {
                break;
            }
        }

    }

    public void startGame() {
        Intent gameActivity = new Intent(MainScreen.this, GameScreen.class); //To create an activity, you need to declare an intent with the parent activty, and the activity you want to create
        gameActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(gameActivity); //This is how you start an activity

    }

    public void startScore() {
        Intent scoreActivity = new Intent(MainScreen.this, ScoreScreen.class);
        scoreActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(scoreActivity);
    }

    public void startTutorial() {
        Intent tutActivity = new Intent(MainScreen.this, TutorialScreen.class);
        tutActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(tutActivity);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
