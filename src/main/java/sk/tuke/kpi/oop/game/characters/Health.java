package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int maxHealth;
    private int currentHealth;
    private List<FatigueEffect> effects;
    private boolean exhausted;

    public Health(int initialHealth) {this(initialHealth, initialHealth);}

    public Health(int initialHealth, int maxHealth) {
        this.currentHealth = Math.min(initialHealth, maxHealth);
        this.maxHealth = maxHealth;
        this.effects = new ArrayList<>();
        this.exhausted = false;
    }

    public void refill(int amount) {
        if (amount > 0) {
            currentHealth = Math.min(currentHealth + amount, maxHealth);
            exhausted = false;
        }
    }

    public void restore() {
        currentHealth = maxHealth;
        exhausted = false;
    }

    public void drain(int amount) {
        if (amount > 0) {
            int newHealth = Math.max(currentHealth - amount, 0);
            boolean hitZero = (newHealth == 0 && currentHealth > 0);

            currentHealth = newHealth;
            if (hitZero && !exhausted) exhaust();
        }
    }

    public void exhaust() {
        if (!exhausted) {
            currentHealth = 0;
            exhausted = true;
            if (effects != null) effects.forEach(FatigueEffect::apply);
        }
    }

    public void onFatigued(FatigueEffect effect) {
        if (effect != null && effects != null) this.effects.add(effect);
    }

    @FunctionalInterface
    public interface FatigueEffect { void apply();}

    public int getValue() {return currentHealth;}
}
