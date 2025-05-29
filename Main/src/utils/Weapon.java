package utils;

/**
 * Weapon class allows the player to use a weapon.
 * Each weapon has a name, damage value, amount of ammo, range, and weight.
 * This class provides accessors (get) and mutators (set) for each field.
 * Allows the player to choose between 3 different weapons to use in RTS.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 * @version ?????
 */

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

    public void setRange(int range) {
        this.range = range;
    }
}