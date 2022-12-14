package Config.Pojos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * The type Orders entity.
 */
public class OrdersEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID_order")
    private int ID_order;
    @Column(name = "ID_user")
    private int ID_user;
    @Column(name = "FullName")
    private String FullName;
    @Column(name = "Status")
    private String Status;
    @Column(name = "Date")
    private Date Date;
    @Column(name = "Delivery_address")
    private String Delivery_address;
    @Column(name = "ID_payment")
    private int ID_payment;
    @Column(name = "Payment")
    private String Payment;
    @Column(name = "Description")
    private String Description;

    /**
     * Instantiates a new Orders entity.
     */
    public OrdersEntity() {
    }

    /**
     * Instantiates a new Orders entity.
     *
     * @param ID_order         the id order
     * @param ID_user          the id user
     * @param fullName         the full name
     * @param status           the status
     * @param date             the date
     * @param delivery_address the delivery address
     * @param ID_payment       the id payment
     * @param payment          the payment
     * @param description      the description
     */
    public OrdersEntity(int ID_order, int ID_user, String fullName, String status, java.sql.Date date, String delivery_address, int ID_payment, String payment, String description) {
        this.ID_order = ID_order;
        this.ID_user = ID_user;
        FullName = fullName;
        Status = status;
        Date = date;
        Delivery_address = delivery_address;
        this.ID_payment = ID_payment;
        Payment = payment;
        Description = description;
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
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return FullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName) {
        FullName = fullName;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        Status = status;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public java.sql.Date getDate() {
        return Date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(java.sql.Date date) {
        Date = date;
    }

    /**
     * Gets delivery address.
     *
     * @return the delivery address
     */
    public String getDelivery_address() {
        return Delivery_address;
    }

    /**
     * Sets delivery address.
     *
     * @param delivery_address the delivery address
     */
    public void setDelivery_address(String delivery_address) {
        Delivery_address = delivery_address;
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
     * Gets payment.
     *
     * @return the payment
     */
    public String getPayment() {
        return Payment;
    }

    /**
     * Sets payment.
     *
     * @param payment the payment
     */
    public void setPayment(String payment) {
        Payment = payment;
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
}
