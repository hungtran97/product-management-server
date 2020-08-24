/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.util.ArrayList;
import model.Product;
import model.User;

/**
 *
 * @author nhudi
 */
public class JDBC {

    private Connection conn;
    private Statement stm;
    private static final String url = "jdbc:mysql://localhost:3306/db13";
    private static final String userName = "root";
    private static final String passWord = "123456";

    public JDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, userName, passWord);
            this.stm = (Statement) this.conn.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public User ktUser(String userName, String passWord) {
        User a= new User();
        try {
            ResultSet rs = this.stm.executeQuery("select *from user where username='" + userName + "'and  password='" + passWord + "';");
            if (rs.next()) {
                a = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertProduct(String productCode, String name, String description, int number, int userid) {
        try {
            this.stm.executeUpdate("insert into product(productcode, name, description,number,userid) values"
                    + "('" + productCode + "','" + name + "','" +description+ "',"+number+","+userid+");");
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public ArrayList<Product> showListProduct(int userID){
        ArrayList<Product> listPrd= new ArrayList<>();
        try {
            ResultSet rs=this.stm.executeQuery("select *from product where userid="+userID);
            while(rs.next()){
                int id=rs.getInt(1);
                String productPrd=rs.getString(2);
                String name=rs.getString(3);
                String description= rs.getString(4);    
                int number=rs.getInt(5);
                Product prd= new Product(id, productPrd, name, description, number, userID);
                listPrd.add(prd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPrd;
    }
}
