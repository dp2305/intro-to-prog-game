package utils;

/**
 * Enemy class represents an enemy character with a name, health, weight, and weapon.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 * @version ?????
 */

public class Attacks {
   private String name;
   private int damage;

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