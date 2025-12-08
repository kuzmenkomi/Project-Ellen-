package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {
    private final int maxAliens;
    private int spawnedAliens;
    private boolean isActive;
    private boolean isSpawning;
    private Disposable checkingAction;

    public SpawnPoint(int maxAliens) {
        this.maxAliens = maxAliens;
        this.spawnedAliens = 0;
        this.isActive = true;
        this.isSpawning = false;

        Animation animation = new Animation("sprites/spawn.png", 32, 32, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        checkingAction = new Loop<>(
            new Invoke<>(this::checkRipleyDistance)
        ).scheduleFor(this);
    }

    private void checkRipleyDistance() {
        if (!isActive || spawnedAliens >= maxAliens || isSpawning) return;

        Scene scene = getScene();
        if (scene == null) return;

        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley == null) return;

        int posX = getPosX() - ripley.getPosX();
        int posY = getPosY() - ripley.getPosY();
        int distance = posX * posX + posY * posY;
        if (distance <= 2500) spawnAlien();
    }

    private void spawnAlien() {
        if (!isActive || spawnedAliens >= maxAliens || isSpawning) return;

        Scene scene = getScene();
        if (scene == null) return;

        Alien alien = new Alien();
        alien.setPosition(getPosX(), getPosY());
        scene.addActor(alien);

        spawnedAliens++;
        isSpawning = true;

        new ActionSequence<>(
            new Wait<>(3),
            new Invoke<>(() -> {
                isSpawning = false;
                if (spawnedAliens >= maxAliens) deactivate();
            })
        ).scheduleFor(this);
    }

    public void deactivate() {
        isActive = false;
        if (checkingAction != null) {
            checkingAction.dispose();
            checkingAction = null;
        }
    }

    public void dispose() {deactivate();}
    public int getSpawnedAliens() {return spawnedAliens;}
    public boolean isActive() {return isActive;}
}
