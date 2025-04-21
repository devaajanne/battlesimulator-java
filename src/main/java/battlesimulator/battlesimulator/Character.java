package battlesimulator.battlesimulator;

import java.util.HashSet;

public class Character {

    private String name, characterClass;
    private int health, maxHealth, armorClass, initiative, hits, misses, actionsPerRound;
    private HashSet<String> resistances, weaknesses;
    private Weapon weapon;
    private boolean winner, knowTargetResistances, knowTargetWeaknesses;

    public Character(String name, String characterClass, int maxHealth, int armorClass, HashSet<String> resistances, HashSet<String> weaknesses, Weapon weapon, int initiative, int hits, int misses, int actionsPerRound, boolean knowTargetResistances, boolean knowTargetWeaknesses, boolean winner) {
        this.name = name;
        this.characterClass = characterClass;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.armorClass = armorClass;
        this.resistances = resistances;
        this.weaknesses = weaknesses;
        this.weapon = weapon;
        this.initiative = initiative;
        this.hits = hits;
        this.misses = misses;
        this.actionsPerRound = actionsPerRound;
        this.knowTargetResistances = knowTargetResistances;
        this.knowTargetWeaknesses = knowTargetWeaknesses;
        this.winner = winner;
    }

    public void attack(Character target, int hitRoll) throws InterruptedException {
        // Adds one to the characters hit counter
        this.setHits(this.getHits() + 1);

        // Modifies the print if character critically hits (= hitRoll == 20)
        String criticalString = hitRoll == 20 ? " CRITICALLY" : "";

        // Rolls for character's damage
        int attackDamage = DiceRolls.rollDice(this.getWeapon().getDamage());

        // If the player character is a rogue, we check if the rogue double strikes (25 % chance)
        boolean doubleStrike = (DiceRolls.rollDice(4) == 4);

        // Modifies the print if character is rogue and double strikes
        String hitString = this.characterClass.equals("rogue") && doubleStrike ? " DOUBLE STRIKES " : " hits ";

        if (hitString.equals(" DOUBLE STRIKES ")) {
            attackDamage = attackDamage * 2;
        }

        // If character critcally hits, add weapons' max damage to the damage
        attackDamage = hitRoll == 20 ? attackDamage + this.getWeapon().getDamage() : attackDamage;

        // If the player character is a mage, their attack's damage type is randomised
        if (this.getCharacterClass().equals("mage")) {
            this.getWeapon().setDamageType(Weapon.randomElementalDamageType());
        }
        
        // Adjusts the damage according to the target's resistances and weaknesses
        attackDamage = this.adjustDamageResistancesAndWeaknesses(attackDamage, target);

        // String formatting for point/points
        String points = FormatMethods.pointsSingularPlural(attackDamage);

        // Checks if the enemy is attacking (so that rogues and warriors can REact to the enemy's attack)
        if (this instanceof ClassEnemy) {

            // Rogue dodge (25 % chance)
            boolean rogueDodge = (DiceRolls.rollDice(4) == 4);
            if (((ClassPlayer) target).getCharacterClass().equals("rogue") && rogueDodge) {

                // Nullify the incoming damage because of the dodge
                attackDamage = ((ClassPlayer) target).useRogueDefenceSkill(this);

                // Turn the dodged attack from a hit to a miss
                this.setHits(this.getHits() - 1);
                this.setMisses(this.getMisses() + 1);

                // Prints out the remaining health of both characters
                Battle.printRemaningHealth(this, target);
            }

            // Warrior block (25 % chance)
            boolean warriorBlock = (DiceRolls.rollDice(4) == 4);
            if (((ClassPlayer) target).getCharacterClass().equals("warrior") && warriorBlock) {

                // Halve the incoming damage because of the damage
                attackDamage = ((ClassPlayer) target).useWarriorDefenceSkill(this, attackDamage);
            }
        }

        // Actually reduces the target's health
        this.dealDamage(attackDamage, target);

        // Prints out the dealt damage to the player, if damage has been dealt (rogue's dodge)
        if (attackDamage > 0) {
            System.out.print(
                FormatMethods.rightPad(
                    FormatMethods.getCorrectDefiniteArticle(this) + this.getName() + criticalString +
                    hitString + FormatMethods.getCorrectDefiniteArticle(target).toLowerCase() + target.getName() +
                    " for " + attackDamage + " " + points +
                    " of "+ this.getWeapon().getDamageType() + " damage!",  80)
            );

            // Prints out the remaining health of both characters
            Battle.printRemaningHealth(this, target);
        }

        // Player character notices the target's resistances
        if (this.knowTargetResistances == false && target instanceof ClassEnemy && this.getActionsPerRound() == 0) {
            if (target.getResistances().contains(this.getWeapon().getDamageType())) {
                this.noticeResistances(target);

                // Set characters action counter to 1 (only one attack per round)
                this.setActionsPerRound(1);

                if (((ClassPlayer) this).getCharacterClass().equals("warrior")) {
                    ((ClassPlayer) this).warriorSwitchWeapon(this.getWeapon().getDamageType());
                }
            }
        }

        // Player character notices the target's weaknesses
        if (this.knowTargetWeaknesses == false && target instanceof ClassEnemy && this.getActionsPerRound() == 0) {
            if (target.getWeaknesses().contains(this.getWeapon().getDamageType())) {
                this.noticeWeaknesses(target);
            
                // Set characters action counter to 1 (only one attack per round)
                this.setActionsPerRound(1);
            }
        }
    }

