package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<A extends Keeper> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null || getActor().getScene() == null || isDone()) {
            setDone(true);
            return;
        }

        A keeper = getActor();
        Scene scene = keeper.getScene();

        Collectible lastItem = keeper.getBackpack().peek();
        if (lastItem == null) {setDone(true);return;}

        int posX = (keeper.getWidth() - lastItem.getWidth()) / 2;
        int posY = (keeper.getHeight() - lastItem.getHeight()) / 2;

        scene.addActor(lastItem, keeper.getPosX() + posX, keeper.getPosY() + posY);
        keeper.getBackpack().remove(lastItem);
        setDone(true);
    }
}
