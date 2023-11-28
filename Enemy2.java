import java.util.ArrayList;
import java.util.Random;

public class Enemy2 extends Enemy{

    private int[] charStats = {6, 5, 50, 7, 5}; ////attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health = charStats[2];
    private int count;
    private String name = "Underpaid CEO Goon";
    private Sound sound = new Sound();
    private int skillPoints = 0;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private int choice = 2;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    public Enemy2() {
        skills.add("Basic Attack");
        skills.add("Parry");
        skills.add("Uppercut");
        skills.add("Fireball");
    }


    public void weaponCheck() { // play weapon sound
        sound.sound("Melee-Swing", 300);
        sound.sound("Hit", 400);
    }

    public int enemyChoice() { // enemy makes their decision
        resetDmg();
        if (skillPoints <= 0) {
            choice = 1;
        } else {
            choice = rand.nextInt(1, 6);
        }
        if (count == 1) choice = 2; // first turn, enemy will always parry
        count++;
        switch (choice) {
            case 1, 3, 4, 5:
                System.out.println(getName() + " strikes!\n");
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
        }
        return choice;
    }
    public void uppercut()
    {
        System.out.println(name.toString() + " tried to perform an uppercut, but accidentally punches your face!");
        dmgDealt = (int) (battleStats[0] * .7);
        sound.sound("Uppercut", 1000);
    }

    public int getHealth() {
        return health;
    }

    public int getDmgDealt() //every enemy needs their own dedicated getter, idk why
    {
        return dmgDealt;
    }

    public int[] getBattleStats() {
        return battleStats;
    }

    public void takeDmg(int damage) {
        battleStats[2] -= (int) (damage * (100.0 / (100 + battleStats[1])));
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void resetDmg()
    {
        dmgDealt = 0;
    }
}
