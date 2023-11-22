import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {
    private String name;
    private Sound sound = new Sound(); // add sound effects for selecting stuff, and errors
    private Shop shop = new Shop();
    private StockMarket stockMarket = new StockMarket();
    private int balance = 4000;
    private String answer;
    private int turns = 2;
    private int days = 1;
    private Protagonist pro = new Protagonist();
    private final Scanner s = new Scanner(System.in);

    public Lobby() throws InterruptedException // i might have to delegate cash flow to the lobby function instead of doing it in the stock market itself
    {
        System.out.print("And what's your name, questionable hero(?): ");
        name = s.nextLine();
        pro.setName(name);
        decisionMaker();
    }

    public void decisionMaker() throws InterruptedException { // make your choice, spend your day wisely
        System.out.println("Day " + days);
        System.out.println("You have " + (7 - days) + " days left");
        while (turns != 0 && days != 7) {
            System.out.println("\nWhat would " + name + " like to do?\n");
            System.out.println("Lower this island's security (1)");
            System.out.println("Invest in the stock market (2)");
            System.out.println("Access the shop (3)");
            System.out.println("Edit character (4)");
            System.out.println("(You currently have " + balance + " shells)");
            System.out.println("You have " + turns + " moves left" );
            answer = s.nextLine();
            switch (answer) {
                case "1":
                    Gameboard g = new Gameboard(days, pro);
                    if (!g.getResults())
                    {
                        balance += g.getBalance();
                        pro.updateStats(days);
                        turns -= 1;
                    }
                    else
                    {
                        System.out.println("\nYour soul got sent to the depths, harsh lesson you learned from the CEO goons huh?\n");
                        System.out.println("\nGame Over, Thanks For Playing!");
                        System.exit(1);
                    }
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
                    if (shop.getItemsBought().size() == 0 && shop.getWeaponsBought().size() == 0) {
                        System.out.println("Nothing here...\n");
                    } else {
                        System.out.println("This is your inventory");
                        for (int i = 0; i < shop.getItemsBought().size(); i++) {
                            System.out.println(shop.getItemsBought().get(i));
                        }
                        for (int i = 0; i < shop.getWeaponsBought().size(); i++) {
                            System.out.println(shop.getWeaponsBought().get(i));
                        }
                    }
                    pro.editChar(shop.getItemsBought(), shop.getWeaponsBought());
                    break;
            }
        }
        dayCheck();
    }

    public void dayCount() throws InterruptedException {
        stockMarket.cashFlow();
        System.out.println("A day had passed");
        days += 1;
        turns = 2;
        decisionMaker();
    }
    public void dayCheck() throws InterruptedException {
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
