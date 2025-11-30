package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.*;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    private int speed;
    private Health health;
    private int ammo;
    private Backpack backpack;
    private Firearm gun;
    private Animation ripleyAnim;
    private Animation dieAnim;
    private boolean dead = false;
    private Disposable disposable;

    public Ripley() {
        super("Ellen");
        health = new Health(100, 100);
        health.onFatigued(() -> {
            die();
        });
        gun = new Gun(50, 100);
        ammo = 100;
        speed = 2;
        backpack = new Backpack("Ripley's backpack",10);
        disposable = null;

        ripleyAnim = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        ripleyAnim.stop();
        dieAnim = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(ripleyAnim);
    }

    public void startedMoving(Direction direction) {
        if (dead) return;
        ripleyAnim.setRotation(direction.getAngle());
        ripleyAnim.play();
        setAnimation(ripleyAnim);
    }

    public void stoppedMoving() {
        if (dead) return;
        ripleyAnim.stop();
        setAnimation(ripleyAnim);
    }

    public int getSpeed() {return this.speed;}


    public void decreaseEnergy() {
        if (dead) return;  // Уже мертвая — ничего не делаем

        stopDecreasingEnergy();

        disposable = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    if (health.getValue() <= 5) {
                        health.exhaust();
                    } else {
                        health.drain(5);
                    }
                }),
                new Wait<>(1)
            )
        ).scheduleFor(this);
    }

    public Disposable stopDecreasingEnergy() {
        if (disposable != null) disposable.dispose();
        Disposable temp = disposable;
        disposable = null;
        return temp;
    }

    public int getAmmo() {return ammo;}
    public void setAmmo(int ammo) {this.ammo = ammo;}

    public Backpack getBackpack() {return backpack;}

    private void die() {
        dead = true;
        setAnimation(dieAnim);
        if (getScene() != null) getScene().getMessageBus().publish(RIPLEY_DIED, this);
    }

    public void showRipleyState() {
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Energy " + this.health.getValue(), 120, yTextPos);
        getScene().getGame().getOverlay().drawText("Ammo " + this.getAmmo(), 240, yTextPos);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }
}
