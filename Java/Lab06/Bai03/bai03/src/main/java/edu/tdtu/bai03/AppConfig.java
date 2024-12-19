package edu.tdtu.bai03;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TextEditor textEditor(@Qualifier("pdfTextWriter") TextWriter textWriter) {
        return new TextEditor(textWriter);
    }

    @Bean
    @Qualifier("plainTextWriter")
    public TextWriter plainTextWriter() {
        return new PlainTextWriter();

    }

    @Bean
    @Qualifier("pdfTextWriter")
    public TextWriter pdfTextWriter() {
        return new PdfTextWiter();

    }
}
