import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
    public void sound(String name) { // code found on stack overflow
        // (https://stackoverflow.com/questions/69870550/java-music-file-returns-no-such-file-or-directory-but-path-seems-correct)
        try {

            File file = new File("Sound/Music/" + name + ".wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();

            // sleep to allow enough time for the clip to play
            Thread.sleep(400);

            stream.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}