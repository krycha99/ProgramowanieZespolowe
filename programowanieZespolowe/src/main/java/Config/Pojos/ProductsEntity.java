package Config.Pojos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Blob;

/**
 * The type Products entity.
 */
public class ProductsEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID_product")
    private int ID_product;
    @Column(name = "Name")
    private String Name;
    @Column(name = "Price")
    private double Price;
    @Column(name = "Quantity")
    private int Quantity;
    @Column(name = "Description")
    private String Description;
    @Column(name = "Category")
    private String Category;
    @Column(name = "Image")
    private Blob Image;

    /**
     * Instantiates a new Products entity.
     */
    public ProductsEntity() {
    }

    /**
     * Instantiates a new Products entity.
     *
     * @param ID_product  the id product
     * @param name        the name
     * @param price       the price
     * @param quantity    the quantity
     * @param description the description
     * @param category    the category
     * @param image       the image
     */
    public ProductsEntity(int ID_product, String name, double price, int quantity, String description, String category, Blob image) {
        this.ID_product = ID_product;
        Name = name;
        Price = price;
        Quantity = quantity;
        Description = description;
        Category = category;
        Image = image;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return Price + " zł";
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

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return Category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        Category = category;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Blob getImage() {
        return Image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(Blob image) {
        Image = image;
    }
}