    // Checks if either of the characters has dealt enough damage to reduce the target's health to zero
    public boolean kill(Character target) {
        if (target.getHealth() <= 0) {

            // Stores the winner boolean in the character's attributes
            this.winner = true;

            // Prints out the killing blow
            System.out.println(
                FormatMethods.rightPad(
                    FormatMethods.getCorrectDefiniteArticle(this) + this.getName() +
                    " slays " + FormatMethods.getCorrectDefiniteArticle(target).toLowerCase() + target.getName() +
                    "!\n",  70));
            return true;
        }

        return false;
    }

    public void miss(Character target) {

        // Adds one to the characters miss counter
        this.setMisses(this.getMisses() + 1);

        // Prints out the miss
        System.out.print(
            FormatMethods.rightPad(
                FormatMethods.getCorrectDefiniteArticle(this) + this.getName() + " misses!", 80));

        // Prints out the remaining health of both characters
        Battle.printRemaningHealth(this, target);
    }

    // Simple method to see if the character hits with the attack (opposed to the targets armor class)
    public int rollToHit() {
        int hit = DiceRolls.rollDice(20);
        return hit;
    }

    public void noticeResistances(Character target) throws InterruptedException {
        Thread.sleep(1000);
            System.out.println(this.getName() + " notices the " + target.getName() + "'s resistance to " + this.getWeapon().getDamageType() + " damage!");
            this.setKnowTargetResistances(true);
            Thread.sleep(1000);
    }

    public void noticeWeaknesses(Character target) throws InterruptedException {
        Thread.sleep(1000);
            System.out.println(this.getName() + " notices the " + target.getName() + "'s weakness to " + this.getWeapon().getDamageType() + " damage!");
            this.setKnowTargetWeaknesses(true);
            Thread.sleep(1000);
        }

    public int adjustDamageResistancesAndWeaknesses(int attackDamage, Character target) {
        // Checks the targets resistances and weaknesses and adjust the damage accordingly: 1/2 for resistance, *2 for weakness
        if (target.getResistances().contains(this.weapon.getDamageType())) {
            attackDamage = attackDamage / 2;
            if (attackDamage == 0) {
                attackDamage = 1;
            }

        } else if (target.getWeaknesses().contains(this.weapon.getDamageType())) {
            attackDamage = attackDamage * 2;
        }

        return attackDamage;
    }

    public void dealDamage(int attackDamage, Character target) {
        target.setHealth(target.getHealth() - attackDamage);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterClass() {
        return this.characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getArmorClass() {
        return this.armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public HashSet<String> getResistances() {
        return this.resistances;
    }

    public void setResistances(HashSet<String> resistances) {
        this.resistances = resistances;
    }

    public HashSet<String> getWeaknesses() {
        return this.weaknesses;
    }

    public void setWeaknesses(HashSet<String> weaknesses) {
        this.weaknesses = weaknesses;
    }
    
    public Weapon getWeapon() {
        return this.weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getInitiative() {
        return this.initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getHits() {
        return this.hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMisses() {
        return this.misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getActionsPerRound() {
        return this.actionsPerRound;
    }

    public void setActionsPerRound(int actionsPerRound) {
        this.actionsPerRound = actionsPerRound;
    }

    public boolean isKnowTargetResistances() {
        return this.knowTargetResistances;
    }

    public boolean getKnowTargetResistances() {
        return this.knowTargetResistances;
    }

    public void setKnowTargetResistances(boolean knowTargetResistances) {
        this.knowTargetResistances = knowTargetResistances;
    }

    public boolean isKnowTargetWeaknesses() {
        return this.knowTargetWeaknesses;
    }

    public boolean getKnowTargetWeaknesses() {
        return this.knowTargetWeaknesses;
    }

    public void setKnowTargetWeaknesses(boolean knowTargetWeaknesses) {
        this.knowTargetWeaknesses = knowTargetWeaknesses;
    }

    public boolean isWinner() {
        return this.winner;
    }

    public boolean getWinner() {
        return this.winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", characterClass='" + getCharacterClass() + "'" +
            ", health='" + getHealth() + "'" +
            ", maxHealth='" + getMaxHealth() + "'" +
            ", armorClass='" + getArmorClass() + "'" +
            ", initiative='" + getInitiative() + "'" +
            ", hits='" + getHits() + "'" +
            ", misses='" + getMisses() + "'" +
            ", actionsPerRound='" + getActionsPerRound() + "'" +
            ", resistances='" + getResistances() + "'" +
            ", weaknesses='" + getWeaknesses() + "'" +
            ", weapon='" + getWeapon() + "'" +
            ", winner='" + isWinner() + "'" +
            ", knowTargetResistances='" + isKnowTargetResistances() + "'" +
            ", knowTargetWeaknesses='" + isKnowTargetWeaknesses() + "'" +
            "}";
    }
    
}