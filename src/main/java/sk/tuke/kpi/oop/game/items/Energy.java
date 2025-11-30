package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Energy extends AbstractActor implements Usable<Alive> {
    public Energy() {
        Animation energyAnim = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energyAnim);
    }

    @Override
    public void useWith(Alive aliveActor) {
        if (aliveActor != null && aliveActor.getHealth().getValue() < 100) {
            aliveActor.getHealth().restore();
            if (getScene() != null) {getScene().removeActor(this);}
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {return Alive.class;}
}
