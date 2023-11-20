import java.util.Random;
public class Gameboard // im gonna need to do some heavy rewriting of this code LMAO
{
    private int turns = 1; //internal counter, if i add another turn counter, create new variable to handle that
    private Protagonist pro;
    private int days;
    private Random rand = new Random();
    private int enemyChoice;
    private int protagChoice;
    private String skill;
    private Enemy enemy = null;


    public Gameboard(int days, Protagonist character) throws InterruptedException //initiates the shop and the game
    {
        this.pro = character;
        this.days = days;
        pro.resetStats();
        dayCheck();
        game();
    }
    public void game() throws InterruptedException { //remember to change this to prioritize speed
        pro.accessoriesCheck();
        enemy.accessoriesCheck();
        while (pro.getBattleStats()[2] > 0) {
            getInfo();
            playerAction();
            if (enemy.getBattleStats()[2] <= 0) {
                enemy.getBattleStats()[2] = 0; // sets enemy health to zero
                System.out.println(enemy.getName() + " has been taken down!");
                getBalance();
                break;
            }
            sleep(200);
            // time interval in milliseconds

            enemyAction();
            if (pro.getBattleStats()[2] <= 0) {
                pro.getBattleStats()[2] = 0; // Set player's health to zero
                break;
            }
            sleep(200);
            // Display stats at the start of the turn
        }
    }

    public void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void enemyAction()
    {
        enemyChoice = enemy.enemyChoice();
        if (turns % 2 == 0)
        {
            int enemyChoice = enemy.enemyChoice();
            if (enemyChoice == 1)
            {
                if (!evasionCheck(pro.getBattleStats()[4], enemy.getName()))
                {
                    pro.takeDmg(enemy.getDmgDealt());
                }
                else{
                    pro.takeDmg(0);
                }
            }
            else
            {
                if (!evasionCheck(pro.getBattleStats()[4], enemy.getName())) {
                    skillCheck();
                    pro.takeDmg(enemy.getDmgDealt());
                }
                else{
                    pro.takeDmg(0);
                }
            }
            turns += 1;
        }
    }

    public void playerAction()
    {
        if (turns % 2 == 1) // this is an implement for stun moves, which will skip over enemy turn by += 1, also,
        {
            protagChoice = pro.choice();
            if (protagChoice == 1) {
                if ((!evasionCheck(enemy.getBattleStats()[4], pro.getName())))
                {
                    enemy.takeDmg(pro.getDmgDealt());
                }
                else{
                    enemy.resetParry();
                    enemy.takeDmg(0);
                }
            }
            else
            {
                if (!evasionCheck(enemy.getBattleStats()[4], pro.getName())) {
                    skillCheck();
                    enemy.takeDmg(pro.getDmgDealt());
                }
                else{
                    enemy.takeDmg(0);
                }
            }
            turns += 1;
        }
    }

    public void skillCheck()
    {
        skill  = pro.getSkills().get(protagChoice - 1);
        switch (skill)
        {
            case "Uppercut": //should only determine stun, theoretically
                if (rand.nextBoolean())
                {
                    if (rand.nextInt(1, 101) <= enemy.getBattleStats()[3])
                    {
                        System.out.println("Enemy resisted the stun!");
                    }
                    else
                    {
                        System.out.println("Enemy is stunned! Use this chance to strike them again!");
                        turns += 1;
                    }
                }
                break;
            case "Parry":
                enemy.resetParry();
                System.out.println(pro.getName() + " parries " + enemy.getName() + "'s attack!");
                break;
        }
    }

    public void enemySkillCheck()
    {
        skill = enemy.getSkills().get(enemyChoice - 1);
        switch (skill)
        {
            case "Uppercut": //should only determine stun, theoretically
                if (rand.nextBoolean())
                {
                    if (rand.nextInt(1, 101) <= enemy.getBattleStats()[3])
                    {
                        System.out.println("Enemy resisted the stun!");
                    }
                    else
                    {
                        System.out.println("Enemy is stunned! Use this chance to strike them again!");
                        turns += 1;
                    }
                }
                break;
            case "Parry":
                enemy.resetParry();
                System.out.println(pro.getName() + " parries " + enemy.getName() + "'s attack!");
                break;
        }
    }

    public boolean evasionCheck(int evasion, String name)
    {
        if (rand.nextInt(1, 101) <= evasion)
        {
            System.out.println(name + " whiffs their attack!");
            return true;
        }
        return false;
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
        return 200 * days;
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
            return enemy.getName() + " looks unscathed, ready to take on the world! Yeah...";
        }
        else if ((enemy.getBattleStats()[2] < (.66) * enemy.getHealth()) && (enemy.getBattleStats()[2] > (.33) * enemy.getHealth()))
        {
            return enemy.getName() + " looks a bit injured, but still able to keep fighting.";
        }
        else
        {
            return enemy.getName() + " looks like they're two steps from keeling over.";
        }
    }
}
