import java.util.Random;

public class Gameboard // im gonna need to do some heavy rewriting of this code LMAO
{
    private int turns = 1; //internal counter, if i add another turn counter, create new variable to handle that
    private Protagonist pro;
    private int days;
    private Random rand = new Random();
    private int enemyChoice;
    private int protagChoice;
    private Sound sound = new Sound();
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
                System.out.println(enemy.getName() + " has been taken down!");
                getBalance();
                break;
            }
            sleep(200);
            // time interval in milliseconds

            enemyAction();
            if (pro.getBattleStats()[2] <= 0) {break;}
            sleep(200);
            // Display stats at the start of the turn
        }
    }

    public void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void enemyAction() {
        enemyChoice = enemy.enemyChoice();
        if (turns % 2 == 0) {
            if (!evasionCheck(pro.getBattleStats()[4], enemy.getName())) {
                enemySkillCheck();
                pro.takeDmg(enemy.getDmgDealt());
            } else {
                pro.takeDmg(0);
            }
            turns += 1;
        }
    }

    public void playerAction() {
        if (turns % 2 == 1) // this is an implement for stun moves, which will skip over enemy turn by += 1, also,
        {
            protagChoice = pro.choice();
            if ((!evasionCheck(enemy.getBattleStats()[4], pro.getName()))) {
                skillCheck();
                enemy.takeDmg(pro.getDmgDealt());
            } else {
                enemy.takeDmg(0);
            }
            turns += 1;
        }
    }

    public void skillCheck() {
        skill = pro.getSkills().get(protagChoice - 1);
        switch (skill) {
            case "Basic Attack", "Deus Ex Machina": // parries only work against basic attacks, not skills, also enemy parries work slightly differently, i give up on fixing this bug, so its a feature now
                if (enemy.getParry()) //this function only works if theres a certain property to the move, ie stun dot etc
                {
                    System.out.println(enemy.getName() + " blocked " + pro.getName() + "'s attack!");
                    sound.sound("Blocked", 1000);
                    pro.resetDmg();
                }
                enemy.resetParry();
                break;
            case "Uppercut": //should only determine stun, theoretically, also uppercut goes past guard, it is intentional
                if (rand.nextBoolean()) {
                    if (rand.nextInt(1, 101) <= enemy.getBattleStats()[3]) {
                        System.out.println("Enemy resisted the stun!");
                    } else {
                        System.out.println("Enemy is stunned! Use this chance to strike them again!");
                        turns += 1;
                    }
                }
                enemy.resetParry();
                break; // dont include parry, (nvm it is relevant)
            case "Parry":
                enemy.resetParry();
                break;
        }
    }

    public void enemySkillCheck() {
        try {
            skill = enemy.getSkills().get(enemyChoice - 1);
        }
        catch (Exception e)
        {
            skill = "Basic Attack"; //defaults to basic attack if the number roller is index out of bounds
        }
        switch (skill) {
            case "Basic Attack":
                if (pro.getParry())
                {
                    System.out.println(pro.getName() + " parries " + enemy.getName() + "'s attack!");
                    sound.sound("Parry", 1200);
                    enemy.resetDmg();
                }
                pro.resetParry();
                break;
            case "Uppercut": //should only determine stun, theoretically
                if (rand.nextBoolean()) {
                    if (rand.nextInt(1, 101) <= enemy.getBattleStats()[3]) {
                        System.out.println("Enemy resisted the stun!");
                    } else {
                        System.out.println("Enemy is stunned! Use this chance to strike them again!");
                        turns += 1;
                    }
                }
                break;
            case "Parry":
                pro.resetParry();
                break;
        }
    }

    public boolean evasionCheck(int evasion, String name) {
        if (rand.nextInt(1, 101) <= evasion) {
            System.out.println(name + " whiffs their attack!");
            return true;
        }
        return false;
    }


    public void dayCheck() {
        switch (days) {
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
        System.out.println("Your stats: " + pro.getBattleStats()[2] + " health, " + pro.getSkillPoints() + " skill points");
        System.out.println(status());
        System.out.println(enemy.getBattleStats()[2]);
    }

    public String status() {
        if (enemy.getBattleStats()[2] > (.66) * enemy.getHealth()) {
            return enemy.getName() + " looks unscathed, ready to take on the world! Yeah...";
        } else if ((enemy.getBattleStats()[2] < (.66) * enemy.getHealth()) && (enemy.getBattleStats()[2] > (.33) * enemy.getHealth())) {
            return enemy.getName() + " looks a bit injured, but still able to keep fighting.";
        } else {
            return enemy.getName() + " looks like they're two steps from keeling over.";
        }
    }
}
