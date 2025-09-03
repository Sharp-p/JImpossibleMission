package Controller;

import Model.Profile;
import View.View;
import javafx.scene.input.KeyCode;

/**
 * This class should handle the ProfileView, and it does,
 * but the ProfileView lacks the functionalities
 */
public class ProfilesController {
    private Profile profile;
    private View view;

    /**
     * Defines the key listeners inside the profileView
      * @param profile The profile model
     * @param view The view to change pane
     */
    public ProfilesController(Profile profile, View view) {
        this.profile = profile;
        this.view = view;

        view.getProfileView().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                view.showMenu();
            }
            else if (e.getCode() == KeyCode.RIGHT){
                profile.next();
            }
            else if (e.getCode() == KeyCode.LEFT){
                profile.previous();
            }
        });
    }
}
