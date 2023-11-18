import java.util.ArrayList;
import java.util.Random;

public class Enemy { // day one enemy
    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, speed, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String sideArm = "";
    private int skillPoints = 0;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private int choice;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private ArrayList<String> accessoriesOn = new ArrayList<String>(); // accessories that you have equipped

    public Enemy() {
        skills.add("Basic Attack");
    }

    public void accessoriesCheck() { //enemies can have accessories too!
        skillPoints = 3;
        for (int i = 0; i < accessoriesOn.size(); i++) {
            switch (accessoriesOn.get(i)) {
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
                case "Better Boots":
                    battleStats[3] += 25;
                    break;
            }
        }
    }

    public int choice() { // enemy makes their decision
        while (true) {
            System.out.println("\nPick your move");
            for (int i = 0; i < skills.size(); i++) {
                System.out.println("(" + i + 1 + ") " + skills.get(i));
            }
            if (skillPoints <= 0)
            {
                choice = 1;
            }
            else
            {
                choice = rand.nextInt(1, 6);
            }
            switch (choice) {
                case 1:
                    skillPoints += 1;
                    dmgDealt = battleStats[0];
                    weaponCheck();
                    break;
                case 2, 3, 4, 5:
                    skill();
                    break;
            }
            break;
        }
        return choice;
    }

    public void skill() {

    }

    public void weaponCheck() {
        switch (weapon) {
            case "Switchblade", "Sword":
                sound.sound("Melee-Swing", 300);
                sound.sound("Hit", 400);
                break;
            default:
                sound.sound("Gun_Load", 400);
                sound.sound("Gun_Fire", 800);
                break;
        }
        if (sideArm.equals("Off-hand Revolver")) {
            sound.sound("Gun_Load", 400);
            sound.sound("Gun_Fire", 800);
            sound.sound("Revolver_Reload", 2700);
        } else {
            sound.sound("Revolver_Reload", 2700);
        }
    }


    public int[] getStats() {
        return charStats;
    }

    public int getDmgDealt()
    {
        return dmgDealt;
    }


    public void takeDmg(int damage) {
        battleStats[0] -= damage;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public int[] getBattleStats() {
        return battleStats;
    }

}
