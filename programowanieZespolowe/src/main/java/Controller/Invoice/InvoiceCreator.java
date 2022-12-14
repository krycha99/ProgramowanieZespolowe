package Controller.Invoice;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * The type Invoice creator.
 */
public class InvoiceCreator {
    /**
     * Create document in pdf format.
     *
     * @param id_order the id order
     * @param path     the path
     * @return the document
     */
    public Document create(int id_order, String path) {
        Document document = new Document(PageSize.A4, 36, 20, 36, 20);

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(path + "\\Faktura numer " + id_order + ".pdf"));
            document.open();

            CreateTable obj = new CreateTable();
            Data objSeller = new Data();
            Font font = new Font(Font.FontFamily.COURIER, 12);

            document.add(objSeller.createSeller(id_order));
            document.add(obj.createHeader(id_order));
            document.add(obj.createData(id_order));
            document.add(obj.createSum());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generowanie faktury");
            alert.setHeaderText("Twoja faktura została wygenerowana w lokalizacji: "+path);
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
