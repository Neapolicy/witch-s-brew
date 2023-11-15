import java.util.Random;
import java.util.Scanner;
public class StockMarket {
    private int cash;
    private Random rand = new Random();
    private Scanner s = new Scanner(System.in);
    private int answer;
    public void setCash(int cash)
    {
        this.cash = cash;
        System.out.println("Invest (1) or sell (2)? (Current balance is" + cash + ")");
        answer = s.nextInt();
        if (answer == 1)
        {
            invest();
        }
        else if (answer == 2)
        {

        }
        else
        {
            System.out.println("...Please try again");
            setCash(cash);
        }
    }
    public void invest()
    {
        System.out.println("How much would you like to invest?");
        answer = s.nextInt();
        if (answer > cash)
        {
            System.out.println("As much as I would like to allow you to invest more than you can, I can't, sorry.");
            invest();
        }
        else
        {

        }
    }
}
