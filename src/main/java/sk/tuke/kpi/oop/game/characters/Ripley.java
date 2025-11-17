package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.graphics.*;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Ripley extends AbstractActor implements Movable, Keeper {
    private int speed;
    private int energy;
    private int ammo;
    private Backpack backpack;
    private Animation ripleyAnim;

    public Ripley() {
        super("Ellen");
        energy = 55;
        ammo = 100;
        speed = 2;
        backpack = new Backpack("Ripley's backpack",10);

        ripleyAnim = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        ripleyAnim.stop();
        setAnimation(ripleyAnim);
    }

    public void startedMoving(Direction direction) {
        ripleyAnim.setRotation(direction.getAngle());
        ripleyAnim.play();
        setAnimation(ripleyAnim);
    }

    public void stoppedMoving() {
        ripleyAnim.stop();
        setAnimation(ripleyAnim);
    }

    public int getSpeed() {return this.speed;}
    public void setSpeed(int speed) {this.speed = speed;}

    public int getEnergy() {return energy;}
    public void setEnergy(int energy) {this.energy = energy;}

    public int getAmmo() {return ammo;}
    public void setAmmo(int ammo) {this.ammo = ammo;}

    public Backpack getBackpack() {return backpack;}
    public void setBackpack(Backpack backpack) {this.backpack = backpack;}
}
