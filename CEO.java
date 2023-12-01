import java.util.ArrayList;

public class CEO {
    private ArrayList<String> skills = new ArrayList<String>(); // equip skills here
    private int[] charStats = {30, 50, 1000, 50, 0}; //attack, defense, health, resistance to debuffs, evasion, do not modify these in battle

    public CEO()
    {
        skills.add("Basic Attack");
        skills.add("RAGING DEMON");
        skills.add("Taunt");
        skills.add("Axe Kick");
        skills.add("Dodge");
    }
}
