package utils;
import java.util.Scanner;
import static utils.Formatting.*;

/**
 * Item class gives the player the ability to use an item, such as healing supplies or ammunition.
 * Each item has a name, value, weight, and type.
 * Items include Ammunition and Healing items such as First Aid Kit and Food Pack
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 */

public class Items {


   /** Scanner for user input when selecting items */
   private static final Scanner sc = new Scanner(System.in);

   // Fields to store item details

   /** Name of the item */
   private String name;
   /**
    * Functional value of the item:
    *   Healing item: amount of HP it restores
    *   Ammunition: number of bullets it adds
    *   Story item: encoded story information
    */
   private int value;
   /** Weight of the item, which may affect carrying capacity */
   private int weight;
   /**
    * Type of the item:
    * 0 = Healing
    * 1 = Ammo
    * 2 = Story item
    */
   private int type; // Type is the type of item, eg. healing (0), ammo (1), story (2)

   /**
    * Constructor to initialize an item with the given name, value, weight, and type.
    *
    * @param name  Name of the item
    * @param value Functional value (e.g., HP restored, bullets added)
    * @param weight Weight of the item
    * @param type Type of the item (0 = healing, 1 = ammo, 2 = story)
    */
   public Items(String name, int value, int weight, int type) {
      this.name = name;
      this.value = value;
      this.weight = weight;
      this.type = type;
   }

   /**
    * Allows the player to use an item from their backpack.
    * Healing items restore health (up to a max of 55)
    * Ammo items add bullets to the current weapon
    * Story items can only be used outside of combat and display lore text
    * If the player attempts to use an item that is invalid or not usable at the moment, a message is shown.
    * @param player The player attempting to use the item
    */

   public static void useItem(Player player) {
      boolean isValidChoice = true;
      Weapon weapon = player.getWeapon();
      int backpackSize = player.displayBackpackContents().split("\n").length;

      // If the player has no items in their backpack, print a message and exit the method
      if (player.displayBackpackContents().isEmpty()) {
         print("You have no items in your backpack.");
         lineBreak();
         isValidChoice = false;
      } else {
         print("What item would you like to use? \n" + ANSI_TEXT_BLUE + "(0) Go Back \n" + player.displayBackpackContents() + ANSI_RESET);
      }

      while (isValidChoice) {
         printColour(" > ", ANSI_TEXT_GREEN);
         String itemChoice = sc.nextLine();

         if (itemChoice.isEmpty()) {
            clearLine(1);
         } else {
            int itemIndex = -1;

            // Try to parse input character as number
            try {
               itemIndex = Character.getNumericValue(itemChoice.charAt(0));
            } catch (NumberFormatException e) {
               clearLine(1);
            }

            if (itemIndex == 0) {
               // User chose to go back
               isValidChoice = false;
            } else if (itemIndex > 0 && itemIndex <= backpackSize) {
               // Valid item index
               try {
                  Items item = player.getBackpackItem(itemIndex - 1);

                  switch (item.getType()) {
                     case 0 -> {
                        // If player will over heal from item, don't allow healing
                        if (player.getHealth() + item.getValue() > 55) {
                              clearLine(backpackSize + 3);
                              print("You can't use the " + ANSI_TEXT_YELLOW + item.getName() + ANSI_RESET + " yet.");
                              lineBreak();
                              lineBreak();
                              useItem(player);
                        } else {
                              player.setHealth(player.getHealth() + item.getValue());
                              print("You used the " + ANSI_TEXT_YELLOW + item.getName() + ANSI_RESET + " and recovered " + ANSI_TEXT_YELLOW + item.getValue() + " health" + ANSI_RESET + ".");
                              // Remove the item after using it
                              player.removeBackpackItem(item);
                        }
                     }
                     case 1 -> {
                        weapon.setAmmo(weapon.getAmmo() + item.getValue());
                        print("You used the " + ANSI_TEXT_YELLOW + item.getName() + ANSI_RESET + " and added " + ANSI_TEXT_YELLOW + item.getValue() + " ammo to your " + weapon.getName() + ANSI_RESET + ".");
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
                           print("This " + ANSI_TEXT_YELLOW + "fragment" + ANSI_RESET + " says " + ANSI_TEXT_YELLOW + item.getValue() + ANSI_RESET + " on it.");
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

   /**
    * @return Item name
    */
   public String getName() {
      return name;
   }

   /**
    * @return Item value (HP restored, bullets added, etc.)
    */
   public int getValue() {
      return value;
   }

   /**
    * @return Item weight
    */
   public int getWeight() {
      return weight;
   }

   /**
    * @return Item type of the item (0 = healing, 1 = ammo, 2 = story)
    */
   public int getType() {
      return type;
   }

   /**
    * Sets the item name.
    * @param name New item name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Sets the item value.
    * @param value New item value
    */
   public void setValue(int value) {
    this.value = value;
   }

   /**
    * Sets the item weight.
    * @param weight New item weight
    */
   public void setWeight(int weight) {
      this.weight = weight;
   }

   /**
    * Sets the item type.
    * @param type New item type (0 = healing, 1 = ammo, 2 = story)
    */
   public void setType(int type) {
      this.type = type;
   }
}