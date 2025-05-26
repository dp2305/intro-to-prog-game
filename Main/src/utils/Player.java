package utils;
import java.util.ArrayList;

public class Player {
    private String name;
    private int health;
    private Weapon weapon;
    private ArrayList<Items> backpack = new ArrayList<>(); // Use an arraylist to handle items in the backpack

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public Player() {
    }

    public String showBackpack() {
        String backpackString = "";
        for (Items item : backpack) {
            backpackString += String.format("(%d) %s\n", backpack.indexOf(item) + 1, item.getName());
        }
        return backpackString;
    }

    public void addBackpackItem(Items item) {
        backpack.add(item);
    }

    public void removeBackpackItem(Items item) {
        backpack.remove(item);
    }

    public Items getBackpackItem(int index) {
        return backpack.get(index);
    }

    public Items getBackpackItem(String name) {
        for (Items item : backpack) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
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