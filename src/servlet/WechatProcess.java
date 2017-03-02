package servlet;

import check.DB;
import check.check;

/**
 * 微信xml消息处理流程逻辑类
 * @author pamchen-1
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
						+ "功能介绍：\n"
						+ "1.智能机器人:随时与机器人聊天~\n"
						+ "2.特殊指令：回复一下任意特殊指令，查看特效！\n"
						+ "【签到绑定微信】、【签到】……未完待续";
			}else if(xmlEntity.getContent().equals("签到")){
				String WeixinID=xmlEntity.getFromUserName();
				String CardID=DB.get(WeixinID);
				if (CardID.equals("没有绑定")) {
					result="您好！\n"
							+ "请先进行【签到绑定微信】~";
				}else if (CardID.equals("出错")) {
					result="您好！\n"
							+ "签到过程中获取您绑定的卡号失败啦~"
							+ "\n重试说不定就好啦呢~";
				}
				else {
					result=check.get(CardID);
				}

			}
			else if(xmlEntity.getContent().equals("签到绑定微信")){
				result="回复：签到绑定微信+打卡的id\n"
						+ "例如：\n\n"
						+ "签到绑定微信1234567890\n\n"
						+ "说明：\n"
						+ "1.中间无空格后面是打卡的ID（十位数字，不懂ID是什么的请询问身边的人）\n"
						+ "2.补办的新卡，请重新绑定~";
			}else if(xmlEntity.getContent().indexOf("签到绑定微信")!=-1 && xmlEntity.getContent().length()=="签到绑定微信1234567890".length()){
				String CardID=xmlEntity.getContent().substring(6);
				String WeixinID=xmlEntity.getFromUserName();
				result=DB.add(CardID, WeixinID);
				
			}
			else {
				result = TulingApiProcess.getTulingResult(xmlEntity.getContent());
			}
			
		}else if("subscribe".endsWith(xmlEntity.getEvent())){
			result = "您好！\n"
					+ "欢迎我亲爱的小伙伴\n"
					+ "功能介绍：\n"
					+ "1.智能机器人:随时与机器人聊天~\n"
					+ "2.特殊指令：回复一下任意特殊指令，查看特效！\n"
					+ "【签到绑定微信】、【签到】……未完待续";
		}
		
		/** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容 
		 *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
		 * */
		result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		
		return result;
	}
}
