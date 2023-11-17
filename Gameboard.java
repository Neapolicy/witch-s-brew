import java.util.Random;
public class Gameboard // im gonna need to do some heavy rewriting of this code LMAO
{
    private int balance;
    private Protagonist pro = null;
    private int days;
    private Object enemy = null;

    public Gameboard(int days, Protagonist character) //initiates the shop and the game
    {
        this.pro = character;
        this.days = days;
        dayCheck();
        game();
    }
    public void game()
    {

    }
    public boolean results()
    {
        if (pro.getBattleStats()[0] <= 0)
        {
            return false;
        }
        return true;
    }

    public void dayCheck()
    {
        switch (days)
        {
            case 1:
                //enemy
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    public boolean getResults() {
        return pro.getBattleStats()[0] <= 0;
    }

    public int getBalance() {
        balance += (200 * days);
        return balance;
    }

    public void getInfo() {
        System.out.println("Daler's stats: " + pro.getBattleStats()[0] + " health, " + pro.getSkillPoints() + " points");
        // print enemy status on this line, but should be vague, like they're injured, and thats it
    }

}
