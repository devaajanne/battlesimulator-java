package battlesimulator.battlesimulator;

import java.util.Scanner;

import java.util.HashSet;

public class ClassPlayer extends Character {

    String offenseSkill, defenceSkill;

    public ClassPlayer(String name, String characterClass, int maxHealth, int armorClass, HashSet<String> resistances, HashSet<String> weaknesses, Weapon weapon, int initiative, int hits, int misses, int actionsPerRound, boolean knowTargetResistances, boolean knowTargetWeaknesses, boolean winner, String offenseSkill, String defenceSkill) {
        super(name, characterClass, maxHealth, armorClass, resistances, weaknesses, weapon, initiative, hits, misses, actionsPerRound, knowTargetResistances, knowTargetWeaknesses, winner);
        this.offenseSkill = offenseSkill;
        this.defenceSkill = defenceSkill;
    }

    public void useMageDefenceSkill() throws InterruptedException {
        // Heals the player instead of attacking
        int healing = DiceRolls.rollDice(6);
        this.setHealth(this.getHealth() + healing);

        if (this.getHealth() > this.getMaxHealth()) {
            healing = this.getHealth() - this.getMaxHealth();
            this.setHealth(this.getMaxHealth());
        }

        // Set characters action counter to 1 (no healing and attacking on the same round)
        this.setActionsPerRound(1);

        System.out.print(FormatMethods.rightPad(this.getName() + " heals for " + healing + " " + FormatMethods.pointsSingularPlural(healing) + " of health!", 80));
        Thread.sleep(1000);
    }

    public int useRogueDefenceSkill(Character attacker) throws  InterruptedException {
        // Chance to dodge all damage instead of attacking
        int dodgedDamage = 0;

        System.out.print(FormatMethods.rightPad(this.getName() + " dodges the " + attacker.getName() + "'s attack!", 80));
        Thread.sleep(1000);

        return dodgedDamage;
    }

    public int useWarriorDefenceSkill(Character attacker, int attackDamage) throws InterruptedException {
         // Chance to block half of the damage instead of attacking
        int blockedDamage = attackDamage / 2;

        blockedDamage = blockedDamage == 0 ? 1 : blockedDamage;

        System.out.println(this.getName() + " blocks half of the " + attacker.getName() + "'s attack!");
        Thread.sleep(1000);
        
        return blockedDamage;
    }

    public static Weapon warriorChooseWeapon() throws InterruptedException {
        Scanner askPlayerChosenWeapon = new Scanner(System.in);
        String playerChosenWeapon;

        System.out.println("As a warrior, you carry all of these weapons into the arena:\n");
        Thread.sleep(3000);

        while (true) {
            System.out.println(
                FormatMethods.rightPad("WEAPON", 10) + 
                FormatMethods.rightPad("DAMAGE", 10) + 
                "DAMAGE TYPE");
            Thread.sleep(500);
            System.out.println(
                FormatMethods.rightPad("sword", 10) + 
                FormatMethods.rightPad("d8", 10) + 
                "slashing");
            Thread.sleep(500);
            System.out.println(
                FormatMethods.rightPad("spear", 10) + 
                FormatMethods.rightPad("d6", 10) + 
                "piercing");
            Thread.sleep(500);
            System.out.println(
                FormatMethods.rightPad("mace", 10) + 
                FormatMethods.rightPad("d6", 10) + 
                "bludgeoning");
            Thread.sleep(2500);

            System.out.print("\nWith which weapon are you starting the battle? ");
            playerChosenWeapon = askPlayerChosenWeapon.nextLine().toLowerCase();

            if (playerChosenWeapon.equals("sword")) {
                Weapon chosenWeapon = new Weapon("sword", 8, "slashing");
                System.out.println("Ah, I see you chose a sword. Let's see if you slash your way to victory!\n");
                Thread.sleep(3000);
                return chosenWeapon;
            } else if (playerChosenWeapon.equals("spear")) {
                Weapon chosenWeapon = new Weapon("spear", 6, "piercing");
                System.out.println("Ah, I see you chose a spear. Let's see if you pierce your way to victory!\n");
                Thread.sleep(3000);
                return chosenWeapon;
            } else if (playerChosenWeapon.equals("mace")) {
                Weapon chosenWeapon = new Weapon("mace", 6, "bludgeoning");
                System.out.println("Ah, I see you chose a mace. Let's see if you bash your way to victory!\n");
                Thread.sleep(3000);
                return chosenWeapon;
            } else {
                System.out.println("...but you only carry a sword, a spear, or a mace. Let me ask you again.\n");
                Thread.sleep(3000);
            }

        }
    }

    public void warriorSwitchWeapon(String targetResistance) throws InterruptedException {
        int weaponNumber = DiceRolls.rollDice(2);

        if (targetResistance.equals("slashing")) {
            if (weaponNumber == 1) {
                this.setWeapon(new Weapon("spear", 8, "piercing"));
            } else if (weaponNumber == 2)
                this.setWeapon(new Weapon("mace", 8, "bludgeoning"));

        } else if (targetResistance.equals("piercing")) {
            if (weaponNumber == 1) {
                this.setWeapon(new Weapon("sword", 8, "slashing"));
            } else if (weaponNumber == 2)
                this.setWeapon(new Weapon("mace", 8, "bludgeoning"));

        } else if (targetResistance.equals("bludgeoning")) {
            if (weaponNumber == 1) {
                this.setWeapon(new Weapon("sword", 8, "slashing"));
            } else if (weaponNumber == 2)
                this.setWeapon(new Weapon("spear", 8, "piercing"));
        }

        System.out.println(this.getName() + " switches their weapon to " + this.getWeapon().getName() + "!");
        Thread.sleep(2000);
    }
    
    public String getOffenseSkill() {
        return this.offenseSkill;
    }

    public void setOffenseSkill(String offenseSkill) {
        this.offenseSkill = offenseSkill;
    }

    public String getDefenceSkill() {
        return this.defenceSkill;
    }

    public void setDefenceSkill(String defenceSkill) {
        this.defenceSkill = defenceSkill;
    }

    @Override
    public String toString() {
        return "{" +
            " offenseSkill='" + getOffenseSkill() + "'" +
            ", defenceSkill='" + getDefenceSkill() + "'" +
            "}";
    }
}