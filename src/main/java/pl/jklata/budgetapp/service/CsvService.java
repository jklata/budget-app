package pl.jklata.budgetapp.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:messages_pl.properties")
public class CsvService {

    @Value("${names.payment.id}")
    private String headerId;

    @Value("${names.payment.date}")
    private String headerDate;

    @Value("${names.payment.amount}")
    private String headerAmount;

    @Value("${names.payment.title}")
    private String headerTitle;

    @Value("${names.payment.category}")
    private String headerCategory;

    private final PaymentService paymentService;

    public ByteArrayInputStream create() {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<PaymentDto> allForAuthUser = paymentService.findAllForAuthUser();

        try {

            int numberOfColumns = 5;
            PdfPTable table = new PdfPTable(numberOfColumns);
            table.setWidthPercentage(85);
            table.setWidths(new int[]{1, 3, 3, 3, 3});

            addTableHeader(headerId, table);
            addTableHeader(headerDate, table);
            addTableHeader(headerAmount, table);
            addTableHeader(headerTitle, table);
            addTableHeader(headerCategory, table);

            for (PaymentDto payment : allForAuthUser) {
                addTableCell(payment.getId().toString(), table, BaseColor.WHITE);
                addTableCell(payment.getPaymentDate().toString(), table, BaseColor.WHITE);

                BaseColor paymentCellColor =
                    PaymentType.EXPENSE.equals(payment.getPaymentType()) ?
                        new BaseColor(255, 153, 153) : new BaseColor(173, 235, 173);

                addTableCell(payment.getAmountWithSign().toString(), table, paymentCellColor);
                addTableCell(payment.getTitle(), table, BaseColor.WHITE);
                addTableCell(payment.getPaymentCategory().getName(), table, BaseColor.WHITE);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
            document.close();


        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTableHeader(String columnHeader, PdfPTable table) {
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell headerCell = new PdfPCell(new Phrase(columnHeader, headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
    }

    private void addTableCell(String value, PdfPTable table, BaseColor cellBackgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(value));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(cellBackgroundColor);
        table.addCell(cell);
    }
}
