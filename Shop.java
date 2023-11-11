import java.util.ArrayList;
import java.util.Scanner;
public class Shop {
    private int balance;
    private Scanner s = new Scanner(System.in);
    private String answer;
    private int cost;
    private ArrayList<String> goods = new ArrayList<String>();
    private ArrayList<String> bought = new ArrayList<String>();
    public Shop(int dilla)
    {
        addItems();
    }
    public void goods()
    {
        System.out.println("What would you like to buy?\nYou have " + balance + " raqs\n");
        for (int i = 0; i < goods.size(); i++)
        {
            System.out.println(i + 1 + ": " + goods.get(i));
        }
        answer = s.nextLine();
        answer.toLowerCase();
        buyItems(answer);
    }

    public boolean priceComparison()
    {
        return balance >= cost;
    }
    public void buyItems(String res) {
        switch(res)
        {
            case "sword":
                cost = 20;
                break;
            case "flintlock":
                cost = 50;
                break;
            case "musket":
                cost = 100;
                break;
            case "mechanical arm":
                cost = 35;
                break;
            case "stronger steel":
                cost = 30;
                break;
            case "surgeons toolkit":
                cost = 40;
                break;
            case "smoke bomb":
                cost = 60;
                break;
            case "powdered wig":
                cost = 500;
                break;
        }
        priceComparison();
    }
    public void setBalance(int balance)
    {
        this.balance = balance;
    }
    public void addItems()
    {
        goods.add("Sword (Weapon) - 20 raqs");
        goods.add("Flintlock (Weapon) - 50 raqs");
        goods.add("Musket (Weapon) - 100 raqs");
        goods.add("Mechanical Arm (Accessory) - 35 raqs"); // the ones with the weird and sucky names are placeholders
        goods.add("Stronger Steel (Accessory) - 30 raqs");
        goods.add("Surgeons Toolkit (Accessory) - 40 raqs");
        goods.add("Smoke Bomb (Accessory) - 60 raqs");
        goods.add("Powdered Wig (Accessory) - 500 raqs");
    }
}
