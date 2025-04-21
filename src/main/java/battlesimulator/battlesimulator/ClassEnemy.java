package battlesimulator.battlesimulator;

import java.util.HashSet;

public class ClassEnemy extends Character {

    private String description;

    public ClassEnemy(String name, String characterClass, int maxHealth, int armorClass, HashSet<String> resistances, HashSet<String> weaknesses,  Weapon weapon, int initiative, int hits, int misses, int actionsPerRound, boolean knowTargetResistances, boolean knowTargetWeaknesses, boolean winner, String description) {
        super(name, characterClass, maxHealth, armorClass, resistances, weaknesses, weapon, initiative, hits, misses, actionsPerRound, knowTargetResistances, knowTargetWeaknesses, winner);
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
            " description='" + getDescription() + "'" +
            "}";
    }
}