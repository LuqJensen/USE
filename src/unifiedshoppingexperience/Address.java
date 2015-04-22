package unifiedshoppingexperience;

/**
 * Keeps information about any address.
 *
 * @author Gruppe12
 */
public class Address
{
    private String streetName;
    private String houseNumber;
    private int zipCode;
    private String city;

    /**
     * Creates an address.
     *
     * @param streetName Street name of the address, example: "Palmer street".
     * @param houseNumber House number of the address, example: "42a, 2".
     * @param zipCode Zip code of the address, example: "6700".
     * @param city City of the address, example: "Copenhagen".
     */
    public Address(String streetName, String houseNumber, int zipCode, String city)
    {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }
}
