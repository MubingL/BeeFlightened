package teamfortytwo.asteroids;

import android.content.res.Resources;

/**
 * Created by BrandonWebster on 4/28/15.
 */
public class Bullet extends Entity {
    public Bullet(Resources res, Collisions collisions, GameView view, int team, Vector pos, int size) {
        super(collisions, view, pos, size);

        if(team == 0)
            image = res.getDrawable(R.drawable.sting); //Set the image
        else
            image = res.getDrawable(R.drawable.butterfly_bullet); //Set the image
        setBounds(pos.getX(), pos.getY(), pos.getX() + size, pos.getY() + size); //initializes the image onto the screen, android has 0, 0 as the top left corner

        if(team == 0)
            speed = GameScreen.screenHeight / 35;
        else
            speed = - GameScreen.screenHeight / 35;

        this.team = team;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public int addPos(float dx, float dy){
        int check = super.addPos(dx, dy);

        if(check == Collisions.off_y){
            destroy();
        }

        return check;
    }
}
