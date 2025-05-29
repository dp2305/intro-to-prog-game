package utils;

/**
 * Enemy class represents an enemy character with a name, health, weight, and weapon.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 * @version ?????
 */

public class Enemy {

    /** Enemy name. */
    private String name;
    /** Enemy Health. */
    private int health;
    /** Enemy Attack or Weapon. */
    private Attacks attack;

    /**
     * Constructs an {@code Enemy} object with the specified name and health.
     * The attack is initially set to {@code null}.
     *
     * @param name   Enemy name
     * @param health Enemy health
     */
    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
    }

    /**
     * Sets the enemy name.
     * @param name the new name for enemy
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Sets enemy health value.
     * @param health the health value for enemy
     */
    public void setHealth(int health) {
        this.health = health;
    }


    /**
     * Assigns an {@code Attacks} object to the enemy.
     *
     * @param attack the attack or weapon of the enemy
     */
    public void setAttack(Attacks attack) {
        this.attack = attack;
    }


    /**
     * Returns the name of enemy.
     * @return the enemy name
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the health of enemy.
     * @return the enemy health
     */
    public int getHealth() {
        return health;
    }


    /**
     * Returns the attack or weapon of enemy.
     * @return the {@code Attacks} object associated with the enemy
     */
    public Attacks getAttack() {
        return attack;
    }


    /**
     * Returns a string representation of the enemy's information, such as name, health, and attack.
     * @return a formatted string containing the enemy's attributes
     */
    @Override
    public String toString() {
        return String.format("--Character info--\nName: %s\nHealth: %d\nAttack: %s", name, health, attack);
    }
}

