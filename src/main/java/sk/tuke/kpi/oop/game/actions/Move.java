package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {

    private Direction direction;
    private float duration;
    private boolean started;
    private boolean isDone;
    private A actor;

    public Move(Direction direction, float duration) {
        this.duration = duration;
        this.direction = direction;
        started = false;
        isDone = false;
    }
    public Move(Direction direction) {
        this.direction = direction;
        duration = 0;
        started = false;
        isDone = false;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public @Nullable A getActor() {
        return actor;
    }

    @Override
    public void setActor(A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void reset() {
        isDone = false;
        started = false;
    }

    public void stop() {
        isDone = true;
        if (actor != null) actor.stoppedMoving();
    }

    @Override
    public void execute(float deltaTime) {
        if (actor == null || isDone) return;

        if (!started) {
            actor.startedMoving(direction);
            started = true;
        }

        if (duration == 0) {stop();return;}

        int oldX = actor.getPosX();
        int oldY = actor.getPosY();
        int newX = oldX + direction.getDx() * actor.getSpeed();
        int newY = oldY + direction.getDy() * actor.getSpeed();

        actor.setPosition(newX, newY);

        boolean wallCollision = false;
        if (actor.getScene() != null) {
            wallCollision = actor.getScene().getMap().intersectsWithWall(actor);

            if (wallCollision) {
                actor.setPosition(oldX, oldY);
                actor.collidedWithWall();
            }
        }

        if (duration > 0) {
            duration -= deltaTime;
            if (duration <= 0) {stop();}
        }
    }
}
