package utils;

/**
 * The {@code Attacks} class is used to create an attack object.
 * Each attack has a name and damage value.
 */
public class Attacks {
   private String name;
   private int damage;

   /**
    * Constructs a new {@code Attacks} object with a specified name and damage value.
    * @param name   The name of the attack
    * @param damage The damage value of the attack
    */
   public Attacks(String name, int damage) {
      this.name = name;
      this.damage = damage;
   }

   public String getName() {
      return name;
   }

   public int getDamage() {
      return damage;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setDamage(int damage) {
      this.damage = damage;
   }
}