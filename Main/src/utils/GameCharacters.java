package utils;

// Private fields to store character information
public class GameCharacters {
    private String name;
    private String charType;
    private int health;

    // Constructor to initialize a new GameCharacters object.
    public GameCharacters(String name, String charType, int health) {
        this.name = name;
        this.charType = charType;
        this.health = health;
    }

    //Sets

    public void setName(String name) {
        this.name = name;
    }

    public void setCharType(String charType) {
        this.charType = charType;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    //Gets

    public String getName() {
        return name;
    }

    public String getCharType() {
        return charType;
    }

    public int getHealth() {
        return health;
    }

    // Returns a string representation of the character's information

    @Override
    public String toString() {
        return String.format("--Character info--\nName: %s\nCharType: %s\nHealth: %d", name, charType, health);
    }
}
