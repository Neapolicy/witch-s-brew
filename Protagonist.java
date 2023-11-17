import java.util.ArrayList;
// once i finish protagonist, I might be able to use inheritance and have enemies borrow most of this code (except for variables cuz of that one req)
public class Protagonist {
    private int[] charStats = {8, 5, 30, 7, 5}; //attack, defense, health, speed, evasion, do not modify these in battle
    private int[] battleStats = charStats; //these are the stats that are used in battle, as i plan on hp carrying over (?)
    private ArrayList <String> inv = new ArrayList<String>();
    private ArrayList <String> equipment = new ArrayList<String>(); //includes your weapons, and accessories

    public int[] getStats()
    {
        return charStats;
    }
    public int[] getBattleStats()
    {
        return battleStats;
    } // possible enemy move, use a while loop to generate a number, ex, number you want is five, and it keeps track of how much times it loops until it generates a five
}
