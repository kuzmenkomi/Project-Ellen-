package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private boolean isPowered;
    private boolean isOn;
    private final Animation lightOnAnimation;
    private final Animation lightOffAnimation;

    // Constructor
    public Light() {
        this.isPowered = false;
        this.isOn = false;

        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);

        setAnimation(lightOffAnimation);
    }

    public boolean isPower() {return isPowered;}

    public boolean isOn() {return isOn;}

    public void toggle() {
        isOn = !isOn;
        updateAnimation();
    }

    public void setElectricityFlow(boolean isPowered) {
        this.isPowered = isPowered;
        updateAnimation();
    }

    private void updateAnimation() {
        if (this.isOn) {setAnimation(this.isPowered ? lightOnAnimation : lightOffAnimation);}
    }

    @Override
    public void turnOff() {
        this.isOn= false;
        updateAnimation();
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        updateAnimation();
    }

    @Override
    public void setPowered(boolean powered) {
        this.isPowered = powered;
        updateAnimation();
    }
}
