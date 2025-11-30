package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Bullet extends AbstractActor implements Movable, Fireable {
    private int speed = 4;
    private boolean removed = false;

    public Bullet() {
        Animation animation = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(animation);
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    private void checkCollisions() {
        if (removed || getScene() == null) return;

        if (getScene().getMap().intersectsWithWall(this)) {
            removeFromScene();
            return;
        }

        for (Actor actor : getScene().getActors()) {
            if (actor instanceof Alive && actor != this && this.intersects(actor)) {
                ((Alive) actor).getHealth().drain(15);
                removeFromScene();
                return;
            }
        }
    }

    private void removeFromScene() {
        if (!removed && getScene() != null) {
            removed = true;
            getScene().removeActor(this);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::checkCollisions)
        ).scheduleFor(this);
    }

    @Override
    public void stoppedMoving() {getAnimation().stop();}
    public void collidedWithWall() {removeFromScene();}
    @Override
    public int getSpeed() {return speed;}
}
