package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {this.keeper = keeper;}

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
            case U: {
                useNearby();
                break;
            }
            case B: {
                useBackpack();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void useNearby() {
        Usable<?> usable = null;
        for (Actor actor : keeper.getScene().getActors()) {
            if (actor instanceof Usable<?> && keeper.intersects(actor)) {
                usable = (Usable<?>) actor;
                break;
            }
        }
        if (usable != null) new Use<>(usable).scheduleForIntersectingWith(keeper);
    }

    private void useBackpack() {
        if (keeper.getBackpack().peek() instanceof Usable) {
            Use<?> use = new Use<>((Usable<?>) keeper.getBackpack().peek());
            use.scheduleForIntersectingWith(keeper);
        }
    }
}
