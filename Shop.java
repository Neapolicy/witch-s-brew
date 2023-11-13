import java.util.ArrayList;
import java.util.Scanner;
public class Shop {
    private int balance = 499;
    private Scanner s = new Scanner(System.in);
    private String answer;
    private int index;
    private int cost;
    private ArrayList<String> goods = new ArrayList<String>();
    private ArrayList<String> bought = new ArrayList<String>(); //keeps track of bought good
    public Shop()
    {
        addItems();
    }
    public void goods()
    {
        System.out.println("What would you like to buy?\nYou have " + balance + " dabloons\n");
        for (int i = 0; i < goods.size(); i++)
        {
            System.out.println(i + 1 + ": " + goods.get(i));
        }
        answer = s.nextLine();
        answer.toLowerCase();
        buyItems(answer);
    }
    public int findItem(String item)
    {
        for (int i = 0; i < goods.size(); i++)
        {
            if (goods.contains(item))
            {
                return i;
            }
        }
        return 0;
    }

    public void priceComparison(int cost, String item)
    {
        if (balance >= cost)
        {
            balance -= cost;
            System.out.println("You have successfully bought " + item);
            goods.remove(findItem(item));
            goods();
        }
        else
        {
            System.out.println("Invalid, please try again\n");
            goods();
        }
    }
    public void buyItems(String res) {
        switch (res) {
            case "sword", "1" -> priceComparison(20, "Sword");
            case "flintlock", "2" -> priceComparison(70, "Flintlock");
            case "musket", "3" -> priceComparison(100, "Musket");
            case "mechanical arm", "4" -> priceComparison(35, "Mechanical Arm");
            case "stronger steel", "5" -> priceComparison(30, "Stronger Steel");
            case "surgeons toolkit", "6" -> priceComparison(40, "Surgeons Toolkit");
            case "smoke bomb", "7" -> priceComparison(60, "Smoke Bomb");
            case "powdered wig", "8" -> priceComparison(500, "Powdered Wig");
            default -> {
                System.out.println("Invalid choice, please try again\n");
                goods();
            }
        }
    }
    public void setBalance(int balance)
    {
        this.balance = balance;
    }
    public void addItems()
    {
        goods.add("Sword (Weapon) - 20 dabloons");
        goods.add("Flintlock (Weapon) - 50 dabloons");
        goods.add("Musket (Weapon) - 100 dabloons");
        goods.add("Mechanical Arm (Accessory) - 35 dabloons"); // the ones with the weird and sucky names are placeholders
        goods.add("Stronger Steel (Accessory) - 30 dabloons");
        goods.add("Surgeons Toolkit (Accessory) - 40 dabloons");
        goods.add("Smoke Bomb (Accessory) - 60 dabloons");
        goods.add("Powdered Wig (Accessory) - 500 dabloons");
    }
}
