package com.Stamp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Stamp {
    private final String path = "src/main/resources/";
    private final String outputPath = path + "ReadyStamp.png";

    public Stamp(String signature) throws  IllegalArgumentException {
        try {
            String inputhPath = path + "OriginalStamp.png";
            BufferedImage image = ImageIO.read(new File(inputhPath));
            if (image == null) {
                throw new IllegalArgumentException("Stamp image not found");
            }

            Graphics2D graphics = image.createGraphics();
            graphics.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
            graphics.setColor(Color.BLACK);

            Font font = new Font("TimesRoman", Font.ITALIC, 150);
            graphics.setFont(font);

            FontMetrics metrics = graphics.getFontMetrics();
            int xCoordinate = (image.getWidth() - metrics.stringWidth(signature)) / 2;
            int yCoordinate = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            graphics.drawString(signature, xCoordinate, yCoordinate);

            ImageIO.write(image, "png", new File(outputPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getOutputPath() {
        return outputPath;
    }
}