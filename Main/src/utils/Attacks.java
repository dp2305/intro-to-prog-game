package utils;

/**
 * The Attacks class represents a basic attack used in combat within the game.
 * Each attack has a name and an associated damage value used by enemies and the player.
 * This class serves as a data model for storing and retrieving information about individual attacks,
 * It includes standard getter and setter methods to manipulate the attack's name and damage.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
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








