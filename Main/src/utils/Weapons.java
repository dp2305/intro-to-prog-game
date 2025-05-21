package utils;

public class Weapons {
    private String name;
    private int damage;
    private int range;
    private int weight;

    public Weapons(String name, int damage, int range, int weight) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.weight = weight;
    }

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