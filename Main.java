public class Main {
    public static void main(String[] args) throws InterruptedException {
        //printSlow("You start here, in an unknown land, after the CEO of Racism\nWhat could go wrong?\n");
        Lobby lobby = new Lobby();
        Sound sound = new Sound();
        /*sound.sound("To_The_Depths!", 2000);*/
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
