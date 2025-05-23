package utils;

public class Player {
    private String name;
    private int health;
    private Weapon weapon;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public Player() {
   }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public String toString() {
        return String.format("--Character info--\nName: %s\nHealth: %d\nWeapon: %s", name, health, weapon);
    }
}
