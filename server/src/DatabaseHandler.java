import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Config {
    Connection dbConnection;
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql//" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection= DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpUser(String Name, String lastName, String login, String password) {

        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_NAME+"," + Const.USERS_LASTNAME + ","
                + Const.USERS_LOGIN + "," + Const.USERS_PASSWORD + ")"+
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(insert);
            prSt.setString(1,Name);
            prSt.setString(2,lastName);
            prSt.setString(3,login);
            prSt.setString(4, password);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}