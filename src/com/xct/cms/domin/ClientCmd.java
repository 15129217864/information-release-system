package com.xct.cms.domin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.util.ConcurrentHashSet;

import com.xct.cms.utils.ConstantUtil;

public class ClientCmd {

	private String ipmac;
	private String cmd;
	private int iscancel=0;//0 为 指令可用，1 为 指令超时取消
	private int isoperator=0;//0 为 指令未执行，1 为 指令已执行
	private long isonlinetime = 0l; // 在线时间

	public ClientCmd() {
		super();
	}
	
	public ClientCmd(String ipmac, String cmd, int iscancel, int isoperator,long isonlinetime) {
			
		super();
		this.ipmac = ipmac;
		this.cmd = cmd;
		this.iscancel = iscancel;
		this.isoperator = isoperator;
		this.isonlinetime = isonlinetime;
	}

	public static void main(String []args){
		
		  Map<String, ConcurrentHashSet<ClientCmd>>CLIENTCMDMAP=new ConcurrentHashMap<String, ConcurrentHashSet<ClientCmd>>();
		
		
		ConcurrentHashSet<ClientCmd>set=new ConcurrentHashSet<ClientCmd>();
		long one=System.currentTimeMillis();
		System.out.println("one=====>"+one);
		set.add(new ClientCmd("123", "lv00028", 0,0,one));
		
		long two=System.currentTimeMillis()+100;
		System.out.println("two=====>"+two);
		set.add(new ClientCmd("123", "lv00028", 0,0,two));
		System.out.println(set.size());
		
		long three=System.currentTimeMillis()+1000;
		System.out.println("three=====>"+three);
		
		CLIENTCMDMAP.put("123", set);
		
		ClientCmd clientcmd= new ClientCmd("123", "lv00028", 0,0,three);
		
		
		 for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : CLIENTCMDMAP.entrySet()){
			 
			    ConcurrentHashSet<ClientCmd> clientcmdset=entry.getValue();
		
				for(ClientCmd clientcmdtmp:clientcmdset){
					if(clientcmd.equals(clientcmdtmp)){
						CLIENTCMDMAP.get(entry.getKey()).remove(clientcmd);
						break;
					}
				}
		 }
		 
		 for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : CLIENTCMDMAP.entrySet()){
			 System.out.println("ClientCmd======>"+entry.getKey()+"__"+entry.getValue());
		 }
		
//		for(ClientCmd clientcmdtmp:set){
//			
//				System.out.println("ClientCmd======>"+clientcmdtmp);
//			
//		}
//		for(ClientCmd clientcmdtmp:set){
//			if(clientcmd.equals(clientcmdtmp)){
//				System.out.println("four======>"+clientcmd.getIsonlinetime());
//			}
//		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClientCmd) {
			ClientCmd temp = (ClientCmd) obj;
			if (temp.toString().equalsIgnoreCase(this.toString()))
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		return new StringBuffer().append(ipmac).append("_").append(cmd).toString();
//		return new StringBuffer().append(ipmac).append("_").append(cmd).append("_").append(iscancel).append("_").append(isoperator).toString();
	}


	public int getIsoperator() {
		return isoperator;
	}

	public void setIsoperator(int isoperator) {
		this.isoperator = isoperator;
	}



	public int getIscancel() {
		return iscancel;
	}

	public void setIscancel(int iscancel) {
		this.iscancel = iscancel;
	}

	public String getIpmac() {
		return ipmac;
	}

	public void setIpmac(String ipmac) {
		this.ipmac = ipmac;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public long getIsonlinetime() {
		return isonlinetime;
	}

	public void setIsonlinetime(long isonlinetime) {
		this.isonlinetime = isonlinetime;
	}

}
