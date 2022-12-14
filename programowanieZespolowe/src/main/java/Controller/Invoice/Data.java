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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Data.
 */
public class Data {

    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Name.
     */
    String name, /**
     * The Surname.
     */
    surname, /**
     * The Addres.
     */
    addres;

    /**
     * Create seller pdf p table.
     *
     * @param id_order the id order
     * @return the pdf p table
     */
    public PdfPTable createSeller(int id_order) {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(CreateTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font f1 = new Font(bf, 9, Font.NORMAL);
        Font f2 = new Font(bf, 10, Font.BOLD);

        Phrase desc = new Phrase("Sprzedawca:\n", f1);
        Phrase seller = new Phrase("Sklep \nPigonia 1, 35-310 Rzeszów", f2);
        desc.add(seller);
        try {
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM orders INNER JOIN users u on orders.ID_user = u.ID_user WHERE ID_order = ?");
            st.setInt(1, id_order);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                name = rs.getString("Name");
                surname = rs.getString("Surname");
                addres = rs.getString("Delivery_address");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Phrase desc2 = new Phrase("Klient:\n", f1);
        Phrase customer = new Phrase(name + " " + surname + "\n" + addres, f2);
        desc2.add(customer);

        Phrase info = new Phrase("Faktura numer: " + id_order);


        //float[] widths1 = {200};
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setBorder(0);
        table.getDefaultCell().setPadding(3);

        table.setWidthPercentage(100f);

        PdfPCell cell_seller = new PdfPCell(desc);
        cell_seller.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_seller.setBorder(Rectangle.NO_BORDER);
        cell_seller.setPaddingBottom(10);

        PdfPCell cell_info = new PdfPCell(info);
        cell_info.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell_info.setBorder(Rectangle.NO_BORDER);
        cell_info.setPaddingBottom(10);


        PdfPCell cell_emptor = new PdfPCell();
        cell_emptor.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_emptor.setPaddingBottom(40);
        cell_emptor.setBorder(Rectangle.NO_BORDER);
        cell_emptor.setPhrase(desc);

        PdfPCell cell_offeree = new PdfPCell();
        cell_offeree.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_offeree.setPaddingBottom(40);
        cell_offeree.setBorder(Rectangle.NO_BORDER);
        cell_offeree.setPhrase(desc2);

        table.addCell(cell_seller);
        table.addCell(cell_info);
        table.addCell(cell_emptor);
        table.addCell(cell_offeree);
        return table;

    }
}
