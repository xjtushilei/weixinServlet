package utils;
/**
 * 
 * @author 石磊
 * @date 2016年12月15日20:19:25
 * @description 这是一个全局的配置文件
 */
public class Config {

	/**
	 * mysql 配置
	 * 
	 */
	public static String MYSQL_HOST="api.xjtushilei.com";
	public static String MYSQL_PORT="3306";
	public static String MYSQL_USER="shilei_weixin";
	public static String MYSQL_PASSWD="shilei123qwe";
	public static String MYSQL_DB="shilei_weixin";
	public static String MYSQL_URL="jdbc:mysql://"+MYSQL_HOST+":"+MYSQL_PORT+"/"+MYSQL_DB+"?user="+MYSQL_USER+"&password="+MYSQL_PASSWD+"&characterEncoding=UTF8";

}
