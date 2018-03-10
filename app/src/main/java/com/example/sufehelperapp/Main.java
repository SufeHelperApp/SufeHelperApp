package com.example.sufehelperapp;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    //用于测试的方法
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, ClassNotFoundException {
        Connection conn = DbUtils.getConn();
        ResultSet rs = null;
        PreparedStatement psmt = null;
        System.out.println(conn);
        try {
            psmt = conn.prepareStatement("select * from person");
            rs = psmt.executeQuery();
            List list = DbUtils.populate(rs, user.class);
            for(int i = 0 ; i<list.size() ; i++){
                user per = (user) list.get(i);
                //System.out.println("person : id = "+per.getId()+" name = "+per.getName()+" age = "+per.getAge());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rs=null;
            }
            if(psmt!=null){
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                psmt=null;
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn=null;
            }
        }

    }

}
