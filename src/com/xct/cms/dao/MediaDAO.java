package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Media;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;


public class MediaDAO extends DBConnection {
	Logger logger = Logger.getLogger(MediaDAO.class);
	
	

	/**
	 * 根据条件查询媒体库
	 * @param str
	 * @return
	 */
	public List<Media> getALLMediaDAO(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Media media=null;
		List<Media> list=null;
		String projectpath=FirstStartServlet.projectpath;
		try{
			String sql=new StringBuffer().append("select * from xct_media,xct_zu,xct_LG where xct_media.zu_id=xct_zu.zu_id and userid=lg_name  ").append(str).toString();
			
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<Media>();
			while(rs.next()){
				media= new Media();
				media.setId(rs.getInt("id"));
				media.setMedia_id(rs.getString("media_id"));
				media.setMedia_title(rs.getString("media_title"));
				media.setMedia_type(rs.getString("media_type"));
				media.setZu_id(rs.getInt("zu_id"));
				media.setM_play_time(rs.getString("m_play_time"));
				media.setM_resolution(rs.getString("m_resolution"));
				String file_name=rs.getString("file_name");
				String file_path=rs.getString("file_path");
				media.setFile_name(file_name);
				media.setFile_path(file_path);
				media.setFile_size(rs.getLong("file_size"));
				media.setSlightly_img_name(rs.getString("slightly_img_name"));
				media.setSlightly_img_size(rs.getLong("slightly_img_size"));
				media.setSlightly_img_path(rs.getString("slightly_img_path"));
				media.setUserid(rs.getString("userid"));
				media.setUsername(rs.getString("name"));
				media.setCreate_date(rs.getString("create_date"));
				media.setLast_date(rs.getString("last_date"));
				media.setZuname(rs.getString("zu_name"));
				media.setIs_exist(Util.decideFile(projectpath+file_path+file_name)); 
				list.add(media);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有媒体库出错！=====").append(e.getMessage()));
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}public List<Media> getALLMediaDAO(Connection con,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Media media=null;
		List<Media> list=null;
		String projectpath=FirstStartServlet.projectpath;
		try{
			String sql=new StringBuffer().append("select * from xct_media,xct_zu,xct_LG where xct_media.zu_id=xct_zu.zu_id and userid=lg_name  ").append(str).toString();
			//System.out.println(sql);
			 pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<Media>();
			while(rs.next()){
				media= new Media();
				media.setId(rs.getInt("id"));
				media.setMedia_id(rs.getString("media_id"));
				media.setMedia_title(rs.getString("media_title"));
				media.setMedia_type(rs.getString("media_type"));
				media.setZu_id(rs.getInt("zu_id"));
				media.setM_play_time(rs.getString("m_play_time"));
				media.setM_resolution(rs.getString("m_resolution"));
				String file_name=rs.getString("file_name");
				String file_path=rs.getString("file_path");
				media.setFile_name(file_name);
				media.setFile_path(file_path);
				media.setFile_size(rs.getLong("file_size"));
				media.setSlightly_img_name(rs.getString("slightly_img_name"));
				media.setSlightly_img_size(rs.getLong("slightly_img_size"));
				media.setSlightly_img_path(rs.getString("slightly_img_path"));
				media.setUserid(rs.getString("userid"));
				media.setUsername(rs.getString("name"));
				media.setCreate_date(rs.getString("create_date"));
				media.setLast_date(rs.getString("last_date"));
				media.setZuname(rs.getString("zu_name"));
				media.setIs_exist(Util.decideFile(projectpath+file_path+file_name)); 
				list.add(media);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有媒体库出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
	}
	public int getMaxNum() {
		String sql = "select max(group_num+1) from xct_media";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try { 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("获取媒体库分组编号出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt,con);
		}
		return 0;
	}
	
	/**
	 * 根据媒体ID查询媒体
	 * @param mediaid
	 * @return
	 */
	
	public Media getMediaBy(String mediaid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 Media media= null;
		try{
			String sql="select * from xct_media,xct_zu where xct_media.zu_id=xct_zu.zu_id and  media_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, mediaid);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				media= new Media();
				media.setId(rs.getInt("id"));
				media.setMedia_id(rs.getString("media_id"));
				media.setMedia_title(rs.getString("media_title"));
				media.setMedia_type(rs.getString("media_type"));
				media.setZu_id(rs.getInt("zu_id"));
				media.setM_play_time(rs.getString("m_play_time"));
				media.setM_resolution(rs.getString("m_resolution"));
				media.setFile_name(rs.getString("file_name"));
				media.setFile_size(rs.getLong("file_size"));
				media.setFile_path(rs.getString("file_path"));
				media.setSlightly_img_name(rs.getString("slightly_img_name"));
				media.setSlightly_img_size(rs.getLong("slightly_img_size"));
				media.setSlightly_img_path(rs.getString("slightly_img_path"));
				media.setUserid(rs.getString("userid"));
				media.setCreate_date(rs.getString("create_date"));
				media.setLast_date(rs.getString("last_date"));
				media.setZuname(rs.getString("zu_name"));
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据媒体ID查询媒体出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return media;
	}
	public Media getMediaBy(Connection con,String mediaid){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 Media media= null;
		try{
			String sql="select * from xct_media where media_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, mediaid);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				media= new Media();
				media.setId(rs.getInt("id"));
				media.setMedia_id(rs.getString("media_id"));
				media.setMedia_title(rs.getString("media_title"));
				media.setMedia_type(rs.getString("media_type"));
				media.setZu_id(rs.getInt("zu_id"));
				media.setM_play_time(rs.getString("m_play_time"));
				media.setM_resolution(rs.getString("m_resolution"));
				media.setFile_name(rs.getString("file_name"));
				media.setFile_size(rs.getLong("file_size"));
				media.setFile_path(rs.getString("file_path"));
				media.setSlightly_img_name(rs.getString("slightly_img_name"));
				media.setSlightly_img_size(rs.getLong("slightly_img_size"));
				media.setSlightly_img_path(rs.getString("slightly_img_path"));
				media.setUserid(rs.getString("userid"));
				media.setCreate_date(rs.getString("create_date"));
				media.setLast_date(rs.getString("last_date"));
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据媒体ID查询媒体出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return media;
	}
	/**
	 * 根据媒体类型查询各个媒体的数量
	 * @param str
	 * @return
	 */
	public int getNumByMediaType(String mediatype){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="select count(*) from xct_media,xct_zu where xct_zu.zu_id=xct_media.zu_id "+mediatype;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){
					num=rs.getInt(1);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(mediatype).append(">>查询各个媒体的数量出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return num;
	}
	/**
	 * 根据条件查询媒体的大小  KB
	 * @param str
	 * @return
	 */
	public long getMediaSizeByStr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		long num=0;
		try{
			String sql=new StringBuffer().append("select SUM(file_size)  FROM xct_media ").append(str).toString();
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){
				num=rs.getInt(1);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>查询媒体的大小出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return num;
	}
}
