package View.AnimationHandler;

import static Model.FurnitureType.*;

import Model.FurniturePiece;
import Utilities.Tuple;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FurniturePainter extends EntityPainter {
    public FurniturePainter(FurniturePiece piece) {
        super(piece);

        Animation terminal = new Animation("TERMINAL", true)
                .add(new Frame(new Rectangle2D(13, 21, TERMINAL.getWidth(), TERMINAL.getHeight()), 0, 0, 1));

        Animation popMachine = new Animation("POP_MACHINE", true)
                .add(new Frame(new Rectangle2D(49, 22, POP_MACHINE.getWidth(), POP_MACHINE.getHeight()),0, 0, 1));

        Animation cigMachine = new Animation("CIG_MACHINE", true)
                .add(new Frame(new Rectangle2D(74, 14, CIG_MACHINE.getWidth(), CIG_MACHINE.getHeight()), 0, 0, 1));

        Animation candyMachine = new Animation("CANDY_MACHINE", true)
                .add(new Frame(new Rectangle2D(122, 12, CANDY_MACHINE.getWidth(), CANDY_MACHINE.getHeight()), 0, 0, 1));

        Animation bathTub = new Animation("BATH_TUB", true)
                .add(new Frame(new Rectangle2D(239, 12, BATH_TUB.getWidth(), BATH_TUB.getHeight()), 0, 0, 1));

        Animation wc = new Animation("WC", true)
                .add(new Frame(new Rectangle2D(198, 27, WC.getWidth(), WC.getHeight()), 0, 0, 1));

        Animation sink = new Animation("SINK", true)
                .add(new Frame(new Rectangle2D(161, 9, SINK.getWidth(), SINK.getHeight()), 0, 0, 1));

        Animation desk = new Animation("DESK", true)
                .add(new Frame(new Rectangle2D(4, 61, DESK.getWidth(), DESK.getHeight()), 0, 0, 1));

        Animation typewriter = new Animation("TYPEWRITER", true)
                .add(new Frame(new Rectangle2D(295, 22, TYPEWRITER.getWidth(), TYPEWRITER.getHeight()), 0, 0, 1));

        Animation drawer = new Animation("DRAWER", true)
                .add(new Frame(new Rectangle2D(69, 65, DRAWER.getWidth(), DRAWER.getHeight()), 0, 0, 1));

        Animation bed = new Animation("BED", true)
                .add(new Frame(new Rectangle2D(99, 74, BED.getWidth(), BED.getHeight()), 0, 0, 1));

        Animation kitchen = new Animation("KITCHEN", true)
                .add(new Frame(new Rectangle2D(157, 62, KITCHEN.getWidth(), KITCHEN.getHeight()), 0, 0, 1));

        Animation fridge = new Animation("FRIDGE", true)
                .add(new Frame(new Rectangle2D(216, 54, FRIDGE.getWidth(), FRIDGE.getHeight()), 0, 0, 1));

        Animation fireplace = new Animation("FIREPLACE", true)
                .add(new Frame(new Rectangle2D(249, 57, FIREPLACE.getWidth(), FIREPLACE.getHeight()), 0, 0, 1));

        Animation sofa = new Animation("SOFA", true)
                .add(new Frame(new Rectangle2D(12, 119, SOFA.getWidth(), SOFA.getHeight()), 0, 0, 1));

        Animation bookshelf = new Animation("BOOKSHELF", true)
                .add(new Frame(new Rectangle2D(63, 99, BOOKSHELF.getWidth(), BOOKSHELF.getHeight()), 0, 0, 1));

        Animation endRoom = new Animation("END_ROOM", true)
                .add(new Frame(new Rectangle2D(97, 97, END_ROOM.getWidth(), END_ROOM.getHeight()), 0, 0, 1));

        setAnimationHandler(new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/furniture/furnitures_spritesheet.png"
                ))
        ));

        getAnimationHandler().addAnimation(terminal);
        getAnimationHandler().addAnimation(popMachine);
        getAnimationHandler().addAnimation(cigMachine);
        getAnimationHandler().addAnimation(candyMachine);
        getAnimationHandler().addAnimation(bathTub);
        getAnimationHandler().addAnimation(wc);
        getAnimationHandler().addAnimation(sink);
        getAnimationHandler().addAnimation(desk);
        getAnimationHandler().addAnimation(bed);
        getAnimationHandler().addAnimation(kitchen);
        getAnimationHandler().addAnimation(fridge);
        getAnimationHandler().addAnimation(fireplace);
        getAnimationHandler().addAnimation(sofa);
        getAnimationHandler().addAnimation(bookshelf);
        getAnimationHandler().addAnimation(endRoom);
        getAnimationHandler().addAnimation(typewriter);
        getAnimationHandler().addAnimation(drawer);

        getAnimationHandler().play(piece.getType().toString());
    }

    @Override
    public void draw(GraphicsContext gc, double dt, double scale) {
        if (getEntity().getSize() == null) {
            getEntity().setSize(new Tuple<>(
                    getAnimationHandler().getCurrentFrameWidth(),
                    getAnimationHandler().getCurrentFrameHeight()
            ));
            System.out.println(getAnimationHandler().getCurrentAnimation().getName());
            getEntity().setPosition(new Tuple<>(
                    getEntity().getPosition().getFirst(),
                    getEntity().getPosition().getSecond() -
                            getAnimationHandler().getCurrentFrameHeight() + 1
            ));
        }

        if (getEntity().isVisible()) {
            getAnimationHandler().update(dt);

            getAnimationHandler().render(
                    gc,
                    getEntity().getPosition().getFirst(),
                    getEntity().getPosition().getSecond(),
                    scale
            );
        }

    }
}
