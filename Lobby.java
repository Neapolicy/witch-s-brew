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
            pro.resetStats();
            System.out.println("\nWhat would " + name + " like to do?\n");
            System.out.println("Lower this island's security (1)");
            System.out.println("Invest in the stock market (2)");
            System.out.println("Access the shop (3)");
            System.out.println("Edit character (4)");
            System.out.println("(You currently have " + balance + " shells)");
            System.out.println("You have " + turns + " moves left" );
            answer = s.nextLine();
            switch (answer) {
                case "1" -> {
                    Gameboard g = new Gameboard(days, pro);
                    if (!g.getResults()) {
                        balance += g.getBalance();
                        pro.updateStats(days);
                        turns -= 1;
                    } else {
                        System.out.println("\nYour soul got sent to the depths, harsh lesson you learned from the CEO goons huh?\n");
                        System.out.println("\nGame Over, Thanks For Playing!");
                        System.exit(1);
                    }
                }
                case "2" -> {
                    stockMarket.setCash(balance);
                    balance = stockMarket.cashBack();
                    updateBalance();
                    turns -= 1;
                }
                case "3" -> {
                    shop.setBalance(balance);
                    shop.goods();
                }
                case "4" -> {
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
                    editChar(shop.getItemsBought(), shop.getWeaponsBought());
                }
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

    public void editChar(ArrayList<String> inv, ArrayList<String> invWep) {
        System.out.print("Character Stats (attack, defense, health, resistance to debuffs, evasion, respectively)");
        System.out.println("Do note that this is after the modifications of your stats through accessories");
        for (int charStat : pro.getBattleStats()) {
            System.out.print(charStat + " ");
        }
        System.out.println("How would you like to modify your character?");
        System.out.println("1) Change Weapons");
        System.out.println("2) Add/Alter Skills");
        System.out.println("3) Equip Accessories");
        String answer = s.nextLine().toLowerCase();
        switch (answer) {
            case "1", "weapon" -> changeWep();
            case "2", "skills" -> changeSkill();
            case "3", "accessories" -> changeAcc();
            default -> System.out.println("Not an option, chum");
        }
    }

    public void changeAcc()
    {

    }
    public void changeWep()
    {
        if (!shop.getWeaponsBought().isEmpty())
        {
            System.out.println("Which weapon would you like to equip?");
            for (int i = 0; i < shop.getWeaponsBought().size(); i++) {
                if (i == 0) System.out.println("Here's your weapons!!");
                System.out.println(i + 1 + ": " + shop.getWeaponsBought().get(i));
            }
            answer = s.nextLine().toLowerCase();
            if (shop.getWeaponsBought().contains(answer))
            {
                pro.setWeapon(answer);
            }
        }
    }
    public void changeSkill()
    {

    }

    public void updateBalance() {
        balance = stockMarket.cashBack();
    }
}
