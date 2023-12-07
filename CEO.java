import java.util.ArrayList;
import java.util.Random;

public class CEO {
    public static int attempts;
    private ArrayList<String> skills = new ArrayList<>(); // equip skills here
    private Sound sound = new Sound();
    private Random rand = new Random();
    private int dmgDealt;
    private int pity;
    private String skill;
    private int[] charStats = {20, 50, 500, 50, 0}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in charStats

    public CEO()
    {
        attempts++;
        skills.add("Basic Attack");
        skills.add("Impale"); // lowers your attack, does insane damage
        skills.add("Taunt"); // lowers defense
        skills.add("Axe Kick"); // does ok damage, but executes when you're low
        skills.add("RAGING DEMON"); // ult move
    }
    public void weaponCheck() { // play weapon sound
        sound.sound("Melee-Swing", 300);
        sound.sound("Hit", 400);
    }

    public int enemyChoice() throws InterruptedException { // enemy makes their decision
        resetDmg();
        int choice = rand.nextInt(1, 21);
        if (pity >= 5) {choice = rand.nextInt(1, 26);}
        switch (choice) {
            default -> {
                System.out.println(this + " strikes!\n");
                skill = skills.get(0);
                dmgDealt = charStats[0];
                weaponCheck();
            }
            case 1 -> {
                skill = skills.get(1);
                skillBook(skill);
            }
            case 2 -> {
                skill = skills.get(2);
                skillBook(skill);
            }
            case 3 -> {
                skill = skills.get(3);
                skillBook(skill);
            }
            case 21, 22, 23, 24, 25 -> {
                pity = 0;
                skill = skills.get(4);
                skillBook(skill);
            }
        }
        pity ++;
        return choice;
    }

    public void skillBook(String skill) throws InterruptedException {
        switch (skill) {
            case "Uppercut" -> { // 40% to stun the opponent, but only does ok dmg
                {uppercut();}
            }
            case "Taunt" -> System.out.println(this + " started to say some not so nice things to you\n");
            case "RAGING DEMON" ->
            {   System.out.println("Ruh Roh\n");
                Thread.sleep(2000);
                dmgDealt = (int) (charStats[0] * 1.15);}
            case "Axe Kick" ->
            {   System.out.println(this + " performs a sick looking axe kick\n");
                dmgDealt = (int) (charStats[0] * .4);}
            case "Impale" -> {
                System.out.println("You were impaled by " + this + "\n");
                dmgDealt = (int) (charStats[0] * 1.1);
            }
        }
    }
    public void uppercut()
    {
        System.out.println(this + " sent you into the heavens!\n");
        dmgDealt = (int) (charStats[0] * 1.3);
        sound.sound("Uppercut", 1000);
    }

    public int getHealth() {return charStats[2];}
    public int[] getBattleStats() {return charStats;}

    public void takeDmg(int damage) {charStats[2] -= (int) (damage * (100.0 / (100 + charStats[1])));}

    public String getSkill() {return skill;}
    public int getDmgDealt() //every enemy needs their own dedicated getter, idk why
    {return dmgDealt;}

    public void resetDmg() {dmgDealt = 0;}
    public String toString() {return "CEO of Racism";}
}