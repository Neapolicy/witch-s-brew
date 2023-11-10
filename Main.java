import java.util.Scanner;
public class Main {
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        printSlow("You start here, in an unknown land, after the CEO of Racism\nWhat could go wrong?\n");
        String answer = s.nextLine();
        answer = answer.toLowerCase();
        System.out.flush();
        System.out.println("lol");
    }

    private static void printSlow(String text)
    {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(45L);    // time interval in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
