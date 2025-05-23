package utils;

public class Weapons {
    // Fields representing properties of the weapon
    private String name;
    private int damage;
    private int range;
    private int weight;

    // Constructor to initialize a new weapon with all its attributes.
    public Weapons(String name, int damage, int range, int weight) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.weight = weight;
    }

    // Gets
    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getWeight() {
        return weight;
    }

    // Sets

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}