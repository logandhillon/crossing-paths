package com.logandhillon.fptgame.resource.io;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Loads a resource from the /gfx/ folder as an {@link Image}
 *
 * @author Logan Dhillon
 */
public class ImageResource extends Resource<Image> {
    /**
     * Creates a new resource and opens an {@link InputStream} for it.
     *
     * @param path the relative path to the resource
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    public ImageResource(String path) throws FileNotFoundException {
        super("gfx/" + path);
    }

    public static Image recolor(Image src, Color tint) {
        int w = (int)src.getWidth();
        int h = (int)src.getHeight();
        WritableImage output = new WritableImage(w, h);

        for (int ix = 0; ix < w; ix++) {
            for (int iy = 0; iy < h; iy++) {
                Color base = src.getPixelReader().getColor(ix, iy);
                output.getPixelWriter().setColor(ix, iy, new Color(
                        base.getRed() * tint.getRed(),
                        base.getGreen() * tint.getGreen(),
                        base.getBlue() * tint.getBlue(),
                        base.getOpacity()
                ));
            }
        }

        return output;
    }

    @Override
    public Image load() {
        assert this.stream != null;
        return new Image(this.stream);
    }
}
