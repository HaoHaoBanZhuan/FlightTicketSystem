package com.wbhz.flightsys.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



/**
 * JDBC增删改查模板类
 * @author Mr.t
 *
 */
public class JdbcTemplate {
	/**
	 * 增加
	 * @param sql
	 * @param params
	 */
	public static int insert(String sql,List<Object> params)throws SQLException{
		return update(sql,params);
	}
	
	/**
	 * 删除
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int delete(String sql ,List<Object> params) throws SQLException{
		return update(sql, params);
	}
	
	/**
	 * 修改
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, List<Object> params) throws SQLException{
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		int affectRows = 0 ;
		try {
			conn = JdbcUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			if(!(null == params || params.size() == 0)){
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i+1, params.get(i));
				}
			}
			affectRows = pstmt.executeUpdate();
		} finally {
			//关闭资源
			JdbcUtil.close(pstmt, null);
		}
		return affectRows;
	}
	
	/**
	 * 查询多条记录
	 */
	public static <T> List<T>  selectList(String sql,RowMapper<T> rowMapper,List<Object> params)throws SQLException{
		List<T> dataList = new ArrayList<T>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//给占位符设置参数
		try {
			conn = JdbcUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			//设置占位符
			if(!(null == params || params.size() == 0)){
				for(int i = 0 ; i < params.size(); i++){
					pstmt.setObject((i + 1), params.get(i));
				}
			}
			//结果集
			rs = pstmt.executeQuery();
			//将结果加到list集合中
			while(rs.next()){
				T obj = rowMapper.mapperObject(rs);//对于模版方法而言，我只要知道能转换即可
				dataList.add(obj);
			}
			//关闭rs
		} catch (ParseException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmt,rs);
		}
		return dataList;
		
	}
	
	/**
	 * 查询单条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static <T> T  selectOne(String sql,RowMapper<T> rowMapper,List<Object> params)throws SQLException{
		List<T> dataList = selectList(sql,rowMapper,params);
		if(dataList.size() == 0){
			return null;
		}else{
			return dataList.get(0);
		}
	}

}
