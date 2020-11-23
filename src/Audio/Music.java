package Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    public Media media;
    public MediaPlayer nhacNen;
    public Music(String fileMp3) {
        media = new Media(new File(fileMp3).toURI().toString());
        nhacNen = new MediaPlayer(media);
    }

}
