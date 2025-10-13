package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class Hammer extends AbstractActor {
    private int remainingUses;
    private Animation hammerAnimation;

    public Hammer() {
        this.remainingUses = 1;
        hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public void use() {
        if (remainingUses > 0) {
            remainingUses--;
        }

        if (remainingUses == 0) {
            getScene().removeActor(this);
        }
    }

    public int getRemainingUses() {
        return remainingUses;
    }
}
