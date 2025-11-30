package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        A actor = getActor();

        if (actor == null || isDone()) {
            markAsDone();
            return;
        }

        Fireable projectile = actor.getFirearm().fire();
        if (projectile == null) {
            markAsDone();
            return;
        }
        setupProjectile(actor, projectile);
        markAsDone();
    }

    private void setupProjectile(A shooter, Fireable projectile) {
        Direction direction = Direction.fromAngle(shooter.getAnimation().getRotation());
        int posX = calculateX(shooter, direction);
        int posY = calculateY(shooter, direction);

        shooter.getScene().addActor(projectile, posX, posY);
        projectile.startedMoving(direction);

        new Move<Fireable>(direction, Float.MAX_VALUE).scheduleFor(projectile);
    }

    private int calculateX(A shooter, Direction direction) {return shooter.getPosX() + 8 + direction.getDx() * 24;}
    private int calculateY(A shooter, Direction direction) {return shooter.getPosY() + 8 + direction.getDy() * 24;}
    private void markAsDone() {setDone(true);}
}
