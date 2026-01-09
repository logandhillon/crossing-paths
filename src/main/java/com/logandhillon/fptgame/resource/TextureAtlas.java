package com.logandhillon.fptgame.resource;

import com.logandhillon.fptgame.resource.io.ImageResource;
import com.logandhillon.fptgame.resource.io.TextResource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * @author Logan Dhillon
 */
public class TextureAtlas {
    private final Image    image;
    private final Metadata meta;

    public TextureAtlas(String path) {
        try (var img = new ImageResource(path);
             var meta = new TextResource("gfx/" + path + ".atlas")
        ) {
            this.image = img.load();
            this.meta = Metadata.fromString(meta.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void draw(GraphicsContext g, Image image, int row, int col, float x, float y, float w, float h) {
        if (row < 0 || row > meta.rows || col < 0 || col > meta.cols)
            throw new IllegalArgumentException("row/col must be within 0 and the number of rows/cols in this atlas");

        g.setImageSmoothing(false);
        g.drawImage(image, row * meta.cellWidth, col * meta.cellHeight, meta.cellWidth, meta.cellHeight, x, y, w, h);
    }

    public void draw(GraphicsContext g, int row, int col, float x, float y, float w, float h) {
        draw(g, image, row, col, x, y, w, h);
    }

    public void draw(GraphicsContext g, int row, int col, float x, float y, float w, float h, Color color) {
        draw(g, ImageResource.recolor(image, color), row, col, x, y, w, h);
    }

    public record Metadata(int cellWidth, int cellHeight, int rows, int cols) {
        public static Metadata fromString(String s) {
            String[] parts = s.split(",");
            if (parts.length < 4) throw new IllegalArgumentException(
                    "invalid metadata, expected 4 comma-separated parts (cellWidth,cellHeight,rows,cols)");
            try {
                return new Metadata(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "invalid metadata, expected 4 comma-separated parts (cellWidth,cellHeight,rows,cols) that can" +
                        " be parsed as integers");
            }
        }
    }
}
