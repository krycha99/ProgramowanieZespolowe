package Controller.Invoice;

import Config.DbConnect;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Create table.
 */
public class CreateTable {

    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Suma.
     */
    double suma = 0;

    /**
     * Create header pdf p table.
     *
     * @param id_order the id order
     * @return the pdf p table
     */
    public PdfPTable createHeader(int id_order) {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font f = new Font(bf, 9, Font.NORMAL);

        float[] widths1 = {5, 70, 10, 10};
        PdfPTable table1 = new PdfPTable(widths1);
        table1.setWidthPercentage(100f);
        PdfPCell header = new PdfPCell(new Phrase("Faktura numer: " + id_order));
        header.setPadding(5);
        header.setColspan(9);
        table1.addCell(header);
        table1.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        table1.getDefaultCell().setPaddingBottom(3);
        table1.getDefaultCell().setPaddingTop(2);
        table1.getDefaultCell().setPaddingLeft(3);
        table1.getDefaultCell().setPaddingRight(3);
        table1.getDefaultCell().setBorderWidth(1);

        Phrase lp = new Phrase("Lp.", f);
        Phrase nazwa = new Phrase("Nazwa przedmiotu", f);
        Phrase ilosc = new Phrase("Ilość", f);
        Phrase wartosc_brutto = new Phrase("Wartość brutto [zł]", f);

        table1.addCell(lp);
        table1.addCell(nazwa);
        table1.addCell(ilosc);
        table1.addCell(wartosc_brutto);

        return table1;
    }

    /**
     * Create data pdf p table.
     *
     * @param id_order the id order
     * @return the pdf p table
     */
    public PdfPTable createData(int id_order) {
        float[] widths1 = {5, 70, 10, 10};
        PdfPTable table = new PdfPTable(widths1);
        try {
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setPaddingRight(4);
            table.setWidthPercentage(100f);

            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM orders_details od INNER JOIN orders o on od.ID_order = o.ID_order INNER JOIN products p on od.ID_product = p.ID_product WHERE od.ID_order = ?");
            st.setInt(1, id_order);
            ResultSet rs = st.executeQuery();
            int k = 1;
            while (rs.next()) {
                table.addCell(String.valueOf(k));
                table.addCell(rs.getString("Name"));
                table.addCell(rs.getString("Quantity"));
                table.addCell(rs.getString("Price"));
                suma += rs.getInt("Quantity") * rs.getDouble("Price");
                k++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return table;
    }


    /**
     * Create sum pdf p table.
     *
     * @return the pdf p table
     */
    public PdfPTable createSum() {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font f = new Font(bf, 12, Font.NORMAL);

        float[] widths1 = {10, 15};
        PdfPTable table = new PdfPTable(widths1);
        table.getDefaultCell().setBorderWidth(1);
        table.getDefaultCell().setPadding(3);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setWidthPercentage(30);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new Phrase("Razem:", f));

        PdfPCell Suma = new PdfPCell(new Phrase(String.valueOf(suma) + " zł", f));
        Suma.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Suma.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(Suma);

        return table;
    }
}
