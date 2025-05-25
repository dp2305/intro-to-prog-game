package utils;
import java.util.Random;
import java.util.Scanner;

public class Combat {

    public static Scanner sc = new Scanner(System.in);
    private static Random diceRoll = new Random();

    // Make player and monster static so they are accessible in static methods
    private static boolean alive = true;

    public static void combat(Player player, boolean gameRunning) {
        int enemySelection = diceRoll.nextInt(8);
        int weaponSelection = diceRoll.nextInt(3);

        final int MAX_AMMO = 1000000;

        // Enemies
        Enemy[] enemies = {
            new Enemy("Snake",            5, 1),
            new Enemy("Raven",            5, 2),
            new Enemy("Vulture",          5, 3),
            new Enemy("Wolf",             10, 3),
            new Enemy("Wild Boar",        15, 5),
            new Enemy("Bear",             20, 8),
            new Enemy("Sabretooth Tiger", 20, 10),
            new Enemy("Gorilla",          25, 15)
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

        // Gorilla Attacks
        Weapon[] gorillaWeapons = {
            new Weapon("Punch", 5, MAX_AMMO, 2, 1),
            new Weapon("Kick", 6, MAX_AMMO, 2, 1),
            new Weapon("Hug", 7, MAX_AMMO, 1, 1)
        };

        // All enemy weapons
        Weapon[] enemyWeapons = {
            snakeWeapons[weaponSelection],
            ravenWeapons[weaponSelection],
            vultureWeapons[weaponSelection],
            wolfWeapons[weaponSelection],
            wildBoarWeapons[weaponSelection],
            bearWeapons[weaponSelection],
            sabretoothTigerWeapons[weaponSelection],
            gorillaWeapons[weaponSelection]
        };

        // Set Enemy and Weapon
        Enemy enemy = enemies[enemySelection];

        // Enemy weapons need to be set every fight
        enemy.setWeapon(enemyWeapons[weaponSelection]);


        int tempPlayerHealth, tempEnemyHealth;
        int playerAttackRoll, enemyAttackRoll;
        boolean continueFighting = true;
        boolean fleeFighting;

        while (continueFighting) {
            System.out.println("Player Health left: " + player.getHealth());
            System.out.println("\nWhat would you like to do?");
            System.out.println("[f]ight\n[r]un");
            char choice = sc.nextLine().toUpperCase().charAt(0);

            switch (choice) {
                case 'F' -> {
                    // human attack
                    System.out.printf("you attempted to hit %s with your %s\n", enemy.getName(), player.getWeapon().getName());
                    playerAttackRoll = diceRoll.nextInt(6) + 1; // 1-6 instead of 0-5
                    int playerDamage = playerAttackRoll + player.getWeapon().getDamage(); // weapon damage
                    if (playerAttackRoll <= 1) {
                        System.out.printf("you failed to hit %s\n", enemy.getName());
                    } else {
                        System.out.printf("\nyou hit %s for %d\n", enemy.getName(), playerDamage);
                        tempEnemyHealth = enemy.getHealth() - playerDamage;
                        enemy.setHealth(tempEnemyHealth);
                    }

                    if (enemy.getHealth() > 0) {
                        System.out.printf("\n%s has %d health left\n", enemy.getName(), enemy.getHealth());
                        System.out.printf("the monster attempted to hit %s with its %s\n", player.getName(), enemy.getWeapon().getName());
                        enemyAttackRoll = diceRoll.nextInt(6) + 1;
                        int enemyDamage = enemyAttackRoll + enemy.getWeapon().getDamage();
                        if (enemyAttackRoll <= 1) {
                            System.out.printf("%s failed to hit %s\n", enemy.getName(), player.getName());
                        } else {
                            System.out.printf("you get hit for %d\n", enemyDamage);
                            tempPlayerHealth = player.getHealth() - enemyDamage;
                            player.setHealth(tempPlayerHealth);
                        }
                    }
                }

                case 'R' -> {
                    fleeFighting = diceRoll.nextBoolean();
                    if (fleeFighting) {
                        System.out.println("you have fleed successfully. tutorial will end now ");
                        continueFighting = false;
                    } else {
                        System.out.println("you failed to flee, the battle continues");
                        enemyAttackRoll = diceRoll.nextInt(6) + 1;
                        System.out.printf("%s has %d health left\n", enemy.getName(), enemy.getHealth());
                        System.out.printf("the monster attempted to hit %s\n", player.getName());
                        if (enemyAttackRoll <= 1) {
                            System.out.printf("%s failed to hit %s\n", enemy.getName(), player.getName());
                        } else {
                            System.out.printf("you get hit for %d\n", enemyAttackRoll);
                            tempPlayerHealth = player.getHealth() - enemyAttackRoll;
                            player.setHealth(tempPlayerHealth);
                        }
                    }
                }
                default -> System.out.println("Invalid choice");
            }
            if (player.getHealth() <= 0) {
                alive = false;
                continueFighting = false;
                System.out.println("You failed your mission, game over.");
                gameRunning = false;
            }
            if (enemy.getHealth() <= 0) {
                continueFighting = false;
                System.out.println("You have defeated the enemy, continue your mission.");
            }
        }
    }
}