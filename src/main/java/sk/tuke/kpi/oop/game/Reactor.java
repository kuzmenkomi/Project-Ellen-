package sk.tuke.kpi.oop.game;

import java.util.HashSet;
import java.util.Set;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private int temperature;
    private int damage;
    private boolean isOn;
    private final Light ReactorLight;

    private final Animation extinguishedAnimation;
    private final Animation NormalAnimation;
    private final Animation HotAnimation;
    private final Animation BrokenAnimation;
    private final Animation OffAnimation;

    private Set<EnergyConsumer> devices;

    public Reactor () {

        this.temperature = 0;
        this.damage = 0;
        this.isOn = false;
        this.ReactorLight = null;
        devices = new HashSet<>();

        NormalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        HotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        BrokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        OffAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png",80,80);

        setAnimation(OffAnimation);
    }

    public int getDamage() {
        return damage;
    }

    public int getTemperature() {return temperature;}

    public void increaseTemperature(int increment) {
        if (increment < 0 || damage >= 100) return;

        temperature += increment;

        if (temperature >= 6000) {
            damage = 100;
            turnOff();
        } else if (temperature > 4000) {
            damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 40.0));
        } else if (temperature > 2000) {
            damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 100.0));
        }
        if (damage > 100) {
            damage = 100;
            turnOff();
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0 || damage >= 100) return;

        int effectiveDecrement = (damage >= 50) ? decrement / 2 : decrement;
        temperature = Math.max(temperature - effectiveDecrement, 0);

        updateAnimation();
    }

    public void updateAnimation() {
        Animation newAnimation = NormalAnimation;

        if (!isOn) {
            newAnimation = (damage < 100) ? OffAnimation : BrokenAnimation;
        } else if (temperature > 4000) {
            newAnimation = (temperature < 6000) ? HotAnimation : BrokenAnimation;
        }

        setAnimation(newAnimation);
        this.devices.forEach(device -> device.setPowered(isOn() && damage == 0));
    }

    public boolean isBroken() {
        return damage >= 100;
    }

    public boolean isOn() {return isOn;}

    public void turnOn() {
        if (damage < 100) {
            this.isOn = true;
            updateAnimation();
        }
    }

    public void turnOff() {
        this.isOn = false;
        setAnimation(OffAnimation);
    }

    public void updateLight() {
        if (ReactorLight != null) {
            ReactorLight.setPowered(isOn && damage < 100);
        }
    }

    public boolean repair() {
        if (damage > 0 && damage < 100) {
            damage = Math.max(damage - 50, 0);
            temperature = (damage * 40) + 2000;
            updateAnimation();
            return true;
        }
        return false;
    }

    public boolean isLaunched() {
        return isOn && ReactorLight != null && ReactorLight.isOn() && ReactorLight.isPower();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }

    public void addDevice(EnergyConsumer device) {
        this.devices.add(device);
        device.setPowered(isOn() && damage == 0);
    }

    public void removeDevice(EnergyConsumer device){
        device.setPowered(false);
        this.devices.remove(device);
    }

    public boolean extinguish() {
        if (!isOn || damage == 0) {return false;}

        this.temperature = this.getTemperature() - 4000;
        setAnimation(extinguishedAnimation);
        return true;
    }
}
