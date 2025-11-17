package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Ammo extends AbstractActor implements Usable<Ripley> {

    public Ammo() {
        Animation ammoAnimation = new Animation("sprites/ammo.png", 16,16);
        setAnimation(ammoAnimation);
    }

    @Override
    public void useWith(Ripley ripley) {
        if (ripley == null || ripley.getAmmo() >= 500) return;

        int newA = Math.min(ripley.getAmmo() + 50, 500);
        ripley.setAmmo(newA);

        if (getScene() != null) {getScene().removeActor(this);}
    }
}
