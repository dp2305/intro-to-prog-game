package utils;

public class Items {
   // Fields to store item details
   private String name;
   private int value;
   private int weight;
   // Function number is what the item provides, eg. for ammo, it is the number of bullets, for healing items, it is the amount of health it heals
   private int functionNumber;
   // Type is the type of item, eg. healing (0), ammo (1)
   private int type;

   // Constructor to initialize an item with a name, value, and weight.
   public Items(String name, int value, int weight, int functionNumber, int type) {
      this.name = name;
      this.value = value;
      this.weight = weight;
      this.functionNumber = functionNumber;
      this.type = type;
   }

   public void useItem(Player player) {
      Weapon weapon = player.getWeapon();
      System.out.println("You used the " + name);

      if (type == 0) {
         player.setHealth(player.getHealth() + functionNumber);
         System.out.println("You healed " + functionNumber + " health");
         player.removeBackpackItem(this);
      } else if (type == 1) {
         if (weapon.getName().equals("Knife")) {
            System.out.println("Knifes do not require ammo");
         } else {
            weapon.setAmmo(weapon.getAmmo() + functionNumber);
            System.out.println("You added " + functionNumber + " ammo to your " + weapon.getName());
            player.removeBackpackItem(this);
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

   public int getFunctionNumber() {
      return functionNumber;
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

   public void setFunctionNumber(int functionNumber) {
      this.functionNumber = functionNumber;
   }

   public void setType(int type) {
      this.type = type;
   }
}