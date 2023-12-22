public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] obligatory = new String[] {"lol"};
        obligatory[0] = "idk";
        printSlow("You start here, on an unknown island, after the CEO of Racism\nWhat could go wrong?\n");
        Lobby lobby = new Lobby();
        Sound sound = new Sound();
        /*sound.sound("Finger-Snap", 500);
        sound.sound("Fireball", 400);
        sound.sound("Explosion", 400);*/
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
