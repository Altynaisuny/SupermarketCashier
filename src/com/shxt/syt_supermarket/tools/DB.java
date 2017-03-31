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
			Class.forName("com.mysql.jdbc.Driver");//��������
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/syt_supermarket","root","mysql");//��ȡ����
			
			stmt = conn.createStatement();//����������
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ӣ�ɾ�����޸�
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
	 * ��ѯ
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
//	��д����
//	����һ����DBTools����DBTools�д���һ������find��find�������ڶ����ݿ���в�ѯ����������Ҫ�󽫽������װ���������Ա�Ƕ���������ʽ��
//	ArrayList<String[]> rsList = new ArrayList<String[]>()��ʽ
//	���У�	String[] ���ڴ洢һ�м�¼�������ֶΣ���ֵ��
//			rsList�洢���е����飨String[]��������¼������
	
	
	public ArrayList<HashMap<String,String>> query(String sql){
		
		getConnection();
		
		
		ArrayList<HashMap<String,String>> alist = new ArrayList<HashMap<String,String>>();
		
		
		
		try {
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();//��ȡ������ṹ����
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
	 * ��ҳ��ѯ
	 * @param pageCount ��ҳ��ʾ������
	 * @param pageNum ��ǰҳ��
	 * @param sql ԭsql���
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
