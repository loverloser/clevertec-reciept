package ru.clevertec.print.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.SneakyThrows;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.print.Printable;
import ru.clevertec.util.ReceiptCalculateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

public class ReceiptPDFPrinter implements Printable {

    private static final Path TEMPLATE_FILE_PATH = Paths.get("src", "main",
            "resources", "Clevertec_Template.pdf");

    public static final String FILE_FORMAT = ".pdf";

    @SneakyThrows
    @Override
    public void print(Map<Product, Integer> products, DiscountCard discountCard) {
        PdfDocument backPdfDocument = new PdfDocument(new PdfReader(TEMPLATE_FILE_PATH.toFile()));
        LocalDateTime currentDate = LocalDateTime.now();
        String filename = "Receipt:" + currentDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                          + FILE_FORMAT;
        Path path = copyTemplateFile(filename);
        PdfDocument receiptPdfDocument = new PdfDocument(new PdfWriter(path.toFile()));
        receiptPdfDocument.addNewPage();

        Document document = new Document(receiptPdfDocument);
        document.add(getInfoTable());
        document.add(getProductsTable(products));
        document.add(getInfoTotalPurchase(products, discountCard));


        PdfCanvas canvas = new PdfCanvas(receiptPdfDocument.getFirstPage().newContentStreamBefore(),
                receiptPdfDocument.getFirstPage().getResources(), receiptPdfDocument);

        PdfFormXObject pdfFormXObject = backPdfDocument.getFirstPage().copyAsFormXObject(receiptPdfDocument);
        canvas.addXObjectAt(pdfFormXObject, 0, 0);

        backPdfDocument.close();
        receiptPdfDocument.close();
        document.close();
    }

    private static Table getInfoTable() {
        Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth().setMarginTop(100);

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setWidth(UnitValue.createPercentValue(100))
                .setFontSize(16f)
                .setBorder(Border.NO_BORDER);

        Cell cashReceipt = new Cell(1, 4)
                .add(new Paragraph("======= CASH RECEIPT =======").setTextAlignment(TextAlignment.CENTER));
        Cell qty = new Cell(1, 1)
                .add(new Paragraph("QTY")).setTextAlignment(TextAlignment.CENTER);
        Cell description = new Cell(1, 1)
                .add(new Paragraph("DESCRIPTION")).setTextAlignment(TextAlignment.CENTER);
        Cell price = new Cell(1, 1)
                .add(new Paragraph("PRICE")).setTextAlignment(TextAlignment.CENTER);
        Cell total = new Cell(1, 1)
                .add(new Paragraph("TOTAL")).setTextAlignment(TextAlignment.CENTER);

        table.addCell(cashReceipt);
        table.addCell(qty);
        table.addCell(description);
        table.addCell(price);
        table.addCell(total);

        return table;
    }

    private static Table getProductsTable(Map<Product, Integer> products) {
        Table tableProducts = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer count = entry.getValue();
            Cell qty = new Cell(1, 1)
                    .add(new Paragraph(String.valueOf(count))).setTextAlignment(TextAlignment.CENTER);
            Cell description = new Cell(1, 1)
                    .add(new Paragraph(product.getName())).setTextAlignment(TextAlignment.CENTER);
            Cell price = new Cell(1, 1)
                    .add(new Paragraph(String.valueOf(product.getPrice()))).setTextAlignment(TextAlignment.CENTER);
            Cell total = new Cell(1, 1)
                    .add(new Paragraph(String.format("%.2f", product.getTotal(count)))).setTextAlignment(TextAlignment.CENTER);

            tableProducts.addCell(qty);
            tableProducts.addCell(description);
            tableProducts.addCell(price);
            tableProducts.addCell(total);
        }

        return tableProducts;
    }

    private static Table getInfoTotalPurchase(Map<Product, Integer> products, DiscountCard discountCard) {
        Table totalPurchase = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
        double total = ReceiptCalculateUtil.getTotal(products);
        double discount = ReceiptCalculateUtil.getDiscount(discountCard, total);
        Cell totalDiscount = new Cell(2, 2).
                add(new Paragraph(String.format("Total discount with card: %.2f", discount)));
        Cell card = new Cell(1, 1).add(
                new Paragraph((discountCard != null) ? "Discount Card-" + discountCard.getId() : "isn't accepted"));
        Cell totalPrice = new Cell(1, 1).add(new Paragraph(String.format("Total price: %.2f",
                total - discount)));

        totalPurchase.addCell(totalDiscount);
        totalPurchase.addCell(card);
        totalPurchase.addCell(totalPrice);

        return totalPurchase;
    }

    private Path copyTemplateFile(String filename) {
        Path dest = Paths.get("src", "main", "java", "ru", "clevertec", "receipts", filename);
        try {
            Files.copy(TEMPLATE_FILE_PATH, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dest;
    }

}
