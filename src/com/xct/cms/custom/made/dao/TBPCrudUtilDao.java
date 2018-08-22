package com.xct.cms.custom.made.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.xct.cms.custom.made.bean.BusBean;
import com.xct.cms.custom.made.bean.PlaneBean;
import com.xct.cms.custom.made.bean.TrainBean;
import com.xct.cms.db.DBConnection;

public class TBPCrudUtilDao extends DBConnection {// 火车，汽车，飞机时刻表

	public final static String TRAIN="http://www.yctelecom.com.cn/bmfw/bmfw_train_time1.asp";//盐城火车时刻表
	
	public final static String BUS="http://www.yctelecom.com.cn/bmfw/bmfw_bus2.asp";//盐城汽车时刻表
	
	public final static String OUTPLANE="http://www.yccas.com/hangbanxx_1.asp?wz=%B3%F6%B8%DB%BA%BD%B0%E0";//盐城飞机时刻（出港航班）
	
	public final static String INPLANE="http://www.yccas.com/hangbanxx_1.asp?wz=%BD%F8%B8%DB%BA%BD%B0%E0";//盐城飞机时刻（进港航班）

	public final static String HOTELINSTR="http://www.9tour.cn/guoqing/104489/";//	酒店介绍，加酒店图片混拼显示
	
	public boolean isExistTable(String tablename) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlString = "select COUNT(*) as onetable from sysobjects  where name='"+ tablename + "'";
				
		try {
			pstmt = con.prepareStatement(sqlString);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("onetable") == 0)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResources(rs, pstmt, con);
		}
		return true;
	}
	public Set<String> SelectTrainDaoZhan() {
		Set<String>list=new LinkedHashSet<String>();
		if (!isExistTable("yc_train")) {
            String sql="select distinct daozhan from yc_train ";
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("daozhan"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_train 表不存在");
		}
		return list;
	}

	
	public List<TrainBean> SelectTrain(String param) {// daozhan='扬州'
		List<TrainBean>list=new ArrayList<TrainBean>();
		if (!isExistTable("yc_train")) {
            String sql="select * from yc_train where "+param;
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(new TrainBean(rs.getString("chenumber"),rs.getString("shifazhang"),rs.getString("zhongdianzhan"),
							rs.getString("cheliangtype"),rs.getString("fazhan"),rs.getString("fashi"),
							rs.getString("daozhan"),rs.getString("daoshi"),rs.getString("tingzhan"), rs.getString("lishi"), 
							rs.getString("yingzuo"),rs.getString("ruanzuo"),rs.getString("yingwozhong"),rs.getString("ruanwoxia")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_train 表不存在");
		
		}
		return list;
	}
	
	public List<String> SelectBusTerminus(String param) {//到站,通过省查看市
		List<String>list=new ArrayList<String>();
		if (!isExistTable("yc_bus")) {
            String sql="select distinct terminus from yc_bus where province like '%"+param+"%'";
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
//    			System.out.println(sql);
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("terminus"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_bus 表不存在");
		}
		return list;
	}
	
	public List<String> SelectBusProvince() {//省份
		List<String>list=new ArrayList<String>();
		if (!isExistTable("yc_bus")) {
            String sql="select distinct province from yc_bus";
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("province"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_bus 表不存在");
		}
		return list;
	}
	
	
	public List<BusBean> SelectBus(String param) {//terminus like '%扬州%'
		List<BusBean>list=new ArrayList<BusBean>();
		if (!isExistTable("yc_bus")) {
            String sql="select * from yc_bus where "+param;
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(new BusBean(rs.getString("province"), rs.getString("terminus"), rs.getString("bustype"), rs.getString("kilometre"), rs.getString("bushour")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_bus 表不存在");
		}
		return list;
	}
	
	public Set<String> SelectPlaneAirline() {
		Set<String>list=new LinkedHashSet<String>();
		if (!isExistTable("yc_plane")) {
            String sql="select distinct airline from yc_plane";
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("airline").replace("盐城", "").replace("-", "").replace("—", ""));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_bus 表不存在");
		}
		return list;
	}
	
//  airline like '北京%' //以北京开头
//	airline like '%北京' //以北京结尾
	public List<PlaneBean> SelectPlane(String param) {
		List<PlaneBean>list=new ArrayList<PlaneBean>();
		if (!isExistTable("yc_plane")) {
            String sql="select * from yc_plane where "+param;
            Connection con = super.getConection();
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(new PlaneBean(rs.getString("airline"),rs.getString("flight"),rs.getString("planetype"),
							rs.getString("planecycle"),rs.getString("flightstarttime"),rs.getString("flightendtime"),rs.getInt("inorout")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				returnResources(rs, pstmt, con);
			}
		}else {
//			System.out.println("------------yc_plane 表不存在");
		}
		return list;
	}
}
