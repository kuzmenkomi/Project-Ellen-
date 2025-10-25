package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private boolean powered;

    public Computer() {
        Animation computerAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(computerAnimation);
    }

    public int add(int a, int b) {
        if (!powered) {
            return 0;
        }
        return a + b;
    }

    public float add(float a, float b) {
        if (!powered) {
            return 0;
        }
        return a + b;
    }

    public float sub(float a, float b) {
        if (!powered) {
            return 0;
        }
        return a - b;
    }

    public int sub(int a, int b) {
        if (!powered) {
            return 0;
        }
        return a - b;
    }

    @Override
    public void setPowered(boolean powered) {
        this.powered = powered;
        this.updateAnimation();

    }

    private void updateAnimation(){
        if (powered) {
            this.getAnimation().play();
        }
        this.getAnimation().pause();
    }
}
