public class Main {
    public static void main(String[] args)
    {
        //printSlow("You start here, in an unknown land, after the CEO of Racism\nWhat could go wrong?\n");
        Shop s = new Shop();
        s.goods();
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
