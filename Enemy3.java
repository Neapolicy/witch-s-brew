import java.util.ArrayList;
import java.util.Random;

public class Enemy3 extends Enemy { // day three enemy
    private int[] charStats = {5, 230, 200, 10, 10}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health = charStats[2];
    private boolean parry;
    private Sound sound = new Sound();
    private int skillPoints = 3;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private int count = 1;

    public Enemy3() { // add accessories here and skills
        skills.add("Chainsaw");
        skills.add("Parry");
        skills.add("Uppercut");
    }

    public void weaponCheck() { // play weapon sound
        sound.sound("Melee-Swing", 300);
        sound.sound("Hit", 400);
    }

    public int enemyChoice() { // enemy makes their decision
        resetDmg();
        int choice;
        if (skillPoints <= 0) {
            choice = 1;
        } else {
            choice = rand.nextInt(1, 6);
        }
        if (count == 1) choice = 2; // first turn, enemy will always parry
        count++;
        switch (choice) {
            case 1, 5, 4:
                System.out.println(this + " strikes!\n");
                skillPoints += 1;
                if (skillPoints > 5) {
                    skillPoints = 5;
                }
                dmgDealt = battleStats[0];
                weaponCheck();
                break;
            case 2:
                String skill = skills.get(choice - 1);
                skillBook(skill, skillPoints);
                break;
            case 3:
                skill = skills.get(choice - 1);
                skillBook(skill, skillPoints);
                break;
        }
        return choice;
    }
    public void uppercut()
    {
        System.out.println(this + " tried to perform an uppercut, but went too low!\n");
        dmgDealt = (int) (battleStats[0] * .6);
        sound.sound("Uppercut", 1000);
    }

    public void resetParry()
    {
        parry = false;
    }

    public int getHealth() {
        return health;
    }

    public int getDmgDealt() //every enemy needs their own dedicated getter, idk why
    {
        return dmgDealt;
    }

    public void parry(String name) {
        parry = true;
        sound.sound("Block_Attempt", 500);
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
    public String toString() {return "Maniacal Goon";}
}