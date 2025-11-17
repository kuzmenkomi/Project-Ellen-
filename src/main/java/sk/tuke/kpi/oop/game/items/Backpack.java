package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private List<Collectible> backpackItems = new ArrayList<>();
    private final String name;
    private final int capacity;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public List<Collectible> getContent() {return List.copyOf(backpackItems);}
    public String getName() {return name;}
    public int getCapacity() {return capacity;}
    public int getSize() {return backpackItems.size();}

    @Override
    public void add(Collectible actor) {
        if (backpackItems.size() >= capacity) {
            throw new IllegalStateException(getName() + " is full");
        }
        backpackItems.add(actor);
    }

    @Override
    public void remove(Collectible actor) {
        backpackItems.remove(actor);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return backpackItems.iterator();
    }

    @Override
    public Collectible peek() {
        return getSize() > 0 ? backpackItems.get(getSize() - 1) : null;
    }

    @Override
    public void shift() {
        if (!backpackItems.isEmpty()) {
            Collections.rotate(backpackItems, 1);
        }
    }
}
