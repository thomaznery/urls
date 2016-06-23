package br.com.urls.util;

public class App {
	
	public static ManagerURL managerUrl;
	public static ManagerUser managerUser;
	
	static{
		managerUrl = new ManagerURL();
		managerUser = new ManagerUser();
	}
}
