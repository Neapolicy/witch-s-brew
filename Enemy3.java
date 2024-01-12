import java.util.ArrayList;
import java.util.Random;

public class Enemy3 extends Enemy { // day three enemy
    private int[] charStats = {5, 50, 80, 10, 10}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health = charStats[2];
    private boolean parry;
    private Sound sound = new Sound();
    private int revs;
    private String RESET = "\u001B[0m";
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private ArrayList<String> skills = new ArrayList<>(); // equip skills here
    private String skill;

    public Enemy3() { // add accessories here and skills
        skills.add("Chainsaw");
        skills.add("Parry");
        skills.add("Uppercut");
    }

    public void weaponCheck() { // play weapon sound
        sound.play("Melee-Swing", false);
        sound.play("Hit", false);
    }

    public int enemyChoice() { // enemy makes their decision
        resetDmg();
        int choice;
        if (revs < 1) { //always rev when damage is zero
            choice = 1;
        } else {
            choice = rand.nextInt(1, 20);
        }
        switch (choice) {
            case 1, 2, 3, 4 -> {
                skill = null;
                System.out.println(this + " revs their chainsaw");
                sound.play("low_revs", false);
                revs++;
            }
            case 5, 6 -> uppercut();
            case 7, 8, 9, 10 -> {
                skill = skills.get(1);
                parry(this);
            }
            default ->
            {
                skill = skills.get(0);
                System.out.println(this + " lunges at you like a madman!");
                sound.play("chain_attack", false);
                dmgDealt = battleStats[0] * revs;
                revs = 0;
            }
        }
        return choice;
    }
    public void uppercut()
    {
        System.out.println(this + " tried to perform an uppercut, but went too low!\n");
        dmgDealt = (battleStats[0] * 2);
        skill = skills.get(2);
        sound.play("Uppercut", false);
    }

    public void resetParry() {parry = false;}
    public int getHealth() {return health;}

    public int getDmgDealt() //every enemy needs their own dedicated getter, idk why
    {
        return dmgDealt;
    }

    public void parry(Enemy3 enemy3) {
        parry = true;
        skill = skills.get(1);
        sound.play("Block_Attempt", false);
        System.out.println("\n" + enemy3 + " prepares to block the next attack!\n");
    }

    public int[] getBattleStats() {return battleStats;}

    public void takeDmg(int damage) {battleStats[2] -= (int) (damage * (100.0 / (100 + battleStats[1])));}
    public boolean getParry() {return parry;}

    public ArrayList<String> getSkills() {return skills;}

    public void resetDmg() {dmgDealt = 0;}
    public String toString() {return "\u001B[31m" + "Maniacal Goon" + RESET;}
    public String getSkill() {return skill;}
}