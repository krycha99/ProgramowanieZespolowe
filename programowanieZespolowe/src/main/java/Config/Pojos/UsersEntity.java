package Config.Pojos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The type Users entity.
 */
public class UsersEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID_user")
    private int ID_user;
    @Column(name = "Login")
    private String Login;
    @Column(name = "Password")
    private String Password;
    @Column(name = "Name")
    private String Name;
    @Column(name = "Surname")
    private String Surname;
    @Column(name = "Email")
    private String Email;
    @Column(name = "Phone_number")
    private int Phone_number;
    @Column(name = "Address")
    private String Address;
    @Column(name = "Salary")
    private String Salary;
    @Column(name = "Role")
    private String Role;

    /**
     * Instantiates a new Users entity.
     */
    public UsersEntity() {
    }

    /**
     * Instantiates a new Users entity.
     *
     * @param ID_user      the id user
     * @param login        the login
     * @param password     the password
     * @param name         the name
     * @param surname      the surname
     * @param email        the email
     * @param phone_number the phone number
     * @param address      the address
     * @param salary       the salary
     * @param role         the role
     */
    public UsersEntity(int ID_user, String login, String password, String name, String surname, String email, int phone_number, String address, String salary, String role) {
        this.ID_user = ID_user;
        Login = login;
        Password = password;
        Name = name;
        Surname = surname;
        Email = email;
        Phone_number = phone_number;
        Address = address;
        Salary = salary;
        Role = role;
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
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return Login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        Login = login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        Password = password;
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
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return Surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        Surname = surname;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public int getPhone_number() {
        return Phone_number;
    }

    /**
     * Sets phone number.
     *
     * @param phone_number the phone number
     */
    public void setPhone_number(int phone_number) {
        Phone_number = phone_number;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public String getSalary() {
        return Salary;
    }

    /**
     * Sets salary.
     *
     * @param salary the salary
     */
    public void setSalary(String salary) {
        Salary = salary;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return Role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        Role = role;
    }
}
