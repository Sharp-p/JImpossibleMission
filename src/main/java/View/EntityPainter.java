package View;

import Model.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class EntityPainter {
    private Entity entity;
    private BufferedImage idleFrame;

    public EntityPainter(Entity entity, String pathToTexture) {
        this.entity = entity;
        try {
            idleFrame = ImageIO.read(new File(pathToTexture));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage getIdleFrame() { return idleFrame; }

    public Entity getEntity() { return entity; }

    public abstract void draw(Graphics2D g);
}
