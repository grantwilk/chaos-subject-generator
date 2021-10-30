package chaos.util;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import static java.lang.String.format;

public class chaosTimer {

    private long lengthNano;

    private long initTime;
    private long endTime;
    private long timeRemaining;

    private Label timerLabel;

    private StringProperty timeText = new SimpleStringProperty();

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (timeRemaining > 0) {
                timeRemaining = endTime - System.nanoTime();
                updateProperties();
            } else {
                expire();
            }
        }
    };

    private final AudioClip alarm = new AudioClip(
            getClass().getClassLoader().getResource("audio/alarm.mp3").toString());

    /**
     * Creates a new timer of a specified length
     * @param lengthSeconds - the amount of time that the timer should run for in seconds
     */
    public chaosTimer(double lengthSeconds, Label timerLabel) {
        this.lengthNano = (long) (lengthSeconds * 1E9);
        this.timerLabel = timerLabel;
        timeRemaining = lengthNano;
        timeText.setValue(toStringDynamic());
    }

    /**
     * Starts the timer
     */
    public void start() {
        initTime = System.nanoTime();
        endTime = initTime + timeRemaining;
        timer.start();
    }

    /**
     * Stops the timer from counting and holds its value
     */
    public void stop() {
        timer.stop();
        alarm.stop();
    }

    /**
     * Stops and resets the timer back to its default value
     */
    public void reset() {
        timer.stop();
        alarm.stop();
        timeRemaining = lengthNano;
        updateProperties();
    }

    /**
     * Sets the amount of time that the timer should run for in seconds
     * @param lengthSeconds the amount of time that the timer should run for in seconds
     */
    public void setTime(double lengthSeconds) {
        this.lengthNano = (long) (lengthSeconds * 1E9);
        reset();
    }

    /**
     * Returns the amount of hours on the clock as a double (e.g. 05:20:54 returns 5)
     * @return the amount of hours on the clock as a double
     */
    public double getHours() {
        double hours = Math.floor((timeRemaining / 3600E9));
        if (hours < 0) { hours = 0; }
        return hours;
    }

    /**
     * Returns the amount of minutes on the clock as a double (e.g. 05:20:54 returns 20)
     * @return the amount of minutes on the clock as a double
     */
    public double getMinutes() {
        double time = timeRemaining - getHours() * 3600E9;
        double minutes = Math.floor((time / 60E9));
        if (minutes < 0) { minutes = 0; }
        return minutes;
    }

    /**
     * Returns the amount of seconds on the clock as a double (e.g. 05:20:54 returns 54)
     * @return the amount of seconds on the clock as a double
     */
    public double getSeconds() {
        double time = timeRemaining - getHours() * 3600E9 - getMinutes() * 60E9;
        double seconds = Math.floor((time / 1E9));
        if (seconds < 0) { seconds = 0; }
        return seconds;
    }

    /**
     * Stops the timer and plays the alarm sound until stopped
     */
    private void expire() {
        timer.stop();
        alarm.setCycleCount(AudioClip.INDEFINITE);
        alarm.play();
    }

    /**
     * Updates the timer's string property
     */
    private void updateProperties() {
        timeText.setValue(toStringDynamic());

        timerLabel.getStyleClass().clear();

        if (timeText.getValue().length() >= 8) {
            timerLabel.setFont(new Font("Montserrat", 25));
        } else if (timeText.getValue().length() >= 5) {
            timerLabel.setFont(new Font("Montserrat", 35));
        } else {
            timerLabel.setFont(new Font("Montserrat", 55));
        }
    }

    /**
     * Returns the internal string property that carries the time on the clock
     * @return the internal string property that carries the time on the clock
     */
    public StringProperty getTimeText() {
        return timeText;
    }

    /**
     * Converts the current amount of time remaining to HH:MM:SS format and returns it as a string
     * @return the amount of time left on the timer in a string in HH:MM:SS format
     */
    @Override
    public String toString() {
        int hours = (int) getHours();
        int minutes = (int) getMinutes();
        int seconds = (int) getSeconds();

        return format("%02d", hours) + ":" + format("%02d", minutes) + ":" + format("%02d", seconds);
    }

    /**
     * Converts the current amount of time remaining to HH:MM:SS, MM:SS, or SS format, depending on whether or not
     * there are any hours of minutes left
     * @return the amount of time remaining on the timer in HH:MM:SS, MM:SS, or SS format
     */
    public String toStringDynamic() {
        int hours = (int) getHours();
        int minutes = (int) getMinutes();
        int seconds = (int) getSeconds();

        String timeString;

        if (hours == 0 && minutes == 0) {
            timeString = format("%02d", seconds);
        } else if (hours == 0) {
            timeString = format("%02d", minutes) + ":" + format("%02d", seconds);
        } else {
            timeString = format("%02d", hours) + ":" + format("%02d", minutes) + ":" + format("%02d", seconds);
        }

        return timeString;
    }
}
