package utils;
import java.util.ArrayList;

public class Player {
    private String name;
    private int health;
    private Weapon weapon;
    // Use an arraylist to handle items in the backpack
    private ArrayList<Items> backpack = new ArrayList<>();

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public Player() {
    }

    public void showBackpack() {
        for (Items item : backpack) {
            System.out.println(item);
        }
    }

    public void addBackpackItem(Items item) {
        backpack.add(item);
    }

    public void removeBackpackItem(Items item) {
        backpack.remove(item);
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