package utils;

public class GameCharacters {
    private String name;
    private String charType;
    private int health;

    public GameCharacters(String name, String charType, int health) {
        this.name = name;
        this.charType = charType;
        this.health = health;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharType(String charType) {
        this.charType = charType;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public String getCharType() {
        return charType;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return String.format("--Character info--\nName: %s\nCharType: %s\nHealth: %d", name, charType, health);
    }
}
