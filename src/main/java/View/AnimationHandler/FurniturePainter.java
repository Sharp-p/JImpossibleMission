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
                .add(new Frame(new Rectangle2D(12, 21, TERMINAL.getWidth(), TERMINAL.getHeight()), 0, 0, 1));

        Animation popMachine = new Animation("POP_MACHINE", true)
                .add(new Frame(new Rectangle2D(48, 22, POP_MACHINE.getWidth(), POP_MACHINE.getHeight()),0, 0, 1));

        Animation cigMachine = new Animation("CIG_MACHINE", true)
                .add(new Frame(new Rectangle2D(73, 12, CIG_MACHINE.getWidth(), CIG_MACHINE.getHeight()), 0, 0, 1));

        Animation candyMachine = new Animation("CANDY_MACHINE", true)
                .add(new Frame(new Rectangle2D(121, 12, CANDY_MACHINE.getWidth(), CANDY_MACHINE.getHeight()), 0, 0, 1));

        Animation bathTub = new Animation("BATH_TUB", true)
                .add(new Frame(new Rectangle2D(238, 12, BATH_TUB.getWidth(), BATH_TUB.getHeight()), 0, 0, 1));

        Animation wc = new Animation("WC", true)
                .add(new Frame(new Rectangle2D(197, 27, WC.getWidth(), WC.getHeight()), 0, 0, 1));

        Animation sink = new Animation("SINK", true)
                .add(new Frame(new Rectangle2D(160, 9, SINK.getWidth(), SINK.getHeight()), 0, 0, 1));

        Animation desk = new Animation("DESK", true)
                .add(new Frame(new Rectangle2D(3, 61, DESK.getWidth(), DESK.getHeight()), 0, 0, 1));

        Animation typewriter = new Animation("TYPEWRITER", true)
                .add(new Frame(new Rectangle2D(294, 22, TYPEWRITER.getWidth(), TYPEWRITER.getHeight()), 0, 0, 1));

        Animation drawer = new Animation("DRAWER", true)
                .add(new Frame(new Rectangle2D(68, 65, DRAWER.getWidth(), DRAWER.getHeight()), 0, 0, 1));

        Animation bed = new Animation("BED", true)
                .add(new Frame(new Rectangle2D(98, 74, BED.getWidth(), BED.getHeight()), 0, 0, 1));

        Animation kitchen = new Animation("KITCHEN", true)
                .add(new Frame(new Rectangle2D(156, 62, KITCHEN.getWidth(), KITCHEN.getHeight()), 0, 0, 1));

        Animation fridge = new Animation("FRIDGE", true)
                .add(new Frame(new Rectangle2D(215, 54, FRIDGE.getWidth(), FRIDGE.getHeight()), 0, 0, 1));

        Animation fireplace = new Animation("FIREPLACE", true)
                .add(new Frame(new Rectangle2D(248, 56, FIREPLACE.getWidth(), FIREPLACE.getHeight()), 0, 0, 1));

        Animation sofa = new Animation("SOFA", true)
                .add(new Frame(new Rectangle2D(11, 119, SOFA.getWidth(), SOFA.getHeight()), 0, 0, 1));

        Animation bookshelf = new Animation("BOOKSHELF", true)
                .add(new Frame(new Rectangle2D(62, 99, BOOKSHELF.getWidth(), BOOKSHELF.getHeight()), 0, 0, 1));

        Animation endRoom = new Animation("END_ROOM", true)
                .add(new Frame(new Rectangle2D(96, 97, END_ROOM.getWidth(), END_ROOM.getHeight()), 0, 0, 1));

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
            //System.out.println(getAnimationHandler().getCurrentAnimation().getName());
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
