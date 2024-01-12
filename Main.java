public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] obligatory = new String[] {"lol"};
        obligatory[0] = "idk";
        boolean a = obligatory[0].equals("lol");
        /*printSlow("You start here, on an unknown island, after the CEO of Racism\nWhat could go wrong?\n");*/
        new Lobby();
    }
    private static void printSlow(String text) {
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