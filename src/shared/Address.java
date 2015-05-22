package shared;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.DataStore;

/**
 * Keeps information about any address.
 *
 * @author Gruppe12
 */
public class Address
{
    private static int addressCreations;
    private final int id;
    private String inhabitantName;
    private String streetAddress;
    private int zipCode;
    private String city;
    private String country;

    static
    {
        addressCreations = 0;
    }

    /**
     * Creates an address.
     *
     * @param inhabitantName
     * @param streetName Street name of the address, example: "Palmer street".
     * @param zipCode Zip code of the address, example: "6700".
     * @param city City of the address, example: "Copenhagen".
     * @param country Country of the address, example: "Denmark".
     */
    public Address(String inhabitantName, String streetName, int zipCode, String city, String country)
    {
        this.id = ++addressCreations;
        this.inhabitantName = inhabitantName;
        this.streetAddress = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;

        DataStore.getPersistence().persist(this);
    }

    public Address(Integer id, String inhabitantName, String streetAddress, Integer zipCode, String city, String country) throws SQLException
    {
        this.id = id;
        this.inhabitantName = inhabitantName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        ++addressCreations;
    }

    /**
     * Turns the address into a string by combining the information and
     * formatting it, as if it was written on a postcard.
     *
     * @return Returns a formatted combination of the address related fields.
     */
    @Override
    public String toString()
    {
        return String.format("%s\n%s\n%s %s\n%s", inhabitantName, streetAddress, zipCode, city, country);
    }

    /**
     * Gets the city of the address.
     *
     * @return The city of the address..
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Gets the country of the address.
     *
     * @return The country of the address.
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Gets the inhabitants name of the address.
     *
     * @return The inhabitants name of the address.
     */
    public String getInhabitantName()
    {
        return inhabitantName;
    }

    /**
     * Gets the street name of the address.
     *
     * @return The street name of the address.
     */
    public String getStreetAddress()
    {
        return streetAddress;
    }

    /**
     * Gets the zip code of the address.
     *
     * @return The zip code of the address.
     */
    public int getZipCode()
    {
        return zipCode;
    }

    /**
     * Gets the ID of the address. This is the number the system uses to
     * identify the address object.
     *
     * @return The ID of the address within the USE system..
     */
    public int getID()
    {
        return id;
    }
}
