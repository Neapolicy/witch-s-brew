import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

// once i finish protagonist, I might be able to use inheritance and have enemies borrow most of this code (except for variables cuz of that one req)
public class Protagonist {
    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private Random rand = new Random();
    private String name;
    private int[] battleStats = charStats.clone(); //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private ArrayList<String> inv = new ArrayList<String>(); //  contains everything
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String sideArm = "";
    private int skillPoints;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Scanner s = new Scanner(System.in);
    private int choice;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private ArrayList<String> accessoriesOn = new ArrayList<String>(); // accessories that you have equipped

    public Protagonist() {
        skills.add("Basic Attack");
        skills.add("Uppercut");
    }

    public void accessoriesCheck() {
        skillPoints = 3;
        for (String value : accessoriesOn) {
            switch (value) {
                case "Surgeons Toolkit":
                    battleStats[2] += 200;
                    break;
                case "Smoke Bomb":
                    battleStats[4] += 10;
                    break;
                case "Powdered Wig":
                    battleStats[0] *= 2;
                    break;
                case "Stronger Steel":
                    battleStats[1] += 20;
                    break;
                case "Cool Looking Helmet":
                    battleStats[3] += 15;
                    break;
            }
        }
    }

    public int choice() { // The place where you make your choice
        while (true) {
            dmgDealt = 0;
            System.out.println("\nPick your move");
            for (int i = 0; i < skills.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + skills.get(i));
            }
            choice = s.nextInt();
            if (choice > skills.size() || choice < 1) {
                System.out.println("Invalid!");
            } else {
                switch (choice) {
                    case 1:
                        skillPoints += 1;
                        if (skillPoints > 5) {
                            skillPoints = 5;
                        }
                        dmgDealt = battleStats[0];
                        weaponCheck();
                        break;
                    case 2, 3, 4, 5:
                        String skill = skills.get(choice - 1);
                        skillBook(skill, skillPoints);
                        break;
                }
                break;
            }
        }
        return choice;
    }

    public void skillBook(String skill, int skillPoints)
    {
        this.skillPoints = skillPoints;
        switch(skill)
        {
            case "Uppercut": // 40% to stun the opponent, but only does ok dmg
                if (skillCheck(1))
                {
                    dmgDealt = (int) (battleStats[0] * .7);
                    sound.sound("Uppercut", 1000);
                }
                break;
            case "Parry": // prepare to counter the next attack, makes it so that if the enemy attacks while you're parrying, it does no damage
                if (skillCheck(1)) parry();
                break;
            case "Fireball": //fireball attack, doesn't do much dmg, but has DOT across two turns? just timestamp
                if (skillCheck(1)) fireball();
                break;
            case "Chainsaw": // this attack requires investment with skill points, can do a lot if you "rev" (invest enough sp) it up enough, also does DOT
                chainsaw();
                break;
            case "Deus Ex Machina": // hc gun barrage lmao, it just does the BIG damage, but is costs a decent amount of sp
                if (skillCheck(3)) deusEx();
                break;
            case "Recover":  // simple move that restores 30% of your hp
                if (skillCheck(1)) recover();
                break;
        }
    }
    public void fireball()
    {

    }


    public void parry()
    {

    }


    public void chainsaw()
    {

    }


    public void recover()
    {

    }

    public void deusEx()
    {

    }

    public boolean skillCheck(int cost)
    {
        if (skillPoints >= cost)
        {
            skillPoints -= cost;
            return true;
        }
        else
        {
            System.out.println("Not enough skill points!");
            choice();
            return false;
        }
    }

    public void addSkill(String skillName)
    {
        skills.add(skillName);
    }

    public void weaponCheck() {
        switch (weapon) {
            case "Switchblade", "Machete":
                sound.sound("Melee-Swing", 300);
                sound.sound("Hit", 400);
                break;
            default:
                sound.sound("Gun_Load", 400);
                sound.sound("Gun_Fire", 800);
                sideArm();
                break;
        }
    }

    public void sideArm() {
        if (sideArm.equals("Off-hand Revolver")) {
            sound.sound("Gun_Load", 400);
            sound.sound("Gun_Fire", 800);
            sound.sound("Revolver_Reload", 2700);
        } else {
            sound.sound("Revolver_Reload", 2700);
        }
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void editChar(ArrayList<String> inv) {
        System.out.print("Character Stats");

    }

    public int getDmgDealt() {
        return dmgDealt;
    }

    public void takeDmg(int damage) {
        battleStats[2] -=  (int) (damage * (100.0/(100 + battleStats[1])));
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public int[] getBattleStats() {
        return battleStats;
    } // possible enemy move, use a while loop to generate a number, ex, number you want is five, and it keeps track of how much times it loops until it generates a five

    public void setName(String name) {
        this.name = name;
    }

    public void resetStats()
    {
        battleStats = charStats.clone();
    }

    public void updateStats(int days)
    {
        for (int i = 0; i < charStats.length; i++)
        {
            charStats[i] += days;
        }
    }


    public String getName() {
        return name;
    }
}