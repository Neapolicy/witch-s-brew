import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {
    private String name;
    private Sound sound = new Sound(); // add sound effects for selecting stuff, and errors
    private Shop shop = new Shop();
    private StockMarket stockMarket = new StockMarket();
    private int balance = 4000; //for the sake of demonstration and stuff, you get way more money than you're supposed to
    private int equipNum;
    private String answer;
    public static int turns = 2;
    private int days = 1;
    private String RESET = "\u001B[0m";
    private Protagonist pro = new Protagonist();
    private final Scanner s = new Scanner(System.in);

    public Lobby() throws InterruptedException // i might have to delegate cash flow to the lobby function instead of doing it in the stock market itself
    {
        System.out.print("Enter your name: ");
        name = "\u001B[34m" + s.nextLine() + RESET;
        pro.setName(name);
        decisionMaker();
    }

    public void decisionMaker() throws InterruptedException { // make your choice, spend your day wisely
        System.out.println("Day " + "\u001B[35m" + days + RESET);
        System.out.println("You have " + "\u001B[35m" + (3 - days) + RESET + " days left");
        while (turns != 0) {
            pro.resetStats();
            System.out.println("\nWhat would " + name + " like to do?\n");
            System.out.println("(1) Lower this island's security");
            System.out.println("(2) Invest in the stock market");
            System.out.println("(3) Access the shop");
            System.out.println("(4) Edit character");
            System.out.println("(5) Skip a day");
            System.out.println("(You currently have " + "\u001B[32m" + balance + RESET + " shells)");
            System.out.println("You have " + "\u001B[33m" + turns + RESET + " moves left" );
            answer = s.nextLine();
            switch (answer) {
                case "1" -> {
                    Gameboard g = new Gameboard(days, pro);
                    if (!g.getResults()) {
                        System.out.println(g.getPro() + " is victorious!");
                        balance += g.getBalance();
                        pro.updateStats(days);
                        turns -= 1;
                    } else {
                        System.out.println("""
                                \u001B[36m
                                Your soul got sent to the depths, harsh lesson you learned from the CEO goons huh?
                                """);
                        System.out.println("\nGame Over, Thanks For Playing!");
                        sound.play("To_The_Depths!", false);
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
                    balance = shop.getBalance();
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
                case "5" -> turns = 0;
            }
        }
        dayCheck();
    }

    public void dayCount() throws InterruptedException {
        stockMarket.cashFlow();
        System.out.println("A day has passed");
        days += 1;
        turns = 2;
        decisionMaker();
    }
    public void dayCheck() throws InterruptedException {
        if (days == 3) {bossRaidOni();}
        else {dayCount();}
    }

    private void bossRaidOni() throws InterruptedException {
        System.out.println("Last chance to edit your character before the Boss Fight\n");
        editChar();
        new BossRaidOni(pro);
    }

    public void editChar() throws InterruptedException {
        System.out.println("\nCharacter Stats (Attack, Defense, Health, Debuff Resistance, and Evasion, respectively)");
        System.out.println("Do note that this is after the modifications of your stats through accessories");
        for (int charStat : pro.getBattleStats()) {System.out.print(charStat + " ");}
        System.out.println("\n\nHow would you like to modify your character?");
        System.out.println("1) Change Weapons");
        System.out.println("2) Equip Accessories");
        System.out.println("3) Inspect Skills");
        System.out.println("(-1 to exit)");
        String answer = s.nextLine().toLowerCase();
        switch (answer) {
            case "1", "weapon" -> changeWep();
            case "2", "accessories" -> changeAcc();
            case "3", "inspect" -> inspect();
            case "-1" ->
            {
                System.out.println("Successfully exited\n");
                decisionMaker();
            }
        }
    }

    public void changeAcc() //change accessories
    {
        if (!shop.getItemsBought().isEmpty()) // checks if accessory is empty
        {
            System.out.println("\nWhich accessories would you like to equip? (-1) to exit\n");
            for (int i = 0; i < shop.getItemsBought().size(); i++) {
                if (i == 0) System.out.println("Here's your accessories!!");
                System.out.println(i + 1 + ": " + shop.getItemsBought().get(i));
            }
            printAccessory();
            System.out.println("Please type the number corresponding to the accessory you would like to equip");
            equipNum = s.nextInt();
            s.nextLine();
            try
            {
                if (equipNum == -1) {editChar();}
                else if (shop.getItemsBought().contains(shop.getItemsBought().get(equipNum - 1)))
                {
                    if (pro.getInvSize() == 3)
                    {
                        System.out.println("Inventory is full!\nWhich item would you like to remove?");
                        printAccessory();
                        System.out.println("Type the number corresponding to the position of the item you would like to remove");
                        try {
                            int temp = Math.abs(s.nextInt());
                            shop.addAccessory(pro.getAccessoriesOn().get(temp - 1)); // returns the removed item back to your inventory
                            pro.getAccessoriesOn().remove(temp - 1); //actually removes it from your inventory
                        }
                        catch (Exception e)
                        {
                            System.out.println("Can't do that!");
                        }
                    }
                    else {
                        pro.addAccessory(shop.getItemsBought().get(equipNum - 1));
                        sound.play("accessory_equip", false);
                        shop.getItemsBought().remove(equipNum - 1);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid option, please try again!");
            }
            changeAcc();
        }
    }
    public void changeWep() throws InterruptedException // REMEMBER TO ADD SFX TO ACCESSORY EQUIP, SKILL EQUIP(IDK IF I HAVE TO DO THIS IF I'M LOWERING THE SKILLS YOU CAN GET), AND ACCESSORY REMOVAL
    {
        if (!shop.getWeaponsBought().isEmpty())
        {
            System.out.println("You currently have a " + pro.getWeapon() + " as your main weapon\n");
            System.out.println("You currently have a " + pro.getSideArm() + " as your side weapon\n");
            System.out.println("\nWhich weapon would you like to equip? (-1) to exit");
            for (int i = 0; i < shop.getWeaponsBought().size(); i++) {
                if (i == 0) System.out.println("Here's your weapons!!");
                System.out.println(i + 1 + ": " + shop.getWeaponsBought().get(i));
            }
            System.out.println("\nPlease type the number corresponding to the weapon you would like to equip");
            equipNum = s.nextInt();
            s.nextLine(); //necessary, consumes the line
            if (equipNum == -1) {editChar();}
            else if (equipNum <= shop.getWeaponsBought().size())
            {
                if (shop.getWeaponsBought().contains(shop.getWeaponsBought().get(equipNum - 1)))
                {
                    String wep = shop.getWeaponsBought().get(equipNum - 1); //checks if the weapon you equipped is a side arm
                    if (wep.equals("Off-hand Revolver")) {pro.setSide(wep);}
                    else {pro.setWeapon(shop.getWeaponsBought().get(equipNum - 1));}
                    sound.play("Equip", false); // i don't think equip actually has any audio to it for some reason?
                }
                changeWep();
            }
            else {System.out.println("Invalid option, please try again!");}
            changeWep();
        }
    }

    public void inspect()
    {
        System.out.println("\nWhich Skill would you like to inspect? (-1) to exit");
        for (int i = 0; i < pro.getSkills().size(); i++) {
            if (i == 0) System.out.println("Here's your skills!!");
            System.out.println(i + 1 + ": " + pro.getSkills().get(i));
        }
        System.out.println("\nPlease type the number corresponding to the skill you would like to inspect");
        equipNum = s.nextInt();
        s.nextLine();
        try{
            if(equipNum == -1) {editChar();}
            else
            {
                equipNum = Math.abs(equipNum);
                switch (pro.getSkills().get(equipNum - 1)) {
                    case "Basic Attack" -> System.out.println("Your primary attack, costs no SP, gives you one SP on use, does moderate damage\n");
                    case "Parry" -> System.out.println("Prepare to parry the next BASIC attack, does not work on skills, 1 SP\n");
                    case "Uppercut" -> System.out.println("An attack that does low damage, but has a high chance of stunning your opponent, allowing you to take another action, 1 SP\n");
                    case "Fireball" -> System.out.println("A generic fireball, does damage over time on your enemy, and deals decent base damage, 1 SP\n");
                    case "Chainsaw" -> System.out.println("A chainsaw, damage scales with the amount of SP you invest in it, does damage over time, SP varies\n");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Can't do that, chum.");
        }
        inspect();
    }
    public void printAccessory()
    {
        if (pro.getInvSize() > 0) {
            System.out.println("\nHere's the items you currently have equipped\n");
            for (int i = 0; i < pro.getAccessoriesOn().size(); i++)
            {
                System.out.println(pro.getAccessoriesOn().get(i));
            }
            System.out.println();
        }
    }
    public void updateBalance() {
        balance = stockMarket.cashBack();
    }
}
