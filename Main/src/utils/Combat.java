package utils;
import java.util.Random;
import java.util.Scanner;
import static utils.Formatting.*;

public class Combat {

    public static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    public static boolean alive;

    public static void combat(Player player) {
        alive = true;

        while (alive && player.getHealth() > 0) {
            // All Enemies
            Enemy[] enemies = {
                new Enemy("Snake",            7),
                new Enemy("Raven",            10),
                new Enemy("Vulture",          10),
                new Enemy("Wolf",             15),
                new Enemy("Wild Boar",        20),
                new Enemy("Bear",             30),
                new Enemy("Sabretooth Tiger", 30)
            };

            // All Enemy Attacks
            // Snake Attacks
            Attacks[] snakeAttacks = {
                new Attacks("Bite", 2),
                new Attacks("Coil", 3),
                new Attacks("Poison", 4)
            };

            // Raven Attacks
            Attacks[] ravenAttacks = {
                new Attacks("Beak", 2),
                new Attacks("Charge", 2),
                new Attacks("Talons", 4)
            };

            // Vulture Attacks
            Attacks[] vultureAttacks = {
                new Attacks("Beak", 3),
                new Attacks("Charge", 3),
                new Attacks("Talons", 5)
            };

            // Wolf Attacks
            Attacks[] wolfAttacks = {
                new Attacks("Headbutt", 2),
                new Attacks("Claws", 3),
                new Attacks("Bite", 4)
            };

            // Wild Boar Attacks
            Attacks[] wildBoarAttacks = {
                new Attacks("Kick", 4),
                new Attacks("Charge", 5),
                new Attacks("Ram", 6)
            };

            // Bear Attacks
            Attacks[] bearAttacks = {
                new Attacks("Punch", 4),
                new Attacks("Kick", 5),
                new Attacks("Hug", 6)
            };

            // Sabretooth Tiger Attacks
            Attacks[] sabretoothTigerAttacks = {
                new Attacks("Headbutt", 4),
                new Attacks("Bite", 5),
                new Attacks("Claws", 7)
            };

            // Variables for combat
            int playerAttackRoll, enemyAttackRoll;
            boolean continueFighting;
            boolean triedToFlee = false;

            int encounterChance = random.nextInt(100);
            int enemyCount; // The number of enemies in the encounter
            int fightCount = 0; // The number of enemies the player has defeated
            int fleeCount = 0; // The number of times the player has fled an encounter

            if (encounterChance < 60 + (2 * player.getWeight())) {
                enemyCount = random.nextInt(Navigation.getPossibleEncounters()) + 1;
                printColour(" <=-- Combat --=>", ANSI_TEXT_YELLOW);
                lineBreak();

                if (enemyCount == 1) {
                    print("You have encountered an enemy!");
                } else {
                    print("You have encountered " + enemyCount + " enemy!");
                }
            } else {
                print("You did not encounter anything dangerous.");
                lineBreak();
                break; // Exit the loop if no encounter
            }
            lineBreak();

            while (fightCount < enemyCount) {
                continueFighting = true;

                // Selects an index for a random enemy every time a new fightr begins
                int enemySelection = random.nextInt(7); // 0 to 6, since there are 7 enemy types

                // Set random Enemy based on random index outside of the main while loop so it doesn't change every fight
                Enemy enemy = enemies[enemySelection];

                print("You're fighting a " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + ".");
                lineBreak();

                while (continueFighting) {
                    // Select a random attack for the selected enemy
                    int weaponSelection = random.nextInt(3); // 0 to 2, since each enemy has 3 attacks

                    // All enemy weapons
                    Attacks[] enemyAttacks = {
                        snakeAttacks[weaponSelection],
                        ravenAttacks[weaponSelection],
                        vultureAttacks[weaponSelection],
                        wolfAttacks[weaponSelection],
                        wildBoarAttacks[weaponSelection],
                        bearAttacks[weaponSelection],
                        sabretoothTigerAttacks[weaponSelection]
                    };

                    enemy.setAttack(enemyAttacks[enemySelection]);

                    // Variance in damage for attacks
                    int playerDamageVariance = random.nextInt(6) - 3; // Randomise damage with a variance of -3 to 2
                    int enemyDamageVariance = random.nextInt(4) - 1; // Randomise damage with a variance of -1 to 3

                    int totalPlayerDamage = player.getWeapon().getDamage() + playerDamageVariance;
                    int totalEnemyDamage = enemy.getAttack().getDamage() + enemyDamageVariance;

                    print("You have " + ANSI_TEXT_YELLOW + player.getHealth() + ANSI_RESET + " health.");
                    lineBreak();
                    print("What would you like to do? " + ANSI_TEXT_BLUE + "(1) Fight, (2) Run, (3) Use Item" + ANSI_RESET);
                    lineBreak();
                    printColour(" > ", ANSI_TEXT_GREEN);
                    String choice = sc.nextLine().toUpperCase();

                    if (choice.isEmpty()) {
                        clearLine(3);
                        continue;
                    } else {
                        char choiceChar = choice.charAt(0);

                        switch (choiceChar) {
                            // Attack sequenbce
                            case '1' -> {
                                // Player attack
                                print("You attempted to attack the " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + ".");
                                lineBreak();
                                playerAttackRoll = random.nextInt(6) + 1; // 1-6 instead of 0-5

                                if (player.getWeapon().getAmmo() <= 0) {
                                    // Search for an ammo item in the player's backpack
                                    boolean foundAmmo = false;
                                    int backpackSize = player.showBackpack().split("\n").length;
                                    for (int i = 0; i < backpackSize; i++) {
                                        Items item = player.getBackpackItem(i);
                                        if (item.getType() == 1) { // type 1 = ammo
                                            player.getWeapon().setAmmo(player.getWeapon().getAmmo() + item.getValue());
                                            print("You were out of ammo. You quickly used the " + ANSI_TEXT_YELLOW + item.getName() + ANSI_RESET + " and added " + ANSI_TEXT_YELLOW + item.getValue() + " ammo to your " + player.getWeapon().getName() + ANSI_RESET + ".");
                                            player.removeBackpackItem(item);
                                            foundAmmo = true;
                                            break;
                                        }
                                    }
                                    if (!foundAmmo) {
                                        print("You ran out of ammo... ");
                                        continueFighting = false;
                                        alive = false;
                                        fightCount = 100;
                                        break;
                                    }
                                    lineBreak();
                                }

                                // Player attack sequence - failed to attack, attacked successfully
                                if (playerAttackRoll <= 1) {
                                    print("Your attack missed the " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + ".");
                                } else {
                                    print("You hit the " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " for " + ANSI_TEXT_YELLOW + totalPlayerDamage + ANSI_RESET + " damage.");
                                    enemy.setHealth(enemy.getHealth() - (totalPlayerDamage));
                                }
                                lineBreak();

                                player.getWeapon().setAmmo(player.getWeapon().getAmmo() - 1);

                                // Enemy attack sequence - failed to attack, attacked successfully - only run sequence if the enemy still has health
                                if (enemy.getHealth() > 0) {
                                    print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " has " + ANSI_TEXT_YELLOW + enemy.getHealth() + ANSI_RESET + " health.");
                                    lineBreak();
                                    print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET  + " attempted to attack you with its " + ANSI_TEXT_YELLOW + enemy.getAttack().getName() + ANSI_RESET + ".");
                                    lineBreak();
                                    enemyAttackRoll = random.nextInt(6) + 1; // 1-6 instead of 0-5

                                    if (enemyAttackRoll <= 1) {
                                        print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " couldn't hit you." );
                                    } else {
                                        print("You got hit for " + ANSI_TEXT_YELLOW + totalEnemyDamage + ANSI_RESET + " damage.");
                                        player.setHealth(player.getHealth() - totalEnemyDamage);
                                    }
                                    lineBreak();
                                }
                            }
                            // Player flee sequence
                            case '2' -> {
                                int fleeFightChance;

                                // If the encounter has more than 1 enemy, the player cannot flee
                                if (triedToFlee) {
                                    print("You've already tried to flee and failed, continue fighting.");
                                } else {
                                    if (enemyCount > 1) {
                                        print("You cannot flee during an encounter with multiple enemies.");
                                        // Manually set Flee Fighting
                                        fleeFightChance = 0;
                                    } else {
                                        // Otherwise a random number out of 100 is generated
                                        fleeFightChance = random.nextInt(100);
                                    }

                                    if (fleeFightChance < 50 - fleeCount) {
                                        // 20 < 50
                                        print("You managed to escape successfully. Continue your mission.");
                                        if (fleeCount != 30) {
                                            fleeCount+=10;
                                        }
                                        fightCount = 100;
                                        continueFighting = false;
                                    } else {
                                        print("You failed to escape, the battle continues.");
                                        lineBreak();
                                        print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " has " + ANSI_TEXT_YELLOW + enemy.getHealth() + ANSI_RESET + " health.");
                                        lineBreak();
                                        print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET  + " attempted to attack you with its " + ANSI_TEXT_YELLOW + enemy.getAttack().getName() + ANSI_RESET + ".");
                                        lineBreak();
                                        enemyAttackRoll = random.nextInt(6) + 1; // 1-5 instead of 0-5

                                        if (enemyAttackRoll <= 1) {
                                            print("The " + ANSI_TEXT_YELLOW + enemy.getName() + ANSI_RESET + " failed to attack you.");
                                        } else {
                                            print("You got hit for " + ANSI_TEXT_YELLOW + totalEnemyDamage + ANSI_RESET + " damage.");
                                            player.setHealth(player.getHealth() - totalEnemyDamage);
                                        }
                                    }
                                    triedToFlee = true;
                                }
                                lineBreak();
                            }
                            // Player uses item
                            case '3' -> {
                                Items.useItem(player);
                            }
                            default -> {
                                clearLine(3);
                            }
                        }
                    }

                    if (player.getHealth() <= 0) {
                        continueFighting = false;
                        alive = false;
                        fightCount = 100;
                    }

                    if (enemy.getHealth() <= 0) {
                        continueFighting = false;
                        fightCount++;
                        if (fightCount >= enemyCount) {
                            print("You survived this encounter, continue your mission.");
                            lineBreak();
                            break;
                        } else {
                            print("You survived this enemy, watch out for the next one.");
                            lineBreak();
                        }
                    }
                }
                lineBreak();
            }
            if (fightCount >= enemyCount) {
                break;
            }
        }
        alive = false;
    }
}