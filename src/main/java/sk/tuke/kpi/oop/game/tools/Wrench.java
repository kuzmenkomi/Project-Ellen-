package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;


public class Wrench extends BreakableTool<DefectiveLight> {

    public Wrench() {
        super(2);
        Animation wrenchAnimation = new Animation("sprites/wrench.png");
        setAnimation(wrenchAnimation);
    }

    @Override
    public void useWith(DefectiveLight actor) {
        if (actor == null) {
            return;
        } else if (actor.repair()) {
            super.useWith(actor);
        }
    }

    public Wrench(int remainingUses) {
        super(remainingUses);
    }
}
