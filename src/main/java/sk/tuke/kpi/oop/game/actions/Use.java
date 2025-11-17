package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.gamelib.Actor;

public class Use<A extends Actor> extends AbstractAction<A> {
    private Usable<A> item;

    public Use(Usable<A> item) {this.item = item;}

    @Override
    public void execute(float deltaTime) {
        if (isDone()) return;

        item.useWith(getActor());
        setDone(true);
    }
}
