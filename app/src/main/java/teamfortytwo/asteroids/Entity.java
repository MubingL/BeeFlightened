package teamfortytwo.asteroids;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public abstract class Entity {
    protected Drawable image; //This is the image drawn to the screen
    protected Vector pos; //The position of the Bee on the screen
    protected int size; //this will be added to position in setBounds
    protected int team;
    protected int speed;
    protected Collisions collisions;
    protected GameView view;
    protected Rect bounds;

    public Entity(Collisions collisions, GameView view, Vector pos, int size) {
        this.size = size;
        this.view = view;
        this.pos = pos; //sets the position vector
        this.collisions = collisions;
        this.bounds = new Rect(pos.getX(), pos.getY(), pos.getX() + size, pos.getY() + size);
        System.gc();
    }

    //Get Functions
    public Vector getPos() {
        return pos;
    }

    public Drawable getImage() {
        return image;
    }

    public int getSize() {
        return size;
    }

    public Rect getBounds() {
        return bounds;
    }

    public int getTeam() {
        return team;
    }

    public abstract int getType();

    public void update(int frames) {
        addPos(0, speed); //adds the speed to position to move the entity
    }

    //Set Functions
     /*
    Set the position of the entity
    check the position with collisions to make sure it is not offscreen or colliding
    if it is colliding, destroy the entity by calling destroy()
    if it is offscreen after the position was added, move it back to where it was
    return check for use with child classes
    */
    public int addPos(float dx, float dy) {
        pos.setX((int) dx + pos.getX());
        pos.setY((int) dy + pos.getY());
        int check = collisions.check(this);

        if (check == Collisions.off_x)
            pos.setX(pos.getX() - (int) dx);
        if (check == Collisions.off_y) {
            pos.setY(pos.getY() - (int) dy);
        }
        if (check == Collisions.colliding) {
            destroy();
        }

        return check;
    }

    /*
            initializes the image onto the screen
            android has 0, 0 as the top left corner

            sets the bounding rectangle to be the same as where the image is
            the bounding rectangle is used in collision detection
             */
    protected void setBounds(int left, int top, int right, int bottom) {

        image.setBounds(left, top, right, bottom);
        bounds = image.getBounds();
    }

    /*
        updates the position of the image on the screen
        then draw the image onto the canvas provided
         */
    public void draw(Canvas canvas) {
        setBounds(pos.getX(), pos.getY(), pos.getX() + size, pos.getY() + size); //
        image.draw(canvas);
    }

    /*
            calls destroy entity in view, which should remove the entity from its array,
            or endgame if it is the player
             */
    public void destroy() {
        view.destroyEntity(this);
    }
}
