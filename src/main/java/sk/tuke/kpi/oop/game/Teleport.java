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
    private boolean onTeleport;

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
        if (player == null || destination == null || getScene() == null) return;

        if (!this.intersects(player)) {
            onTeleport = false;return;}
        if (onTeleport) return;

        int posX = destination.getPosX() + destination.getWidth() / 2 - player.getWidth() / 2;
        int posY = destination.getPosY() + destination.getHeight() / 2 - player.getHeight() / 2;
        player.setPosition(posX, posY);
        destination.onTeleport = true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(() -> {
                Player player = getScene().getFirstActorByType(Player.class);
                teleportPlayer(player);
            })
        ).scheduleFor(this);
    }
}
