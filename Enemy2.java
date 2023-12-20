import java.util.ArrayList;
import java.util.Random;

public class Enemy2 extends Enemy{

    private int[] charStats = {14, 10, 50, 7, 5}; ////attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health = charStats[2];
    private Sound sound = new Sound();
    private int skillPoints = 0;
    private String previousSkill = "lol";
    private String RESET = "\u001B[0m";
    private String skill;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private ArrayList<String> skills = new ArrayList<>(); // equip skills here
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
        int choice = rand.nextInt(1, 10); //INCOMPLETE WIP
        if (previousSkill.equals("Fireball")) choice = 11;
        switch (choice) {
            case 1, 3, 4, 5 -> {
                System.out.println(getName() + " strikes!\n");
                skill = skills.get(0);
                dmgDealt = battleStats[0];
                weaponCheck();
            }
            case 2, 6 -> {
                skill = skills.get(1); // parry
                skillBook(skill, skillPoints);
            }
            case 11 -> uppercut();
            default -> {
                skill = skills.get(3); // fireball
                skillBook(skill, skillPoints);
            }
        }
        previousSkill = skill;
        return choice;
    }
    public void uppercut()
    {
        skill = skills.get(2);
        System.out.println(this + " tried to perform an uppercut, but accidentally punches your face!");
        dmgDealt = (int) (battleStats[0] * .8);
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

    public String getName() {return "\u001B[31m" + "Fire Wielding Goon" + RESET;}

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void resetDmg()
    {
        dmgDealt = 0;
    }
    public String getSkill() {return skill;}

}
