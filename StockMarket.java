import java.util.Random;
import java.util.Scanner;
public class StockMarket {
    private int cash;
    private int stockVal;
    private Random rand = new Random();
    private Scanner s = new Scanner(System.in);
    private int answer;
    public void setCash(int cash)
    {
        this.cash = cash;
        System.out.println("Invest (1), sell (2), or leave? (3) (Current balance is " + cash + ")");
        System.out.println("Your stocks are currently worth $" + stockVal);
        answer = s.nextInt();
        if (answer == 1)
        {
            invest();
        }
        else if (answer == 2)
        {
            sell();
        }
        else if (answer == 3){}
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
            System.out.println("As much as I would like to allow you to invest yourself into debt, I can't, sorry.");
            invest();
        }
        else
        {
            stockVal += answer;
            cashFlow();
        }
    }
    public void sell()
    {
        System.out.println("How much would you like to sell?");
        answer = s.nextInt();
        if (answer > stockVal)
        {
            System.out.println("Can't do that!");
            setCash(cash);
        }
        else
        {
            System.out.println("Selling successful, lets hope you don't create a depression!");
            cash += answer;
            stockVal -= answer;
            cashBack();
        }
    }
    public int cashBack()
    {
        return cash;
    }

    public void cashFlow()
    {
        double multiplier = rand.nextDouble(-.2, .2);
        stockVal *= (1 + multiplier);
    }
}
