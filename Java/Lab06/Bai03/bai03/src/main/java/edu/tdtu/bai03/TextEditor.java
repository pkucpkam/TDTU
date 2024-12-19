package edu.tdtu.bai03;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TextEditor {
    private String text;


    private TextWriter writer;

    @Autowired
    public TextEditor(@Qualifier("pdfTextWriter") TextWriter writer) {
        this.writer = writer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextWriter getWriter() {
        return writer;
    }

    public void setWriter(TextWriter writer) {
        this.writer = writer;
    }

    public void input(String text) {
        System.out.println("Input text: " + text);
    }

    public void save(String text) {
        writer.write("output.txt", text);
    }

}

