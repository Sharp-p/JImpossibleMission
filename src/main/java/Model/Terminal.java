package Model;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private final List<String> options = new ArrayList<>();
    private int selectedIndex = 0;
    private boolean showingTerminal = false;

    public Terminal() {
        options.add("Disattiva temporaneamente tutti i robot.");
        options.add("Riposiziona tutte le piattaforme.");
        options.add("Spegni.");
    }

    public void setShowingTerminal(boolean showingTerminal) { this.showingTerminal = showingTerminal; }

    public boolean isShowingTerminal() { return showingTerminal; }

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }


    /**
     * Updates the selected index the change of state to the view.
     */
    public void previous() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
    }

    /**
     * Updates the selected index the change of state to the view.
     */
    public void next() { selectedIndex = (selectedIndex + 1) % options.size(); }

}
