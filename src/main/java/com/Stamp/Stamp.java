package com.Stamp;

import com.itextpdf.text.pdf.BaseFont;
import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import java.awt.*;

public class Stamp {
    private final String path = "src/main/resources/";
    private final String inputhPath = path + "OriginalStamp.png";
    private final String outputPath = path + "ReadyStamp.png";
    private final ImagePlus image = IJ.openImage(inputhPath);

    public Stamp(String signature) {
        ImageProcessor processor = image.getProcessor();
        processor.setColor(Color.BLACK);
        Font font = new Font(BaseFont.TIMES_ROMAN, Font.ITALIC, 150);
        processor.setFont(font);
        FontMetrics metrics = processor.getFontMetrics();
        int xCoordinate = (image.getWidth() - metrics.stringWidth(signature)) / 2;
        int yCoordinate = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        processor.drawString(signature, xCoordinate, yCoordinate);
        saveImage();
    }

    public String getOutputPath() {
        return outputPath;
    }

    private void saveImage() {
        FileSaver fileSaver = new FileSaver(image);
        fileSaver.saveAsPng(outputPath);
    }
}