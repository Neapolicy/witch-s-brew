import java.util.Random;
import java.util.Scanner;
public class StockMarket {
    private int cash;
    private int stockVal;
    private Random rand = new Random();
    private Scanner s = new Scanner(System.in);
    private int answer;
    private String RESET = "\u001B[0m";
    public void setCash(int cash)
    {
        this.cash = cash;
        cashFlow();
        System.out.println("Invest (1) or sell (2)? (Current balance is " + "\u001B[32m" + cash + RESET + " shells)");
        System.out.println("Your stocks are currently worth " + "\u001B[32m" + stockVal + RESET + " shells");
        answer = s.nextInt();
        if (answer == 1)
        {
            invest();
        }
        else if (answer == 2)
        {
            sell();
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
        answer = Math.abs(s.nextInt());
        if (answer > cash)
        {
            System.out.println("As much as I would like to allow you to invest yourself into debt, I can't, sorry.");
            invest();
        }
        else
        {
            stockVal += answer;
            cash -= answer;
            cashBack();
        }
    }
    public void sell()
    {
        System.out.println("How much would you like to sell?");
        answer = Math.abs(s.nextInt());
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
        double multiplier = rand.nextDouble(-.2, .35);
        stockVal *=  (1 + multiplier);
    }
}
