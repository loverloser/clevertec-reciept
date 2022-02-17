package ru.clevertec.print.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.print.Printable;
import ru.clevertec.util.ReceiptCalculateUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Map;

public class ReceiptPrinter implements Printable {

    private static final Path TEMPLATE_FILE = Paths.get("src", "main",
            "resources", "Clevertec_Template.pdf");

    public static final String FILE_FORMAT = ".pdf";

    @SneakyThrows
    @Override
    public void printInPDF(Map<Product, Integer> products, DiscountCard discountCard) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        final String filename = "sometext" + FILE_FORMAT;
//        Path path = copyTemplateFile(filename);
        String receipt = ReceiptCalculateUtil.getReceipt(products, discountCard);
//        String res = receipt + receipt + receipt + receipt;
//        Document document = new Document();
//        PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(path.toFile()));
//        instance.add(new Paragraph(receipt));
//        document.open();
//
//        Paragraph paragraph = new Paragraph(receipt);
////        paragraph.setLeading(30);
//
//        paragraph.setSpacingBefore(317);
//        document.add(paragraph);
//
//        document.close();

//        Files.write(path, res.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);





        System.out.println(receipt);

    }

    private Path copyTemplateFile(String filename) {
        Path dest = Paths.get("src", "main", "java", "ru", "clevertec", "receipts", filename);
        try {
            Files.copy(TEMPLATE_FILE, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }
}
