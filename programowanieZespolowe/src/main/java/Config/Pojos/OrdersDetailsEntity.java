package Config.Pojos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The type Orders details entity.
 */
public class OrdersDetailsEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID_order_details")
    private int ID_order_details;
    @Column(name = "ID_order")
    private int ID_order;
    @Column(name = "Description")
    private int Description;
    @Column(name = "ID_product")
    private int ID_product;
    @Column(name = "Product_name")
    private String Product_name;
    @Column(name = "Product_Price")
    private double Product_Price;
    @Column(name = "Price")
    private double Price;
    @Column(name = "Quantity")
    private int Quantity;

    /**
     * Instantiates a new Orders details entity.
     */
    public OrdersDetailsEntity() {
    }

    /**
     * Instantiates a new Orders details entity.
     *
     * @param ID_order_details the id order details
     * @param ID_order         the id order
     * @param description      the description
     * @param ID_product       the id product
     * @param product_name     the product name
     * @param product_Price    the product price
     * @param price            the price
     * @param quantity         the quantity
     */
    public OrdersDetailsEntity(int ID_order_details, int ID_order, int description, int ID_product, String product_name, double product_Price, double price, int quantity) {
        this.ID_order_details = ID_order_details;
        this.ID_order = ID_order;
        Description = description;
        this.ID_product = ID_product;
        Product_name = product_name;
        Product_Price = product_Price;
        Price = price;
        Quantity = quantity;
    }

    /**
     * Gets id order details.
     *
     * @return the id order details
     */
    public int getID_order_details() {
        return ID_order_details;
    }

    /**
     * Sets id order details.
     *
     * @param ID_order_details the id order details
     */
    public void setID_order_details(int ID_order_details) {
        this.ID_order_details = ID_order_details;
    }

    /**
     * Gets id order.
     *
     * @return the id order
     */
    public int getID_order() {
        return ID_order;
    }

    /**
     * Sets id order.
     *
     * @param ID_order the id order
     */
    public void setID_order(int ID_order) {
        this.ID_order = ID_order;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public int getDescription() {
        return Description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(int description) {
        Description = description;
    }

    /**
     * Gets id product.
     *
     * @return the id product
     */
    public int getID_product() {
        return ID_product;
    }

    /**
     * Sets id product.
     *
     * @param ID_product the id product
     */
    public void setID_product(int ID_product) {
        this.ID_product = ID_product;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProduct_name() {
        return Product_name;
    }

    /**
     * Sets product name.
     *
     * @param product_name the product name
     */
    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    /**
     * Gets product price.
     *
     * @return the product price
     */
    public double getProduct_Price() {
        return Product_Price;
    }

    /**
     * Sets product price.
     *
     * @param product_Price the product price
     */
    public void setProduct_Price(double product_Price) {
        Product_Price = product_Price;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return Price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        Price = price;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
