package edu.tdtu.bai03;


public class PlainTextWriter implements TextWriter {

    @Override
    public void write(String fileName, String text) {
        System.out.println("Filename is: " + fileName);
        System.out.println("Text content is: " + text);
        System.out.println("This is plainTextWriter");
    }

}


