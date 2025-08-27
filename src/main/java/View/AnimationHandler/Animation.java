package View.AnimationHandler;

import java.util.ArrayList;
import java.util.List;

public final class Animation {
    final String name;
    final List<Frame> frames = new ArrayList<>();
    final boolean loop;

    public Animation(String name, boolean loop) {
        this.name = name;
        this.loop = loop;
    }

    public Animation add(Frame frame) {
        frames.add(frame);
        return this;
    }

    public String getName() { return name; }

    @Override
    public String toString() { return "Animation: " + name
            + "\n\tNumber of frames: " + frames.size(); }
}
