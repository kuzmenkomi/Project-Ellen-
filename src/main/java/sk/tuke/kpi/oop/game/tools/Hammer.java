package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool {
    private final Animation hammerAnimation;

    public Hammer() {
        super(1);
        hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    @Override
    public void use() {
        super.use(); // вызываем родительский метод
    }
}
