package com.hongyuan.talk.cfg;

public enum Sql {
	//提取用户信息SQL语句
	GET_USERINFO("select id,user_name,password from user where user_name=:userName and password=md5(:password)"),
	
	//保存用户信息SQL语句
	SAVE_USER("insert into user(user_name,password) values(:userName,md5(:password))");
	
	private final String value;
	private Sql(String value){
		this.value=value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
