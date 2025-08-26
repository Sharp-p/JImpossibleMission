package View.AnimationHandler;

import javafx.geometry.Rectangle2D;

public final class Frame {
    final Rectangle2D src;
    final double ox, oy;
    final double duration;

    public Frame(Rectangle2D src, double ox, double oy, double duration) {
        this.src = src;
        this.ox = ox;
        this.oy = oy;
        this.duration = duration;
    }
}
