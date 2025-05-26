package utils;

public class Enemy {
    private String name;
    private int health;
    private int weight;
    private Weapon weapon;

    public Enemy(String name, int health, int weight) {
        this.name = name;
        this.health = health;
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getWeight() {
        return weight;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public String toString() {
        return String.format("--Character info--\nName: %s\nHealth: %d\nWeapon: %s", name, health, weapon);
    }
}
