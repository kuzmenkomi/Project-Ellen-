package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {
    public Hammer() {this(1);}

    public Hammer(int remainingUses) {
        super(remainingUses);
        Animation hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public void useWith(Repairable repairable) {
        if (repairable == null) return;
        if (repairable.repair()) super.useWith(repairable);
    }
    public Class<Repairable> getUsingActorClass() {return Repairable.class;}
}
