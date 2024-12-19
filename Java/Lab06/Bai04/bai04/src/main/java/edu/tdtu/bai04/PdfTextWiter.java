package edu.tdtu.bai04;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("pdfTextWriter")
public class PdfTextWiter implements TextWriter{

    @Override
    public void write(String fileName, String text) {
        System.out.println("Filename is: " + fileName);
        System.out.println("Text content is: " + text);
        System.out.println("This is pdfTextWriter");
    }

}
