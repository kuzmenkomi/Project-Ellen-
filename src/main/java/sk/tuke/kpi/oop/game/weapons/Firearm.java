package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int currentAmmo;
    private final int maxAmmo;

    public Firearm(int initialAmmo, int maxAmmo) {
        this.currentAmmo = Math.min(initialAmmo, maxAmmo);
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int ammo) {this(ammo, ammo);}
    public int getAmmo() {return currentAmmo;}
    public int getMaxAmmo() {return maxAmmo;}
    public void reload(int newAmmo) {currentAmmo = Math.min(maxAmmo, getAmmo() + newAmmo);}

    public Fireable fire() {
        if (currentAmmo > 0) {
            currentAmmo--;
            return createBullet();
        }
        return null;
    }

    protected abstract Fireable createBullet();
}
