package Config.Pojos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Blob;

/**
 * The type Payments entity.
 */
public class PaymentsEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID_payment")
    private int ID_payment;
    @Column(name = "Name")
    private String Name;
    @Column(name = "Description")
    private String Description;
    @Column(name = "Image")
    private Blob Image;

    /**
     * Instantiates a new Payments entity.
     */
    public PaymentsEntity() {
    }

    /**
     * Instantiates a new Payments entity.
     *
     * @param ID_payment  the id payment
     * @param name        the name
     * @param description the description
     * @param image       the image
     */
    public PaymentsEntity(int ID_payment, String name, String description, Blob image) {
        this.ID_payment = ID_payment;
        Name = name;
        Description = description;
        Image = image;
    }

    /**
     * Gets id payment.
     *
     * @return the id payment
     */
    public int getID_payment() {
        return ID_payment;
    }

    /**
     * Sets id payment.
     *
     * @param ID_payment the id payment
     */
    public void setID_payment(int ID_payment) {
        this.ID_payment = ID_payment;
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
