package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Lucas Jensen - lujen14@student.sdu.dk
 */
public class DatabaseConnection
{
    private Connection connection = null;
    private String db;
    private String address;
    private String port;
    private String database;
    private String username;
    private String password;

    private boolean keepAlive = false;

    /**
     * Initialiazes a DatabaseConnection and tests the connection with default
     * values database name: test username: postgres password: test address:
     * localhost port: 5432.
     *
     */
    public DatabaseConnection()
    {
        try
        {
            Class.forName("org.postgresql.Driver");

            Properties properties = new Properties();
            InputStream input = new FileInputStream(new File("Database/Database.properties"));
            properties.load(input);

            this.address = properties.getProperty("address");
            this.port = properties.getProperty("port");
            this.database = properties.getProperty("database");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");

            this.db = String.format("jdbc:postgresql://%s:%s/%s", this.address, this.port, this.database);
            Connect();
            Disconnect();
        }
        catch (SQLException ex)
        {
            System.out.println("Could not connect to database, please check your settings.");
            ex.printStackTrace();
            System.exit(1);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Could not find database config file, cannot load properties.");
            System.exit(1);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Could not find postgresql jdbc drivers. Please ensure that you have properly installed postgres.");
            System.exit(1);
        }
    }

    /**
     * Keeps a connection open, allowing for many queries to be done in a row
     * without closing the connection.
     *
     * @throws SQLException
     */
    public void prepareSequentialTasks() throws SQLException
    {
        Connect();
        keepAlive = true;
    }

    /**
     * Stops keeping the connection open for sequential tasks.
     *
     * @throws SQLException
     */
    public void unprepareSequentialTasks() throws SQLException
    {
        keepAlive = false;
        Disconnect();
    }

    /**
     * Connects to the database.
     *
     * @return Returns false if an error happened connecting to the database.
     * @throws SQLException
     */
    private boolean Connect() throws SQLException
    {
        if (keepAlive)
        {
            return true;
        }

        connection = DriverManager.getConnection(db, username, password);
        return connection != null;
    }

    /**
     * Disconnects from the database
     *
     * @throws SQLException
     */
    private void Disconnect() throws SQLException
    {
        if (keepAlive)
        {
            return;
        }
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        finally
        {
            connection = null;
        }
    }

    /**
     * For querying select statements to the database.
     *
     * @param query a select query
     * @param arguments for the prepared statement
     * @return Returns the result set provided by the select query.
     * @throws java.sql.SQLException
     */
    public ResultSet Select(String query, Object... arguments) throws SQLException
    {
        try
        {
            if (!Connect())
            {
                return null;
            }

            PreparedStatement pstmt = connection.prepareStatement(query);

            if (arguments != null && arguments.length > 0)
            {
                for (int i = 0; i < arguments.length; ++i)
                {
                    pstmt.setObject(i + 1, arguments[i]); // argument indexes start at 1 according to javadoc.
                }
            }

            return pstmt.executeQuery();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            Disconnect();
        }

        return null;
    }

    /**
     * For querying insert statements on the databae.
     *
     * @param query an insert statement
     * @param arguments arguments for the preparedstatement
     * @return Returns error codes as int. For now "-1" is the only error code
     * error.
     * @throws SQLException
     */
    public int Insert(String query, Object... arguments) throws SQLException
    {
        return Query(query, arguments);
    }

    /**
     * For querying Alter statements on the database.
     *
     * @param query a DDL statement
     * @return Returns error codes as int. For now "-1" is the only error code
     * error.
     * @throws SQLException
     */
    public int Alter(String query) throws SQLException
    {
        try
        {
            if (!Connect())
            {
                return -1;
            }

            Statement pstmt = connection.createStatement();

            return pstmt.executeUpdate(query);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            Disconnect();
        }

        return -1;
    }

    /**
     * For querying Update statements on the database.
     *
     * @param query an update statement
     * @param arguments arguments for the preparedstatement
     * @return Returns error codes as int. For now "-1" is the only error code
     * error.
     * @throws SQLException
     */
    public int Update(String query, Object... arguments) throws SQLException
    {
        return Query(query, arguments);
    }

    /**
     * Main query method. Provides the means to parse a query to the database.
     *
     * @param query The query to be queried on the database.
     * @param arguments arguments for the preparedstatement
     * @return Returns error codes as int. For now "-1" is the only error code
     * error.
     * @throws SQLException
     */
    public int Query(String query, Object... arguments) throws SQLException
    {
        try
        {
            if (!Connect())
            {
                return -1;
            }

            PreparedStatement pstmt = connection.prepareStatement(query);

            if (arguments != null && arguments.length > 0)
            {
                for (int i = 0; i < arguments.length; ++i)
                {
                    pstmt.setObject(i + 1, arguments[i]); // argument indexes start at 1 according to javadoc.
                }
            }

            return pstmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            Disconnect();
        }

        return -1;
    }

    /**
     * Prepares a statement with an SQL statement.
     *
     * @param sql any SQL statement fitting a prepared statement
     * @return new preparedstatement to use with batches of statements
     * @throws SQLException
     */
    public PreparedStatement createPreparedStatement(String sql) throws SQLException
    {
        if (!Connect())
        {
            return null;
        }
        return connection == null ? null : connection.prepareStatement(sql);
    }
}
