package Model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class that handles the score.csv file and the ScoreboardView
 */
public class Scoreboard extends Observable {
    private final Path filePath;
    private List<String> options = new ArrayList<>();
    private int selectedIndex = 0;

    public Scoreboard(String fileName) {
        this.filePath = Paths.get(fileName);

        // Se il file non esiste lo crea
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        options.add("Decrescente (temporale)");
        options.add("Crescente (temporale)");
        options.add("Decrescente (punteggio)");
        options.add("Crescente (punteggio)");

        setChanged();
        notifyObservers();
    }

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Updates the selected index and communicates the change of state to the view.
     */
    public void previous() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
        setChanged();
        notifyObservers();
    }

    /**
     * Updates the selected index and communicates the change of state to the view.
     */
    public void next() {
        selectedIndex = (selectedIndex + 1) % options.size();
        setChanged();
        notifyObservers();
    }

    /**
     * Aggiunge uno score in fondo al file
     */
    public void addScore(String playerName, int score) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardOpenOption.APPEND)) {
            writer.write(playerName + "," + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the score as saved in the scoreboard.csv (Oldest to newest)
     */
    public List<ScoreEntry> getScores() {
        List<ScoreEntry> scores = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int value = Integer.parseInt(parts[1]);
                    scores.add(new ScoreEntry(name, value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }

    /**
     * Returns the scores from the newest to the oldest.
     * @return
     */
    public List<ScoreEntry> getNewestScores() {
        return getScores().reversed();
    }

    /**
     * Get scores sorted from the highest to the lowest.
     * @return
     */
    public List<ScoreEntry> getLowestScores() {
        List<ScoreEntry> scores = getHighestScores();
        return scores.reversed();
    }

    /**
     * Get scores sorted from the highest to the lowest.
     */
    public List<ScoreEntry> getHighestScores() {
        List<ScoreEntry> scores = getScores();

        return scores.stream()
                .sorted((s1, s2) -> Integer.compare(s2.score, s1.score))
                .toList();
    }

    /**
     * Classe interna per rappresentare un punteggio
     */
    public static class ScoreEntry {
        private final String playerName;
        private final int score;

        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return playerName + " - " + score;
        }
    }
}