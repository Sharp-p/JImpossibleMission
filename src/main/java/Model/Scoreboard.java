package Model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final Path filePath;

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
     * Restituisce la lista di tutti gli score
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