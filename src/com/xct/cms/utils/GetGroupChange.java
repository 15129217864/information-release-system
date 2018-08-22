package com.xct.cms.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.xy.dao.ConnectionFactory;
import com.xct.cms.xy.domain.ClientIpAddress;
import com.xct.cms.xy.domain.Group;

public class GetGroupChange {

	public void tree(Collection co, int pid, Element elment,
			List<ClientIpAddress> clientipaddresslist) {// 递归

		Element itemelement = null;
		Iterator<Group> it = co.iterator();
		while (it.hasNext()) {
			Group group = it.next();
			if (group.getZu_pth() == pid) {

				int idtemp = group.getZu_id();
				String id = idtemp + "";
				itemelement = elment.addElement("item");

				if (pid == 1) {// 父目录
					itemelement.addAttribute("text", UrlEncodeUtil.englishSysCharTo(group.getZu_name()))
							.addAttribute("id", id).addAttribute("im0", "folderClosed.gif").addAttribute("im1", "folderOpen.gif").addAttribute("im2", "folderClosed.gif");;
					for (ClientIpAddress clientipaddress : clientipaddresslist) {

						if (clientipaddress.getCntzuid().equals(id)) {
							Element itemelent2 = itemelement.addElement("item");
							itemelent2.addAttribute("text",
									UrlEncodeUtil.englishSysCharTo(clientipaddress.getCntname())).addAttribute(
									"id", clientipaddress.getCntip());
						}
					}

				} else { // 子目录
					if(idtemp==1){
						itemelement.addAttribute("text", UrlEncodeUtil.englishSysCharTo(group.getZu_name()))
						.addAttribute("id", id).addAttribute("select",
						"1").addAttribute("open", "1").addAttribute("im0", "folderClosed.gif").addAttribute("im1", "folderOpen.gif").addAttribute("im2", "folderClosed.gif");;
					}else{
						itemelement.addAttribute("text", UrlEncodeUtil.englishSysCharTo(group.getZu_name()))
						.addAttribute("id", id).addAttribute("im0", "folderClosed.gif").addAttribute("im1", "folderOpen.gif").addAttribute("im2", "folderClosed.gif");
					}
					for (ClientIpAddress clientipaddress : clientipaddresslist) {

						if (clientipaddress.getCntzuid().equals(id)) {
							Element itemelent2 = itemelement.addElement("item");
							itemelent2.addAttribute("text",
									UrlEncodeUtil.englishSysCharTo(clientipaddress.getCntname())).addAttribute(
									"id", clientipaddress.getCntip());
						}
					}

				}

				tree(co, idtemp, itemelement, clientipaddresslist);
			}
		}
	}

	
	public void changeProjectXml(Collection co, int pid,List<ClientIpAddress> clientipaddressmap) {
		
		Document document = DocumentHelper.createDocument();
		Element elment = document.addElement("tree");
		elment.addAttribute("id", "0");
		tree(co, pid, elment, clientipaddressmap);
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			String webpath=FirstStartServlet.projectpath;
			XMLWriter output = new XMLWriter(new FileWriter(new File(webpath+"admin/checkboxtree/tree.xml")), format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void tree1(Map<Integer,Terminal> map, int pid, Element elment,
			List<ClientIpAddress> clientipaddresslist) {// 递归

		Element itemelement = null;
		String zuname="";
		Terminal terminal = new Terminal();
		
		for (Map.Entry<Integer, Terminal> entry : map.entrySet()) {
			terminal=entry.getValue();
			
			if (terminal.getZu_pth() == pid) {
				int idtemp = terminal.getZu_id();
				zuname=UrlEncodeUtil.englishSysCharTo(terminal.getZu_name());
				String id = idtemp + "";
				itemelement = elment.addElement("item");
				if (pid == 1) {// 父目录
					//System.out.println("父目录==="+zuname+"======"+terminal.getZu_type());
					itemelement.addAttribute("text", zuname)
							.addAttribute("id", id).addAttribute("im0", "folderClosed.gif").addAttribute("im1", "folderOpen.gif").addAttribute("im2", "folderClosed.gif");;
					if(terminal.getZu_type()==1){
						for (ClientIpAddress clientipaddress : clientipaddresslist) {
							if (clientipaddress.getCntzuid().equals(id)) {
								Element itemelent2 = itemelement.addElement("item");
								itemelent2.addAttribute("text",UrlEncodeUtil.englishSysCharTo(clientipaddress.getCntname()))
										  .addAttribute("id", new StringBuffer(clientipaddress.getCntip()).append("_").append(clientipaddress.getCntmac()).toString());
										
							}
						}
					}
				}else { // 子目录
					//System.out.println("子目录===="+zuname+"====="+terminal.getZu_type());
					if(idtemp==1){
						itemelement.addAttribute("text", zuname)
						.addAttribute("id", id).addAttribute("select",
						"1").addAttribute("open", "1").addAttribute("im0", "folderClosed.gif").addAttribute("im1", "folderOpen.gif").addAttribute("im2", "folderClosed.gif");;
					}else{
						itemelement.addAttribute("text",zuname)
						.addAttribute("id", id).addAttribute("im0", "folderClosed.gif").addAttribute("im1", "folderOpen.gif").addAttribute("im2", "folderClosed.gif");
					}
					if(terminal.getZu_type()==1){
						for (ClientIpAddress clientipaddress : clientipaddresslist) {
							if (clientipaddress.getCntzuid().equals(id)) {
								Element itemelent2 = itemelement.addElement("item");
								itemelent2.addAttribute("text",
										UrlEncodeUtil.englishSysCharTo(clientipaddress.getCntname())).addAttribute(
										"id", new StringBuffer(clientipaddress.getCntip()).append("_").append(clientipaddress.getCntmac()).toString());
							}
						}
					}
				}

				tree1(map, idtemp, itemelement, clientipaddresslist);
			}
		}
	}
public void getProjectXmlByUsername(String username, int pid,List<ClientIpAddress> clientipaddressmap,String treename) {
		String webpath=FirstStartServlet.projectpath;
		Util.deletesignFile(webpath+"admin/checkboxtree/tree_"+username+".xml");
	
		Map<Integer,Terminal> map=TerminalDAO.getZuListByUsername(username);
		Terminal terminal = map.get(1);
		terminal.setZu_pth(0);
		map.put(1, terminal);
		Document document = DocumentHelper.createDocument();
		Element elment = document.addElement("tree");
		elment.addAttribute("id", "0");
		tree1(map, pid, elment, clientipaddressmap);
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			 ///webpath+"admin/checkboxtree/tree_"+username+".xml"
			XMLWriter output = new XMLWriter(new FileWriter(new File(webpath+"admin/checkboxtree/"+treename)), format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void changeProjectXml(Collection co, int pid,List<ClientIpAddress> clientipaddressmap,String treefile) {
			
		Document document = DocumentHelper.createDocument();
		Element elment = document.addElement("tree");
		elment.addAttribute("id", "0");
		tree(co, pid, elment, clientipaddressmap);
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			String webpath=FirstStartServlet.projectpath;
			XMLWriter output = new XMLWriter(new FileWriter(new File(webpath+"admin/checkboxtree/"+treefile)), format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ConnectionFactory connectionfactory = new ConnectionFactory();
		GetGroupChange getgroupchange = new GetGroupChange();
		getgroupchange.getProjectXmlByUsername("999", 0, connectionfactory
				.getAllIpAddress(""),"tree_999.xml");
	}

	/*
	 * <tree id="0"> <item text="Books" id="books" open="1" im0="tombs.gif"
	 * im1="tombs.gif" im2="iconSafe.gif" call="1" select="1"> <item
	 * text="Mystery &amp; Thrillers" id="mystery" im0="folderClosed.gif"
	 * im1="folderOpen.gif" im2="folderClosed.gif"> </item> <item
	 * text="Magazines" id="magazines" im0="folderClosed.gif"
	 * im1="folderOpen.gif" im2="folderClosed.gif"> <item text="Sport"
	 * id="mag_sp" im0="folderClosed.gif" im1="folderOpen.gif"
	 * im2="folderClosed.gif"></item> </item> </tree>
	 */

	public static String TOMBSGIF = "tombs.gif"; // im0,im1

	public static String ICONSAFEGIF = "iconSafe.gif"; // im2

	// ---------------------------------------------------------以上---------父目录
	public static String FOLDERCLOSEDGIF = "folderClosed.gif"; // im0

	public static String FOLDEROPENGIF = "folderOpen.gif"; // im1
	// ---------------------------------------------------------以上---------子目录

	public static String BOOKGIF = "book.gif"; // im1

	public static String BOOK_TITELGIF = "book_titel.gif"; // im0

}
