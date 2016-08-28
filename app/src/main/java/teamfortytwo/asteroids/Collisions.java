package teamfortytwo.asteroids;

import java.util.ArrayList;

/**
 * Created by BrandonWebster on 4/27/15.
 */
public class Collisions {

    static final int off_x = 1, off_y = 2, colliding = 3;
    int screenWidth, screenHeight;

    GameView view;
    ArrayList<Entity> entities;

    public Collisions(GameView view){
        this.view = view;

        screenHeight = GameScreen.screenHeight;
        screenWidth = GameScreen.screenWidth;
    }

    public int check(Entity entity){
        if(view.getEntities()!=null) {
            int x = entity.getPos().getX(), y = entity.getPos().getY();
            entities = view.getEntities();
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getBounds().intersect(entity.getBounds()) && entities.get(i).getTeam() != entity.getTeam()) {
                    return colliding;
                }
            }
            if (x < 0 || x > screenWidth - entity.getSize()) {
                return off_x;
            }
            if (y < -entity.getSize() || y > screenHeight + entity.getSize()) {
                return off_y;
            }
        }



        return 0;
    }

}
