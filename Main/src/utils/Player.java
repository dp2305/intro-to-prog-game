package utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import static utils.Formatting.*;

public class Player {

    private static final Random random = new Random();

    private String name;
    private int health;
    private Weapon weapon;
    private int weight;
    private final ArrayList<Items> backpack = new ArrayList<>(); // Use an arraylist to handle items being added and removed from the backpack

    // All available fragments
    private static final ArrayList<Items> fragments = new ArrayList<>(Arrays.asList(
        new Items("Fragment No. 1", 1, 0, 2),
        new Items("Fragment No. 2", 2, 0, 2),
        new Items("Fragment No. 3", 3, 0, 2),
        new Items("Fragment No. 4", 4, 0, 2),
        new Items("Fragment No. 5", 5, 0, 2),
        new Items("Fragment No. 6", 6, 0, 2),
        new Items("Fragment No. 7", 7, 0, 2),
        new Items("Fragment No. 8", 8, 0, 2),
        new Items("Fragment No. 9", 9, 0, 2)
    ));

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
            if (Navigation.getRestedLocations()) {
                print("You were unable rest at this location again.");
            } else {
                boolean canRest = random.nextBoolean();

                if (canRest) {
                    int healingAmount = random.nextInt(3) + 1; // Player can heal 1-3 hp
                    player.setHealth(player.getHealth() + healingAmount);
                    print("You rested and were able to recover " + ANSI_TEXT_YELLOW + healingAmount + " health." + ANSI_RESET);
                } else {
                    print("You weren't able to rest properly, you didn't recover any health.");
                }

                // Sets restedLocation to false so player cannot rest again
                Navigation.UpdateRestedLocations();
            }
        }
        lineBreak();
    }

    public static void checkCharacter(Player player) {
        print("You have " + player.getHealth() + " health.");
        lineBreak();
        if (player.getWeapon().getName().equals("Knife")) {
            print("You are carrying a " + player.getWeapon().getName() + ".");
        } else {
            print("You are carrying a " + player.getWeapon().getName() + " with " + player.getWeapon().getAmmo() + " ammo.");
        }
        lineBreak();
        if (player.showBackpack().isEmpty()) {
            print("You are carrying no items.");
        } else {
            print("You are carrying the following items: \n" + player.showBackpack());
        }
    }

    public static void search(Player player) {
        switch (Navigation.getLocationSearchIndex()) {
            case 0 -> print("You looked around but couldn't find anything useful.");
            case 1 -> {
                int searchChance = random.nextInt();
                if (searchChance < 33) {
                    int itemIndex = random.nextInt(4); // 0-3 for 4 items
                    Items itemPool[] = {
                        new Items("Bundle of Rotten Fruit", 2, 1, 0),
                        new Items("Pack of Pain Killers", 5, 1, 0),
                        new Items("Handful of Loose Ammo", 3, 1, 1),
                        new Items("Used Magazine", 8, 1, 1),
                    };

                    Items randomItem = itemPool[itemIndex];
                    print("You found a " + ANSI_TEXT_YELLOW + randomItem.getName() + ANSI_RESET + ". ");
                    if (randomItem.getType() == 0) {
                        print("It recovers " + ANSI_TEXT_YELLOW + randomItem.getValue() + " health" + ANSI_RESET + ".");
                    } else {
                        print("It gives you " + ANSI_TEXT_YELLOW + randomItem.getValue() + " ammo" + ANSI_RESET + ".");
                    }

                    // Add the new item to the backpack
                    player.addBackpackItem(randomItem);
                } else {
                    print("You weren't able to find anything useful");
                }
                // Set the location search variable to false so the same location can't be searched again
                Navigation.updateLocationSearchIndex();
            }
            case 2 -> {
                int totalFragments = fragments.size(); // Size of 9 at start
                int randomFragment = random.nextInt(totalFragments); // Random number from 0-8 (9 indexes)
                print("You've found what looks to be a " + ANSI_TEXT_YELLOW + "fragment" + ANSI_RESET + ", this one says " + ANSI_TEXT_YELLOW + "the number " + fragments.get(randomFragment).getValue() + ANSI_RESET + ".");
                Navigation.updateLocationSearchIndex();
                player.addBackpackItem(fragments.get(randomFragment));
                fragments.remove(randomFragment);
            }
            case 9 -> {
                print("You've already searched this location, theres nothing here.");
            }
            default -> {
                lineBreak();
                printColour("Error while searching", ANSI_TEXT_RED);
                lineBreak();
            }
        }
        lineBreak();
    }

    public void calculatePlayerWeight() {
        weight = 0;
        weight = weight + weapon.getWeight();

        for (Items items : backpack) {
            weight = weight + items.getWeight();
        }

        setWeight(weight);
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

    public void setWeight(int weight) {
        this.weight = weight;
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

    public int getWeight() {
        return weight;
    }
}