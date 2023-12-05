import java.util.ArrayList;
import java.util.Random;

public class CEO {
    public static int attempts = 1;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private Sound sound = new Sound();
    private int skillPoints = 5;
    private Random rand = new Random();
    private int dmgDealt;
    private int[] charStats = {30, 50, 1000, 50, 0}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in charStats

    public CEO()
    {
        attempts++;
        skills.add("Basic Attack");
        skills.add("Impale"); // lowers your attack
        skills.add("Taunt"); // lowers defense
        skills.add("Axe Kick"); // does ok damage, but executes when you're low
        skills.add("RAGING DEMON"); // ult move
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
        switch (choice) {
            case 1, 5, 4:
                System.out.println(this + " strikes!\n");
                skillPoints += 1;
                if (skillPoints > 5) {
                    skillPoints = 5;
                }
                dmgDealt = charStats[0];
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

    public void skillBook(String skill, int skillPoints)
    {
        this.skillPoints = skillPoints;
        switch (skill) {
            case "Uppercut" -> { // 40% to stun the opponent, but only does ok dmg
                {uppercut();}
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

    public ArrayList<String> getSkills() {return skills;}
    public int getDmgDealt() //every enemy needs their own dedicated getter, idk why
    {return dmgDealt;}

    public void resetDmg() {dmgDealt = 0;}
    public String toString() {return "CEO of Racism";}
}