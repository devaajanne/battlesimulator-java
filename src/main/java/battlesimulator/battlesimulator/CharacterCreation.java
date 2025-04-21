package battlesimulator.battlesimulator;

import java.util.Scanner;

import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CharacterCreation {

    // Method to ask the player what their character's name is
    public static String askPlayerCharacterName() throws InterruptedException {
        Scanner askPlayerCharacterName = new Scanner(System.in);
        String playerCharacterName;

        System.out.print("What is your name? ");
        playerCharacterName = askPlayerCharacterName.nextLine();

        System.out.println("Greetings, " + playerCharacterName + "! I'm looking forward to seeing you in the arena.\n");
        Thread.sleep(3000);

        return playerCharacterName;
    }

    // Method to ask the player what their character's class is
    public static String askPlayerCharacterClass(String playerCharacterName) throws InterruptedException {
        Scanner askPlayerCharacterClass = new Scanner(System.in);
        String playerCharacterClass;

        System.out.println("Now, " + playerCharacterName + ", tell me more about yourself. What are you capable of?\n");
        Thread.sleep(3000);

        while (true) {
            System.out.println(
                FormatMethods.rightPad("CLASS", 10) +
                FormatMethods.rightPad("HEALTH", 10) +
                FormatMethods.rightPad("AC", 6) +
                FormatMethods.rightPad("RESISTANCES", 22) +
                FormatMethods.rightPad("WEAKNESSES", 22) +
                FormatMethods.rightPad("WEAPON", 18) +
                FormatMethods.rightPad("DAMAGE", 10) +
                FormatMethods.rightPad("DAMAGE TYPE", 23) +
                "DESCRIPTION");
            Thread.sleep(500);
            System.out.println(
                FormatMethods.rightPad("Mage", 10) +
                FormatMethods.rightPad("40", 10) +
                FormatMethods.rightPad("12", 6) +
                FormatMethods.rightPad("fire & bludgeoning", 22) +
                FormatMethods.rightPad("lightning & piercing", 22) +
                FormatMethods.rightPad("elemental bolt", 18) +
                FormatMethods.rightPad("d6", 10) +
                FormatMethods.rightPad("fire/frost/lightning", 23) +
                "Blasts enemies with elemental spells and heals their own wounds");
            Thread.sleep(500);
            System.out.println(
                FormatMethods.rightPad("Rogue", 10) +
                FormatMethods.rightPad("50", 10) +
                FormatMethods.rightPad("14", 6) +
                FormatMethods.rightPad("lightning & piercing", 22) +
                FormatMethods.rightPad("frost & slashing", 22) +
                FormatMethods.rightPad("dual daggers", 18) +
                FormatMethods.rightPad("d4", 10) +
                FormatMethods.rightPad("piercing", 23) +
                "Stabs enemies with two daggers and dodges all incoming damage");
            Thread.sleep(500);
            System.out.println(
                FormatMethods.rightPad("Warrior", 10) +
                FormatMethods.rightPad("60", 10) +
                FormatMethods.rightPad("16", 6) +
                FormatMethods.rightPad("frost & slashing",22) +
                FormatMethods.rightPad("fire & bludgeoning", 22) +
                FormatMethods.rightPad("various", 18) +
                FormatMethods.rightPad("d6/d8", 10) +
                FormatMethods.rightPad("various", 23) +
                "Hits enemies with various weapons and blocks damage with a shield");
            Thread.sleep(2500);

            System.out.print("\nWhat is your class? ");
            playerCharacterClass = askPlayerCharacterClass.nextLine().toLowerCase();

            if (playerCharacterClass.equals("mage") ||
                playerCharacterClass.equals("rogue") ||
                playerCharacterClass.equals("warrior")) {
                    System.out.println("So you are a " + playerCharacterClass + ", huh, " + playerCharacterName + "? Well, let's see how you fare in the arena!\n");
                    Thread.sleep(3000);
                    return playerCharacterClass;
            } else {
                System.out.println("\nOutsiders are only allowed to the arena if they are a mage, a rogue or a warrior. Sorry!\n");
                Thread.sleep(3000);
            }
        }
    }

    public static Character createCharacter() throws InterruptedException {
        System.out.println("First, for the arena records, tell me who you are.\n");
        Thread.sleep(3000);

        // Initiates a new player character object
        ClassPlayer playerCharacter = null;

        // Asks for the player character's name
        String playerCharactername = askPlayerCharacterName();

        // Asks for the player character's class
        String playerCharacterClass = askPlayerCharacterClass(playerCharactername);

        // Creates the player character object based on player's choices
        if (playerCharacterClass.equals("mage")) {
            playerCharacter = new ClassPlayer(playerCharactername, playerCharacterClass, 40, 12, new HashSet<>(Arrays.asList("fire", "bludgeoning")), new HashSet<>(Arrays.asList("lightning", "piercing")), new Weapon("elemental bolt", 6, Weapon.randomElementalDamageType()), 0, 0, 0, 0, false, false, false, null, "heal");
        }
        
        if (playerCharacterClass.equals("rogue")) {
            playerCharacter = new ClassPlayer(playerCharactername, playerCharacterClass, 50, 14, new HashSet<>(Arrays.asList("lightning", "piercing")), new HashSet<>(Arrays.asList("frost", "slashing")), new Weapon("dual daggers", 4, "piercing"), 0, 0, 0, 0, false, false, false, null, "dodge");
        }
        
        if (playerCharacterClass.equals("warrior")) {
            Weapon chosenWeapon = ClassPlayer.warriorChooseWeapon();
            playerCharacter = new ClassPlayer(playerCharactername, playerCharacterClass, 60, 16, new HashSet<>(Arrays.asList("frost", "slashing")), new HashSet<>(Arrays.asList("fire", "bludgeoning")), chosenWeapon, 0, 0, 0, 0, false, false, false, null, "block");
        } 

        return playerCharacter;
    }

    public static ClassEnemy createEnemy() {
        Random rand = new Random();

        // Creates an empty list of enemies
        List<ClassEnemy> listOfEnemies = new ArrayList<ClassEnemy>();

        // Creates an enemy and adds it to the list of enemies
        ClassEnemy necromancer = new ClassEnemy("necromancer", "spellcaster", 40, 12, new HashSet<>(Arrays.asList("frost", "slashing")), new HashSet<>(Arrays.asList("fire", "bludgeoning")), new Weapon("frost bolt", 6, "frost"), 0, 0, 0, 0, false, false, false, "terrifying");
        listOfEnemies.add(necromancer);

        ClassEnemy sorcerer = new ClassEnemy("sorcerer", "spellcaster", 40, 12, new HashSet<>(Arrays.asList("lightning", "piercing")), new HashSet<>(Arrays.asList("frost", "slashing")), new Weapon("lightning bolt", 6, "lightning"), 0, 0, 0, 0, false, false, false, "shocking");
        listOfEnemies.add(sorcerer);

        ClassEnemy battlemage = new ClassEnemy("battlemage", "spellcaster", 40, 12, new HashSet<>(Arrays.asList("fire", "bludgeoning")), new HashSet<>(Arrays.asList("lightning", "piercing")), new Weapon("fire bolt", 6, "fire"), 0, 0, 0, 0, false, false, false, "scorching");
        listOfEnemies.add(battlemage);

        ClassEnemy wolf = new ClassEnemy("wolf", "beast", 50, 13, new HashSet<>(Arrays.asList("fire", "slashing")), new HashSet<>(Arrays.asList("lightning", "piercing")), new Weapon("fangs", 6, "piercing"), 0, 0, 0, 0, false, false, false, "vicious");
        listOfEnemies.add(wolf);

        ClassEnemy direSpider = new ClassEnemy("dire spider", "beast", 50, 13, new HashSet<>(Arrays.asList("frost", "bludgeoning")), new HashSet<>(Arrays.asList("fire", "slashing")), new Weapon("jaws", 6, "piercing"),  0, 0, 0, 0, false, false, false, "venomous");
        listOfEnemies.add(direSpider);

        ClassEnemy highwayman= new ClassEnemy("highwayman", "fighter", 60, 14, new HashSet<>(Arrays.asList("lightning", "piercing")), new HashSet<>(Arrays.asList("frost", "bludgeoning")), new Weapon("sword", 8, "slashing"), 0, 0, 0, 0, false, false, false, "intimidating");
        listOfEnemies.add(highwayman);

        ClassEnemy marauder = new ClassEnemy("marauder", "fighter", 60, 14, new HashSet<>(Arrays.asList("lightning", "bludgeoning")), new HashSet<>(Arrays.asList("frost", "piercing")), new Weapon("axe", 8, "slashing"), 0, 0, 0, 0, false, false, false, "mighty");
        listOfEnemies.add(marauder);

        ClassEnemy troll = new ClassEnemy("troll", "monster", 70, 10, new HashSet<>(Arrays.asList("frost", "slashing")), new HashSet<>(Arrays.asList("fire", "bludgeoning")), new Weapon("fists", 10, "bludgeoning"), 0, 0, 0, 0, false, false, false, "brutal");
        listOfEnemies.add(troll);

        ClassEnemy ogre = new ClassEnemy("ogre", "monster", 70, 10, new HashSet<>(Arrays.asList("fire", "piercing")), new HashSet<>(Arrays.asList("lightning", "slashing")), new Weapon("fists", 12, "bludgeoning"), 0, 0, 0, 0, false, false, false, "blood-thirsty");
        listOfEnemies.add(ogre);

        // Chooses a random enemy from the list for the player to fight against
        return listOfEnemies.get(rand.nextInt(listOfEnemies.size()));
    }
}