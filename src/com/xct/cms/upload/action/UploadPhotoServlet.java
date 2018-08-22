package com.xct.cms.upload.action;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Media;
import com.xct.cms.domin.Users;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.ImageUtil;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;

/**
 * 文件上传实例
 *
 */
public class UploadPhotoServlet extends HttpServlet {
	Logger logger = Logger.getLogger(FileUploadAction.class);
	private static final long serialVersionUID = 1L;
       
    public UploadPhotoServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  Users user = (Users) request.getSession().getAttribute("lg_user");
	  if(user!=null){
		String fileexts_video=",mpg,wmv,rm,avi,vob,mpa,asf,mov,rmvb,mpeg,mvb,tp,3gp,ts,mp4,asx,flv,mkv,dat";
		String fileexts_img=",jpg,gif,bmp,png";
		String	fileexts_text=",txt";
		String	fileexts_sound=",mp3,wav,wma";
		String	fileexts_flash=",swf";
		String	fileexts_ppt=",ppt,pptx";
		String	fileexts_pdf=",pdf";
		String	fileexts_word=",doc,docx";
		String	fileexts_excel=",xls,xlsx";
		String	fileexts_web=",html,htm";
		String	fileexts_exe=",exe";
		String filetype="未知";
		UtilDAO utildao = new UtilDAO();
		LogsDAO logsdao= new LogsDAO();
		Map<String ,Object> formmap= new HashMap<String ,Object>();
		String project_path = FirstStartServlet.projectpath;// 项目路径 
		//创建磁盘文件工厂
		DiskFileItemFactory fac = new DiskFileItemFactory();    
		//创建servlet文件上传组件
		ServletFileUpload upload = new ServletFileUpload(fac);   
		
		upload.setSizeMax(-1);//不限制上传的大小
		upload.setFileSizeMax(-1);//不限制上传的大小
		
		upload.setHeaderEncoding("utf-8");
		//文件列表
		List fileList = null;    
        //解析request从而得到前台传过来的文件
		try {    
            fileList = upload.parseRequest(request);    
        } catch (FileUploadException ex) {    
        	logger.info("上传文件失败！===》用户取消上传文件！");
            return;    
        } 
        //保存后的文件名
        String imageName ="";
        String fieldName="";
        //便利从前台得到的文件列表
        Iterator<FileItem> it = fileList.iterator();  
        int file_num=0;
        while(it.hasNext()){    
            FileItem item =  it.next();   
            //如果不是普通表单域，当做文件域来处理
            if(!item.isFormField()){
            	String itemname=item.getName();
            	if(itemname.indexOf(".")!=-1){
					filetype=itemname.substring(itemname.lastIndexOf(".")+1).toLowerCase();
					fieldName=itemname.substring(0,itemname.lastIndexOf("."));
				}
            	Media newmedia= new Media(); 
            	long filezize=item.getSize();//文件大小
            	String TimeNum = UtilDAO.getNowtime("yyyyMMddHHmmssSSS")+(int)(Math.random()*10000);
            	imageName = TimeNum+"."+filetype;
            	newmedia.setMedia_title(fieldName);
				newmedia.setFile_name(imageName);
				newmedia.setFile_size(filezize);
            	///////////////////////////////////保存的文件路径
				String uploadPath="";
            	if(fileexts_img.indexOf(filetype)!=-1){  ///图片
        			uploadPath=project_path+"mediafile\\img\\";
        			newmedia.setFile_path("/mediafile/img/");
        			newmedia.setMedia_type("IMAGE");
        		}else if(fileexts_video.indexOf(filetype)!=-1){   //视频
        			uploadPath=project_path+"mediafile\\video\\";
        			newmedia.setFile_path("/mediafile/video/");
        			newmedia.setMedia_type("MOVIE");
        		}else if(fileexts_flash.indexOf(filetype)!=-1){ //FLASH
        			uploadPath=project_path+"mediafile\\flash\\";
        			newmedia.setFile_path("/mediafile/flash/");
        			newmedia.setMedia_type("FLASH");
        		}else if(fileexts_sound.indexOf(filetype)!=-1){ ///声音
        			uploadPath=project_path+"mediafile\\sound\\";
        			newmedia.setFile_path("/mediafile/sound/");
        			newmedia.setMedia_type("SOUND");
        		}else if(fileexts_ppt.indexOf(filetype)!=-1){ //PPT
        			uploadPath=project_path+"mediafile\\office\\ppt\\";
        			newmedia.setFile_path("/mediafile/office/ppt/");
        			newmedia.setMedia_type("PPT");
        		}else if(fileexts_word.indexOf(filetype)!=-1){ ///WORD
        			uploadPath=project_path+"mediafile\\office\\word\\";
        			newmedia.setFile_path("/mediafile/office/word/");
        			newmedia.setMedia_type("WORD");
        		}else if(fileexts_excel.indexOf(filetype)!=-1){ ///EXCEL
        			uploadPath=project_path+"mediafile\\office\\excel\\";
        			newmedia.setFile_path("/mediafile/office/excel/");
        			newmedia.setMedia_type("EXCEL");
        			
        		}else if(fileexts_pdf.indexOf(filetype)!=-1){ //PDF
        			uploadPath=project_path+"mediafile\\pdf\\";
        			newmedia.setFile_path("/mediafile/pdf/");
        			newmedia.setMedia_type("PDF");
        			
        		}else if(fileexts_text.indexOf(filetype)!=-1){ ///TEXT
        			uploadPath=project_path+"mediafile\\text\\";
        			newmedia.setFile_path("/mediafile/text/");
        			newmedia.setMedia_type("TEXT");
        		}else if(fileexts_web.indexOf(filetype)!=-1){ ///WEB
        			uploadPath=project_path+"mediafile\\web\\";
        			newmedia.setFile_path("/mediafile/web/");
        			newmedia.setMedia_type("WEB");
        		}else if(fileexts_exe.indexOf(filetype)!=-1){ ///EXE
        			uploadPath=project_path+"mediafile\\exe\\";
        			newmedia.setFile_path("/mediafile/exe/");
        			newmedia.setMedia_type("EXE");
        		}else{   ///未知文件
        			uploadPath=project_path+"mediafile\\other\\";
        			newmedia.setFile_path("/mediafile/other/");
        			newmedia.setMedia_type("OTHER");
        		}
            	
            	formmap.put("file"+file_num, newmedia);
        		//文件存放的目录
        		File tempDirPath =new File(uploadPath);
        		
        		if(!tempDirPath.exists()){
        			tempDirPath.mkdirs();
        		}
        		
//              imageName = new Date().getTime()+Math.random()*10000+item.getName();
                //如下这样做会有问题，图片会丢失
//            	imageName =new SimpleDateFormat("yyyyMMddHHmmsssss").format(new Date())+itemname.substring(itemname.lastIndexOf("."));
            	BufferedInputStream in = new BufferedInputStream(item.getInputStream());   
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(tempDirPath+"\\"+imageName)));      
                Streams.copy(in, out, true);
        		/////////////////////////////////////////////////////
            	
				if(fileexts_img.indexOf(filetype)!=-1){  ///图片
					
					//ImageUtil.FormatJpg(uploadPath+imageName);//针对高清图片等比例裁剪，裁剪后比较高清,但会有毛刺
					
					boolean bool=UtilDAO.createThumbnailImg(uploadPath+imageName,TimeNum);  ///生成图片文件缩略图
					if(bool){
						newmedia.setSlightly_img_name(TimeNum+"_slightly.jpg");
						newmedia.setSlightly_img_path("/mediafile/img/");
						newmedia.setSlightly_img_size(100);
					}else{
						newmedia.setSlightly_img_name("img_slightly.gif");
						newmedia.setSlightly_img_path("/mediafile/");
						newmedia.setSlightly_img_size(0);
					}
					
				}else if(fileexts_video.indexOf(filetype)!=-1){   //视频
					if("rmvb".equals(filetype) || "rm".equals(filetype)||"mvb".equals(filetype) ||"flv".equals(filetype)){
						newmedia.setSlightly_img_name("video_slightly.gif");
						newmedia.setSlightly_img_path("/mediafile/");
						newmedia.setSlightly_img_size(0);
					}else{
						UtilDAO.createThumbnailVideo(uploadPath+imageName,TimeNum);///生成视频文件缩略图
						newmedia.setSlightly_img_name(TimeNum+"_slightly.jpg");
						newmedia.setSlightly_img_path("/mediafile/video/");
						newmedia.setSlightly_img_size(100);
					}
				}else if(fileexts_flash.indexOf(filetype)!=-1){ //FLASH
					newmedia.setSlightly_img_name("flash_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_sound.indexOf(filetype)!=-1){ ///声音
					newmedia.setSlightly_img_name("sound_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_ppt.indexOf(filetype)!=-1){ //PPT
					newmedia.setSlightly_img_name("ppt_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_pdf.indexOf(filetype)!=-1){ //PDF
					newmedia.setSlightly_img_name("pdf_slightly.jpg");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_word.indexOf(filetype)!=-1){ ///WORD
					newmedia.setSlightly_img_name("word_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_excel.indexOf(filetype)!=-1){ ///EXCEL
					newmedia.setSlightly_img_name("excel_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_text.indexOf(filetype)!=-1){ ///TEXT
					newmedia.setSlightly_img_name("text_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else if(fileexts_web.indexOf(filetype)!=-1){ ///WEB
					newmedia.setSlightly_img_name("web_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}
				else if(fileexts_exe.indexOf(filetype)!=-1){ ///WEB
					newmedia.setSlightly_img_name("exe_slightly.gif");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}else{   ///未知文件
					newmedia.setSlightly_img_name("no_img.jpg");
					newmedia.setSlightly_img_path("/mediafile/");
					newmedia.setSlightly_img_size(0);
					
				}
				file_num++;
            }else{//////得到普通表单数据
            	String fidilName=item.getFieldName();
				String content=item.getString();
				formmap.put(fidilName, UtilDAO.getGBK(content));
			}
          
        }
////////////////////////////
        PrintWriter out = null;
		try {
			out = encodehead(request, response);
		} catch (IOException e) {
			logger.info("上传文件失败！===》" + e.getMessage());
			e.printStackTrace();
		}
		//这个地方不能少，否则前台得不到上传的结果
		out.write("1");
		out.close(); 
        ////////////////////
      
        String groupid="".equals((String)formmap.get("group_id"))?"1":(String)formmap.get("group_id");
        String group_num="".equals((String)formmap.get("group_num"))?"0":(String)formmap.get("group_num");
        
        DBConnection dbc= new DBConnection();
        Connection con = dbc.getConection();
        
        for(int i=0;i<file_num;i++){
        		Media media= new Media(); 
				media.setZu_id((Integer.parseInt(groupid)));
				media.setGroup_num((Integer.parseInt(group_num)));
				Object mmm=formmap.get("file"+i);
				if(mmm!=null&&!"".equals(mmm.toString())){
					Media media1=(Media)mmm;
					media.setMedia_title(media1.getMedia_title());
					media.setFile_name(media1.getFile_name());
					media.setFile_path(media1.getFile_path());
					media.setFile_size(media1.getFile_size());
					media.setMedia_type(media1.getMedia_type());
					media.setSlightly_img_name(media1.getSlightly_img_name());
					media.setSlightly_img_path(media1.getSlightly_img_path());
					media.setSlightly_img_size(media1.getSlightly_img_size());
				}
				if(!"".equals(media.getMedia_title()) && !"".equals(media.getZu_id()) &&media.getFile_name()!=null ){
					int m_play_time=8000;
					if("MOVIE".equals(media.getMedia_type())){
						//String move_name=media.getFile_name();
						//String move_type=move_name.substring(move_name.lastIndexOf(".")+1).toLowerCase();
						//if("flv".equals(move_type)){
							//m_play_time= (int)Util.getMplayerLength(project_path+media.getFile_path()+move_name) * 1000;
						//}else{
							m_play_time= (int)Util.getMplayerLength(project_path+media.getFile_path()+media.getFile_name()) * 1000;	
						//}
					}
					Map<String ,String> mediamap=UtilDAO.getMap();
					String nowtime1=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
					String meidaid="media."+ UUID.randomUUID().toString();
					mediamap.put("media_id", meidaid);
					mediamap.put("media_title", media.getMedia_title());
					mediamap.put("media_type", media.getMedia_type());
					mediamap.put("zu_id", media.getZu_id()+"");
					mediamap.put("group_num", media.getGroup_num()+"");
					mediamap.put("m_play_time", m_play_time+"");
					mediamap.put("file_name", media.getFile_name());
					mediamap.put("file_size", media.getFile_size()+"");
					mediamap.put("file_path", media.getFile_path());
					mediamap.put("slightly_img_name", media.getSlightly_img_name());
					mediamap.put("slightly_img_path", media.getSlightly_img_path());
					mediamap.put("slightly_img_size", media.getSlightly_img_size()+"");
					mediamap.put("create_date", nowtime1);
					mediamap.put("last_date", nowtime1);
					mediamap.put("userid", user.getLg_name());
					
					utildao.saveinfo(con,mediamap, "xct_media");
					
					logsdao.addlogs1(con,user.getName(), "用户【"+user.getName()+"】上传了【"+media.getMedia_type()+"】文件：【"+media.getMedia_title()+"】", 1);
			}
		}
        dbc.returnResources(con);
	  }
	}
	
	/**
	 * Ajax辅助方法 获取 PrintWriter
	 * @return
	 * @throws IOException 
	 * @throws IOException 
	 * request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
	 */
	private PrintWriter encodehead(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		return response.getWriter();
	}
}
