package Config.Pojos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Blob;

/**
 * The type Cart entity.
 */
public class CartEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID_cart")
    private int ID_cart;
    @Column(name = "ID_product")
    private int ID_product;
    @Column(name = "Product_image")
    private Blob Product_image;
    @Column(name = "Product_name")
    private String Product_name;
    @Column(name = "Product_price")
    private double Product_price;
    @Column(name = "Product_description")
    private String Product_description;
    @Column(name = "ID_user")
    private int ID_user;
    @Column(name = "Quantity")
    private int Quantity;

    /**
     * Instantiates a new Cart entity.
     */
    public CartEntity() {
    }

    /**
     * Gets id cart.
     *
     * @return the id cart
     */
    public int getID_cart() {
        return ID_cart;
    }

    /**
     * Sets id cart.
     *
     * @param ID_cart the id cart
     */
    public void setID_cart(int ID_cart) {
        this.ID_cart = ID_cart;
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
     * Gets product image.
     *
     * @return the product image
     */
    public Blob getProduct_image() {
        return Product_image;
    }

    /**
     * Sets product image.
     *
     * @param product_image the product image
     */
    public void setProduct_image(Blob product_image) {
        Product_image = product_image;
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
    public String getProduct_price() {
        return Product_price + " zł";
    }

    /**
     * Sets product price.
     *
     * @param product_price the product price
     */
    public void setProduct_price(double product_price) {
        Product_price = product_price;
    }

    /**
     * Gets product description.
     *
     * @return the product description
     */
    public String getProduct_description() {
        return Product_description;
    }

    /**
     * Sets product description.
     *
     * @param product_description the product description
     */
    public void setProduct_description(String product_description) {
        Product_description = product_description;
    }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public int getID_user() {
        return ID_user;
    }

    /**
     * Sets id user.
     *
     * @param ID_user the id user
     */
    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
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
