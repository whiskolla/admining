package com.lw1;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQL {

    public static void writeNewUserToDB(String userName) {

        String url = "jdbc:postgresql://localhost:5432/lw13";
        String user = "postgres";
        String password = "postgres";

        String name = userName;

        // query
        //INSERT INTO users(username, password, isblock, ispswd) VALUES('user', 'user', false, false)
        String query = "INSERT INTO users(username, isblock, ispswd) VALUES(?, ?, ?)\n";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setBoolean(2, false);
            pst.setBoolean(3, false);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
    public static List<User> getUsersFromDB(){
        Connection con;
        String url = "jdbc:postgresql://localhost:5432/lw13";
        String user = "postgres";
        String password = "postgres";
        try{
            con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from users");

            List<User> usersList = new ArrayList<>();
            while(rs.next()){
                String username = rs.getString("username");
                String pass = rs.getString("password");
                if(pass == null){pass = "";}
                boolean isblock = rs.getBoolean("isblock");
                boolean ispswd = rs.getBoolean("ispswd");
                User newUser = new User(username, pass, isblock, ispswd);
                usersList.add(newUser);
            }
            return usersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void UpdatePswToDB(String username, String passWord) {

        String url = "jdbc:postgresql://localhost:5432/lw13";
        String user = "postgres";
        String password = "postgres";

        String name = username;
        String pass = passWord;

        // query
        //INSERT INTO users(username, password, isblock, ispswd) VALUES('user', 'user', false, false)
        String query = "UPDATE users SET password = ? Where username = ?\n";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, pass);
            pst.setString(2, name);
            pst.executeUpdate();
            System.out.println(name + " " + pass);
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
    public static void ChangeBlockInDB(String username, boolean isBlock) {

        String url = "jdbc:postgresql://localhost:5432/lw13";
        String user = "postgres";
        String password = "postgres";

        String name = username;
        boolean isblock = isBlock;
        System.out.println(isblock);

        // query
        //INSERT INTO users(username, password, isblock, ispswd) VALUES('user', 'user', false, false)
        String query = "UPDATE users SET isblock = ? Where username = ?\n";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setBoolean(1, isblock);
            pst.setString(2, name);
            pst.executeUpdate();
            System.out.println(name + " " + isblock);
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
    public static void ChangeIsPswdInDB(String username, boolean isPswd) {

        String url = "jdbc:postgresql://localhost:5432/lw13";
        String user = "postgres";
        String password = "postgres";

        String name = username;
        boolean ispswd = isPswd;

        // query
        //INSERT INTO users(username, password, isblock, ispswd) VALUES('user', 'user', false, false)
        String query = "UPDATE users SET ispswd = ? Where username = ?\n";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setBoolean(1, ispswd);
            pst.setString(2, name);
            pst.executeUpdate();
            System.out.println(name + " " + ispswd);
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

}