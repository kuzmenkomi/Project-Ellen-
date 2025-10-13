package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean running;
    private Animation NormalAnimation;
    private Animation HotAnimation;
    private Animation BrokenAnimation;
    private Animation OffAnimation;
    private Light ReactorLight;  // для привязанного света

    public Reactor() {
        temperature = 0;
        damage = 0;
        this.running = false; // По умолчанию реактор выключен

        NormalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        HotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        BrokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        OffAnimation = new Animation    ("sprites/reactor.png", 80, 80, 0.1f);

        setAnimation(OffAnimation);
    }

    public int getDamage() {
        return damage;
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !running || damage >= 100) return;

        temperature += increment;

        if (temperature >= 6000) {
            damage = 100;
            turnOff();
        } else if (temperature > 4000) {
            damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 25.0));
        } else if (temperature > 2000) {
            damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 50.0));
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0 || !running || damage >= 100) return;

        int effectiveDecrement = (damage >= 50) ? decrement / 2 : decrement;
        temperature = Math.max(temperature - effectiveDecrement, 0);

        updateAnimation();
    }

    private void updateAnimation() {
        Animation upperDamageAnimation = damage >= 66 ? BrokenAnimation : null;
        Animation damageAnimation = damage < 33 ? NormalAnimation : (upperDamageAnimation == null ? HotAnimation : upperDamageAnimation);
        Animation finalAnimation = !running ? OffAnimation : damageAnimation;

        setAnimation(finalAnimation);
    }

    public boolean isBroken() {
        return damage >= 100;
    }

    public boolean isRunning() {
        return running;
    }

    public void turnOn() {
        if (!isBroken()) {
            this.running = true;
            updateAnimation();
        }
    }

    public void turnOff() {
        this.running = false;
        setAnimation(OffAnimation);
    }

    public void addLight(Light light) {
        this.ReactorLight = light;
        updateElectricityFlow();
    }

    public void removeLight() {
        this.ReactorLight = null;
        updateElectricityFlow();
    }

    private void updateElectricityFlow() {
        if (ReactorLight != null) {
            ReactorLight.setElectricityFlow(running && damage < 50);
        }
    }

    public void repairWith(Hammer hammer) {
        if (hammer == null || damage <= 0 || damage >= 100) return;

        hammer.use();

        damage -= 50;
        if (damage < 0) damage = 0;

        int newTemp = (int) ((6000 * damage) / 100);
        if (newTemp < temperature) {
            temperature = newTemp;
        }

        updateElectricityFlow();
        updateAnimation();
    }

    public int getTemperature() {
        return temperature;
    }

}
