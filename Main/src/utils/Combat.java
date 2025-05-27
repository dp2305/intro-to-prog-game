package utils;
import java.util.Random;
import java.util.Scanner;
import static utils.Formatting.*;

public class Combat {

    public static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    public static void combat(Player player, int enemyCount) {
        int enemySelection = random.nextInt(8);
        int weaponSelection = random.nextInt(3);

        final int MAX_AMMO = 1000000;

        // Enemies
        Enemy[] enemies = {
            new Enemy("Snake",            5, 1),
            new Enemy("Raven",            5, 2),
            new Enemy("Vulture",          5, 3),
            new Enemy("Wolf",             10, 3),
            new Enemy("Wild Boar",        15, 5),
            new Enemy("Bear",             20, 8),
            new Enemy("Sabretooth Tiger", 20, 10)
        };

        // Enemy Attacks

        // Snake Attacks
        Weapon[] snakeWeapons = {
            new Weapon("Bite", 2, MAX_AMMO, 2, 1),
            new Weapon("Coil", 3, MAX_AMMO, 1, 1),
            new Weapon("Poison", 4, MAX_AMMO, 1, 1)
        };

        // Raven Attacks
        Weapon[] ravenWeapons = {
            new Weapon("Beak", 2, MAX_AMMO, 1, 1),
            new Weapon("Charge", 2, MAX_AMMO, 2, 1),
            new Weapon("Talons", 4, MAX_AMMO, 1, 1)
        };

        // Vulture Attacks
        Weapon[] vultureWeapons = {
            new Weapon("Beak", 3, MAX_AMMO, 2, 1),
            new Weapon("Charge", 3, MAX_AMMO, 2, 1),
            new Weapon("Talons", 5, MAX_AMMO, 1, 1)
        };

        // Wolf Attacks
        Weapon[] wolfWeapons = {
            new Weapon("Headbutt", 2, MAX_AMMO, 2, 1),
            new Weapon("Claws", 3, MAX_AMMO, 1, 1),
            new Weapon("Bite", 4, MAX_AMMO, 1, 1)
        };

        // Wild Boar Attacks
        Weapon[] wildBoarWeapons = {
            new Weapon("Kick", 4, MAX_AMMO, 2, 1),
            new Weapon("Charge", 5, MAX_AMMO, 2, 1),
            new Weapon("Ram", 6, MAX_AMMO, 1, 1)
        };

        // Bear Attacks
        Weapon[] bearWeapons = {
            new Weapon("Punch", 4, MAX_AMMO,2, 1),
            new Weapon("Kick", 5, MAX_AMMO, 2, 1),
            new Weapon("Hug", 6, MAX_AMMO, 1, 1)
        };

        // Sabretooth Tiger Attacks
        Weapon[] sabretoothTigerWeapons = {
            new Weapon("Headbutt", 4, MAX_AMMO, 2, 1),
            new Weapon("Bite", 5, MAX_AMMO, 2, 1),
            new Weapon("Claws", 7, MAX_AMMO, 1, 1)
        };

        // All enemy weapons
        Weapon[] enemyWeapons = {
            snakeWeapons[weaponSelection],
            ravenWeapons[weaponSelection],
            vultureWeapons[weaponSelection],
            wolfWeapons[weaponSelection],
            wildBoarWeapons[weaponSelection],
            bearWeapons[weaponSelection],
            sabretoothTigerWeapons[weaponSelection]
        };

        int playerAttackRoll, enemyAttackRoll;
        boolean continueFighting, fleeFighting;

        // Set Enemy outside of while loop so it doesn't change every fight
        Enemy enemy = enemies[enemySelection];

        int fightCount = 0;

        while (fightCount < enemyCount) {
            continueFighting = true;

            while (continueFighting) {
                // Enemy weapons need to be set every fight
                enemy.setWeapon(enemyWeapons[weaponSelection]);

                // Variance in damage for attacks
                int damageVariance = random.nextInt(7) - 3; // Randomise damage with variance of -3 to 3
                int rangeEffect = 3 - player.getWeapon().getRange(); // Make weapon range effect damage, low range = more damage

                int totalDamageEffect = damageVariance + rangeEffect;

                print("You have " + ANSI_TEXT_BLUE + player.getHealth() + ANSI_RESET + " health left.");
                lineBreak();
                print("What would you like to do?" + ANSI_TEXT_BLUE + "(1) Fight, (2) Run" + ANSI_RESET);
                lineBreak();
                printColour(" > ", ANSI_TEXT_GREEN);

                String choice = sc.nextLine().toUpperCase();

                if (choice.isEmpty()) {
                    clearLine(1);
                    continue;
                } else {
                    char choiceChar = choice.charAt(0);

                    switch (choiceChar) {
                        case '1' -> {
                            // Player attack
                            print("You attempted to attack the " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + ".");
                            lineBreak();
                            playerAttackRoll = random.nextInt(6) + 1; // 1-6 instead of 0-5

                            if (playerAttackRoll <= 1) {
                                print("You failed to attack the " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + ".");
                                lineBreak();
                            } else {
                                print("You hit the " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " for " + ANSI_TEXT_YELLOW + (player.getWeapon().getDamage() + totalDamageEffect) + ANSI_RESET + " damage.");
                                lineBreak();
                                enemy.setHealth(enemy.getHealth() - (player.getWeapon().getDamage() + totalDamageEffect));
                            }

                            if (enemy.getHealth() > 0) {
                                print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " now has " + ANSI_TEXT_YELLOW + enemy.getHealth() + ANSI_RESET + " health.");
                                lineBreak();
                                print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET  + " attempted to attack you with its " + ANSI_TEXT_YELLOW + enemy.getWeapon().getName() + ANSI_RESET + ".");
                                lineBreak();
                                enemyAttackRoll = random.nextInt(6) + 1;

                                if (enemyAttackRoll <= 1) {
                                    print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " failed to attack you." );
                                    lineBreak();
                                } else {
                                    print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " has " + ANSI_TEXT_YELLOW + enemy.getHealth() + ANSI_RESET + " health.");
                                    lineBreak();
                                    player.setHealth(player.getHealth() - (enemy.getWeapon().getDamage() + damageVariance));
                                }
                            }
                        }

                        case '2' -> {
                            fleeFighting = random.nextBoolean();
                            if (fleeFighting) {
                                print("You escaped successfully. Continue your mission.");
                                lineBreak();
                                continueFighting = false;
                            } else {
                                print("You failed to escape, the battle continues.");
                                lineBreak();
                                print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " has " + ANSI_TEXT_YELLOW + enemy.getHealth() + ANSI_RESET + " health.");
                                lineBreak();
                                print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " attempted to hit you.");
                                lineBreak();
                                enemyAttackRoll = random.nextInt(6) + 1;

                                if (enemyAttackRoll <= 1) {
                                    print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " failed to attack you.");
                                    lineBreak();
                                } else {
                                    print("You get hit for " + ANSI_TEXT_YELLOW + enemy.getWeapon().getDamage() + damageVariance + ANSI_RESET + " damage.");
                                    lineBreak();
                                    player.setHealth(player.getHealth() - (enemy.getWeapon().getDamage() + damageVariance));
                                }
                            }
                        }
                        default -> {
                            clearLine(1);
                        }
                    }
                }

                if (player.getHealth() <= 0) {
                    print("You failed to rescue the scientists, game over.");
                    continueFighting = false;
                    fightCount = 10;
                }

                if (enemy.getHealth() <= 0) {
                    continueFighting = false;
                    fightCount++;
                    print("You survived this encoutner, continue your mission.");
                }
            }
        }
    }
}