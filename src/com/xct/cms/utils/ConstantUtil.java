package com.xct.cms.utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.mina.util.ConcurrentHashSet;

import com.xct.cms.domin.ClientCmd;
import com.xct.cms.domin.UdpNatClient;

public class ConstantUtil {//ConstantUtil.HTTP
	
	static Logger logger = Logger.getLogger(ConstantUtil.class);
	
	public static Map<String, UdpNatClient>UDPNATMAP=new ConcurrentHashMap<String, UdpNatClient>();
	
	public static Map<String, ConcurrentHashSet<ClientCmd>>CLIENTCMDMAP=new ConcurrentHashMap<String, ConcurrentHashSet<ClientCmd>>();
	
	public static String HTTP="HTTP";
	public static String UDP="UDP";
	public static String PNP="PNP";
	
    public static void changeClientCmd(String ipmac,String cmd){
		
		if(System.getProperty("SENDTYPE").equals(ConstantUtil.PNP)){
			String key=ipmac;//new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString();
			if(null!=ConstantUtil.CLIENTCMDMAP&&ConstantUtil.CLIENTCMDMAP.size()>0){
			    if(null!=ConstantUtil.CLIENTCMDMAP.get(key)){
	   			   for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
	   				  Set<ClientCmd> clientcmdset=entry.getValue();
				      for(ClientCmd clientcmd:clientcmdset){
	   					  if(clientcmd.getIpmac().equals(ipmac)&&DESPlusUtil.Get().decrypt(clientcmd.getCmd()).startsWith(cmd)){
	   						 logger.info("删除已经执行过的指令----->"+clientcmd.getIpmac()+"***>"+DESPlusUtil.Get().decrypt(clientcmd.getCmd()));
	   						 clientcmd.setIsoperator(1);//设置已经返回执行指令的
	   						 ConstantUtil.CLIENTCMDMAP.get(key).remove(clientcmd);
	   						 break;
	   					  }
	   				  }
	   			   }
	   			  //===================================================
//	   			  for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
//	   				  Set<ClientCmd> clientcmdset=entry.getValue();
//	   				  logger.info("clientcmdset######【"+entry.getKey()+"】#####>"+clientcmdset.size());
//				      for(ClientCmd clientcmd:clientcmdset){
//   						 logger.info("还未执行的指令%%%%%%%%%%-----------2--->"+clientcmd);
//	   				  }
//	   			   }
		        }
			}
		}
	}
}
