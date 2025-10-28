package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import java.awt.Point;

public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean active;

    public Teleport(Teleport destination) {
        this.destination = destination;
        setAnimation(new Animation("sprites/lift.png"));
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport destinationTeleport) {
        if (destinationTeleport != this) {
            this.destination = destinationTeleport;
        }
    }

    private Point getCenter() {
        return new Point(
            getPosX() + getWidth() / 2,
            getPosY() + getHeight() / 2
        );
    }
    private boolean playerInside(Player player) {
        int posX = player.getPosX() + player.getWidth() / 2;
        int posY = player.getPosY() + player.getHeight() / 2;
        return posX >= getPosX() && posX <= getPosX() + getWidth() && posY >= getPosY() && posY <= getPosY() + getHeight();
    }

    public void teleportPlayer(Player player) {
        if (player == null) return;

        Point center = getCenter();
        int posCenterX = center.x - player.getWidth() / 2;
        int posCenterY = center.y - player.getHeight() / 2;
        player.setPosition(posCenterX, posCenterY);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(() -> {
            Player player = getScene().getFirstActorByType(Player.class);
            if (player == null || destination == null) return;

            if (this.intersects(player)) {
                if (playerInside(player) && !active) {
                    active = true;
                    destination.teleportPlayer(player);
                    destination.active = true;
                }
            } else {
                active = false;
                if (destination != null) destination.active = false;
            }
        })).scheduleFor(this);
    }
}
