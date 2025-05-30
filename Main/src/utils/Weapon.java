package utils;

/**
 * The {@code Weapon} class is used to create a weapon object.
 * Each weapon has a name, damage value, amount of ammo, range, and weight.
 */
public class Weapon {
   private String name;
   private int damage;
   private int ammo;
   private int weight;
   private int range;

   /**
    * Constructs a new {@code Weapon} object with the specified attributes.
    * @param name   The name of the weapon
    * @param damage The damage value of the weapon
    * @param ammo   The amount of ammo available
    * @param weight The weight of the weapon
    * @param range  The range of the weapon
    */
   public Weapon(String name, int damage, int ammo, int weight, int range) {
      this.name = name;
      this.damage = damage;
      this.ammo = ammo;
      this.weight = weight;
      this.range = range;
   }

   public String getName() {
      return name;
   }

   public int getDamage() {
      return damage;
   }

   public int getAmmo() {
      return ammo;
   }

   public int getWeight() {
      return weight;
   }

   public int getRange() {
      return range;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setDamage(int damage) {
      this.damage = damage;
   }

   public void setAmmo(int ammo) {
      this.ammo = ammo;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   public void setRange(int range) {
      this.range = range;
   }
}