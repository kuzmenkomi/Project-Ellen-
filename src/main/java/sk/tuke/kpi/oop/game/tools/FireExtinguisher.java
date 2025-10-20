package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends BreakableTool {
    private final Animation extinguisherAnimation;

    public FireExtinguisher() {
        super(1);
        extinguisherAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(extinguisherAnimation);
    }
}
