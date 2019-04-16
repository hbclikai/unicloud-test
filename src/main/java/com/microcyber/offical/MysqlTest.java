package com.microcyber.offical;

import java.sql.*;
import java.util.Scanner;

public class MysqlTest {

    public static void test() {

        Scanner scanner = new Scanner(System.in);
        //1.dbName
        System.out.println("enter dbName:");
        String dbName = scanner.nextLine();

        //2.username
        System.out.println("enter username: ");
        String username = scanner.nextLine();

        //3.password
        System.out.println("enter password: ");
        String password = scanner.nextLine();

        Connection con;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.2.186:3306/"+dbName;

        try {
            //加载驱动程序
            Class.forName(driver);
            con = DriverManager.getConnection(url,username,password);
            if(!con.isClosed()){
                System.out.println("Succeeded connecting to the Database!");
            }

            Statement statement = con.createStatement();
            String sql = "select * from mc_device";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                long device_id = rs.getLong("device_id");
                System.out.println("device_id:"+device_id);
            }
            rs.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}