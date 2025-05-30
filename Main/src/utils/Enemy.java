package utils;

/**
 * The {@code Enemy} class represents an enemy with a name, health, and an attack.
 */
public class Enemy {
   private String name;
   private int health;
   private Attacks attack;

   /**
    * Constructs a new {@code Enemy} object with a specified name and health.
    * @param name   The name of the enemy
    * @param health The health of the enemy
    */
   public Enemy(String name, int health) {
      this.name = name;
      this.health = health;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setHealth(int health) {
      this.health = health;
   }

   public void setAttack(Attacks attack) {
      this.attack = attack;
   }

   public String getName() {
      return name;
   }

   public int getHealth() {
      return health;
   }

   public Attacks getAttack() {
      return attack;
   }

   @Override
   public String toString() {
      return String.format("--Character info--\nName: %s\nHealth: %d\nAttack: %s", name, health, attack);
   }
}
