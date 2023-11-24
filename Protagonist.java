import java.util.ArrayList;
import java.util.Scanner;

// once i finish protagonist, I might be able to use inheritance and have enemies borrow most of this code (except for variables cuz of that one req)
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
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private ArrayList<String> skillList = new ArrayList<String>();
    private ArrayList<String> accessoriesOn = new ArrayList<String>(); // accessories that you have equipped

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
                case "Stronger Steel" -> battleStats[1] += 20;
                case "Cool Looking Helmet" -> battleStats[3] += 15;
            }
        }
    }

    public void weaponBoost() {
            switch (weapon) {
                case "Machete" -> battleStats[2] += 20;
                case "Flintlock" -> battleStats[4] += 40;
                case "Musket" -> battleStats[0] = 60;
            }
            if (sideArm.equals("Off-hand Revolver")) {battleStats[0] *= 1.3;}
        }

    public int choice() { // The place where you make your choice
        while (true) {
            dmgDealt = 0;
            System.out.println(sideArm);
            System.out.println("\nPick your move");
            for (int i = 0; i < skills.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + skills.get(i));
            }
            int choice = s.nextInt();
            System.out.println();
            if (choice > skills.size() || choice < 1) {
                System.out.println("Invalid!");
            } else {
                switch (choice) {
                    case 1 -> {
                        System.out.println(name + " strikes!\n");
                        skillPoints += 1;
                        if (skillPoints > 5) {
                            skillPoints = 5;
                        }
                        dmgDealt = battleStats[0];
                        weaponCheck();
                    }
                    case 2, 3, 4, 5 -> {
                        String skill = skills.get(choice - 1);
                        skillBook(skill, skillPoints);
                    }
                }
                return choice;
            }
        }
    }

    public void skillBook(String skill, int skillPoints)
    {
        this.skillPoints = skillPoints;
        switch (skill) {
            case "Uppercut" -> { // 40% to stun the opponent, but only does ok dmg
                if (skillCheck(1)) {
                    uppercut();
                }
            }
            case "Parry" -> { // prepare to counter the next attack, makes it so that if the enemy attacks while you're parrying, it does no damage
                if (skillCheck(1)) parry(name);
            }
            case "Fireball" -> { //fireball attack, doesn't do much dmg, but has DOT across two turns? just timestamp
                if (skillCheck(1)) fireball();
            }
            case "Chainsaw" -> // this attack requires investment with skill points, can do a lot if you "rev" (invest enough sp) it up enough, also does DOT
                    chainsaw();
            case "Deus Ex Machina" -> { // hc gun barrage lmao, it just does the BIG damage, but is costs a decent amount of sp
                if (skillCheck(4)) deusEx();
            }
            case "Recover" -> {  // simple move that restores 30% of your hp
                if (skillCheck(1)) recover();
            }
        }
    }

    public void uppercut()
    {
        System.out.println("You tried to perform an uppercut, but accidentally strike them in the throat!");
        dmgDealt = (int) (battleStats[0] * .7);
        sound.sound("Uppercut", 1000);
    }
    public void fireball()
    {

    }


    public void parry(String name)
    {
        parry = true;
        sound.sound("Block_Attempt", 500);
        System.out.println("\n" + name + " prepares to parry the next attack!\n");
    }


    public void chainsaw()
    {

    }


    public void recover()
    {
        System.out.println("You drink some vending machine coffee, restoring some HP in the process!\n");
        battleStats[2] += (int) (charStats[2] * .3);
        if (battleStats[2] > charStats[2])
        {
            battleStats[2] = charStats[2];
        }
    }

    public void deusEx()
    {
        System.out.println("You used a teeny bit of magic to project some magical guns\nThen unleashed a barrage of bullets!");
        sound.sound("Deus_Ex", 5000);
        dmgDealt = battleStats[0] * 3;
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

    public void resetDmg()
    {
        dmgDealt = 0;
    }

    public void weaponCheck() {
        switch (weapon) {
            case "Switchblade", "Machete" -> {
                sound.sound("Melee-Swing", 300);
                sound.sound("Hit", 400);
                sideArm();
            }
            case "Musket", "Flintlock" -> {
                sound.sound("Gun_Load", 400);
                sound.sound("Gun_Fire", 800);
                sideArm();
            }
        }
    }

    public void sideArm() {
        if (sideArm.equals("Off-hand Revolver")) {
            sound.sound("Gun_Load", 400);
            sound.sound("Gun_Fire", 800);
        }
    }

    public int getSkillPoints() {
        return skillPoints;
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
        switch (days)
        {
            case 2:
                checkObtained("Uppercut");
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }

    public void checkObtained(String name)
    {
        if (!skillList.contains(name))
        {
            skillList.add(name);
        }
    }

    public void resetParry()
    {
        parry = false;
    }

    public boolean getParry()
    {
        return parry;
    }
    public ArrayList<String> getSkillList() // prints out all your skills, meant to be used when doing character editing
    {
        return skillList;
    }

    public void setWeapon(String weapon)
    {
        this.weapon = weapon;
    }

    public void setSide(String weapon)
    {
        this.sideArm = weapon;
    }

    public void addAccessory(String accessory)
    {
        accessoriesOn.add(accessory);
    }

    public int getInvSize()
    {
        return accessoriesOn.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAccessoriesOn()
    {
        return accessoriesOn;
    }
}