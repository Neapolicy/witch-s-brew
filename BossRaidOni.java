import static java.lang.Thread.sleep;
import java.util.Random;

public class BossRaidOni { // you have ten turns, it ends either when you or the boss dies, or 10 turns have passed
    private int tally = 1;
    private double multiplier;
    private int protagChoice;
    private int enemyChoice;
    private Protagonist pro;
    private CEO ceo = new CEO();
    private Random rand = new Random();

    public BossRaidOni(Protagonist pro) throws InterruptedException {
        this.pro = pro;
        /*game();*/
    }
    public void game() throws InterruptedException { //remember to change this to prioritize speed
        pro.accessoriesCheck();
        pro.weaponBoost();
        while (pro.getBattleStats()[2] > 0 && tally <= 10) {
            while (rand.nextInt(1, 11) != 5)
            {
                multiplier ++;
            }
            if (multiplier > 1.3) {}
            System.out.println("Turn " + tally);
            getInfo();
            DotP(); //enemy takes dot if applicable
            enemyDot();
            DotE(); // player takes dot if inflicted
            playerDot();
            playerAction();
            if (ceo.getBattleStats()[2] <= 0) {
                System.out.println("woohoo you won I guess");
                break;
            }
            sleep(200);
            // time interval in milliseconds

            enemyAction();
            if (pro.getBattleStats()[2] <= 0) {
                System.out.println("hah, loser");
                break;
            }
            sleep(200);
            // Display stats at the start of the turn
            tally++;
        }
    }
    public void playerAction() {
        if (tally % 2 == 1) // this is an implement for stun moves, which will skip over enemy turn by += 1, also,
        {
            protagChoice = pro.choice();
            if ((!evasionCheck(ceo.getBattleStats()[4], pro.toString()))) {
                skillCheck();
                ceo.takeDmg(pro.getDmgDealt());
            } else {
                ceo.takeDmg(0);
            }
            tally += 1;
        }
    }
    public void enemyAction() {
        enemyChoice = ceo.enemyChoice();
        if (tally % 2 == 0) {
            if (!evasionCheck(pro.getBattleStats()[4], ceo.toString())) {
                enemySkillCheck();
                pro.takeDmg(ceo.getDmgDealt());
            } else {
                pro.takeDmg(0);
            }
            tally += 1;
        }
    }

    public boolean evasionCheck(int evasion, String name) {
        if (rand.nextInt(1, 101) <= evasion) {
            System.out.println(name + " whiffs their attack!");
            return true;
        }
        return false;
    }
    public void getInfo() {
        System.out.println("Your stats: " + pro.getBattleStats()[2] + " health, " + pro.getSkillPoints() + " skill points");
        System.out.println(status());
        System.out.println(ceo.getBattleStats()[2]);
    }
    /** Gives player rough estimate of enemy health**/
    public String status() {
        if (ceo.getBattleStats()[2] > (.66) * ceo.getHealth()) {
            return ceo.toString() + " looks unscathed, ready to take on the world! Yeah...";
        } else if ((ceo.getBattleStats()[2] < (.66) * ceo.getHealth()) && (ceo.getBattleStats()[2] > (.33) * ceo.getHealth())) {
            return ceo.toString() + " looks a bit injured, but still able to keep fighting.";
        } else {
            return ceo.toString() + " looks like they're two steps from keeling over.";
        }
    }
}
