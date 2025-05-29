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

   /** The attack name. */
   private String name;
   /** The attack damage value. */
   private int damage;


   /**
    * Constructs a new {@code Attacks} object with the specified name and damage.
    * @param name   Attack name
    * @param damage Attack damage value
    */
   public Attacks(String name, int damage) {
      this.name = name;
      this.damage = damage;
   }

   /**
    * Returns the Attack name.
    * @return the attack name
    */
   public String getName() {
      return name;
   }

   /**
    * Returns Attack damage value.
    * @return the attack damage
    */
   public int getDamage() {
      return damage;
   }

   /**
    * Sets the name of attack.
    * @param name the new name of the attack
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Sets the damage value of attack.
    * @param damage the new damage value
    */
   public void setDamage(int damage) {
      this.damage = damage;
   }
}








