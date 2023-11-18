public class Gameboard // im gonna need to do some heavy rewriting of this code LMAO
{
    private int balance;
    private int turns = 1;
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
        while (pro.getBattleStats()[2] > 0) {
            getInfo();

            if (turns % 2 == 1) // this is an implement for stun moves, which will skip over enemy turn by += 2
            {
                int protagChoice = pro.choice();
                if (protagChoice == 1) {
                    enemy.takeDmg(pro.getDmgDealt());
                }
                else
                {
                    skillCheck();
                }
                turns += 1;
            }

            if (enemy.getBattleStats()[2] <= 0) {
                enemy.getBattleStats()[2] = 0; // sets enemy health to zero
                System.out.println("CEO goon taken down!");
                getBalance();
                getResults();
                break;
            }

            if (turns % 2 == 0)
            {
                int enemyDec = enemy.choice();
                System.out.println(enemy.getDmgDealt());
                if (enemyDec == 1) {
                    pro.takeDmg(enemy.getDmgDealt());;
                }
                else
                {
                    skillCheck();
                }
                turns += 1;
            }

            if (pro.getBattleStats()[2] <= 0) {
                pro.getBattleStats()[2] = 0; // Set player's health to zero
                getBalance();
                getResults();
                break;
            }
            // Display stats at the start of the turn
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
                enemy = new Enemy2();
                break;
            case 3:
                enemy = null;
                break;
            case 4:
                enemy = null;
                break;
            case 5:
                enemy = null;
                break;
            case 6:
                enemy = null; // 2-5 are all placeholders for now (nulls delete the object so i can create a new enemy type object under same name)
                break;
        }
    }

    public boolean getResults() {
        return pro.getBattleStats()[2] <= 0;
    }

    public int getBalance() {
        balance += (200 * days);
        return balance;
    }

    public void getInfo() {
        System.out.println("Your stats: " + pro.getBattleStats()[2] + " health, " + pro.getSkillPoints() + " points");
        System.out.println(status());
        System.out.println(enemy.getBattleStats()[2]);
    }

    public String status()
    {
        if (enemy.getBattleStats()[2] > (.66) * enemy.getHealth())
        {
            return "Healthy";
        }
        else if ((enemy.getBattleStats()[2] < (.66) * enemy.getHealth()) && (enemy.getBattleStats()[2] > (.33) * enemy.getHealth()))
        {
            return "Moderate";
        }
        else
        {
            return "Terrible";
        }
    }
}
