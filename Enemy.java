import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Protagonist { // day one enemy
    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health;
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String sideArm = "";
    private int skillPoints = 3;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private int choice = 1;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private ArrayList<String> accessoriesOn = new ArrayList<String>(); // accessories that you have equipped

    public Enemy() { // add accessories here and skills
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
                case "Cool Looking Helmet":
                    battleStats[3] += 25;
                    break;
            }
        }
        health = battleStats[2];
    }

    public int choice() { // enemy makes their decision
        dmgDealt = 0;
        while (true) {
            /*if (skillPoints <= 0)
            {
                choice = 1;
            }
            else
            {
                choice = rand.nextInt(1, 6);
            }*/
            switch (choice) {
                case 1:
                    System.out.println("attacked");
                    skillPoints += 1;
                    if (skillPoints > 5)
                    {
                        skillPoints = 5;
                    }
                    dmgDealt = battleStats[0];
                    System.out.println(dmgDealt);
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
    public int getHealth()
    {
        return health;
    }
}
