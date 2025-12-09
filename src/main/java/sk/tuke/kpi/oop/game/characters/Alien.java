package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable, Alive, Enemy {

    private int speed = 2;
    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien() {
        Animation alienAnim = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnim);
        this.behaviour = null;
        health = new Health(100, 100);
        health.onFatigued(() -> getScene().removeActor(this));
    }

    public Alien(int healthValue, int maxHealth) {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(healthValue, maxHealth);
        this.behaviour = null;
        health.onFatigued(() -> getScene().removeActor(this));
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(healthValue, 100);
        this.behaviour = behaviour;
        health.onFatigued(() -> getScene().removeActor(this));
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        if (behaviour != null) behaviour.setUp(this);

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attackNearbyAlive),
                new Wait<>(0.5f)
            )
        ).scheduleFor(this);
    }

    private void attackNearbyAlive() {
        if (getScene() == null) return;

        for (Actor actor : getScene().getActors()) {
            if (actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)) {
                Alive aliveActor = (Alive) actor;
                aliveActor.getHealth().drain(3);
            }
        }
    }

    @Override
    public int getSpeed() {return speed;}
    @Override
    public Health getHealth() {return health;}
}
