package Controller.Report;

import Config.DbConnect;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Create table.
 */
public class CreateTable {

    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Ilosc.
     */
    int ilosc = 0;
    /**
     * The Suma.
     */
    double suma = 0;

    /**
     * Create header pdf p table.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the pdf p table
     */
    public PdfPTable createHeader(LocalDate dateFrom, LocalDate dateTo) {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font f = new Font(bf, 9, Font.NORMAL);

        float[] widths1 = {5, 60, 10, 10, 10};
        PdfPTable table1 = new PdfPTable(widths1);
        table1.setWidthPercentage(100f);
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
        Phrase suma = new Phrase("Suma", f);

        table1.addCell(lp);
        table1.addCell(nazwa);
        table1.addCell(ilosc);
        table1.addCell(wartosc_brutto);
        table1.addCell(suma);

        return table1;
    }

    /**
     * Create data pdf p table.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the pdf p table
     */
    public PdfPTable createData(LocalDate dateFrom, LocalDate dateTo) {
        float[] widths1 = {5, 60, 10, 10, 10};
        PdfPTable table = new PdfPTable(widths1);
        try {
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setPaddingRight(4);
            table.setWidthPercentage(100f);

            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT products.Name, SUM(orders_details.Quantity) AS ilosc, orders_details.Price, SUM(orders_details.Quantity*orders_details.Price) AS suma FROM orders \n" +
                    "INNER JOIN orders_details ON orders.ID_order=orders_details.ID_order\n" +
                    "INNER JOIN products ON orders_details.ID_product=products.ID_product\n" +
                    "WHERE orders.Status != 'Anulowane' AND Date BETWEEN ? AND ?\n" +
                    "GROUP BY products.ID_product, orders_details.Price");
            st.setDate(1, Date.valueOf(dateFrom));
            st.setDate(2, Date.valueOf(dateTo));
            ResultSet rs = st.executeQuery();
            int k = 1;
            while (rs.next()) {
                table.addCell(String.valueOf(k));
                table.addCell(rs.getString("Name"));
                table.addCell(rs.getString("ilosc"));
                table.addCell(rs.getString("Price"));
                table.addCell(rs.getString("suma"));
                suma += rs.getDouble("suma");
                ilosc += rs.getInt("ilosc");
                k++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return table;
    }

    /**
     * Create metoda pdf p table.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the pdf p table
     */
    public PdfPTable createMetoda(LocalDate dateFrom, LocalDate dateTo) {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(Controller.Invoice.CreateTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.Invoice.CreateTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font f1 = new Font(bf, 16, Font.BOLD);

        Phrase desc = new Phrase("Raport z dni od: " + dateFrom + ", do: " + dateTo, f1);


        //float[] widths1 = {200};
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.getDefaultCell().setPadding(3);

        table.setWidthPercentage(100f);

        PdfPCell cell_seller = new PdfPCell(desc);
        cell_seller.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_seller.setBorder(Rectangle.NO_BORDER);
        cell_seller.setPaddingBottom(10);


        table.addCell(cell_seller);
        return table;
    }

    /**
     * Create sum element.
     *
     * @return the element
     */
    public Element createSum() {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font f = new Font(bf, 12, Font.NORMAL);

        float[] widths1 = {10, 10};
        PdfPTable table = new PdfPTable(widths1);
        table.getDefaultCell().setBorderWidth(1);
        table.getDefaultCell().setPadding(3);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setWidthPercentage(30);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new Phrase("Ilosc:", f));
        table.addCell(new Phrase("Suma:", f));

        PdfPCell Ilosc = new PdfPCell(new Phrase(String.valueOf(ilosc), f));
        Ilosc.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Ilosc.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(Ilosc);

        PdfPCell Suma = new PdfPCell(new Phrase(String.valueOf(suma) + " zł", f));
        Suma.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Suma.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(Suma);

        return table;
    }
}
