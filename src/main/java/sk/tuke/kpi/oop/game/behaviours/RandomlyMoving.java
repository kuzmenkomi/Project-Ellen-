package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable> {
    private final Random random = new Random();
    private final float moveTime = 1f;  // длительность движения
    private final float waitTime = 1.1f; // пауза

    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    Direction dir = getRandomDirection();
                    if (dir != Direction.NONE) {
                        actor.getAnimation().setRotation(dir.getAngle());
                        new Move<>(dir, moveTime).scheduleFor(actor);
                    }
                }),
                new Wait<>(waitTime)
            )
        ).scheduleFor(actor);
    }

    private Direction getRandomDirection() {
        Direction[] dirs = Direction.values();
        Direction dir = dirs[random.nextInt(dirs.length)];
        return (dir == Direction.NONE) ? Direction.NORTH : dir;
    }
}
