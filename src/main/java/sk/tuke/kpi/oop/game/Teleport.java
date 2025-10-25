package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destination;

    public Teleport(Teleport destination) {
        this.destination = destination;
        Animation teleportAnimation = new Animation("sprites/lift.png");
        setAnimation(teleportAnimation);
    }

    public Teleport getDestination() {return destination;}

    public void setDestination(Teleport destinationTeleport) {
        if (this != destinationTeleport) {
            this.destination = destinationTeleport;
        }
    }

    public void teleportPlayer(Player player) {
        if (this.destination == null || getScene() == null || player == null) return;

        int playerCenterX = player.getPosX() + player.getWidth() / 2;
        int playerCenterY = player.getPosY() + player.getHeight() / 2;

        int teleportCenterX = this.getPosX() + this.getWidth() / 2;
        int teleportCenterY = this.getPosY() + this.getHeight() / 2;

        if ((playerCenterX == teleportCenterX) && (playerCenterY == teleportCenterY)) {
            int targetX = destination.getPosX() + destination.getWidth() / 2 - player.getWidth() / 2;
            int targetY = destination.getPosY() + destination.getHeight() / 2 - player.getHeight() / 2;

            player.setPosition(targetX, targetY);
            destination.teleportPlayer(player);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Player player = getScene().getLastActorByType(Player.class);
        new Loop<>(new Invoke<>(this::teleportPlayer)).scheduleFor(player);
    }
}
