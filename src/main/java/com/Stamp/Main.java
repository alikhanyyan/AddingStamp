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
        Stamp stamp;
        try {
            stamp = createStamp();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Input PDF path for input: ");
        String pdfInputPath = scanner.nextLine();

        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        try {
            pdfReader = new PdfReader(pdfInputPath);

            System.out.print("Input PDF path for output: ");
            String pdfOutputPath = scanner.nextLine();
            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(pdfOutputPath));

            addImageToPDF(pdfStamper, stamp);
        }  catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
        } finally {
            if (pdfReader != null) {
                pdfReader.close();
            }
            if (pdfStamper != null) {
                try {
                    pdfStamper.close();
                } catch (DocumentException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static Stamp createStamp() throws IllegalArgumentException {
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
        float desiredWidth = 80;
        float desiredHeight = 80;
        image.scaleAbsolute(desiredWidth, desiredHeight);
        image.setAbsolutePosition(60, 90);
        pdfContentByte.addImage(image);

        System.out.println("Stamp successfully added to the PDF");
    }
}