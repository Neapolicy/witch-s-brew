import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Protagonist { // day one enemy
    private int[] charStats = {6, 10, 35, 5, 7}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health = charStats[2];
    private boolean parry;
    private Sound sound = new Sound();
    private int skillPoints = 3;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private int count = 1;
    private String skill;
    private String RESET = "\u001B[0m";

    public Enemy() { // add accessories here and skills
        skills.add("Basic Attack");
        skills.add("Parry");
        skills.add("Uppercut");
    }

    public void weaponCheck() { // play weapon sound
        sound.play("Melee-Swing", false);
        sound.play("Hit", false);
    }

    public int enemyChoice() throws InterruptedException { // enemy makes their decision
        resetDmg();
        int choice;
        if (skillPoints <= 0) {
            choice = 1;
        } else {
            choice = rand.nextInt(1, 6);
        }
        if (count == 1) choice = 3; // first turn, enemy will always parry
        count++;
        switch (choice) {
            case 1, 5, 4 -> {
                System.out.println(this + " strikes!\n");
                skillPoints += 1;
                skill = skills.get(0);
                dmgDealt = battleStats[0];
                weaponCheck();
            }
            case 2 -> {
                skill = skills.get(1); //parry
                skillBook(skill, skillPoints);
            }
            case 3 -> {
                skill = skills.get(2); //uppercut
                skillBook(skill, skillPoints);
            }
        }
        return choice;
    }
    public void uppercut()
    {
        System.out.println(this + " tried to perform an uppercut, but went too low!\n");
        dmgDealt = (int) (battleStats[0] * .6);
        sound.play("Uppercut", false);
    }

    public void resetParry()
    {
        parry = false;
    }

    public int getHealth() {
        return health;
    }

    public int getDmgDealt() //every enemy needs their own dedicated getter, idk why
    {return dmgDealt;}

    public void parry(String name) {
        parry = true;
        sound.play("Block_Attempt", false);
        System.out.println("\n" + this + " prepares to block the next attack!\n");
    }

    public int[] getBattleStats() {
        return battleStats;
    }

    public void takeDmg(int damage) {
        battleStats[2] -= (int) (damage * (100.0 / (100 + battleStats[1])));
    }
    public boolean getParry() {
        return parry;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void resetDmg()
    {
        dmgDealt = 0;
    }
    public String toString() {return "\u001B[31m" + "CEO Goon" + RESET;}
    public String getSkill() {return skill;}
}