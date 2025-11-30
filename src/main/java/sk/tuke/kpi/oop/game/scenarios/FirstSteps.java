package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {

    private Ripley ripley;
    private Energy energy;
    private MovableController controller;
    private Ammo ammo;
    private Hammer hammer;
    private Wrench wrench;
    private KeeperController keeperContrl;
    private FireExtinguisher fireExting;

    @Override
    public void sceneInitialized(Scene scene) {
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);

        controller = new MovableController(ripley);
        scene.getInput().registerListener(controller);

        energy = new Energy();
        scene.addActor(energy, 200, 100);

        energyUse();

        ammo = new Ammo();
        scene.addActor(ammo, 100, 150);
        ammoUse();

        hammer = new Hammer();
        fireExting = new FireExtinguisher();
        wrench = new Wrench();

        scene.addActor(hammer, -23, 102);
        scene.addActor(fireExting, 29, 50);
        scene.addActor(wrench, 166, 99);


        scene.getGame().pushActorContainer(ripley.getBackpack());
        ripley.getBackpack().shift();

        keeperContrl = new KeeperController(ripley);
        scene.getInput().registerListener(keeperContrl);
    }

    private void energyUse() {
        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);
    }

    private void ammoUse() {
        new When<>(
            () -> ripley.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(ripley))
        ).scheduleFor(ripley);
    }

    @Override
    public void sceneUpdating(Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " + ripley.getHealth().getValue(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " + ripley.getAmmo(), 320, yTextPos);
    }
}
