package utils;

public class Weapon {
    private String name;
    private int damage;
    private int ammo;
    private int range;
    private int weight;

    public Weapon(String name, int damage, int ammo, int range, int weight) {
        this.name = name;
        this.damage = damage;
        this.ammo = ammo;
        this.range = range;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getRange() {
        return range;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}