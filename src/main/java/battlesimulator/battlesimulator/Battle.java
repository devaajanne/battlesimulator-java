package battlesimulator.battlesimulator;

import java.util.ArrayList;

public class Battle {

    // This method sets the initiative order, ie. the order in which the characters attack one another
    public static ArrayList<Character> rollInitiative(Character playCharacter, ClassEnemy enemy) throws InterruptedException {
		int highestInitiative = 0;

        ArrayList<Character> fightOrder = new ArrayList<Character>();
        fightOrder.add(null);

        ArrayList<Character> fighterList = new ArrayList<Character>();
        fighterList.add(playCharacter);
        fighterList.add(enemy);

        System.out.println("But first, ROLL FOR INITIATIVE!!!");
        Thread.sleep(3000);
		
        // Rolls initiative for both fighters
		for (Character character : fighterList) {
			character.setInitiative(DiceRolls.rollDice(20));
		}

        // Checks if the player character and the enemy have the same initiative roll; if yes, one of them gets a +1 to the roll to avoid same rolls
        if (fighterList.get(0).getInitiative() == fighterList.get(1).getInitiative()) {
            int randomFighterForPlus1Initiative = DiceRolls.rollDice(2) - 1;
            fighterList.get(randomFighterForPlus1Initiative).setInitiative(fighterList.get(randomFighterForPlus1Initiative).getInitiative() + 1);
        }
		
        // Determines who got the higher initiative
		for (Character character : fighterList) {
			if (character.getInitiative() > highestInitiative) {
				highestInitiative = character.getInitiative();
			}
		}
		
        // Assings the fighters to initiative order 
		for (Character character : fighterList) {
			if (character.getInitiative() == highestInitiative) {
				fightOrder.add(0, character);
			} else {
				fightOrder.add(1, character);
			}
		}

        fightOrder.remove(null);

        // Prints the initiative winner
        System.out.println(FormatMethods.getCorrectDefiniteArticle(fightOrder.get(0)) + fightOrder.get(0).getName() + " wins the initiative!"+
         " (" + fightOrder.get(0).getInitiative() + " vs " + fightOrder.get(1).getInitiative() + ")\n");
        Thread.sleep(3000);

		return fightOrder;
	}

    // This methods switches the attacker and the target, so both opponents attack on their turn
    public static int switchTarget(int targetIndex) {

        // Returns 0 if target's index is 1
        // Returns 1 if target's index is 0
        targetIndex = (targetIndex == 1 ? 0 : 1);
		return targetIndex;
	}

    // Prints both the player character's and the enemy's remaining health
    public static void printRemaningHealth(Character attacker, Character target) {
        if (target.getHealth() > 0) {
            if (target instanceof ClassEnemy) {
                System.out.println(
                    FormatMethods.rightPad(
                        FormatMethods.capitalize(attacker.getName()) + ": " + attacker.getHealth() + "/" + attacker.getMaxHealth(), 23) +
                    FormatMethods.capitalize(target.getName()) + ": " + target.getHealth() + "/" + target.getMaxHealth()
                );

            } else {
                System.out.println(
                    FormatMethods.rightPad(
                        FormatMethods.capitalize(target.getName()) + ": " + target.getHealth() + "/" + target.getMaxHealth(), 23) + 
                    FormatMethods.capitalize(attacker.getName()) + ": " + attacker.getHealth() + "/" + attacker.getMaxHealth()
                );
            }
        }
    }

    public static void showEnemy(ClassEnemy enemy) throws InterruptedException {
        System.out.println("Your opponent is " + FormatMethods.getCorrectIndefiniteArticle(enemy.getName()) + " " + enemy.getName() + ", a truly " + enemy.getDescription() + " " + enemy.getCharacterClass() + "!");
        Thread.sleep(2000);
    }
    
