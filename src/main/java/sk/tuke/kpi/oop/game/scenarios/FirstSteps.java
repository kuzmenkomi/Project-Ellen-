package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;

public class FirstSteps implements SceneListener {
    @Override
    public void sceneInitialized(Scene scene) {
        Ripley ripley = new Ripley();
        scene.addActor(ripley, 0, 0);

        MovableController controller = new MovableController(ripley);
        scene.getInput().registerListener(controller);
    }
}
