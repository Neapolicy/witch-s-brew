import java.util.ArrayList;
import java.util.Scanner;
public class Lobby {
    private String name;
    private Shop shop = new Shop();
    private StockMarket stockMarket = new StockMarket();
    private int balance = 4000;
    private ArrayList<String> boughtStuff = new ArrayList<>();
    private String answer;
    private final Scanner s = new Scanner(System.in);
    public Lobby()
    {
        System.out.println("And what's your name, hero(?)");
        name = s.nextLine();
        decisionMaker();
    }
    public void decisionMaker()
    {
        System.out.println("\nWhat would " + name + " like to do?\n");
        System.out.println("Invest in the stock market (1), lower this island's security (2), access the shop (3), or check inventory? (4)");
        answer = s.nextLine();
        switch (answer)
        {
            case "1":
            case "2":
                stockMarket.setCash(balance);
            case "3":
                shop.setBalance(balance);
                shop.goods();
            case "4":
                boughtStuff = shop.getItemsBought();
                System.out.println("This is your inventory");
                if (boughtStuff.size() == 0)
                {
                    System.out.println("Nothing here...");
                }
                else {
                    for (int i = 0; i < boughtStuff.size(); i++)
                    {
                        System.out.println(boughtStuff.size());
                    }
                }
            default:
                decisionMaker();
        }
    }
}
