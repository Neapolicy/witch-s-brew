import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private int balance;
    private Scanner s = new Scanner(System.in);
    private String answer;
    private ArrayList<String> goods = new ArrayList<>();
    private ArrayList<String> bought = new ArrayList<>(); // keeps track of bought good

    public Shop() {
        addItems();
    }

    public void goods() {
        System.out.println("\nWhat would you like to buy?\nYou have " + balance + " shells\nType -1 to exit, -2 for inventory");
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


    public void priceComparison(int cost, String item) {
        if (balance >= cost) {
            balance -= cost;
            System.out.println("Successfully bought " + item + "!");
            bought.add(item);
            goods();
        } else {
            System.out.println("Invalid, please try again\n");
            goods();
        }
    }
    public void printInv()
    {
        System.out.println();
        if (bought.size() == 0)
        {
            System.out.println("You've got nothing!\n");
        }
        else {
            for (int i = 0; i < bought.size(); i++) {
                System.out.println(i + 1 + ": " + bought.get(i));
            }
        }
        goods();
    }

    public void buyItems(String res) {
        switch (res) {
            case "sword", "1" -> priceComparison(20, "Sword");
            case "flintlock", "2" -> priceComparison(70, "Flintlock");
            case "musket", "3" -> priceComparison(100, "Musket");
            case "mechanical arm", "4" -> priceComparison(35, "Better Boots");
            case "better boots", "5" -> priceComparison(30, "Stronger Steel");
            case "surgeons toolkit", "6" -> priceComparison(40, "Surgeons Toolkit");
            case "smoke bomb", "7" -> priceComparison(60, "Smoke Bomb");
            case "powdered wig", "8" -> priceComparison(500, "Powdered Wig");
            case "off-hand revolver", "9" -> priceComparison(500, "Off-hand Revolver");
            case "-1" -> {}
            case "-2" -> printInv();
            default -> {
                System.out.println("Invalid choice, please try again\n");
                goods();
            }
        }
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<String> getItemsBought()
    {
        return bought;
    }

    public void addItems() {
        goods.add("Sword (Weapon) - 20 shells");
        goods.add("Flintlock (Weapon) - 70 shells");
        goods.add("Musket (Weapon) - 100 shells");
        goods.add("Better Boots (Accessory) - 35 shells");
        goods.add("Stronger Steel (Accessory) - 30 shells");
        goods.add("Surgeons Toolkit (Accessory) - 40 shells");
        goods.add("Smoke Bomb (Accessory) - 60 shells");
        goods.add("Powdered Wig (Accessory) - 500 shells");
        goods.add("Off-hand Revolver (Off-hand Weapon) - 500 shells");
    }
}