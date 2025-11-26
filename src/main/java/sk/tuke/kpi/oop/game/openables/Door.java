package sk.tuke.kpi.oop.game.openables;

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

    private Animation doorAnim;
    boolean isOpened;

    public Door() {
        doorAnim = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(doorAnim);
        getAnimation().stop();
        isOpened = false;
    }

    public void open() {
        if (isOpened) return;
        isOpened = true;

        // Убираем стены
        Scene scene = getScene();
        if (scene != null) {
            int x = getPosX() / 16;
            int y = getPosY() / 16;
            scene.getMap().getTile(x, y).setType(MapTile.Type.CLEAR);
            scene.getMap().getTile(x, y + 1).setType(MapTile.Type.CLEAR);

            scene.getMessageBus().publish(DOOR_OPENED, this);
        }
        getAnimation().setPlayMode(Animation.PlayMode.ONCE);
        getAnimation().play();
    }

    public void close() {
        if (!isOpened) return;
        isOpened = false;

        // Ставим стены
        Scene scene = getScene();
        if (scene != null) {
            int x = getPosX() / 16;
            int y = getPosY() / 16;
            scene.getMap().getTile(x, y).setType(MapTile.Type.WALL);
            scene.getMap().getTile(x, y + 1).setType(MapTile.Type.WALL);

            scene.getMessageBus().publish(DOOR_CLOSED, this);
        }
        getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        getAnimation().play();
    }

    public boolean isOpen(){return isOpened;}

    @Override
    public void useWith(Actor actor) {
        Runnable action = isOpened ? this::close : this::open;
        action.run();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        getScene().getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(this.getPosX() /16, this.getPosY() / 16+1 ).setType(MapTile.Type.WALL);
    }

    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
