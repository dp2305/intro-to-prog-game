package utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import static utils.Formatting.*;


/**
 * Player class represents the main character controlled by the user.
 * Name, health, weapon, and inventory  is organised here.
 * Methods included are manage inventory, rest to regain health, and check player status. (Search??)
 * Used to control character state and interactions throughout the game.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 */

public class Player {

    private static final Random random = new Random();

    /** Player name */
    private String name;
    /** Player health */
    private int health;
    /** Weapon used by the player */
    private Weapon weapon;
    /** Total weight of the player's carried items and weapon */
    private int weight;
    /** Flag indicating whether the player is alive */
    private boolean isAlive;
    /** Flag indicating whether the player is currently in combat */
    private boolean isFighting;
    /** Flag indicating whether the game is ending (i.e., final event triggered) */
    private boolean isGameEnding;
    /** Flag to control unique first-time dialogue with an NPC */
    private static boolean isFirstNPCEncounter = true;
    /** The player's inventory, stored as a list of Items */
    private final ArrayList<Items> backpack = new ArrayList<>(); // Use an arraylist to handle items being added and removed from the backpack

    /** List of all possible game fragments that the player can find */
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

    /**
     * Constructs a new Player with specified name, health and alive status.
     * @param name the player's name
     * @param health initial health value
     * @param isAlive true if the player is alive, false otherwise
     */
    public Player(String name, int health, boolean isAlive) {
        this.name = name;
        this.health = health;
        this.isAlive = isAlive;
    }

    /**
     * Displays the contents of the player's backpack.
     * @return a formatted String listing the items in the backpack
     */
    // Display the player's backpack contents
    public String displayBackpackContents() {
        String backpackString = "";
        for (Items item : backpack) {
            backpackString += String.format(" - (%d) %s\n", backpack.indexOf(item) + 1, item.getName());
        }
        return backpackString;
    }

    /**
     * Adds an item to the player's backpack.
     * @param item the item to add
     */
    public void addBackpackItem(Items item) {
        backpack.add(item);
    }

    /**
     * Removes an item from the player's backpack.
     * @param item the item to remove
     */
    public void removeBackpackItem(Items item) {
        backpack.remove(item);
    }

    /**
     * Retrieves an item from the backpack by index.
     * @param index position of the item in the backpack
     * @return the item at the given index
     */
    public Items getBackpackItem(int index) {
        return backpack.get(index);
    }

    /**
     * Retrieves an item from the backpack by name.
     * @param name the name of the item to find
     * @return the item if found, null otherwise
     */
    public Items getBackpackItem(String name) {
        for (Items item : backpack) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Allows the player to rest and regain health if possible.
     * A random chance determines healing success.
     * @param player the player attempting to rest
     */
    public static void rest(Player player) {
        if (player.getHealth() >= 45) {
            print("You can't rest, you are already at full health.");
        } else {
            // If player has rested in the same location, they can't rest again
            if (Navigation.getRestedLocations()) {
                print("You were unable to rest at this location again.");
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
                Navigation.updateRestedLocations();
            }
        }
        lineBreak();
    }

    /**
     * Prints the current status of the player including health, weapon, and inventory.
     * @param player the player whose stats is being checked
     */
    public static void checkCharacter(Player player) {
        print("You have " + player.getHealth() + " health.");
        lineBreak();
        if (player.getWeapon().getName().equals("Knife")) {
            print("You are carrying a " + player.getWeapon().getName() + ".");
        } else {
            print("You are carrying a " + player.getWeapon().getName() + " with " + player.getWeapon().getAmmo() + " ammo.");
        }
        lineBreak();
        if (player.displayBackpackContents().isEmpty()) {
            print("You are carrying no items.");
        } else {
            print("You are carrying the following items: \n" + player.displayBackpackContents());
        }
    }

    /**
     * Allows the player to search their current location for items, fragments, or NPC encounters.
     * Result depends on the current search index and random chance.
     * @param player the player performing the search
     */
    public static void search(Player player) {
        switch (Navigation.getLocationSearchIndex()) {
            case 0 -> print("You looked around but couldn't find anything useful.");
            case 1 -> {
                int searchChance = random.nextInt(100);
                if (searchChance < 33) {
                    int itemIndex = random.nextInt(4); // 0-3 for 4 items
                    Items[] itemPool = {
                        new Items("Bundle of Rotten Fruit", 3, 1, 0),
                        new Items("Pack of Pain Killers", 6, 1, 0),
                        new Items("Handful of Loose Ammo", 5, 1, 1),
                        new Items("Used Magazine", 10, 1, 1),
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
            case 3 -> {
                // If its the first encounter with the NPC, print the NPC's dialogue
                if (isFirstNPCEncounter) {
                    print("\"" + ANSI_TEXT_YELLOW + player.getName() + ANSI_RESET + ", you must be here to help us, right?\"");
                    lineBreak();
                    print("\"" + ANSI_TEXT_YELLOW + player.getName() + ANSI_RESET + ", we've been waiting for you. I'm Victor, my partners in another room unconcious.\"");
                    lineBreak();
                    print("\"I've been trying to get into the vault, but " + ANSI_TEXT_YELLOW + "I'm missing the fragments required to open it" + ANSI_RESET + " and I wasn't able to get past the security system.\"");
                    lineBreak();
                    print("\"Have you found the fragments by any chance?\"");
                    lineBreak();
                    lineBreak();
                    isFirstNPCEncounter = false;
                }
                int fragmentCount = 0;
                int backpackSize = player.displayBackpackContents().split("\n").length;

                for (int i = 0; i < backpackSize; i++) {
                    Items item = player.getBackpackItem(i);

                    // type 2 = story items
                    if (item.getType() == 2) {
                        fragmentCount++;
                    }
                }

                // If the player doesn't have all 9 fragments, tell them how many are missing
                if (fragmentCount < 9) {
                    print("\"It seems like you still need to find " + ANSI_TEXT_YELLOW + (9 - fragmentCount) + " fragments" + ANSI_RESET + ".\"");
                } else {
                    print("\"You did it! You got all 9 fragments!\"");
                    lineBreak();
                    print("\"Now we have access to the vault, we can stay there until the reinforcements come in.\"");
                    player.setIsGameEnding(true);
                }
            }
            case 9 -> {
                // The the player that they have already searched this location
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

    /**
     * Calculates the total weight of the player based on weapon and backpack items.
     */
    public void calculatePlayerWeight() {
        weight = 0;
        weight = weight + weapon.getWeight();

        for (Items items : backpack) {
            weight = weight + items.getWeight();
        }

        setWeight(weight);
    }

    // ----- Setters -----

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

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setIsFighting(boolean isFighting) {
        this.isFighting = isFighting;
    }

    public void setIsGameEnding(boolean isGameEnding) {
        this.isGameEnding = isGameEnding;
    }

    // ----- Getters -----

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

    public boolean getIsAlive() {
        return isAlive;
    }

    public boolean getIsFighting() {
        return isFighting;
    }

    public boolean getIsGameEnding() {
        return isGameEnding;
    }
}