package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    public enum Orientation { VERTICAL, HORIZONTAL }
    private final Orientation orientation;
    private boolean isOpened;

    public Door(String name, Orientation orientation) {
        super(name);
        this.orientation = orientation;
        this.isOpened = false;

        String doorPicture = orientation == Orientation.VERTICAL ? "sprites/vdoor.png" : "sprites/hdoor.png";
        int width = orientation == Orientation.VERTICAL ? 16 : 32;
        int height = orientation == Orientation.VERTICAL ? 32 : 16;
        Animation doorAnim = new Animation(doorPicture, width, height, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(doorAnim);
        doorAnim.stop();
    }

    @Override public void open() {
        if (isOpened) return;
        isOpened = true;
        removeWalls();
        getScene().getMessageBus().publish(DOOR_OPENED, this);
        getAnimation().play();
    }

    @Override public void close() {
        if (!isOpened) return;
        isOpened = false;
        addWalls();
        getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        getAnimation().play();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    private void updateWalls(MapTile.Type type) {
        if (getScene() == null) return;
        int posX = getPosX() / 16;
        int posY = getPosY() / 16;
        if (orientation == Orientation.VERTICAL) {
            getScene().getMap().getTile(posX, posY).setType(type);
            getScene().getMap().getTile(posX, posY + 1).setType(type);
        } else {
            getScene().getMap().getTile(posX, posY).setType(type);
            getScene().getMap().getTile(posX + 1, posY).setType(type);
        }
    }

    private void addWalls()    { updateWalls(MapTile.Type.WALL); }
    private void removeWalls() { updateWalls(MapTile.Type.CLEAR); }

    @Override public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        addWalls();
    }

    @Override public void useWith(Actor actor) { if (isOpened) close(); else open(); }
    @Override public Class<Actor> getUsingActorClass() { return Actor.class; }

    public Door(Orientation orientation) { this(null, orientation); }
    public boolean isOpen() { return isOpened; }
}
