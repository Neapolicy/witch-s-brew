import java.util.Random;
public class Gameboard // im gonna need to do some heavy rewriting of this code LMAO
{
    private int balance;
    private Protagonist pro;
    private int days;
    private Enemy enemy = null;

    public Gameboard(int days, Protagonist character) //initiates the shop and the game
    {
        this.pro = character;
        this.days = days;
        dayCheck();
        game();
    }
    public void game()
    {
        pro.accessoriesCheck();
        enemy.accessoriesCheck();
        while (pro.getBattleStats()[0] > 0) {
            getInfo();

            int protagChoice = pro.choice();

            if (protagChoice == 1) {
                enemy.takeDmg(pro.getDmgDealt());
            }
            else
            {
                skillCheck();
            }
            if (enemy.getBattleStats()[0] <= 0) {
                enemy.getBattleStats()[0] = 0; // sets enemy health to zero
                System.out.println("CEO goon taken down!");
                getBalance();
                getResults();
                break;
            }

            enemy.choice();
            pro.takeDmg(enemy.getDmgDealt());

            if (pro.getBattleStats()[0] <= 0) {
                pro.getBattleStats()[0] = 0; // Set player's health to zero
                getBalance();
                getResults();
                break;
            }
            // Display stats at the end of the turn
        }
    }

    public void skillCheck()
    {
        for (int i = 0; i < pro.getSkills().size(); i++)
        {

        }
    }

    public void dayCheck()
    {
        switch (days)
        {
            case 1:
                enemy = new Enemy();
                break;
            case 2:
                enemy = new Enemy();
                break;
            case 3:
                enemy = new Enemy();
                break;
            case 4:
                enemy = new Enemy();
                break;
            case 5:
                enemy = new Enemy();
                break;
            case 6:
                enemy = new Enemy(); // 2-5 are all placeholders for now
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
        System.out.println("Your stats: " + pro.getBattleStats()[0] + " health, " + pro.getSkillPoints() + " points");
        System.out.println(enemy.status());
    }
}
