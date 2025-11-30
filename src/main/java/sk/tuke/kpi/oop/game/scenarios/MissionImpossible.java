package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;

public class MissionImpossible implements SceneListener {

    private Disposable moveControl;
    private Disposable keeperControl;
    private Ripley ripley;

    @Override
    public void sceneInitialized(Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);

        moveControl = scene.getInput().registerListener(new MovableController(ripley));
        keeperControl = scene.getInput().registerListener(new KeeperController(ripley));

        energyUse(scene);

        FireExtinguisher fireExtinguisher= new FireExtinguisher();
        ripley.getBackpack().add(fireExtinguisher);
        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ripley.decreaseEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> moveControl.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperControl.dispose());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ripley.stopDecreasingEnergy().dispose());
    }

    private void energyUse(Scene scene) {
        new Loop<>(
            new Invoke<>(() -> {
                for (Actor actor : scene.getActors()) {
                    if (actor instanceof Energy && ripley.intersects(actor)) {
                        new Use<>((Energy) actor).scheduleFor(ripley);
                        break;
                    }
                }
            })
        ).scheduleFor(ripley);
    }

    @Override
    public void sceneUpdating(Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }

    public static class Factory implements ActorFactory {
        @Override
        public Actor create(String type, String name) {
            assert name != null;

            switch (name) {
                case "ellen":       return new Ripley();
                case "energy":      return new Energy();
                case "door":
                    Door.Orientation orientation = Door.Orientation.VERTICAL;
                    if (type != null && "horizontal".equals(type)) orientation = Door.Orientation.HORIZONTAL;
                    return new Door(name, orientation);
                case "access card": return new AccessCard();
                case "locker":      return new Locker();
                case "ventilator":  return new Ventilator();
                default:            return null;
            }
        }
    }
}
