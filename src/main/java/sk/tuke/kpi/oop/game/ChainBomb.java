package sk.tuke.kpi.oop.game;

import java.awt.geom.Rectangle2D;
import java.util.List;
import sk.tuke.kpi.gamelib.Actor;
import java.awt.geom.Ellipse2D;

public class ChainBomb extends TimeBomb {

    private static final int RADIUS = 50;
    public ChainBomb(float time) {
        super(time);
    }

    @Override
    public void explode() {
        super.explode();

        Ellipse2D.Float Area = new Ellipse2D.Float(
            this.getPosX() - RADIUS,
            this.getPosY() - RADIUS, RADIUS * 2, RADIUS * 2
        );
        /*System.out.println(Ellipse.getMinX());
        System.out.println(Ellipse.getMinY());
        System.out.println(Ellipse.getMaxX());
        System.out.println(Ellipse.getMaxY());
        System.out.println(Ellipse.getCenterX());
        System.out.println(Ellipse.getCenterY());*/
        List<Actor> actors = getScene().getActors();

        for (Actor actor : actors) {
            if ( actor instanceof ChainBomb && !( (ChainBomb) actor).isActivated() ) {
                Rectangle2D.Float bombArea = new Rectangle2D.Float(
                    actor.getPosX() - actor.getWidth() / 2,
                    actor.getPosY() - actor.getHeight() / 2,
                    actor.getWidth(), actor.getHeight()
                );
                /*System.out.println(bombArea.getMinX());
                System.out.println(bombArea.getMinY());
                System.out.println(bombArea.getMaxX());
                System.out.println(bombArea.getMaxY());
                System.out.println(bombArea.getCenterX());
                System.out.println(bombArea.getCenterY());*/
                if (Area.intersects(bombArea)) {
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }
}
