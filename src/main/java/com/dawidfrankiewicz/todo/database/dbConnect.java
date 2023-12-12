package com.dawidfrankiewicz.todo.database;

import java.sql.*;
public class dbConnect{
    public static void  main(String[] args){
    try{
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root","");
    Statement myStmt = myConn.createStatement();
        ResultSet myRs = myStmt.executeQuery("select * from users");
        while (myRs.next()){
            System.out.println(myRs.getString("email"));
        }
    }
    catch (Exception exc) {
        exc.printStackTrace();
    }}
}
