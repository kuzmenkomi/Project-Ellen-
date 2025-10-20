package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class BreakableTool extends AbstractActor {
    private int remainingUses;
    private Animation hammerAnimation;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
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
