package com.xct.cms.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Users;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.HttpRequest;
import com.xct.cms.utils.UtilDAO;
import com.xct.cms.xy.dao.GetAllClinetDao;

public class ClearSelectedProjectAction extends Action {
	
	Logger logger = Logger.getLogger(ClearSelectedProjectAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		String cmd = request.getParameter("cmd");
		String opp = request.getParameter("opp") == null ? "" : request.getParameter("opp");
		String resips = request.getParameter("checkips") == null ? "!": request.getParameter("checkips");
		String resip[] = resips.replaceAll("\\s+","").split("!");
		Map<String ,String>	ipmacmap=new GetAllClinetDao().getClientIpMac();
		DBConnection dbconn=new DBConnection();
		Connection conn=dbconn.getConection();
		UtilDAO utildao=new UtilDAO();
		Document document;
		OutputFormat format;
		XMLWriter output;
	    for (int i = 1; i < resip.length; i++) {
		  if(null!=ipmacmap&&!ipmacmap.isEmpty()){
			for(Map.Entry<String, String>m:ipmacmap.entrySet()){
				String []clientarray=resip[i].split("_");
				if(clientarray[1].equals(m.getKey())){
					//删除数据库所有此终端的节目,根据MAC地址
					utildao.deleteinfo(conn,"program_delid", m.getKey(), "xct_JMhistory");
					document=DocumentHelper.createDocument();
					document.addElement("project");	
					try {
							format = OutputFormat.createPrettyPrint();
							format.setEncoding("GBK");
							output = new XMLWriter(new FileWriter(new File(
									new StringBuffer(FirstStartServlet.projectpath).append("serverftp\\program_file\\").append(m.getKey()).append(".xml").toString())), format);
							output.write(document);
							output.close();
						} catch (IOException e) {
							logger.error(new StringBuffer().append("初始化节目，生成空的节目单失败-->").append(e.getMessage()).toString());
							e.printStackTrace();
						}
					}
				}
			}
		}
		dbconn.returnResources(conn);
		request.setAttribute("opp", opp);
		request.setAttribute("cmd", cmd);
		request.setAttribute("checkips", resips);
		return mapping.findForward("clearOk");
	}

}
