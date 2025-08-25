package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MenuModel extends Observable {
    /**
     * The selectable options in the menu.
     */
    private final List<String> options = new ArrayList<>();
    private int selectedIndex = 0;

    public MenuModel() {
        options.add("Nuova Partita");
        options.add("Classifica");
        options.add("Profilo");
        options.add("Esci");
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
    public void moveUp() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
        setChanged();
        notifyObservers();
    }

    /**
     * Updates the selected index and communicates the change of state to the view.
     */
    public void moveDown() {
        selectedIndex = (selectedIndex + 1) % options.size();
        setChanged();
        notifyObservers();
    }
}
