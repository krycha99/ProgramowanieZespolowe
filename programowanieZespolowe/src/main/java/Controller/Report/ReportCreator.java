package Controller.Report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

/**
 * The type Report creator.
 */
public class ReportCreator {
    /**
     * Create document.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @param path     the path
     * @return the document
     */
    public Document create(LocalDate dateFrom, LocalDate dateTo, String path) {
        Document document = new Document(PageSize.A4, 36, 20, 36, 20);

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(path + "\\Raport z " + dateFrom + " do " + dateTo + ".pdf"));
            document.open();

            CreateTable obj = new CreateTable();
            Font font = new Font(Font.FontFamily.COURIER, 12);

            document.add(obj.createMetoda(dateFrom, dateTo));
            document.add(obj.createHeader(dateFrom, dateTo));
            document.add(obj.createData(dateFrom, dateTo));
            document.add(obj.createSum());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generowanie raportu");
            alert.setHeaderText("Twój raport został wygenerowany w lokalizacji: " + path);
            alert.showAndWait();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();
        return document;
    }

    /**
     * Create document in pdf format.
     *
     * @param path the path
     * @return the document
     */
    public Document create(String path) {
        Document document = new Document(PageSize.A4, 36, 20, 36, 20);

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(path + "\\Raport całej sprzedazy.pdf"));
            document.open();

            CreateTable2 obj = new CreateTable2();
            Font font = new Font(Font.FontFamily.COURIER, 12);

            document.add(obj.createMetoda());
            document.add(obj.createHeader());
            document.add(obj.createData());
            document.add(obj.createSum());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generowanie raportu");
            alert.setHeaderText("Twój raport został wygenerowany w lokalizacji: " + path);
            alert.showAndWait();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();
        return document;
    }
}
