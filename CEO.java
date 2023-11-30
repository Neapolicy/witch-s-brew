import java.util.ArrayList;

public class CEO {
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private int[] charStats = {30, 50, 500, 10, 15}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle

    public CEO()
    {
        skills.add("Basic Attack");
        skills.add("Parry");
        skills.add("Uppercut");
    }
}
