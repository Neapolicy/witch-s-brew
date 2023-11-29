import java.util.Random;
/** A class that acts as a "game-board", where everything is handled, ex enemy options, player choice
 * contains methods that allows the fundamental parts of the game to function
 * @author Matthew Lin
 * @version 1.??? */
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
        dayCheck();
        game();
    }

    public void game() throws InterruptedException { //remember to change this to prioritize speed
        pro.accessoriesCheck();
        pro.weaponBoost();
        while (pro.getBattleStats()[2] > 0) {
            getInfo();
            playerAction();
            if (enemy.getBattleStats()[2] <= 0) {
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
            if (!evasionCheck(pro.getBattleStats()[4], enemy.toString())) {
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
            if ((!evasionCheck(enemy.getBattleStats()[4], pro.toString()))) {
                skillCheck();
                enemy.takeDmg(pro.getDmgDealt());
            } else {
                enemy.takeDmg(0);
            }
            turns += 1;
        }
    }
    /**Checks what skill player uses and acts accordingly **/
    public void skillCheck() {
        skill = pro.getSkills().get(protagChoice - 1);
        switch (skill) {
            case "Basic Attack" -> { // parries only work against basic attacks, not skills, also enemy parries work slightly differently, i give up on fixing this bug, so its a feature now
                if (enemy.getParry()) //this function only works if there's a certain property to the move, ie stun dot etc
                {
                    System.out.println(enemy.toString() + " blocked " + pro.toString() + "'s attack!");
                    sound.sound("Blocked", 1000);
                    pro.resetDmg();
                }
                enemy.resetParry();
            }
            case "Uppercut" -> { //should only determine stun, theoretically, also uppercut goes past guard, it is intentional
                if (rand.nextBoolean()) {
                    if (rand.nextInt(1, 101) <= enemy.getBattleStats()[3]) {
                        System.out.println("Enemy resisted the stun!");
                    } else {
                        System.out.println("Enemy is stunned! Use this chance to strike them again!");
                        turns += 1;
                    }
                }
                enemy.resetParry();
            } // dont include parry, (nvm it is relevant)
            case "Parry" -> enemy.resetParry();
        }
    }
    /**Same function as skillCheck, except these handles enemies **/

    public void enemySkillCheck() {
        try {
            skill = enemy.getSkills().get(enemyChoice - 1);
        }
        catch (Exception e)
        {
            skill = "Basic Attack"; //defaults to basic attack if the number roller is index out of bounds
        }
        switch (skill) {
            case "Basic Attack" -> {  // checks for parry, if parry on, no dmg, else take dmg
                if (pro.getParry()) {
                    System.out.println(pro.toString() + " parries " + enemy.toString() + "'s attack!");
                    sound.sound("Parry", 1200);
                    enemy.resetDmg();
                }
                pro.resetParry();
            }
            case "Uppercut" -> { //should only determine stun, theoretically
                if (rand.nextBoolean()) {
                    if (rand.nextInt(1, 101) <= enemy.getBattleStats()[3]) {
                        System.out.println(pro.toString() + " resisted the stun!");
                    } else {
                        System.out.println(pro.toString() + " is stunned!");
                        turns += 1;
                    }
                }
            }
            case "Parry" -> pro.resetParry();
        }
    }
    /**Checks if the target is able to dodge an attack, this is pure chance **/
    public boolean evasionCheck(int evasion, String name) {
        if (rand.nextInt(1, 101) <= evasion) {
            System.out.println(name + " whiffs their attack!");
            return true;
        }
        return false;
    }


    public void dayCheck() {
        switch (days) {
            case 1 -> enemy = new Enemy();
            case 2 -> enemy = new Enemy2();
            case 3 -> enemy = null;
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
    /** Gives player rough estimate of enemy health**/
    public String status() {
        if (enemy.getBattleStats()[2] > (.66) * enemy.getHealth()) {
            return enemy.toString() + " looks unscathed, ready to take on the world! Yeah...";
        } else if ((enemy.getBattleStats()[2] < (.66) * enemy.getHealth()) && (enemy.getBattleStats()[2] > (.33) * enemy.getHealth())) {
            return enemy.toString() + " looks a bit injured, but still able to keep fighting.";
        } else {
            return enemy.toString() + " looks like they're two steps from keeling over.";
        }
    }
    public Protagonist getPro() {return pro;}
}
