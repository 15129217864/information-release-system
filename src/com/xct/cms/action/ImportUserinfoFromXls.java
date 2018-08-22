package com.xct.cms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.excel.util.OpExcelUtils;
import com.xct.cms.dao.UsersDAO;

public class ImportUserinfoFromXls extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String xlsfile="";
		UsersDAO usersDAO=new UsersDAO();
		OpExcelUtils opexcelutils=new OpExcelUtils();
	    List<String>  list=	opexcelutils.getImportUserinfo(xlsfile);
		try {
			PrintWriter out=response.getWriter();
			boolean flag=usersDAO.addUserInfo(list);
			if(flag){
				out.print("导入成功！");
			}else {
				out.print("导入失败！");
			}
			out.print("");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
