package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(Input.Key key) {
        switch (key) {
            case ENTER: {
                new Take<Keeper>().scheduleFor(keeper);
                break;
            }
            case BACKSPACE: {
                new Drop<Keeper>().scheduleFor(keeper);
                break;
            }
            case S: {
                new Shift<Keeper>().scheduleFor(keeper);
                break;
            }
            default: {
                break;
            }
        }
    }
}
