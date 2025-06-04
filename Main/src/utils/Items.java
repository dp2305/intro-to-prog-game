package utils;
import java.util.Scanner;
import static utils.Formatting.*;

/**
 * The {@code Items} class is used to create an item object.
 * Each item has a unique name, value, weight, and type.
 * Each item serves a different purpose, such as healing, providing ammo, or items needed for the story.
 */
public class Items {

   private static final Scanner sc = new Scanner(System.in);

   // Fields to store item details
   private String name;
   private int value; // Value is what the item provides, eg. for ammo, it is the number of bullets, for healing items, it is the amount of health it heals
   private int weight;
   private int type; // Type is the type of item, eg. healing (0), ammo (1), story (2)

   /**
    * Constructs a new {@code Items} object with the specified attributes.
    *
    * @param name  The name of the item
    * @param value The functional value of the item (e.g. HP restored or ammo added)
    * @param weight The weight of the item
    * @param type  The type of item, where 0 is healing, 1 is ammo, and 2 is story-related
    */
   public Items(String name, int value, int weight, int type) {
      this.name = name;
      this.value = value;
      this.weight = weight;
      this.type = type;
   }

   /**
    * Constructs a new {@code Items} object for story items with a specific name.
    *
    * @param name The name of the item
    */
    public Items(String name) {
      this.name = name;
      this.type = 2;
   }

   /**
    * Allows the player to use an item from their backpack
    * Once an item is used, it is removed from the player's backpack.
    *
    * @param player The player that is using the item
    */
   public static void useItem(Player player) {
      boolean isValidChoice = true;
      Weapon weapon = player.getWeapon();
      int backpackSize = player.getBackpackSize();

      // If the player has no items in their backpack, print a message and exit the method
      if (player.displayBackpackContents().isEmpty()) {
         print("You have no items in your backpack.");
         lineBreak();
         isValidChoice = false;
      } else {
         print("What item would you like to use? \n" + colour(BLUE, "(0) Go Back \n") + player.displayBackpackContents());
      }

      while (isValidChoice) {
         print(GREEN, " > ");
         String itemChoice = sc.nextLine();

         if (itemChoice.isEmpty()) {
            clearLine(1);
         } else {
            int itemChoiceInt;

            try {
               itemChoiceInt = Integer.parseInt(itemChoice);
            } catch (NumberFormatException e) {
               itemChoiceInt = -1;
               clearLine(1);
            }

            if (itemChoiceInt == 0) {
               // User chose to go back
               isValidChoice = false;
            } else if (itemChoiceInt > 0 && itemChoiceInt <= backpackSize) {
               // Valid item index
               try {
                  Items item = player.getBackpackItem(itemChoiceInt - 1);

                  switch (item.getType()) {
                     case 0 -> {
                        // If player will over-heal from item, don't allow healing
                        if (player.getHealth() + item.getValue() > 90) {
                           clearLine(backpackSize + 3);
                           print("You can't use the " + colour(YELLOW, item.getName()) + " yet.");
                           lineBreak();
                           lineBreak();
                           useItem(player);
                        } else {
                           player.setHealth(player.getHealth() + item.getValue());
                           print("You used the " + colour(YELLOW, item.getName()) + " and recovered " + colour(YELLOW, item.getValue() + " health") + ".");
                           // Remove the item after using it
                           player.removeBackpackItem(item);
                        }
                     }
                     case 1 -> {
                        weapon.setAmmo(weapon.getAmmo() + item.getValue());
                        print("You used the " + colour(YELLOW, item.getName()) + " and added " + colour(YELLOW, item.getValue() + " ammo") + " to your " + colour(YELLOW, weapon.getName()) + ".");
                        // Remove the item after using it
                        player.removeBackpackItem(item);
                     }
                     case 2 -> {
                        // Player can't use story related items during combat, but can use them outside of combat
                        if (player.getIsFighting()) {
                           print("You cannot use this item right now.");
                           lineBreak();
                           useItem(player);
                           return;
                        } else {
                           print("The core seems to be glowing green, I probably shouldn't mess with it right now.");
                        }
                     }
                     default -> {
                        clearLine(1);
                     }
                  }
                  lineBreak();

                  isValidChoice = false;
               } catch (IndexOutOfBoundsException e) {
                  clearLine(1);
               }
            } else {
               clearLine(1);
            }
         }
      }
   }

   public String getName() {
      return name;
   }

   public int getValue() {
      return value;
   }

   public int getWeight() {
      return weight;
   }

   public int getType() {
      return type;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setValue(int value) {
    this.value = value;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   public void setType(int type) {
      this.type = type;
   }
}