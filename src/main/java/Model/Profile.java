package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Profile extends Observable {
    private List<Profile.ProfileEntry> profiles = new ArrayList<>();
    private int currentIndex = 0;
    /**
     * Paths needs to start from spriteSheets
     */
    private final Path filePath;
    private List<String> options = new ArrayList<>();

    public Profile(String fileName) {
        this.filePath = Paths.get(fileName);
        profiles = getProfiles();

        // if the path does not exist it creates the file
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Updates the selected index and communicates the change of state to the view.
     */
    public void previous() {
        currentIndex = (currentIndex - 1 + profiles.size()) % profiles.size();
        setChanged();
        notifyObservers();
    }

    /**
     * Updates the selected index and communicates the change of state to the view.
     */
    public void next() {
        currentIndex = (currentIndex + 1) % profiles.size();
        setChanged();
        notifyObservers();
    }

    public int getCurrentProfIndex() { return currentIndex; }

    public void setCurrentProfIndex(int currentProf) {
        this.currentIndex = currentProf;
    }

    // TODO: usare per aggiungere utente
    /**
     * Aggiunge uno score in fondo al file
     */
    public void addProfile(String playerName) {
        // check if the player already exist
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if (split[0].equals(playerName)) {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // else adds a new line in the file
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardOpenOption.APPEND)) {
            writer.write(playerName + ",,0,0,0");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAvatarCurrent(String avatarPath) {
        try {
            // Leggi tutte le righe
            List<String> lines = Files.readAllLines(filePath);

            List<String> updated = new ArrayList<>();
            String toReAdd = null;

            for (String line : lines) {
                String[] split = line.split(",");
                if (split.length > 0 && split[0].equals(profiles.get(currentIndex).getNickname())) {
                    // Costruisci la nuova riga con avatar aggiornato
                    // Se vuoi solo nick+avatar:
                    // toReAdd = split[0] + "," + avatarPath;
                    // Se vuoi mantenere gli altri campi esistenti:
                    StringBuilder sb = new StringBuilder();
                    sb.append(split[0]).append(",").append(avatarPath);
                    for (int i = 2; i < split.length; i++) {
                        sb.append(",").append(split[i]);
                    }
                    toReAdd = sb.toString();
                    // Non aggiungere questa riga, verrà rimpiazzata
                } else {
                    updated.add(line);
                }
            }

            // Se trovato, aggiungi alla fine
            if (toReAdd != null) {
                updated.add(toReAdd);
            }

            // Riscrivi tutto il file
            Files.write(filePath, updated);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers();
    }

    /**
     * This method increments the played games and handles wins and losses,
     * updating the profile line inside the CSV file.
     * @param won true if the game was won, false if lost
     */
    public void incrementGames(boolean won) {
        try {
            // Leggi tutte le righe
            List<String> lines = Files.readAllLines(filePath);

            List<String> updated = new ArrayList<>();
            String toReAdd = null;

            for (String line : lines) {
                String[] split = line.split(",");
                if (split.length >= 5 && split[0].equals(profiles.get(currentIndex).getNickname())) {
                    String nick = split[0];
                    String avatar = split[1];
                    int giocate = Integer.parseInt(split[2]);
                    int vinte = Integer.parseInt(split[3]);
                    int perse = Integer.parseInt(split[4]);

                    // aggiorno statistiche
                    giocate++;
                    if (won) {
                        vinte++;
                    } else {
                        perse++;
                    }

                    // riscrivo la riga aggiornata
                    toReAdd = nick + "," + avatar + "," + giocate + "," + vinte + "," + perse;
                } else {
                    updated.add(line);
                }
            }

            // aggiungi riga aggiornata (alla fine, senza duplicati)
            if (toReAdd != null) {
                updated.add(toReAdd);
            }

            // riscrivi il file aggiornato
            Files.write(filePath, updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the score as saved in the scoreboard.csv (Oldest to newest)
     */
    public List<Profile.ProfileEntry> getProfiles() {
        List<Profile.ProfileEntry> profiles = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    String avatar = parts[1];
                    int played = Integer.parseInt(parts[2]);
                    int wins = Integer.parseInt(parts[3]);
                    int losses = Integer.parseInt(parts[4]);
                    profiles.add(new Profile.ProfileEntry(name, avatar, played, wins, losses));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profiles;
    }


    /**
     * internal class that defines an entry in the csv file
     */
    public static class ProfileEntry {
        private final String nickname;
        private final String avatar;
        private int played;
        private int wins;
        private int losses;

        public ProfileEntry(String nickname, String avatar, int played, int wins, int losses) {
            this.nickname = nickname;
            this.avatar = avatar;
            this.played = played;
            this.wins = wins;
            this.losses = losses;
        }

        public String getNickname() { return nickname; }
        public String getAvatar() { return avatar; }
        public int getGiocate() { return played; }
        public int getVinte() { return wins; }
        public int getPerse() { return losses; }

        /**
         * This method increments the played game and handles wins and losses
         * @param won A boolean that indicates the result of the game
         */
        public void incrementGames(boolean won) {
            played++;
            if (won) {
                wins++;
            } else {
                losses++;
            }
        }

        public String toCsv() {
            return nickname + "," + avatar + "," + played + "," + wins + "," + losses;
        }

        @Override
        public String toString() {
            return nickname + " G:" + played + " V:" + wins + " P:" + losses;
        }
    }
}
