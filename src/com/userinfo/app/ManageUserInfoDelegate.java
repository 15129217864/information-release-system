package com.userinfo.app;

@javax.jws.WebService(targetNamespace = "http://app.userinfo.com/", serviceName = "ManageUserInfoService", portName = "ManageUserInfoPort", wsdlLocation = "WEB-INF/wsdl/ManageUserInfoService.wsdl")
public class ManageUserInfoDelegate {

	com.userinfo.app.ManageUserInfo manageUserInfo = new com.userinfo.app.ManageUserInfo();

	public int addUserInfo(String lg_name,String lg_password,String name,String email) {
		return manageUserInfo.addUserInfo(lg_name, lg_password, name, email);
	}

	public int updateUserInfo(String lg_name,String lg_password,String name,String email) {
		return manageUserInfo.updateUserInfo(lg_name, lg_password, name, email);
	}

	public int deleteUserInfo(String lg_name) {
		return manageUserInfo.deleteUserInfo(lg_name);
	}

}