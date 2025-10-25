package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private int positionX = 0, positionY = 0;
    private Animation down;
    private Animation up;
    private Animation left;
    private Animation right;


    public Helicopter() {
        down = new Animation("sprites/heli_down.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        up = new Animation("sprites/heli_up.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        left = new Animation("sprites/heli_left.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        right = new Animation("sprites/heli_right.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(up);
    }

    public void searchAndDestroy() {
        new Loop<>(new Invoke<>(this::searchActor)).scheduleFor(this);
    }

    private void searchActor() {
        Player player = getScene().getLastActorByType(Player.class);
        if (player == null) return;

        int dx = Integer.compare(player.getPosX(), getPosX());
        int dy = Integer.compare(player.getPosY(), getPosY());

        setPosition(getPosX() + dx, getPosY() + dy);
        updateHelicopter(dx, dy);

        if (intersects(player)) {
            player.setEnergy(player.getEnergy() - 1);
        }
    }

    public void updateHelicopter(int deltaX, int deltaY) {
        if (deltaX > 0) {
            setAnimation(right);
        } else if (deltaX < 0) {
            setAnimation(left);
        } else if (deltaY > 0) {
            setAnimation(down);
        } else if (deltaY < 0) {
            setAnimation(up);
        }
    }
}
