package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

import static sun.audio.AudioPlayer.player;

public class Controller {

    @FXML
    private TextField input;
    @FXML
    private MediaView media;
    @FXML
    private Button button2;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider timeSlider;

    @FXML
    private void play(ActionEvent event) {
        Media n;
        try {
            n = new Media(input.getText());
        } catch (Exception e) {
            return;
        }
        if (media.getMediaPlayer() != null) {
            media.getMediaPlayer().dispose();
        }
        timeSlider.setValue(0.0);
        MediaPlayer player = new MediaPlayer(n);
        player.setAutoPlay(true);
        media.setMediaPlayer(player);
        media.setFitWidth(550);
        media.setFitHeight(600);


        volumeSlider.setValue(player.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volumeSlider.getValue() / 100);
            }
        });

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Duration total = media.getMediaPlayer().getTotalDuration();
                player.seek(total.multiply(timeSlider.getValue() * 0.01f));
                player.play();
            }
        });
    }

    @FXML
    private void stop(ActionEvent event) {
        //media.getMediaPlayer().setAutoPlay(false);
        media.getMediaPlayer().stop();
        //timeSlider.setValue(0.0);

    }

    @FXML
    private void pauseresume(ActionEvent event) {
        if (button2.getText().equals("PLAY")) {
            media.getMediaPlayer().play();
            button2.setText("PAUSE");
        } else {
            media.getMediaPlayer().pause();
            button2.setText("PLAY");
        }
    }
}