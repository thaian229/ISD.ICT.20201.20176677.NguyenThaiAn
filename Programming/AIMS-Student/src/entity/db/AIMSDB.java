package entity.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import entity.order.RushOrder;
import utils.*;

public class AIMSDB {

	private static Logger LOGGER = Utils.getLogger(Connection.class.getName());
	private static Connection connect;

    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
			Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:assets/db/aims.db";
            connect = DriverManager.getConnection(url);
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        } 
        return connect;
    }
    

    public static void main(String[] args) {
        AIMSDB.getConnection();
    }

    public static ArrayList<RushOrder> queryAllRushOrder() throws SQLException {
        ArrayList<RushOrder> rushOrderArrayList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM RushOrder";
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            // loop the result set
            while (rs.next()) {
                RushOrder rushOrder = new RushOrder();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rushOrderArrayList;
    }

    public static void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = getConnection().createStatement();
        if (value instanceof String) {
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " "
                + field + "=" + value + " "
                + "where id=" + id + ";");
    }
}
