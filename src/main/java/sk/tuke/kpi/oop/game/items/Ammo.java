package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;
public class Ammo extends AbstractActor implements Usable<Armed> {

    public Ammo() {
        Animation ammoAnim = new Animation("sprites/ammo.png", 16,16);
        setAnimation(ammoAnim);
    }

    @Override
    public void useWith(Armed armed) {
        if (armed != null && armed.getFirearm() != null) {
            armed.getFirearm().reload(50);
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Armed> getUsingActorClass() {return Armed.class;}
}
