package battlesimulator.battlesimulator;

import java.util.ArrayList;


public class Weapon {
    
    private String name, damageType;
    private int damage;

    public Weapon() {
    }

    public Weapon(String name, int damage, String damageType) {
        this.name = name;
        this.damage = damage;
        this.damageType = damageType;
    }

    public static String randomElementalDamageType() {
        String returnString;
        ArrayList<String> elementalDamageTypes = new ArrayList<>();
        elementalDamageTypes.add("fire");
        elementalDamageTypes.add("frost");
        elementalDamageTypes.add("lightning");

        returnString = elementalDamageTypes.get(DiceRolls.rollDice(elementalDamageTypes.size()) - 1);

        return returnString;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getDamageType() {
        return this.damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", damage='" + getDamage() + "'" +
            ", damage type='" + getDamageType() + "'" +
            "}";
    }

}