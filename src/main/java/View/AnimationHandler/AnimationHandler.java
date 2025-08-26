package View.AnimationHandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class AnimationHandler {
    private final Image sheet;
    private final Map<String, Animation> animations = new HashMap<>();
    private Animation current;
    private int frameIndex = 0;
    private double frameTime = 0.0;
    private boolean playing = true;

    public AnimationHandler(Image sheet) { this.sheet = sheet; }

    public void addAnimation(Animation animation) {
        animations.put(animation.name, animation);
        if (current == null) current = animation;
    }

    public void play(String name) {
        Animation animation = animations.get(name);
        if (animation == null || animation == current) return;
        current = animation;
        frameIndex = 0;
        frameTime = 0.0;
        playing = true;
    }

    public void pause() { playing = false; }
    public void resume() { playing = true; }

    /**
     * Updates the current frame based on the time elapsed since the last call
     * @param dt The delta time
     */
    public void update(double dt) {
        if (!playing || current == null || current.frames.isEmpty()) return;

        frameTime += dt;
        Frame f = current.frames.get(frameIndex);
        // iterates over the duration
        while (frameTime >= f.duration) {
            frameTime -= f.duration;
            frameIndex++;
            if (frameIndex >= current.frames.size()) {
                if (current.loop) frameIndex = 0;
                else {
                    frameIndex = current.frames.size() - 1;
                    playing = false;
                    break;
                }
                f = current.frames.get(frameIndex);
            }
        }
    }

    public void render(GraphicsContext gc, double x, double y, double scale) {
        if (current == null || current.frames.isEmpty()) return;
        Frame f = current.frames.get(frameIndex);

        // coordinates, width and height of the frame in the sprite sheet
        double sx = f.src.getMinX(),  sy = f.src.getMinY();
        double sw = f.src.getWidth(), sh = f.src.getHeight();

        // scaled coordinates (plus anchor offset) of the drawing in the actual canvas
        double dx = Math.round((x + f.ox) * scale);
        double dy = Math.round((y + f.oy) * scale);
        double dw = Math.round(sw * scale);
        double dh = Math.round(sh * scale);

        System.out.println("Posizione: " + x + "," + y + "\n" +
                "\tPozione scalata: " + dx + "," + dy + "\n" +
                "\tFrame size: " + getCurrentFrameWidth());
        gc.drawImage(sheet, sx, sy, sw, sh, dx, dy, dw, dh);
    }

    public double getCurrentFrameWidth() {
        if (current == null || current.frames.isEmpty()) return 0;
        return current.frames.get(frameIndex).src.getWidth();
    }

    public Animation getCurrentAnimation() { return current; }
}
