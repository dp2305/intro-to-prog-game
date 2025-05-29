package utils;

/**
 * Weapon class allows the player to use a weapon.
 * Each weapon has a name, damage value, amount of ammo, range, and weight.
 * This class provides accessors (get) and mutators (set) for each field.
 * Allows the player to choose between 3 different weapons to use in RTS.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 * @version ?????
 */

public class Weapon {
    /** Weapon name */
    private String name;
    /** Weapon damage value */
    private int damage;
    /** Weapon ammunition */
    private int ammo;
    /** Weapon weight */
    private int weight;
    /** Weapon range */
    private int range;

    /**
     * Constructs a {@code Weapon} with the specified attributes.
     *
     * @param name   weapon name
     * @param damage weapon damage value
     * @param ammo   amount of ammunition available
     * @param weight weapon weight
     * @param range  the range of the weapon
     */

    public Weapon(String name, int damage, int ammo, int weight, int range) {
        this.name = name;
        this.damage = damage;
        this.ammo = ammo;
        this.weight = weight;
        this.range = range;
    }

    /**
     * Returns the name of the weapon.
     * @return the weapon name
     */

    public String getName() {
        return name;
    }

    /**
     * Returns the weapon damage value.
     * @return the damage value of weapon
     */

    public int getDamage() {
        return damage;
    }

    /**
     * Returns the current ammunition count of the weapon.
     * @return the ammo count
     */

    public int getAmmo() {
        return ammo;
    }

    /**
     * Returns the weapon weight.
     * @return the weight of the weapon
     */

    public int getWeight() {
        return weight;
    }

    /**
     * Returns the range of the weapon.
     * @return the weapon range
     */

    public int getRange() {
        return range;
    }

    /**
     * Sets the weapon name.
     * @param name the new name of the weapon
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the weapon damage.
     * @param damage the new damage value
     */

    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets the amount of ammo for the weapon.
     * @param ammo the new ammo count
     */

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * Sets the weapon weight.
     * @param weight the new weight value
     */

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Sets the effective range of the weapon.
     * @param range the new range value
     */

    public void setRange(int range) {
        this.range = range;
    }
}