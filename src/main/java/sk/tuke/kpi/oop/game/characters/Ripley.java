package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.graphics.*;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Ripley extends AbstractActor implements Movable {
    private int speed;
    private Animation ripleyAnim;

    public Ripley() {
        super("Ellen");
        speed = 2;
        ripleyAnim = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        ripleyAnim.stop();
        setAnimation(ripleyAnim);
    }

    public int getSpeed() {
        return this.speed;
    }

    public void startedMoving(Direction direction) {
        ripleyAnim.setRotation(direction.getAngle());
        ripleyAnim.play();
        setAnimation(ripleyAnim);
    }

    public void stoppedMoving() {
        ripleyAnim.stop();
        setAnimation(ripleyAnim);
    }
}
