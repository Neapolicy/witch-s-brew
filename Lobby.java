import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {
    private String name;
    private Sound sound = new Sound();
    private Shop shop = new Shop();
    private StockMarket stockMarket = new StockMarket();
    private int balance = 4000;
    private ArrayList<String> boughtStuff = new ArrayList<>();
    private String answer;
    private int turns = 2;
    private int days = 1;
    private final Scanner s = new Scanner(System.in);

    public Lobby() // i might have to delegate cash flow to the lobby function instead of doing it in the stock market itself
    {
        System.out.println("And what's your name, o questionable hero(?)");
        name = s.nextLine();
        decisionMaker();
    }

    public void decisionMaker() {
        while (turns != 0 && days != 7) {
            System.out.println("\nWhat would " + name + " like to do?\n");
            System.out.println("Lower this island's security (1)");
            System.out.println("Invest in the stock market (2)");
            System.out.println("Access the shop (3)");
            System.out.println("Check your inventory (4)");
            System.out.println("Edit character (5)");
            System.out.println("(You currently have " + balance + " shells");
            System.out.println(turns); // remember to remove this in the final version
            answer = s.nextLine();
            switch (answer) {
                case "1":
                    turns -= 1;
                    break;
                case "2":
                    stockMarket.setCash(balance);
                    balance = stockMarket.cashBack();
                    updateBalance();
                    turns -= 1;
                    break;
                case "3":
                    shop.setBalance(balance);
                    shop.goods();
                    break;
                case "4":
                    boughtStuff = shop.getItemsBought();
                    if (boughtStuff.size() == 0) {
                        System.out.println("Nothing here...");
                    } else {
                        System.out.println("This is your inventory");
                        for (int i = 0; i < boughtStuff.size(); i++) {
                            System.out.println(boughtStuff.size());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        dayCheck();
    }

    public void dayCount()
    {
        stockMarket.cashFlow();
        System.out.println("day pass");
        days += 1;
        turns = 2;
        decisionMaker();
    }
    public void dayCheck()
    {
        if (days == 7)
        {
            bossRaidOni();
        }
        else
        {
            dayCount();
        }
    }

    private void bossRaidOni()
    {

    }

    public void updateBalance() {
        balance = stockMarket.cashBack();
    }
}
