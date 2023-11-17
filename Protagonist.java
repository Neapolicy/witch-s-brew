import java.util.ArrayList;
import java.util.Scanner;

// once i finish protagonist, I might be able to use inheritance and have enemies borrow most of this code (except for variables cuz of that one req)
public class Protagonist {
    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, speed, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private ArrayList<String> inv = new ArrayList<String>(); //  contains everything
    private String weapon = "Switchblade";
    private Sound sound = new Sound();
    private String sideArm = "";
    private Scanner s = new Scanner(System.in);
    private int choice;
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private ArrayList<String> accessoriesOn = new ArrayList<String>(); // accessories that you have equipped

    public Protagonist() {
        skills.add("Basic Attack");
    }
    public void accessoriesCheck()
    {
        for (int i = 0; i < accessoriesOn.size(); i++)
        {
            switch (accessoriesOn.get(i))
            {
                case "Surgeons Toolkit":
                    charStats[2] += 200;
                    break;
                case "Smoke Bomb":
                    charStats[4] += 10;
                    break;
                case "Powdered Wig":
                    charStats[0] *= 2;
                    break;
                case "Stronger Steel":
                    charStats[1] += 20;
                    break;
                case "Better Boots":
                    charStats[3] += 25;
                    break;
            }
        }
    }

    public int choice() { // The place where Daler(you) make your choice
        while (true) {
            System.out.println("\nPick your move");
            for (int i = 0; i < skills.size(); i++) {
                System.out.println("(" + i + 1 + ") " + skills.get(i));
            }
            choice = s.nextInt();
            if (choice > skills.size() || choice < skills.size()) {
                choice();
            }
            else {
                switch (choice) {
                    case 1:
                        weaponCheck();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                break;
            }
        }
        return choice;
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
        if (sideArm.equals("Off-hand Revolver"))
        {
            sound.sound("Gun_Load", 400);
            sound.sound("Gun_Fire", 800);
            sound.sound("Revolver_Reload", 2700);
        }
        else
        {
            sound.sound("Revolver_Reload", 2700);
        }
    }


    public int[] getStats() {
        return charStats;
    }

    public int[] getBattleStats() {
        return battleStats;
    } // possible enemy move, use a while loop to generate a number, ex, number you want is five, and it keeps track of how much times it loops until it generates a five
}
