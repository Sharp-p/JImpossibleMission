package View;

import Model.MenuModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MenuView extends VBox implements Observer {
    private List<Text> options = new ArrayList<>();

    private MenuModel menuModel;

    public MenuView(MenuModel menuModel, View view) {
        this.menuModel = menuModel;
        menuModel.addObserver(this);

        setAlignment(Pos.CENTER);
        setSpacing(20);
        setStyle("-fx-alignment: center;");

        Text title = new Text("{J-Impossible}{Mission}");

        Text start = new Text("Nuova partita");
        Text scoreboard = new Text("Classifica");
        Text profile = new Text("Profilo");
        Text end = new Text("Esci");

        options.add(start);
        options.add(scoreboard);
        options.add(profile);
        options.add(end);

        Font titleFont = Font.loadFont(getClass().getResourceAsStream(
                "/fonts/TopSecret/TopSecret.ttf"), 60);
        Font optionsFont = Font.loadFont(getClass().getResourceAsStream(
                "/fonts/script-screen/Script Screen.ttf"), 25);

        title.setFont(titleFont);
        title.setFill(Color.ORANGE);

        for (int i = 0; i < menuModel.getOptions().size(); i++) {
            options.get(i).setFont(optionsFont);
            options.get(i).setFill(i == menuModel.getSelectedIndex() ? Color.CYAN : Color.WHITE);
        }

        BackgroundFill backgroundFill = new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, null);
        setBackground(new Background(backgroundFill));

        getChildren().addAll(title, start, scoreboard, profile, end);

        setFocusTraversable(true);

    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;

        for (int i = 0; i < menuModel.getOptions().size(); i++) {
            options.get(i).setFill(i == menuModel.getSelectedIndex() ? Color.CYAN: Color.WHITE);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        for (int i = 0; i < menuModel.getOptions().size(); i++) {
            options.get(i).setFill(i == menuModel.getSelectedIndex() ? Color.CYAN : Color.WHITE);
        }
    }

}
