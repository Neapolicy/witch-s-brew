import static java.lang.Thread.sleep;
import java.util.Random;

public class BossRaidOni { // you have ten tally, it ends either when you or the boss dies, or 10 tally have passed
    private int tally = 1;
    private double multiplier;
    private int protagChoice;
    private int ceoChoice;
    private Protagonist pro;
    private CEO ceo = new CEO();
    private Random rand = new Random();
    private Sound sound = new Sound();
    private String skill;
    private int EnemyDotDuration;
    private int PlayerDotDuration;
    private int PlayerDotDuration2;
    private boolean enemyDot;
    private boolean playerDot;
    private boolean playerSaw;
    private boolean playerAdvantage;

    public BossRaidOni(Protagonist pro) throws InterruptedException {
        this.pro = pro;
        game();
    }
    public void game() throws InterruptedException { //remember to change this to prioritize speed
        pro.accessoriesCheck();
        pro.weaponBoost();
        System.out.println("You were ambushed by the CEO of Racism!");
        if (CEO.attempts > 1) System.out.println("How did you let him get the jump on you again");
        sound.sound("FIGHT-BACK", 5000);
        pro.takeDmg((int) (.3 * pro.getBattleStats()[2]));
        while (pro.getBattleStats()[2] > 0 && tally <= 10) {
            while (rand.nextInt(1, 11) != 5)
            {
                multiplier ++;
            }
            if (multiplier > 1.3) {playerAdvantage = true;}
            System.out.println("Turn " + tally);
            getInfo();
            enemyDot();//enemy takes dot
            DotP();
            playerDot();// player takes dot if inflicted
            playerAction();
            if (ceo.getBattleStats()[2] <= 0) {
                System.out.println("woohoo you won I guess");
                break;
            }
            sleep(200);
            // time interval in milliseconds

            ceoAction();
            if (pro.getBattleStats()[2] <= 0) {
                System.out.println("hah, loser, we're going in for another round BABY");
                pro.resetStats();
                CEO.attempts += 1;
                System.out.println("Attempt " + CEO.attempts);
                BossRaidOni retry = new BossRaidOni(pro);
                break;
            }
            sleep(200);
            // Display stats at the start of the turn
            tally++;
        }
    }
    public void playerAction() {
        if (tally % 2 == 1) // this is an implement for stun moves, which will skip over ceo turn by += 1, also,
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
    public void ceoAction() {
        ceoChoice = ceo.enemyChoice();
        if (tally % 2 == 0) {
            if (!evasionCheck(pro.getBattleStats()[4], ceo.toString())) {
                ceoSkillCheck();
                pro.takeDmg(ceo.getDmgDealt());
            } else {
                pro.takeDmg(0);
            }
            tally += 1;
        }
    }
    public void skillCheck() {
        skill = pro.getSkills().get(protagChoice - 1);
        switch (skill) {
            case "Basic Attack" -> { // parries only work against basic attacks, not skills, also enemy parries work slightly differently, i give up on fixing this bug, so its a feature now
            }
            case "Uppercut" -> { //should only determine stun, theoretically, also uppercut goes past guard, it is intentional
                if (rand.nextBoolean()) {
                    if (rand.nextInt(1, 101) <= ceo.getBattleStats()[3]) {
                        System.out.println(ceo.toString() + " resisted the stun!\n");
                    } else {
                        System.out.println(ceo.toString() + " is stunned! Use this chance to strike them again!\n");
                        tally += 1;
                    }
                }
            }
            case "Fireball" -> {
                if (rand.nextInt(1, 101) <= ceo.getBattleStats()[3]) {
                    System.out.println(ceo.toString() + " was not set on fire\n");
                } else {
                    System.out.println(ceo.toString() + " is on fire!\n");
                    enemyDot = true;
                }
            }
            case "Chainsaw" -> {
                if (rand.nextInt(1, 101) >= ceo.getBattleStats()[3]) {
                    System.out.println(ceo.toString() + " is bleeding!\n");
                    playerSaw = true;
                }
            }
        }
    }
    /**Same function as skillCheck, except these handles enemies **/
    public void ceoSkillCheck() {
        skill = ceo.getSkill();
        switch (skill) {
            case "Basic Attack":
            case "Taunt":
            case "Impale":
            case "Axe Kick":
            case "RAGING DEMON":
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
    /** Gives player rough estimate of ceo health**/
    public String status() {
        if (ceo.getBattleStats()[2] > (.66) * ceo.getHealth()) {
            return ceo.toString() + " looks unscathed, ready to take on the world! Yeah...";
        } else if ((ceo.getBattleStats()[2] < (.66) * ceo.getHealth()) && (ceo.getBattleStats()[2] > (.33) * ceo.getHealth())) {
            return ceo.toString() + " looks a bit injured, but still able to keep fighting.";
        } else {
            return ceo.toString() + " looks like they're two steps from keeling over.";
        }
    }

    public void playerDot()
    {
        if (playerDot) {PlayerDotDuration = tally + 2;}
        playerDot = false;
        if (tally < PlayerDotDuration) {pro.takeDmg((int) (ceo.getBattleStats()[0] * .8));}
    }
    public void enemyDot()
    {
        if (enemyDot) {EnemyDotDuration = tally + 2;}
        enemyDot = false;
        if (tally < EnemyDotDuration) {ceo.takeDmg((int) (pro.getBattleStats()[0] * .4));}
    }

    public void DotP() // enemy bleeds
    {
        if (playerSaw) {PlayerDotDuration2 = tally + 3;}
        playerSaw = false;
        if (tally < PlayerDotDuration2) {ceo.takeDmg((int) (pro.getBattleStats()[0] * .7));}
    }
}