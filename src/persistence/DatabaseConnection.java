package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lucas Jensen - lujen14@student.sdu.dk
 */
public class DatabaseConnection
{
    private static Connection connection = null;
    private String db;
    private String address = "127.0.0.1";
    private String port = "5432";
    private String database = "test";
    private String userName = "postgres";
    private String password = "test";

    private boolean keepAlive = false;

    /**
     * Initialiazes a DatabaseConnection and tests the connection with default
     * values database name: test username: postgres password: test address:
     * localhost port: 5432.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public DatabaseConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        db = String.format("jdbc:postgresql://%s:%s/%s", address, port, database);
        Connect();
        Disconnect();
    }

    /**
     * Initialiazes a DatabaseConnection and tests the connection with given
     * database name, username and password with default address: localhost and
     * port: 5432.
     *
     * @param database name of database to connect to. Default value test
     * @param userName name of user/owner of database. Default value: postgres
     * @param password password of user/owner of database. Default value: test
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public DatabaseConnection(String database, String userName, String password) throws SQLException, ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        this.database = database;
        this.userName = userName;
        this.password = password;
        db = String.format("jdbc:postgresql://%s:%s/%s", address, port, database);
        Connect();
        Disconnect();
    }

    /**
     * Initialiazes a DatabaseConnection and tests the connection with given
     * database name, username, password address and port.
     *
     * @param address address of database to connecto. Default value: localhost
     * @param port port of database to connect to. Default value: 5432
     * @param database name of database to connect to. Default value: test
     * @param userName name of user/owner of database. Default value: postgres
     * @param password password of user/owner of database. Default value: test
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public DatabaseConnection(String address, String port, String database, String userName, String password) throws SQLException, ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        this.address = address;
        this.port = port;
        this.database = database;
        this.userName = userName;
        this.password = password;
        db = String.format("jdbc:postgresql://%s:%s/%s", address, port, database);
        Connect();
        Disconnect();
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

        connection = DriverManager.getConnection(db, userName, password);
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
     * @return Returns error codes as int. For now "-1" is the only error
     * code error.
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
     * @return Returns error codes as int. For now "-1" is the only error
     * code error.
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
     * @return Returns error codes as int. For now "-1" is the only error
     * code error.
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
     * @return Returns error codes as int. For now "-1" is the only error
     * code error.
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
