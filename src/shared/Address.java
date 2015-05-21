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

    @Override
    public String toString()
    {
        return String.format("%s\n %s\n%s %s\n%s", inhabitantName, street_address, zipCode, city, country);
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public String getInhabitantName()
    {
        return inhabitantName;
    }

    public String getStreetName()
    {
        return street_address;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public int getID()
    {
        return id;
    }
}
