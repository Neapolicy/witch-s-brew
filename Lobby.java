import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {
    private String name;
    private Sound sound = new Sound(); // add sound effects for selecting stuff, and errors
    private Shop shop = new Shop();
    private StockMarket stockMarket = new StockMarket();
    private int balance = 4000;
    private int equipNum;
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
                        sound.sound("To_The_Depths!", 2000);
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
                    if (shop.getItemsBought().isEmpty() && shop.getWeaponsBought().isEmpty()) {
                        System.out.println("Nothing here...\n");
                    } else {
                        System.out.println("\nThis is your inventory");
                        for (int i = 0; i < shop.getItemsBought().size(); i++) {
                            System.out.println(shop.getItemsBought().get(i));
                        }
                        for (int i = 0; i < shop.getWeaponsBought().size(); i++) {
                            System.out.println(shop.getWeaponsBought().get(i));
                        }
                    }
                    editChar();
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
        if (days == 7) {bossRaidOni();}
        else {dayCount();}
    }

    private void bossRaidOni()
    {

    }

    public void editChar() {
        System.out.println("\nCharacter Stats (Attack, Defense, Health, Debuff Resistance, and Evasion, respectively)");
        System.out.println("Do note that this is after the modifications of your stats through accessories");
        for (int charStat : pro.getBattleStats()) {
            System.out.print(charStat + " ");
        }
        System.out.println("\n\nHow would you like to modify your character?");
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

    public void changeAcc() //change accessories
    {
        if (!shop.getItemsBought().isEmpty()) // checks if accessory is empty
        {
            System.out.println("\nWhich accessories would you like to equip? (-1) to exit");
            for (int i = 0; i < shop.getItemsBought().size(); i++) {
                if (i == 0) System.out.println("Here's your accessories!!");
                System.out.println(i + 1 + ": " + shop.getItemsBought().get(i));
            }
            printAccessory();
            System.out.println("Please type the number corresponding to the accessory you would like to equip");
            equipNum = s.nextInt();
            if (shop.getItemsBought().contains(shop.getItemsBought().get(equipNum - 1)))
            {
                if (pro.getInvSize() == 3)
                {
                    System.out.println("Inventory is full!\nWhich item would you like to remove?");
                    printAccessory();
                    try {
                        int temp = s.nextInt();
                        shop.addAccessory(pro.getAccessoriesOn().get(temp - 1)); // returns the removed item back to your inventory
                        pro.getAccessoriesOn().remove(temp - 1); //actually removes it from your inventory
                    }
                    catch (Exception e)
                    {
                        System.out.println("Can't do that!");
                        changeAcc();
                    }
                }
                else {
                    pro.addAccessory(shop.getItemsBought().get(equipNum - 1));
                    shop.getItemsBought().remove(equipNum - 1);
                }
            }
            else if (equipNum == -1) {}
            else
            {
                System.out.println("Invalid option, please try again!");
                changeAcc();
            }
        }
    }
    public void changeWep()
    {
        if (!shop.getWeaponsBought().isEmpty())
        {
            System.out.println("\nWhich weapon would you like to equip? (-1) to exit");
            for (int i = 0; i < shop.getWeaponsBought().size(); i++) {
                if (i == 0) System.out.println("Here's your weapons!!");
                System.out.println(i + 1 + ": " + shop.getWeaponsBought().get(i));
            }
            System.out.println("Please type the number corresponding to the weapon you would like to equip");
            equipNum = s.nextInt();
            if (shop.getWeaponsBought().contains(shop.getWeaponsBought().get(equipNum - 1)))
            {
                String wep = shop.getWeaponsBought().get(equipNum - 1); //checks if the qeapon you equipped is a side arm
                if (wep.equals("Off-hand Revolver")) {pro.setSide(wep);}
                else {pro.setWeapon(shop.getWeaponsBought().get(equipNum - 1));}
                sound.sound("Equip", 1800);
            }
            else if (equipNum == -1) {}
            else
            {
                System.out.println("Invalid option, please try again!");
                changeWep();
            }
        }
    }
    public void changeSkill() //skill editing, will do once i get the motivation to finish accessory changing, actually i might just lower the number of skills down to 4
    {
        System.out.println("Here are your skills! (-1 to exit)\n");
        for (int i = 0; i < pro.getSkillList().size(); i++) {
            System.out.println(i + 1 + ": " + pro.getSkillList().get(i));
        }
        System.out.println("Please type the number corresponding to the skill you would like to equip/change\n(You cannot swap out your basic attack)");
        equipNum = s.nextInt();
        if (shop.getItemsBought().contains(shop.getItemsBought().get(equipNum - 1)))
        {
            if (pro.getSkillList().size() > 4)
            {
                System.out.println("Skill list is full!\nWhich skill would you like to replace?");

            }
            else {pro.addSkill(pro.getSkillList().get(equipNum - 1));}
        }
        else if (equipNum == -1) {}
        else
        {
            System.out.println("Invalid option, please try again!");
            changeAcc();
        }
    }
    public void printAccessory()
    {
        if (pro.getInvSize() > 0) {
            System.out.println("\nHere's the items you currently have equipped\n");
            for (int i = 0; i < pro.getAccessoriesOn().size(); i++)
            {
                System.out.println(pro.getAccessoriesOn().get(i));
            }
        }
    }
    public void updateBalance() {
        balance = stockMarket.cashBack();
    }
}
