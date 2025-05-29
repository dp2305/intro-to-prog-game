package utils;

/**
 * Combat Class is where the fights against enemies occurs.
 * This class is not meant to be instantiated.
 * This class runs battles between the player and a number of enemies.
 * It randomly picks enemies and their attacks, then lets the player choose to fight or run.
 * Damage calculations are random, and weapon effects.
 * It does not end until the player defeats all enemies, runs away, or dies.
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