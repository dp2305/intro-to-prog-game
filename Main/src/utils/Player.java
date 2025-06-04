package utils;
import java.util.ArrayList;
import java.util.Random;
import static utils.Formatting.*;

/**
 * The {@code Player} class represents the main character controlled by the user.
 * It contains the player's name, health, weapon, and inventory.
 * It includes methods for managing the player's inventory, and the actions they perform, such as searching for items, resting, and checking their stats.
 */
public class Player {

   private static final Random random = new Random();

   private String name;
   private int health;
   private Weapon weapon;
   private int weight;
   private boolean isAlive;
   private boolean isFighting;
   private boolean isGameEnding;
   private boolean isFirstNPCEncounter = true; // Used to check if the player has encountered the NPC for the first time
   private final ArrayList<Items> backpack = new ArrayList<>(); // Use an arraylist to handle items being added and removed from the backpack
   private final ArrayList<Items> nuclearCores = new ArrayList<>(); // All available nuclear cores - created globally so that the player can that the collected cores can be removed from the array

   /**
    * Constructs a new {@code Player} with health and alive status.
    *
    * @param health The initial health of the player
    * @param isAlive Whether the player is alive or not
    */
   public Player(int health, boolean isAlive) {
      this.health = health;
      this.isAlive = isAlive;

      // Add 9 nuclear cores to the nuclearCores arraylist
      for (int i = 0; i < 9; i++) {
         nuclearCores.add(new Items("Nuclear Core No. " + (i + 1)));
      }
   }

   /**
    * Displays the contents of the player's backpack.
    * @return A formatted string listing the items in the backpack
    */
   public String displayBackpackContents() {
      String backpackString = "";
      for (Items item : backpack) {
         backpackString += String.format(" - (%d) %s\n", backpack.indexOf(item) + 1, item.getName());
      }
      return backpackString;
   }

   /**
    * Calculates the number of items in the player's backpack.
    *
    * @return The number of items in the backpack
    */
   public int getBackpackSize() {
      return displayBackpackContents().split("\n").length;
   }

   /**
    * Adds an item to the player's backpack.
    *
    * @param item The item to add to the backpack
    */
   public void addBackpackItem(Items item) {
      backpack.add(item);
   }

   /**
    * Removes an item from the player's backpack.
    *
    * @param item The item to remove from the backpack
    */
   public void removeBackpackItem(Items item) {
      backpack.remove(item);
   }

   /**
    * Gets an item from the player's backpack by index.
    *
    * @param index The index of the item to get
    * @return The item at the specified index
    */
   public Items getBackpackItem(int index) {
      return backpack.get(index);
   }

