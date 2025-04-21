package battlesimulator.battlesimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BattlesimulatorApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BattlesimulatorApplication.class, args);

        String startSimulation = "yes";

        while (startSimulation.equals("yes")) {
            // Welcome the player to the simulator
            System.out.println("\nWelcome, adventurer!\n");
            Thread.sleep(2000);
            System.out.println(
                    "You have arrived to the Darkwood arena to pit yourself against mighty foes and dangerous beasts.\n");
            Thread.sleep(5000);
            System.out.println(
                    "My name is Indricus. I am the arena's record keeper: I record all the battles and their outcomes.\n");
            Thread.sleep(5000);

            // Create the player character
            Character playerCharacter = CharacterCreation.createCharacter();

            // Create the enemy
            ClassEnemy enemy = CharacterCreation.createEnemy();

            // Show enemy info
            Battle.showEnemy(enemy);
            Thread.sleep(3000);

            // Wish the player good luck
            System.out.println("\nMay the gods of battle smile favourably to you! I wonder who will survive...\n");
            Thread.sleep(5000);

            // Begin battle: initiative roll, then battle according to the initiative order,
            // and lastly show the battle records
            Battle.beginBattle(playerCharacter, enemy);

            // Say goodbye to the player
            System.out.println(
                    "\nThe Darkwood arena has claimed yet another soul, but, I am sure that more are to come...\n");
            Thread.sleep(3000);
            System.out.println("And I am here to greet them.\n");
            Thread.sleep(5000);


            // Ask for another game
            Scanner askForNewGame = new Scanner(System.in);

            System.out.println("Thanks for trying the simulator! Do you want to simulate another battle? (yes/no)");

            while (true) {
                startSimulation = askForNewGame.nextLine().toLowerCase();
                
                if (startSimulation.equals("no") || startSimulation.equals("yes")) {
                    break;
                }

                System.out.println("Sorry! I need a yes or a no.");
            }

            if (startSimulation.equals("no")) {
                System.out.println("Bye!");
                break;
            }
        }
    }
}
