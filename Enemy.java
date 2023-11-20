import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Protagonist { // day one enemy
    private int[] charStats = {7, 4, 25, 7, 7}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health;
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String name = "CEO Goon";
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
        for (String s : accessoriesOn) {
            switch (s) {
                case "Surgeons Toolkit" -> battleStats[2] += 200;
                case "Smoke Bomb" -> battleStats[4] += 10;
                case "Powdered Wig" -> battleStats[0] *= 2;
                case "Stronger Steel" -> battleStats[1] += 20;
                case "Cool Looking Helmet" -> battleStats[3] += 15;
            }
        }
        health = battleStats[2];
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

    public int enemyChoice() { // enemy makes their decision
        dmgDealt = 0;
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
                    skillPoints += 1;
                    if (skillPoints > 5)
                    {
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
            return choice;
        }

    public int getHealth()
    {
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
        battleStats[2] -= (int) (damage * (100.0/(100 + battleStats[1])));
    }

    public String getName() {
        return name;
    }

    public int getChoice()
    {
        return choice;
    }
}
