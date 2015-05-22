package shared;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private String street_address;
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
        this.street_address = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Address(ResultSet rs) throws SQLException
    {
        this.id = rs.getInt("id");
        this.inhabitantName = rs.getString("inhabitant_name");
        this.street_address = rs.getString("street_address");
        this.zipCode = rs.getInt("zip_code");
        this.city = rs.getString("city");
        this.country = rs.getString("country");
        ++addressCreations;
    }

    public void save(PreparedStatement insertPS, PreparedStatement updatePS) throws SQLException
    {
        updatePS.setString(1, inhabitantName);
        updatePS.setString(2, street_address);
        updatePS.setInt(3, zipCode);
        updatePS.setString(4, city);
        updatePS.setString(5, country);
        updatePS.setInt(6, id);
        updatePS.addBatch();

        insertPS.setInt(1, id);
        insertPS.setString(2, inhabitantName);
        insertPS.setString(3, street_address);
        insertPS.setInt(4, zipCode);
        insertPS.setString(5, city);
        insertPS.setString(6, country);
        insertPS.setInt(7, id);
        insertPS.addBatch();
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
        return String.format("%s\n %s\n%s %s\n%s", inhabitantName, street_address, zipCode, city, country);
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
    public String getStreetName()
    {
        return street_address;
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
