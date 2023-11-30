import static java.lang.Thread.sleep;
import java.util.Random;

public class BossRaidOni { // you have ten turns, it ends either when you or the boss dies, or 10 turns have passed
    private int turns = 1; //maybe add a buff?
    private int tally = 1;
    private double multiplier;
    private Protagonist pro;
    private Random rand = new Random();

    public BossRaidOni(Protagonist pro) throws InterruptedException {
        this.pro = pro;
        game();
    }
    public void game() throws InterruptedException { //remember to change this to prioritize speed
        pro.accessoriesCheck();
        pro.weaponBoost();
        while (pro.getBattleStats()[2] > 0 && tally <= 10) {
            if (tally % 5 == 0)
            {
                while (rand.nextInt(1, 11) != 5)
                {
                    multiplier ++;
                }
                if (multiplier )
            }
            System.out.println("Turn " + tally);
            getInfo();
            DotP(); //enemy takes dot if applicable
            enemyDot();
            DotE(); // player takes dot if inflicted
            playerDot();
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
            tally++;
        }
    }
}
