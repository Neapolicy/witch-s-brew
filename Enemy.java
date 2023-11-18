import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Protagonist { // day one enemy
    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, speed, evasion, do not modify these in battle, also remember to alter this
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health;
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
        health = battleStats[0];
    }

    public int choice() { // enemy makes their decision
        while (true) {
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

    public String status()
    {
        if (battleStats[0] > (.66) * health)
        {
            return "Healthy";
        }
        else if (battleStats[0] > (.66) * health)
        {
            return "Moderate";
        }
        else
        {
            return "Terrible";
        }
    }

}
