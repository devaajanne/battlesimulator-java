package battlesimulator.battlesimulator;

import java.util.Random;

public class DiceRolls {

    public static int rollDice(int die) {
		Random rand = new Random();
		int roll = rand.nextInt(die) + 1;
		
        return roll;
    }
}