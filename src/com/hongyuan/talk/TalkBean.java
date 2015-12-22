package com.hongyuan.talk;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hongyuan.core.DBUtil;
import com.hongyuan.talk.cfg.Sql;

public class TalkBean{

	/**
	 * 提取用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public Map<String,Object> getUserInfo(final String userName,final String password) {

		try {
			List<Map<String,Object>> userInfo=DBUtil.select(Sql.GET_USERINFO,new HashMap<String,Object>(){{
				put("userName",userName);
				put("password",password);
			}});
			if(userInfo!=null&&userInfo.size()==1){
				return userInfo.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean saveUser(final String userName,final String password){
		try {
			int count=DBUtil.insert(Sql.SAVE_USER,new HashMap<String,Object>(){{
				put("userName",userName);
				put("password",password);
			}});
			if(count==1){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
