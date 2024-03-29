import static java.lang.Thread.sleep;
import java.util.Random;

public class BossRaidOni { // you have ten tally, it ends either when you or the boss dies, or 10 tally have passed
    private int tally = 1;
    private double multiplier = 1;
    private int protagChoice;
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

    public BossRaidOni(Protagonist pro) throws InterruptedException {
        this.pro = pro;
        game();
    }
    public void game() throws InterruptedException { //remember to change this to prioritize speed
        pro.accessoriesCheck();
        pro.weaponBoost();
        CEO.attempts++;
        System.out.println("Attempt " + CEO.attempts);
        System.out.println("You were ambushed by the CEO of Racism!");
        if (CEO.attempts > 1) System.out.println("How did you let him get the jump on you again??");
        sound.play("FIGHT-BACK", false);
        pro.takeDmg((int) (.1 * pro.getBattleStats()[2]));
        while (pro.getBattleStats()[2] > 0 && tally <= 10) {
            while (rand.nextInt(1, 11) != 5)
            {
                multiplier ++;
            }
            if (multiplier > 1.5) {
                pro.alterStats(0, 1.1);
                multiplier = 1;
            }
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
                Thread.sleep(3000);
                new BossRaidOni(pro);
                break;
            }
            sleep(200);
            // Display stats at the start of the turn
        }
    }
    public void playerAction() throws InterruptedException {
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
    public void ceoAction() throws InterruptedException {
        ceo.enemyChoice();
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
            case "Basic Attack" -> parryCheck();
            case "Taunt" -> //permanently lowers your defense by 5%
                    pro.alterStats(1, .95);
            case "Impale" -> { //permanently lowers your attack by 3%, applies dot
                if (!parryCheck()) {
                    pro.alterStats(0, .97);
                    playerDot = true;
                }
            }
            case "Axe Kick" -> {
                if (pro.getBattleStats()[2] <= pro.getMaxHealth() * .3) {
                    System.out.println("You got executed by his axe kick, ngl I think it's a skill issue\n");
                    pro.alterStats(2, 0);
                }
            }
            case "RAGING DEMON" -> {
                if (!parryCheck()) {
                    sound.play("FIGHT-BACK", false);
                }
            }
        }
        pro.resetParry();
    }

    public boolean parryCheck()
    {
        if (pro.getParry()) {
            System.out.println(pro.toString() + " parries " + ceo.toString() + "'s attack!");
            sound.play("Parry", false);
            ceo.resetDmg();
            return true;
        }
        return false;
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