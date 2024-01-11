import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private int balance;
    private Scanner s = new Scanner(System.in);
    private String answer;
    private ArrayList<String> goods = new ArrayList<>();
    private ArrayList<String> bought = new ArrayList<>(); // keeps track of bought accessories
    private ArrayList<String> boughtWeapons = new ArrayList<>();
    private String RESET = "\u001B[0m";

    public Shop() {
        addItems();
    }

    public void goods() throws InterruptedException {
        System.out.println("\nWhat would you like to buy?\nYou have " + "\u001B[32m" + balance + RESET + " shells\n\nType -1 to exit, -2 for inventory\n");
        for (int i = 0; i < goods.size(); i++) {
            System.out.println(i + 1 + ": " + goods.get(i));
        }
        System.out.println();
        answer = getUserInput();
        buyItems(answer);
    }

    private String getUserInput() {
        System.out.print("Enter your choice: ");
        return s.nextLine().toLowerCase();
    }


    public void priceComparison(int cost, String item) throws InterruptedException {
        if (balance >= cost) {
            balance -= cost;
            System.out.println("Successfully bought " + item + "!");
            if (item.contains("weapon"))
            {
                boughtWeapons.add(item.substring(7));
            }
            else
            {
                bought.add(item);
            }
            goods();
        } else {
            System.out.println("Invalid, please try again\n");
            goods();
        }
    }
    public void printInv() throws InterruptedException {
        System.out.println();
        Thread.sleep(500);
        if (bought.isEmpty() && boughtWeapons.isEmpty())
        {
            System.out.println("You've got nothing!\n");
        }
        else {
            for (int i = 0; i < bought.size(); i++) {
                if (i == 0) System.out.println("Heres your accessories!!");
                System.out.println(i + 1 + ": " + bought.get(i));
            }
            System.out.println();
            for (int i = 0; i < boughtWeapons.size(); i++) {
                if (i == 0) System.out.println("Heres your weapons!!");
                System.out.println(i + 1 + ": " + boughtWeapons.get(i));
            }
        }
        Thread.sleep(500);
        goods();
    }

    public void buyItems(String res) throws InterruptedException {
        switch (res) {
            case "machete", "1" -> priceComparison(20, "weapon Machete");
            case "flintlock", "2" -> priceComparison(70, "weapon Flintlock");
            case "musket", "3" -> priceComparison(100, "weapon Musket");
            case "cool looking helmet", "4" -> priceComparison(35, "Cool Looking Helmet");
            case "padded armor", "5" -> priceComparison(30, "Padded Armor");
            case "surgeons toolkit", "6" -> priceComparison(40, "Surgeons Toolkit");
            case "smoke bomb", "7" -> priceComparison(60, "Smoke Bomb");
            case "powdered wig", "8" -> priceComparison(500, "Powdered Wig");
            case "off-hand revolver", "9" -> priceComparison(500, "weapon Off-hand Revolver");
            case "-1" -> {}
            case "-2" -> printInv();
            default -> {
                System.out.println("Invalid choice, please try again\n");
                goods();
            }
        }
    }

    public void setBalance(int balance) {this.balance = balance;}

    public int getBalance() {return balance;}

    public ArrayList<String> getItemsBought()
    {
        return bought;
    }
    public ArrayList<String> getWeaponsBought()
    {
        return boughtWeapons;
    }
    public void addAccessory(String name)
    {
        bought.add(name);
    }


    public void addItems() {
        goods.add("Machete (Weapon) Attack +20! - 20 shells");
        goods.add("Flintlock (Weapon) Attack +40! - 70 shells");
        goods.add("Musket (Weapon) Attack +60! - 100 shells");
        goods.add("Cool Looking Helmet (Accessory) Increases CC Resist! - 35 shells");
        goods.add("Padded Armor (Accessory) Increases Defense! - 30 shells");
        goods.add("Surgeons Toolkit (Accessory) Increases Health! - 40 shells");
        goods.add("Smoke Bomb (Accessory) Increases Evasion! - 60 shells");
        goods.add("Powdered Wig (Accessory) Doubles Attack! - 500 shells");
        goods.add("Off-hand Revolver (Off-hand Weapon) +30% Boost to Your Attack! - 500 shells");
    }
}