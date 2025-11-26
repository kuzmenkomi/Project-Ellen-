package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private Animation lockerAnim;
    private boolean isUsed;

    public Locker() {
        lockerAnim = new Animation("sprites/locker.png", 16, 16);
        setAnimation(lockerAnim);

        isUsed = false;
    }

    @Override
    public void useWith(Ripley actor) {
        if (isUsed) return;

        isUsed = true;
        Objects.requireNonNull(getScene()).addActor(new Hammer(), getPosX(), getPosY());
    }

    @Override
    public Class<Ripley> getUsingActorClass() {return Ripley.class;}
}
