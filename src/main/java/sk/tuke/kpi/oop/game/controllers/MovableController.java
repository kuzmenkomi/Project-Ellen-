package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Set<Input.Key> pressed_keys;
    private Disposable dispo;
    private Move<Movable> move = null;
    private Input.Key firstK = null;
    private Input.Key secondK = null;

    private Map<Input.Key, Direction> directionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );

    public MovableController(Movable actor) {
        this.actor = actor;
        pressed_keys = new HashSet<>();
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (directionMap.containsKey(key)) {
            pressed_keys.add(key);

            if (firstK == null) firstK = key;
            else if (secondK == null) secondK = key;

            updateMove();
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (!directionMap.containsKey(key)) return;
        pressed_keys.remove(key);

        if (key == firstK) { firstK = secondK; secondK = null; }
        else if (key == secondK) secondK = null;

        if (firstK == null && !pressed_keys.isEmpty()) {
            for (Input.Key temp : pressed_keys) {
                if (firstK == null) firstK = temp;
                else if (secondK == null) secondK = temp;
            }
        }
        updateMove();
    }

    private void updateMove() {
        Direction direction = Direction.NONE;

        for (Input.Key key : pressed_keys) {
            direction = direction.combine(directionMap.get(key));
        }

        stopMove();
        if (direction == Direction.NONE) return;

        move = new Move<>(direction, Float.MAX_VALUE);
        dispo = move.scheduleFor(actor);
    }

    private void stopMove() {
        if (move == null) return;

        move.stop();
        if (dispo != null) {
            dispo.dispose();
            dispo = null;
        }
        move = null;
    }
}
