package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Direction;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MovableController implements KeyboardListener {
    private Move<Movable> move;
    private Set<Direction> pressed_keys = new HashSet<>();
    private Movable movable;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );

    public MovableController(Movable movable) {
        this.movable = movable;
        move = null;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressed_keys.add(keyDirectionMap.get(key));
            updateMove();
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressed_keys.remove(keyDirectionMap.get(key));
            updateMove();
        }
    }

    private void updateMove() {
        Direction finalDirection = Direction.NONE;

        for (Direction d : pressed_keys) {
            if (d != null) {
                finalDirection = finalDirection.combine(d);
            }
        }

        if (move != null && finalDirection != Direction.NONE && finalDirection != move.getDirection()) {
            move.stop();
            move = null;
        }

        if (finalDirection != Direction.NONE && (move == null)) {
            move = new Move<>(finalDirection, Float.MAX_VALUE);
            move.scheduleFor(movable);
        }
    }

    private void helpDirectionForMove(Direction helpDirection) {
        if (move != null) move.stop();
        move = new Move<>(helpDirection, Float.MAX_VALUE);
        move.scheduleFor(movable);
    }

    private void keyIsEmpty() {
        if (move != null && pressed_keys.isEmpty()) {
            move.stop();
            move = null;
        }
    }
}
