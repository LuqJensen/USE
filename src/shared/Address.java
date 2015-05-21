package shared;

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
    private String country;

    /**
     * Creates an address.
     *
     * @param streetName Street name of the address, example: "Palmer street".
     * @param houseNumber House number of the address, example: "42a, 2".
     * @param zipCode Zip code of the address, example: "6700".
     * @param city City of the address, example: "Copenhagen".
     * @param country Country of the address, example: "Denmark".
     */
    public Address(String streetName, String houseNumber, int zipCode, String city, String country)
    {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s\n%s %s\n%s", streetName, houseNumber, zipCode, city, country);
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public String getStreetName()
    {
        return streetName;
    }

    public int getZipCode()
    {
        return zipCode;
    }
}