   /**
    * Gets an item from the player's backpack by name.
    *
    * @param name The name of the item to get
    * @return The item with the specified name
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
    * Allows the player to rest and recover health.
    * If the player has already rested in the same location, they cannot rest again.
    */
   public void rest() {
      if (this.health >= 90) {
         print("You can't rest, you are already at full health.");
      } else {
         // If player has rested in the same location, they can't rest again
         if (Navigation.getRestedLocations()) {
            print("You were unable to rest at this location again.");
         } else {
            boolean canRest = random.nextBoolean();

            if (canRest) {
               int healingAmount = random.nextInt(3) + 1; // Player can heal 1-3 hp
               this.setHealth(this.health + healingAmount);
               print("You rested and were able to recover " + colour(YELLOW, healingAmount + " health") + ".");
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
    * Displays the player's stats, and what they are carrying.
    */
   public void checkCharacter() {
      print("You have " + this.health + " health.");
      lineBreak();
      print("You are carrying a " + this.weapon.getName() + " with " + this.weapon.getAmmo() + " ammo.");
      lineBreak();
      if (this.displayBackpackContents().isEmpty()) {
         print("You are carrying no items.");
      } else {
         print("You are carrying the following items: \n" + this.displayBackpackContents());
      }
   }

   /**
    * Searches the player's current location for items.
    * The player can search for items, nuclear cores, or the NPC.
    * If the player has already searched the location, they will not find anything.
    */
   public void search() {
      switch (Navigation.getLocationSearchIndex()) {
         case 0 -> print("You looked around but couldn't find anything useful.");
         case 1 -> {
            int searchChance = random.nextInt(100);
            if (searchChance < 40) {
               int itemIndex = random.nextInt(4); // 0-3 for 4 items
               Items[] itemPool = {
                  new Items("Bundle of Rotten Fruit", 4, 1, 0),
                  new Items("Pack of Pain Killers", 8, 1, 0),
                  new Items("Handful of Loose Ammo", 6, 1, 1),
                  new Items("Used Magazine", 12, 1, 1),
               };

               Items randomItem = itemPool[itemIndex];
               print("You found a " + colour(YELLOW, randomItem.getName()) + ". ");
               if (randomItem.getType() == 0) {
                  print("It recovers " + colour(YELLOW, randomItem.getValue() + " health") + ".");
               } else {
                  print("It gives you " + colour(YELLOW, randomItem.getValue() + " ammo") + ".");
               }

               // Add the new item to the backpack
               this.addBackpackItem(randomItem);
            } else {
               print("You weren't able to find anything useful");
            }
            // Set the location search variable to 9 so the same location can't be searched again
            Navigation.updateLocationSearchIndex(9);
         }
         case 2 -> {
            int totalNuclearCores = nuclearCores.size(); // Size of 9 at start
            int randomNuclearCore = random.nextInt(totalNuclearCores); // Random number from 0-8 (9 indexes)
            print("You've found what looks to be a " + colour(YELLOW, "nuclear core") + ".");
            Navigation.updateLocationSearchIndex(8); // Update to 8 so the player can't search this location again
            this.addBackpackItem(nuclearCores.get(randomNuclearCore));
            nuclearCores.remove(randomNuclearCore);
         }
         case 3 -> {
            // If its the first encounter with the NPC, print the NPC's dialogue
            if (isFirstNPCEncounter) {
               print(colour(BLUE, "Unknown Scientist") + ": \"" + colour(YELLOW, this.name) + ", you must be here to help us, right?\"");
               lineBreak();
               print(colour(BLUE, "Unknown Scientist") + ": \"We've been waiting for you. I'm Victor, my partners in another room unconcious.\"");
               lineBreak();
               print(colour(BLUE, "Victor") + ": \"I've been trying to get into the bunker, but we have no power and " + colour(YELLOW, "wer'e missing the nuclear cores to run the backup reactor") + ". We won't last much longer out here with all the radiation.\"");
               lineBreak();
               print(colour(BLUE, "Victor") + ": \"Have you found any of the nuclear cores by any chance?\"");
               lineBreak();
               lineBreak();
               isFirstNPCEncounter = false;
            }
            int nuclearCoreCount = 0;
            int backpackSize = this.getBackpackSize();

            for (int i = 0; i < backpackSize; i++) {
               Items item = this.getBackpackItem(i);

               // type 2 = story items
               if (item.getType() == 2) {
                  nuclearCoreCount++;
               }
            }

            // If the player doesn't have all 9 nuclear cores, tell them how many are missing
            if (nuclearCoreCount < 9) {
               print(colour(BLUE, "Victor") + ": \"It seems like you still need to find " + (9 - nuclearCoreCount) + " nuclear cores.\"");
            } else {
               print(colour(BLUE, "Victor") + ": \"You did it! You got all 9 nuclear cores! Now I can run the backup reactor and open the bunker.\"");
               lineBreak();
               print(colour(BLUE, "Victor") + ": \"Now we have access to the bunker, we can stay here until the reinforcements come in.\"");
               this.setIsGameEnding(true);
            }
         }
         case 8 -> {
            // Tell the player that they have already searched this location
            print("You've already searched this location, this is where you found a nuclear core.");
         }
         case 9 -> {
            // Tell the player that they have already searched this location
            print("You've already searched this location, theres nothing here.");
         }
         default -> {
            lineBreak();
            print(RED, "Error while searching");
            lineBreak();
         }
      }
      lineBreak();
   }

   /**
    * Calculates the player's weight based on the weapon and items in the backpack.
    */
   public void calculateWeight() {
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

   public void setIsAlive(boolean isAlive) {
      this.isAlive = isAlive;
   }

   public void setIsFighting(boolean isFighting) {
      this.isFighting = isFighting;
   }

   public void setIsGameEnding(boolean isGameEnding) {
      this.isGameEnding = isGameEnding;
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