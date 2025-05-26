package utils;
import java.util.ArrayList;
import java.util.Random;
import static utils.Formatting.*;

public class Player {

    private static final Random random = new Random();

    private String name;
    private int health;
    private Weapon weapon;
    private final ArrayList<Items> backpack = new ArrayList<>(); // Use an arraylist to handle items being added and removed from the backpack

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
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

    public static void rest(Player player) {
        if (player.getHealth() >= 45) {
            print("You are can't rest, you are already at full health.");
        } else {
            // If player has rested in the same location, they can't rest again
            if (Navigation.playerHasVisitedLocation()) {
                print("You can't rest at this location again.");
            } else {
                boolean canRest = random.nextBoolean();

                if (canRest) {
                    player.setHealth(player.getHealth() + random.nextInt(3));
                    print("You rested and were able to recover " + ANSI_TEXT_YELLOW + random.nextInt(3) + " health." + ANSI_RESET);
                } else {
                    print("You weren't able to rest properly.");
                }
            }
        }
        lineBreak();
    }

    public static void checkCharacter(Player player) {
        print("You have " + player.getHealth() + " health.");
        lineBreak();
        print("You are carrying a " + player.getWeapon().getName() + " with " + player.getWeapon().getAmmo() + " ammo.");
        lineBreak();
        if (player.showBackpack().isEmpty()) {
            print("You are carrying no items.");
        } else {
            print("You are carrying the following items: \n" + player.showBackpack());
        }
        lineBreak();
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
}