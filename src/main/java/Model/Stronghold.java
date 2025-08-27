package Model;

import Utilities.Tuple;

import java.util.ArrayList;
import java.util.List;

public class Stronghold {
    private List<Platform> platforms= new ArrayList<>();

    public Stronghold() {
        for (int i = 0; i < 10; i++) {
            platforms.add(new Platform(new Tuple<>(100.0 + 13*i, 175.0), new StillMovement(), 0.0));
        }
    }

    public List<Platform> getPlatforms() { return platforms; }
}
