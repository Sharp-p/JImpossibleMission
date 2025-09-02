package Model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameClock {
    /**
     * In the game the time will be managed in seconds.
     * In the game the clock start from 12:00:00, and it will end after 6h.
     */
    private int seconds = 3600 * 12;
    private final Timeline timeline;

    public GameClock() {
        timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                e -> seconds++));

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void addSeconds(int s) { seconds += s; }

    public int getSeconds() { return seconds; }

    public void start() { timeline.play(); }

    public void stop() { timeline.stop(); }

    public void reset() { seconds = 0; }

    @Override
    public String toString() {
        int hrs = seconds/3600;
        int min = (seconds % 3600) / 60;
        int sec = seconds % 60;

        return hrs + ":" + min + ":" + sec;
    }

}
