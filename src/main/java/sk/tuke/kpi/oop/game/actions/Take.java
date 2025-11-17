package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take<A extends Keeper> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {

        if (getActor() == null || getActor().getScene() == null || isDone()) {
            setDone(true);
            return;
        }

        A keeper = getActor();
        Scene scene = keeper.getScene();
        Collectible item = null;

        for (Actor actor : scene.getActors()) {
            if (actor instanceof Collectible && keeper.intersects(actor)) {
                item = (Collectible) actor;
                break;
            }
        }

        if (item != null) {
            try {
                keeper.getBackpack().add(item);
                scene.removeActor(item);
            } catch (IllegalStateException e) {
                scene.getOverlay().drawText(e.getMessage(), 0, 0).showFor(2);
            }
        }
        setDone(true);
    }
}
