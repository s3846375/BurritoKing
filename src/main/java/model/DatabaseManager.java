package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DatabaseManager {

    public static void createTableIfNotExist() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "username TEXT, " +
                    "password TEXT, " +
                    "firstName TEXT, " +
                    "lastName TEXT)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS userVIP (" +
                    "username TEXT, " +
                    "email TEXT, " +
                    "creditPoint INTEGER)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS orderHistory (" +
                    "username TEXT, " +
                    "orderNo INTEGER, " +
                    "orderDate TEXT, " +
                    "orderTime TEXT, " +
                    "orderCost REAL, " +
                    "orderDetail TEXT," +
                    "orderStatus TEXT," +
                    "prepareTime REAL)");

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    // Get all users from database
    public static ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
             Statement statement = connection.createStatement()
        ) {
            ResultSet userSet = statement.executeQuery("SELECT * FROM user");
            while (userSet.next()) {
                String username = userSet.getString("userName");
                String password = userSet.getString("password");
                String firstName = userSet.getString("firstName");
                String lastName = userSet.getString("lastName");
                userList.add(new User(username, password, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return userList;
    }

    // Get VIP Users from database
    public static ArrayList<UserVIP> getVIPList() {
        ArrayList<UserVIP> vipList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
             Statement statement = connection.createStatement()
        ) {
            ResultSet vipSet = statement.executeQuery("SELECT u.username, u.password, u.firstName, u.lastName, v.email, v.creditPoint "
                    + "FROM user u "
                    + "INNER JOIN userVIP v ON u.username = v.username");
            while (vipSet.next()) {
                String username = vipSet.getString("username");
                String password = vipSet.getString("password");
                String firstName = vipSet.getString("firstName");
                String lastName = vipSet.getString("lastName");
                String email = vipSet.getString("email");
                int creditPoint = vipSet.getInt("creditPoint");
                vipList.add(new UserVIP(username, password, firstName, lastName, email, creditPoint));
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return vipList;
    }

    // Get current user order history details from database
    public static ArrayList<OrderDetail> getUserOrderDetails(String currentUserName) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
             Statement statement = connection.createStatement()
        ) {

        ResultSet orderSet = statement.executeQuery("SELECT * FROM orderHistory");
        while (orderSet.next()) {
            String username = orderSet.getString("username");
            int orderNo = orderSet.getInt("orderNo");
            String orderDate = orderSet.getString("orderDate");
            String orderTime = orderSet.getString("orderTime");
            double orderCost = orderSet.getDouble("orderCost");
            String orderDetail = orderSet.getString("orderDetail");
            StatusEnum orderStatus = StatusEnum.fromString(orderSet.getString("orderStatus"));
            double prepareTime = orderSet.getDouble("prepareTime");

            orderDetails.add(new OrderDetail(username, orderNo, orderDate, orderTime, orderCost, orderDetail, orderStatus, prepareTime));
        }

    } catch (SQLException e) {
        e.printStackTrace(System.err);
    }

        return orderDetails.stream()
                .filter(orderDetail -> orderDetail.getUsername().equals(currentUserName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void saveUserToDB(String username, String password, String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user values (?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void saveVIPtoDB(String username, String email) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into userVIP values (?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void saveOrderToDB(String username, ArrayList<OrderDetail> orderDetails) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db")) {
            // Delete rows with the specific username
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM orderHistory WHERE username = ?");
            deleteStatement.setString(1, username);
            deleteStatement.executeUpdate();

            // Insert new order details
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO orderHistory VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (OrderDetail orderDetail : orderDetails) {
                insertStatement.setString(1, orderDetail.getUsername());
                insertStatement.setInt(2, orderDetail.getOrderNo());
                insertStatement.setString(3, orderDetail.getOrderDate());
                insertStatement.setString(4, orderDetail.getOrderTime());
                insertStatement.setDouble(5, orderDetail.getOrderCost());
                insertStatement.setString(6, orderDetail.getOrderDetail());
                insertStatement.setString(7, orderDetail.getOrderStatus().toString());
                insertStatement.setDouble(8, orderDetail.getPrepareTime());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void updateUserDB(String username, String newPassword, String newFirstName, String newLastName) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET password = ?, firstName = ?, lastName = ? WHERE username = ?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newFirstName);
            preparedStatement.setString(3, newLastName);
            preparedStatement.setString(4, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void updateVIP(String username, int credit) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE userVIP SET creditPoint = ? WHERE username = ?");
            preparedStatement.setInt(1, credit);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
