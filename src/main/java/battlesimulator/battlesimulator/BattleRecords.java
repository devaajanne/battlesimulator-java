package battlesimulator.battlesimulator;

import java.util.ArrayList;

public class BattleRecords {

    int battleStartTime, battleEndTime, battleTurns;

    public BattleRecords(int battleStartTime, int battleEndTime, int battleTurns) {
        this.battleStartTime = battleStartTime;
        this.battleEndTime = battleEndTime;
        this.battleTurns = battleTurns;
    }

    public String showCombatants(Character playerCharacter, ClassEnemy enemy) throws InterruptedException {
        String returnString =
            "- The battle's fighters were the " + playerCharacter.getCharacterClass() +
            " " + playerCharacter.getName() +
            " and " + FormatMethods.getCorrectIndefiniteArticle(enemy.getDescription()) +
            " " + enemy.getDescription() +
            " " + enemy.getName() + ".";

        return returnString;
    };

    public String showInitiativeWinner(ArrayList<Character> fightOrder) {
        String returnString = 
            "- " + FormatMethods.getCorrectDefiniteArticle(fightOrder.get(0)) + fightOrder.get(0).getName() + 
            " won the initiative.";

        return returnString;
    }

    public String showWinner(Character playerCharacter, ClassEnemy enemy) throws InterruptedException {
        String returnString = 
            (playerCharacter.getWinner() ? 
            "- " + playerCharacter.getName() + " won the battle." :
            "- The " + enemy.getName() + " won the battle.");

        return returnString;
    }

    public String showBattleTime() throws InterruptedException {
        String returnString;

        int seconds, minutes;
        int battleTime = this.getBattleEndTime() - this.getBattleStartTime();
        String minuteString = "minutes", secondsString = "seconds";

        if (battleTime < 60) {
            returnString =  "- The fighters fought for " + battleTime + " " + secondsString + ".";
        } else if (battleTime == 60) {
            minuteString = "minute";
            returnString =  "- The fighters fought for " + (battleTime / 60) + " " + minuteString + ".";
        } else if (battleTime % 60 == 0) {
            returnString =  "- The fighters fought for " + (battleTime / 60) + " " + minuteString + ".";
        } else {

            seconds = battleTime % 60;
            if (seconds == 1) {
                secondsString = "second";
            }

			minutes = (battleTime - seconds) / 60;
            if (minutes == 1) {
                minuteString = "minute";
            }

			returnString =  "- The fighters fought for " + minutes + " " + minuteString + " and " + seconds + " " + secondsString + ".";
        }

        return returnString;
	}

    public String showBattleRounds() throws InterruptedException {
        String returnString;

        int rounds = (
            this.getBattleTurns() % 2 == 0 ? this.getBattleTurns() / 2 : (this.getBattleTurns() / 2) + 1);
        
        String roundString = (rounds == 1 ? " round" : " rounds");

        returnString =  "- The battle lasted for " + rounds + roundString + ".";

        return returnString;
    }

    public String showHitsAndMisses(Character character) throws InterruptedException {
        String hitString = (character.getHits() == 1 ? " time" : " times");
        String missesString = (character.getMisses() == 1 ? " time" : " times");

        String returnString =
            "- " + FormatMethods.getCorrectDefiniteArticle(character) + character.getName() +
            " hit " + character.getHits() + hitString +
            " and missed " + character.getMisses() + missesString + ".";

        return returnString;
    }

    public int getBattleStartTime() {
        return this.battleStartTime;
    }

    public void setBattleStartTime(int battleStartTime) {
        this.battleStartTime = battleStartTime;
    }

    public int getBattleEndTime() {
        return this.battleEndTime;
    }

    public void setBattleEndTime(int battleEndTime) {
        this.battleEndTime = battleEndTime;
    }
    
    public int getBattleTurns() {
        return this.battleTurns;
    }

    public void setBattleTurns(int battleTurns) {
        this.battleTurns = battleTurns;
    }
}
