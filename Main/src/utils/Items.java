package utils;
import java.util.Scanner;
import static utils.Formatting.*;

/**
 * Item class gives the player the ability to use an item, such as healing supplies or ammunition.
 * Each item has a name, value, weight, and type.
 * Items include Ammunition and Healing items such as First Aid Kit and Food Pack
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 * @version ?????
 */

public class Items {

   private static final Scanner sc = new Scanner(System.in);

   // Fields to store item details
   private String name;
   private int value; // Value is what the item provides, eg. for ammo, it is the number of bullets, for healing items, it is the amount of health it heals
   private int weight;
   private int type; // Type is the type of item, eg. healing (0), ammo (1), story (2)

   // Constructor to initialize an item with a name, value, and weight.
   public Items(String name, int value, int weight, int type) {
      this.name = name;
      this.value = value;
      this.weight = weight;
      this.type = type;
   }

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