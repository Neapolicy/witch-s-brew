import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that holds player information for combat
 * contains methods that assist with player decision-making
 *
 * @author Matthew Lin
 * @version 1.???
 */
public class Protagonist {
    private int[] charStats = {8, 5, 30, 7, -5}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private boolean parry;
    private String name;
    private int[] battleStats = charStats.clone(); //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String sideArm = "";
    private int skillPoints;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Scanner s = new Scanner(System.in);
    private ArrayList<String> skills = new ArrayList<>(); // equip skills here
    private ArrayList<String> accessoriesOn = new ArrayList<>(); // accessories that you have equipped
    private int maxHealth;

    public Protagonist() {
        skills.add("Basic Attack");
        skills.add("Parry");
    }

    public void accessoriesCheck() {
        skillPoints = 3;
        for (String value : accessoriesOn) {
            switch (value) {
                case "Surgeons Toolkit" -> battleStats[2] += 200;
                case "Smoke Bomb" -> battleStats[4] += 10;
                case "Powdered Wig" -> battleStats[0] *= 2;
                case "Padded Armor" -> battleStats[1] += 20;
                case "Cool Looking Helmet" -> battleStats[3] += 15;
            }
        }
        maxHealth = battleStats[2];
    }

    public void weaponBoost() {
        switch (weapon) {
            case "Machete" -> battleStats[0] += 20;
            case "Flintlock" -> battleStats[0] += 40;
            case "Musket" -> battleStats[0] += 60;
        }
        if (sideArm.equals("Off-hand Revolver")) {
            battleStats[0] *= 1.3;
        }
    }

    public int choice() throws InterruptedException { // The place where you make your choice
        while (true) {
            dmgDealt = 0;
            System.out.println("\nPick your move");
            for (int i = 0; i < skills.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + skills.get(i));
            }
            var choice = s.nextInt();
            System.out.println();
            if (choice > skills.size() || choice < 1) {
                System.out.println("Invalid!");
            } else {
                String skill = skills.get(choice - 1);
                skillBook(skill, skillPoints);
                return choice;
            }
        }
    }


    public void skillBook(String skill, int skillPoints) throws InterruptedException {
        this.skillPoints = skillPoints;
        switch (skill) {
            case "Basic Attack" -> basicAttack(); // 40% to stun the opponent, but only does ok dmg
            case "Uppercut" -> { // 40% to stun the opponent, but only does ok dmg
                if (skillCheck(1)) {
                    uppercut();
                }
            }
            case "Parry" -> { // prepare to counter the next attack, makes it so that if the enemy attacks while you're parrying, it does no damage
                if (skillCheck(1)) parry(name);
            }
            case "Fireball" -> { //fireball attack, doesn't do much dmg, but has DOT across two turns? just timestamp if possible
                if (skillCheck(1)) fireball();
            }
            case "Chainsaw" ->
                    chainsaw(); // this attack requires investment with skill points, can do a lot if you "rev" (invest enough sp) it up enough, also does DOT
        }
    }

    /**
     * methods below are code that allows the skill to function
     **/

    public void basicAttack() throws InterruptedException {
        weaponCheck();
        System.out.println(name + " strikes!\n");
        skillPoints += 1;
        if (skillPoints > 5) {skillPoints = 5;}
        dmgDealt = battleStats[0];
    }

    public void uppercut() {
        System.out.println("You tried to perform an uppercut, but accidentally strike them in the throat!");
        dmgDealt = (int) (battleStats[0] * .7);
        sound.play("Uppercut", false);
    }

    public void fireball() throws InterruptedException {
        System.out.println(this + " threw out a fireball!");
        dmgDealt = (int) (this.battleStats[0] * .8);
        sound.play("Finger-Snap", false);
        Thread.sleep(10);
        sound.play("Fireball", false);
    }


    public void parry(String name) {
        parry = true;
        System.out.println("\n" + name + " prepares to parry the next attack!\n");
        sound.play("Block_Attempt", false);
    }


    public void chainsaw() throws InterruptedException {
        System.out.println("How many times would you like to rev your chainsaw?");
        int response = s.nextInt();
        if (skillPoints - response < 0) {
            System.out.println("Not enough Skill Points.TM!\n");
            choice();
        } else {
            dmgDealt = (battleStats[0] * response) + 5;
            skillPoints -= response;
            if (response >= 2) sound.play("low_revs", false);
            else sound.play("high_rev", false);
            Thread.sleep(10);
            sound.play("chain_attack", false);
        }
    }

    public boolean skillCheck(int cost) throws InterruptedException {
        if (skillPoints >= cost) {
            skillPoints -= cost;
            return true;
        } else {
            System.out.println("Not enough Skill Points!");
            choice();
            return false;
        }
    }

    /**
     * method checks if player has enough skill points to perform an action
     **/

    public void weaponCheck() throws InterruptedException {
        switch (weapon) {
            case "Switchblade", "Machete" -> {
                sound.play("Melee-Swing", false);
                Thread.sleep(10);
                sound.play("Hit", false);
            }
            case "Musket", "Flintlock" -> {
                sound.play("Gun_Load", false);
                Thread.sleep(10);
                sound.play("Gun_Fire", false);
            }
        }
        sideArm();
    }

    /**
     * method plays sound corresponding to the weapon the player has equipped
     **/

    public void sideArm() throws InterruptedException {
        if (sideArm.equals("Off-hand Revolver")) {
            sound.play("Gun_Load", false);
            Thread.sleep(10);
            sound.play("Gun_Fire", false);
        }
    }

    /**
     * same function as weaponCheck, separated for the purpose of neatness
     **/

    public void updateStats(int days) {
        for (int i = 0; i < charStats.length; i++) {
            charStats[i] += days;
        }
        switch (days) {
            case 1 -> checkObtained("Uppercut");
            case 2 -> checkObtained("Fireball");
            case 3 -> checkObtained("Chainsaw");
        }
    }

    /**
     * methods below are getters + setters
     **/
    public void checkObtained(String name) {
        if (!skills.contains(name)) {skills.add(name);}
    }

    public void resetParry() {
        parry = false;
    }
    public boolean getParry() {
        return parry;
    }
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
    public void setSide(String weapon) {
        this.sideArm = weapon;
    }
    public void addAccessory(String accessory) {
        accessoriesOn.add(accessory);
    }
    public int getInvSize() {
        return accessoriesOn.size();
    }
    public ArrayList<String> getAccessoriesOn() {
        return accessoriesOn;
    }
    public int getSkillPoints() {
        return skillPoints;
    }
    public int getDmgDealt() {
        return dmgDealt;
    }
    public void takeDmg(int damage) {
        battleStats[2] -= (int) (damage * (100.0 / (100 + battleStats[1])));
    }
    public ArrayList<String> getSkills() {
        return skills;
    }
    public int[] getBattleStats() {return battleStats;}
    public void setName(String name) {
        this.name = name;
    }
    public void resetStats() {
        battleStats = charStats.clone();
    }
    public void resetDmg() {
        dmgDealt = 0;
    }
    public String toString() {
        return name;
    }
    public void alterStats(int statChange, double percent) {
        battleStats[statChange] *= percent;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
}