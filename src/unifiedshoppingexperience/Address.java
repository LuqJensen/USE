package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class Address
{
    private String streetName;
    private String houseNumber;
    private int zipCode;
    private String city;

    public Address(String streetName, String houseNumber, int zipCode, String city)
    {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }
}
