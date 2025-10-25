package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light {
    private Disposable brokenEffect;
    private boolean repaired;

    public DefectiveLight() {
        super();
        this.repaired = false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.brokenEffect = new Loop<>(new Invoke<Actor>(this::changeLight)).scheduleFor(this);
    }

    public void breakLight() {
        this.brokenEffect = new Loop<>(new Invoke<>(this::changeLight)).scheduleFor(this);
    }

    public void changeLight() {
        int i = (int) (Math.random() * 20);
        if (i == 1) {
            super.toggle();
        }
    }

    public boolean repair() {
        if (brokenEffect == null || repaired) return false;

        repaired = true;
        brokenEffect.dispose();

        this.brokenEffect = new ActionSequence<>(new Wait<>(10), new Loop<>(new Invoke<>(this::toggle))).scheduleFor(this);
        return true;
    }
}
