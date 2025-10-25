package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::smartCooler)).scheduleFor(this);
    }

    private void smartCooler() {
        Reactor reactor = getReactor();
        if (reactor == null) return;

        int temperature = reactor.getTemperature();

        if (temperature >= 2500) {
            turnOn();
        } else if (temperature <= 1500) {
            turnOff();
        }
    }
}
