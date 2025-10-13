package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {
    private Reactor reactor;
    private Animation ControllerAnimation;

    public Controller(Reactor reactor) {
        this.reactor = reactor;

        ControllerAnimation = new Animation("sprites/switch.png", 16, 16, 0.1f);
        setAnimation(ControllerAnimation);
    }

    public void toggle() {
        if (reactor != null) {
            if (reactor.isRunning()) {
                reactor.turnOff();
            } else {
                reactor.turnOn();
            }
        }
    }
}
