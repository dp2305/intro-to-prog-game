package utils;

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