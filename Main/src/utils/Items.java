package utils;

public class Items {
   // Fields to store item details
   private String name;
   private int value; // Value is what the item provides, eg. for ammo, it is the number of bullets, for healing items, it is the amount of health it heals
   private int weight;
   private int type; // Type is the type of item, eg. healing (0), ammo (1)

   // Constructor to initialize an item with a name, value, and weight.
   public Items(String name, int value, int weight, int type) {
      this.name = name;
      this.value = value;
      this.weight = weight;
      this.type = type;
   }

   public Items(String name, int weight) {
      this.name = name;
      this.weight = weight;
   }

   public void useItem(Player player, Items item) {
      Weapon weapon = player.getWeapon();

      if (type == 0) {
         player.setHealth(player.getHealth() + value);
         System.out.println("You used the " + name + " and recovered " + value + " health");
         player.removeBackpackItem(item);
      } else if (type == 1) {
         if (weapon.getName().equals("Knife")) {
            System.out.println("Knifes do not require ammo");
         } else {
            weapon.setAmmo(weapon.getAmmo() + value);
            System.out.println("You used the " + name + " and added " + value + " ammo to your " + weapon.getName());
            player.removeBackpackItem(item);
         }
      }
   }

   // Gets
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

   // Sets
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