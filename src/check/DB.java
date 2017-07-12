package check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.mysqlUtils;

public class DB {

	public static void main(String[] args) {
//		System.out.println(get( "dfasfsad"));
		System.out.println(add("sad", "1"));
		System.out.println("签到绑定微信1234567890".length());
	}

	public static String add(String CardID, String WeixinID) {
		String result = "";
		mysqlUtils mysql = new mysqlUtils();
		try {
			// 先进行删除
			String sql = "delete from userbind where WeixinID=?";
			List<Object> params = new ArrayList<Object>();
			params.add(WeixinID);
			mysql.addDeleteModify(sql, params);
			// 再进行添加，确保不会重复
			List<Object> params222 = new ArrayList<Object>();
			params222.add(CardID);
			params222.add(WeixinID);
			sql = "insert into userbind  (CardID,WeixinID) values(?,?)";
			mysql.addDeleteModify(sql, params222);
			
			result="您好！\n"
					+ WeixinID+"-"+CardID+"\n"
					+ "绑定成功!";
		} catch (Exception e) {
			System.out.println(e);
			result = "您好~\n" + "绑定发生了意外~\n" + "请重试或者联系管理员 xjtushilei@foxmail.com";
		} finally {
			mysql.closeconnection();
		}
		return result;
	}


	public static String get(String WeixinID) {
		String result = "";
		mysqlUtils mysql = new mysqlUtils();
		try {
			String sql = "select * from userbind where WeixinID=?";
			List<Object> params = new ArrayList<Object>();
			params.add(WeixinID);
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			list=mysql.returnMultipleResult(sql, params);
			if (list.size()==0) {
				result="没有绑定";
			}else{
				result=list.get(0).get("CardID").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "出错";
		} finally {
			mysql.closeconnection();
		}
		return result;
	}
	
	public static boolean canSign(String id) {
		boolean result = false;
		mysqlUtils mysql = new mysqlUtils();
		try {
			String sql = "select * from sign_users where idcard=?";
			List<Object> params = new ArrayList<Object>();
			params.add(id);
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			list=mysql.returnMultipleResult(sql, params);
			if (list.size()!=0) {
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mysql.closeconnection();
		}
		return result;
	}
	
}
