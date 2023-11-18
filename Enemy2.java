import java.util.ArrayList;
import java.util.Random;

public class Enemy2 extends Enemy{

    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, speed, evasion, do not modify these in battle, also remember to alter this
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private int health;
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String sideArm = "";
    private int skillPoints = 0;
    private int dmgDealt; //takes the damage that you do with your attack and deals it to the enemy
    private Random rand = new Random();
    private int choice = 1;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private ArrayList<String> accessoriesOn = new ArrayList<String>(); // accessories that you have equipped
    public Enemy2() {
        skills.add("Basic Attack");
    }
}