    // This method initiates the battle between the player character and the enemy
    public static void beginBattle(Character playerCharacter, ClassEnemy enemy) throws InterruptedException {
        ArrayList<Character> fightOrder = rollInitiative(playerCharacter, enemy);
        int targetIndex = 1, battleTurns = 0, hitRoll;
        boolean battleEnd = false;
        BattleRecords battleRecords = new BattleRecords(0, 0, 0);

        System.out.println("The battle between " + playerCharacter.getName() + " and " + FormatMethods.getCorrectDefiniteArticle(enemy).toLowerCase() + enemy.getName() + " begins!");
        Thread.sleep(3000);

        long battleStartTime = System.currentTimeMillis();

        do {
            for (Character character : fightOrder) {

                // Resets character's action counter
                character.setActionsPerRound(0);

                // Prints an empty line between rounds to keep the terminal more readable
                if ((battleTurns) % 2 == 0) {
                    System.out.println("");
                }

                // If the player character is a mage, there is a change of them using their defensive spell instead of attacking
                boolean mageHeal = (DiceRolls.rollDice(4) == 4);

                if (character.getCharacterClass().equals("mage") && character.getHealth() < character.getMaxHealth() / 2) {
                    if (mageHeal) {
                        ((ClassPlayer) character).useMageDefenceSkill();

                        // Adds 1 to the battleturns counter
                        battleTurns = battleTurns + 1;
                        
                        // Prints out the remaining health of both characters
                        Battle.printRemaningHealth(playerCharacter, enemy);
                    }
                }

                // Only allow the character to attack if they have not taken an action during the round (ie. their action counter is 0)
                if (character.getActionsPerRound() == 0) {

                    // Character rolls to hit; if hitRoll higher or the same as target's armor class or if hitRoll is 20 (automatic hit and critical damage)
                    hitRoll = character.rollToHit();
                    if (hitRoll >= fightOrder.get(targetIndex).getArmorClass() || hitRoll == 20) {

                        // If the character hits, the character attacks
                        character.attack(fightOrder.get(targetIndex), hitRoll);

                        // Adds 1 to the battleturns counter
                        battleTurns = battleTurns + 1;
                
                        // Set characters action counter to 1 (only one attack per round)
                        character.setActionsPerRound(1);
                        Thread.sleep(1000);

                        // if the target's health is reduced to zero, the target dies
                        if (character.kill(fightOrder.get(targetIndex))) {

                            if (fightOrder.get(targetIndex) instanceof ClassEnemy) {
                                Thread.sleep(2000);
                                System.out.println("\nCongratulations! You are victorious!\n");
                                Thread.sleep(4000);
                            } else {
                                Thread.sleep(2000);
                                System.out.println("\nOh well, I guess it is time for another funeral...\n");
                                Thread.sleep(4000);
                            }

                            battleEnd = true;
                            break;
                        }
                    } // if the character misses to attack the target
                        else {
                            character.miss(fightOrder.get(targetIndex));

                            // Adds 1 to the battleturns counter
                            battleTurns = battleTurns + 1;

                            // Set characters action counter to 1
                            character.setActionsPerRound(1);
                            Thread.sleep(1000);
                        }
                }

                // the attacker and the target switch
                targetIndex = switchTarget(targetIndex);
            }
        } while (battleEnd == false);

        long battleEndTime = System.currentTimeMillis();

        // Log in battle records
        battleRecords.setBattleStartTime((int) (battleStartTime / 1000));
        battleRecords.setBattleEndTime((int) (battleEndTime / 1000));
        battleRecords.setBattleTurns(battleTurns);

        //Show battle records to the player
        System.out.println("As the record keeper, I must record this battle to the arena's records.\n");
        Thread.sleep(4000);

        System.out.println("Darkwood arena records, battle #" + DiceRolls.rollDice(10000) + ":");

        Thread.sleep(3000);
        System.out.println(battleRecords.showCombatants(playerCharacter, enemy));
        Thread.sleep(3000);
        System.out.println(battleRecords.showInitiativeWinner(fightOrder));
        Thread.sleep(3000);
        System.out.println(battleRecords.showWinner(playerCharacter, enemy));
        Thread.sleep(3000);
        System.out.println(battleRecords.showBattleTime());
        Thread.sleep(3000);
        System.out.println(battleRecords.showBattleRounds());
        Thread.sleep(3000);
        System.out.println(battleRecords.showHitsAndMisses(playerCharacter));
        Thread.sleep(3000);
        System.out.println(battleRecords.showHitsAndMisses(enemy));
        Thread.sleep(4000);
    }
}
