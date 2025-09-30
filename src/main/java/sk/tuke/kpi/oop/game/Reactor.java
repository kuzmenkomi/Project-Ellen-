package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation NormalAnimation;
    private Animation HotAnimation;
    private Animation BrokenAnimation;

    public Reactor() {
        temperature = 0;
        damage = 0;
        NormalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(NormalAnimation);

        HotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.5f, Animation.PlayMode.LOOP_PINGPONG);

        BrokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

    }

    public int getDamage() {
        return damage;
    }

    public void increaseTemperature(int increment) {
        if (increment < 0) {
            return;
        }

        temperature += increment;
        damage = temperature / 60;

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0) {
            return;
        }
        temperature -= decrement;

        updateAnimation();
    }

    private void updateAnimation() {
        if(temperature > 4000) {
            setAnimation(HotAnimation);
        }else {
            setAnimation(NormalAnimation);
        }

        if(damage >= 100) {
            setAnimation(BrokenAnimation);
        }

    }

    public int getTemperature() {
        return temperature;
    }

}
