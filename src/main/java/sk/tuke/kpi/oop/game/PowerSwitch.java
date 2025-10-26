package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {
    private Switchable switchable;

    public PowerSwitch(Switchable switchable) {
        Animation switchAnimation = new Animation("sprites/switch.png", 16, 16);
        this.switchable = switchable;

        setAnimation(switchAnimation);
    }

    public Switchable getDevice() {return this.switchable;}

    public void switchOn() {
        if (switchable == null) return;

        this.switchable.turnOn();
        getAnimation().setTint(Color.WHITE);
    }

    public void switchOff() {
        if (switchable == null) return;

        this.switchable.turnOff();
        getAnimation().setTint(Color.GRAY);
    }
}
