package com.shxt.syt_supermarket.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {
	
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;

	
	public void getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");//加载驱动
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/syt_supermarket","root","mysql");//获取连接
			
			stmt = conn.createStatement();//创建语句对象
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加，删除，修改
	 * @param sql
	 * @return
	 */
	public int update(String sql){
		int num = -1;
		getConnection();
		
		try {
			num = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return num;
	}
	public static void main(String[] args) {
		DB db = new DB();
		ArrayList<String[]> arr = db.arrQuery("select *from warehouse");
		
		for (int i = 0; i < arr.size(); i++) {
			String[] temp = arr.get(i);
			
			for(String t : temp){
				System.out.print(t+" ");
			}
			System.out.println();
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	/**
	 * 查询
	 * @param sql
	 * @return
	 */
	public ArrayList<String[]> arrQuery(String sql){
		getConnection();
		ArrayList<String[]> rsList = new ArrayList<String[]>();
		try {
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			while(rs.next()){
				
				String[] arr = new String[num];
				
				
				
				for (int i = 1; i <=num; i++) {
					String temp = rs.getString(i);
					arr[i-1] = temp;
				}
				
				
				rsList.add(arr);
				
				
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}
		return rsList;
		
	}
//	编写程序：
//	创建一个类DBTools，在DBTools中创建一个方法find，find方法用于对数据库进行查询操作，现在要求将结果集封装成数组线性表嵌套数组的形式：
//	ArrayList<String[]> rsList = new ArrayList<String[]>()形式
//	其中：	String[] 用于存储一行记录的所有字段（的值）
//			rsList存储所有的数组（String[]），即记录的总数
	
	
	public ArrayList<HashMap<String,String>> query(String sql){
		
		getConnection();
		
		
		ArrayList<HashMap<String,String>> alist = new ArrayList<HashMap<String,String>>();
		
		
		
		try {
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();//获取结果集结构对象
			while(rs.next()){
				HashMap<String,String> hm = new HashMap<String,String>();
				
				
				for (int i = 1; i <=rsmd.getColumnCount(); i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);
					hm.put(key, value);
				}
				
				alist.add(hm);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}
		return alist;
		
		
	}
	/**
	 * 分页查询
	 * @param pageCount 本页显示的行数
	 * @param pageNum 当前页数
	 * @param sql 原sql语句
	 * @return
	 */
	public ArrayList<String[]> pageQuery(int pageCount,int pageNum,String sql){//select *from employeelimit
		
		String newSql = sql+" limit "+(pageNum-1)*pageCount+","+pageCount;
		return arrQuery(newSql);
		
		
	}
	
	
	
	
	
	public void close(){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
}
