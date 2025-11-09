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
        this.isDone = false;
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
        actor.stoppedMoving();
    }

    @Override
    public void execute(float deltaTime) {
        if (actor == null || isDone) return;

        if (!started) {
            actor.startedMoving(direction);
            started = true;
        }

        actor.setPosition(
            actor.getPosX() + direction.getDx() * actor.getSpeed(),
            actor.getPosY() + direction.getDy() * actor.getSpeed()
        );

        if (duration > 0) {
            duration -= deltaTime;
            if (duration <= 0) stop();
        }
    }
}
