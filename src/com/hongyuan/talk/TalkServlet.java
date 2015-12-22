package com.hongyuan.talk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;

import com.hongyuan.core.WebServlet;

public class TalkServlet extends WebServlet {

	public TalkBean talkBean;
			
	@Override
	public void initPage(){
		Object userInfo = request.getSession().getAttribute("userInfo");
		if(userInfo!=null){
			talkPage();
		}else{
			loginPage();
		}
	}
	
	//进入登陆页面
	public void loginPage(){
		show("login.jsp");
	}
	
	//进入注册页面
	public void regPage(){
		show("reg.jsp");
	}
	
	//登录
	public void login() throws IOException{
		String userName=this.get("userName","");
		String password=this.get("password","");
		if(!"".equals(userName)&&!"".equals(password)){
			//提取用户信息
			Map<String,Object> userInfo=talkBean.getUserInfo(userName, password);
			if(userInfo!=null){
				//将用户信息存入session
				request.getSession().setAttribute("userInfo",userInfo);
				response.sendRedirect("./talkService.srv?action=talkPage");
				return;
			}
		}
		show("login.jsp");
	}
	
	//注册
	public void reg() throws IOException{
		String userName=this.get("userName","");
		String password=this.get("password","");
		String passConfirm=this.get("passConfirm","");
		if(!"".equals(userName)&&!"".equals(password)&&password.equals(passConfirm)){
			if(talkBean.saveUser(userName, password)){
				response.sendRedirect("./talkService.srv?action=loginPage");
				return;
			}
		}
		show("reg.jsp");
	}
	
	//进入聊天页面
	public void talkPage(){
		Object userInfo = request.getSession().getAttribute("userInfo");
		if(userInfo!=null){
			Map<String,Object> info=(Map<String,Object>)userInfo;
			this.put("userName",info.get("user_name"));
			show("talk.jsp");
			return;
		}
		show("login.jsp");
	}
	
	//发送消息
	public void sendMsg() throws UnsupportedEncodingException{
		String msg=this.get("message","");
		if(!"".equals(msg)){
			Event event=Event.createDataEvent("/message/world");
			
			Object userInfo = request.getSession().getAttribute("userInfo");
			if(userInfo!=null){
				Map<String,Object> info=(Map<String,Object>)userInfo;
				event.setField("userName",new String(info.get("user_name").toString().getBytes("utf-8"),"iso-8859-1"));
			}
			event.setField("message",new String(msg.getBytes("utf-8"),"iso-8859-1"));
			
			Dispatcher.getInstance().multicast(event);
		}
	}
}
