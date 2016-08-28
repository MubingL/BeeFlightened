package teamfortytwo.asteroids;

import android.content.res.Resources;

/**
 * Created by BrandonWebster on 4/19/15.
 */
public class Caterpillar extends Entity{

    private int timer = 0, shootTimer = 0;
    private static int morphTime;
    private boolean hasMorphed = false;

    private int oldX; //used for determining the center of the sinusoidal movement
    private Resources res;

    public Caterpillar(Resources res, Collisions collisions, GameView view, Vector pos, int size) {
        /*
        call the super constructor
        establish res, needed for reassigning the image later
        setup the image and bounds

        set the speed, dependent on the height of the screen
        set the morphTime, in seconds, until it changes to a butterfly
        set the team to be 1, which is the enemy team
         */

        super(collisions, view, pos, size);
        this.res = res;
        image = res.getDrawable(R.drawable.caterpillar_small); //Set the image to be the file b_small.png
        setBounds(pos.getX(), pos.getY(), pos.getX() + size, pos.getY() + size); //initializes the image onto the screen, android has 0, 0 as the top left corner

        speed = - GameScreen.screenHeight / 200;
        morphTime = 3 + GameView.random.nextInt(5);

        team = 1;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void update(int frame) {
        /*
        call the super update

        if hasMorphed, move sinusoidally
        else, if the frame is 0, increment the timer

        increment the timer if a second has passed.
        if the timer == morphTime, call morph
         */

        super.update(frame);


        if (hasMorphed) {
            addPos((int) (10 * Math.sin(Math.toRadians(pos.getY()))), 0);
            if(frame == 0){
                shootTimer += 1;
                if(shootTimer == 8){
                    view.shoot(new Bullet(res, collisions, view, team, new Vector(pos.getX() - 3 * size / 4, pos.getY()), size / 2));
                    shootTimer = 0;
                }
            }

        }else if(frame == 0){
            timer++;
            if(timer == morphTime){
                morph(res);
            }
        }

    }


    @Override
    public int addPos(float dx, float dy){
        /*
        if the entity has reached the top of the screen, destroy it
         */
        int check = super.addPos(dx, dy);

        if(check == Collisions.off_y){
            destroy();
        }

        return check;
    }

    public void morph(Resources res){
        /*
        change the image to that of the butterfly, set its bounds again
        slow the speed down

        set the oldX and set hasMorphed to true
         */
        image = res.getDrawable(R.drawable.butterfly);
        setBounds(pos.getX(), pos.getY(), pos.getX() + size, pos.getY() + size);
        speed /= 2;

        oldX = pos.getX();
        hasMorphed = true;
    }

}
