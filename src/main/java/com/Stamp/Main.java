package com.Stamp;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Stamp stamp = createStamp();

        System.out.print("Input PDF path for input: ");
        String pdfInputPath = scanner.nextLine();

        System.out.print("Input PDF path for output: ");
        String pdfOutputPath = scanner.nextLine();

        try {
            PdfReader pdfReader = new PdfReader(pdfInputPath);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(pdfOutputPath));

            addImageToPDF(pdfStamper, stamp);
        }  catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Stamp createStamp() {
        String signature;
        do {
            System.out.print("Input signature (2-3 letters): ");
            signature = scanner.nextLine();
        } while (signature.length() > 3 || signature.length() < 2);
        return new Stamp(signature);
    }

    private static void addImageToPDF(PdfStamper pdfStamper, Stamp stamp) throws IOException, DocumentException {
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(1.0f);

        PdfContentByte pdfContentByte = pdfStamper.getOverContent(1);

        Image image = Image.getInstance(stamp.getOutputPath());
        float desiredWidth = 70;
        float desiredHeight = 70;
        image.scaleAbsolute(desiredWidth, desiredHeight);
        image.setAbsolutePosition(60, 100);
        pdfContentByte.addImage(image);

        pdfStamper.close();

        System.out.println("Image added to the PDF successfully!");
    }
}