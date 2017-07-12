package servlet;

import check.DB;
import check.check;

/**
 * 微信xml消息处理流程逻辑类
 *
 */
public class WechatProcess {
	/**
	 * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
	 * @param xml 接收到的微信数据
	 * @return	最终的解析结果（xml格式数据）
	 */
	public String processWechatMag(String xml){
		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
		
		/** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
		String result = "";
		
		if("text".endsWith(xmlEntity.getMsgType())){
			if (xmlEntity.getContent().indexOf("功能")!=-1) {
				result="您好！\n"
						+ "欢迎我亲爱的小伙伴\n"
						+ "功能介绍：\n"
						+ "1.智能机器人:随时与机器人聊天~\n";
			}else if(xmlEntity.getContent().indexOf("刷卡")!=-1 && xmlEntity.getContent().length()=="刷卡1234567890".length()){
				String id =xmlEntity.getContent().substring(2);
				if (DB.canSign(id)) {
					result = check.sign(id);
				}
				else{
					result = TulingApiProcess.getTulingResult(xmlEntity.getContent());
				}
			}
			else if (xmlEntity.getContent().equals("签到")) {
				result = TulingApiProcess.getTulingResult(xmlEntity.getContent())+ "\n(友情提示：应学术部要求，关掉该签到服务了,以上内容来自石磊机器人~~ 天地网实验室考勤系统：http://checkin.9lou.org ) ";
			}
			else {
				result = TulingApiProcess.getTulingResult(xmlEntity.getContent());
			}
			
		}else if("subscribe".endsWith(xmlEntity.getEvent())){
			result = "您好！\n"
					+ "欢迎我亲爱的小伙伴\n"
					+ "功能介绍：\n"
					+ "1.智能机器人:随时与机器人聊天~\n";
		}
		
		/** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容 
		 *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
		 * */
		result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		
		return result;
	}
}
