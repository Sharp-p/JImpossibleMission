package Model;

import java.util.ArrayList;
import java.util.List;

public class GameMenuModel {
    private final List<String> options = new ArrayList<>();
    private int selectedIndex = 0;
    private boolean showingMenu = false;

    public GameMenuModel() {
        options.add("CONTINUA");
        options.add("ESCI");
    }

    public void setShowingMenu(boolean showingMenu) { this.showingMenu = showingMenu; }

    public boolean isShowingMenu() { return showingMenu; }

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
    }

    /**
     * Updates the selected index and communicates the change of state to the view.
     */
    public void next() { selectedIndex = (selectedIndex + 1) % options.size(); }


}
