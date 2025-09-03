package View;

import Model.Profile;
import Model.Scoreboard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import static config.GameConstants.SCREEN_HEIGHT;
import static config.GameConstants.SCREEN_WIDTH;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.WHITE;

public class ProfileView extends StackPane implements Observer {
    private final Profile profile;
    private BorderPane borderPane = new BorderPane();
    private Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_WIDTH);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private GridPane gridPane = new GridPane();
    private Font font = Font.loadFont(getClass().getResourceAsStream(
            "/fonts/script-screen/Script Screen.ttf"
    ), 15);

    public ProfileView(View view, Profile profile) {
        profile.addObserver(this);
        this.profile = profile;
        canvas.setMouseTransparent(true);
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(150, 38, 60, 38));
        gridPane.setHgap(30);
        gridPane.setVgap(10);


        Image image = new Image(getClass().getResourceAsStream("/spriteSheets/scoreboard_border.png"));
        gc.setImageSmoothing(false);
        gc.drawImage(image, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        setAlignment(canvas, Pos.TOP_LEFT);

        Text profileText = new Text("Schermata profilo ");
        profileText.setFont(Font.font(font.getFamily(), 18));
        profileText.setFill(YELLOW);
        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER);
        topHBox.getChildren().addAll(profileText);
        borderPane.setTop(topHBox);

        Text back = new Text("- Indietro: ");
        back.setFont(Font.font(font.getFamily(), 15));
        back.setFill(YELLOW);
        Text escape = new Text("ESCAPE ");
        escape.setFont(Font.font(font.getFamily(), 13));
        escape.setFill(WHITE);

        Text change = new Text("- Cambio profilo: ");
        change.setFont(Font.font(font.getFamily(), 15));
        change.setFill(YELLOW);
        Text arrows = new Text("LEFT/RIGHT ");
        arrows.setFont(Font.font(font.getFamily(), 13));
        arrows.setFill(WHITE);

        HBox commandsHBox = new HBox();
        commandsHBox.setAlignment(Pos.TOP_LEFT);
        commandsHBox.setPadding(new Insets(60, 38, 60, 38));
        commandsHBox.getChildren().addAll(back, escape);

        getChildren().addAll(canvas, gridPane, borderPane, commandsHBox);


        refresh();
    }

    @Override
    public void update(Observable o, Object arg) {
        refresh();
    }

    public void refresh() {
        gridPane.getChildren().clear();
        gc.setFill(BLACK);

        List<Profile.ProfileEntry> profiles = profile.getProfiles();

        String pathToAvatar = null;
        for (Profile.ProfileEntry profileEntry : profiles) {
            if (profileEntry.getNickname().equals(profiles.get(profile.getCurrentProfIndex()).getNickname())) {
                pathToAvatar = profileEntry.getAvatar();
            }
        }
        Image image = null;
        if (pathToAvatar != null) {
            image = new Image(getClass().getResourceAsStream(pathToAvatar));
        }

        gc.drawImage(image,  100, 38);

        Profile.ProfileEntry profileEntry = profiles.get(profile.getCurrentProfIndex());

        Text name = new Text(profileEntry.toString());
        name.setFont(font);
        name.setFill(WHITE);
        gridPane.add(name, 0, 0);
    }
}
