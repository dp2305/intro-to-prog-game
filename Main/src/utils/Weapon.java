package utils;

public class Weapon {
    private String name;
    private int damage;
    private int ammo;
    private int weight;
    private int range;

    public Weapon(String name, int damage, int ammo, int weight, int range) {
        this.name = name;
        this.damage = damage;
        this.ammo = ammo;
        this.weight = weight;
        this.range = range;
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

    public int getWeight() {
        return weight;
    }

    public int getRange() {
        return range;
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setRanget(int range) {
        this.range = range;
    }
}