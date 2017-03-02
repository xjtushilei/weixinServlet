package check;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class check {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 System.out.println(get("312312312312"));
		System.out.println();
	}

	@SuppressWarnings("deprecation")
	public static String get(String id) {
		/** 发送httpget请求 */
		// http://123.139.159.38:9218/API/send/get?id=4250228211&ip=202.117.54.85&city=%E9%99%95%E8%A5%BF%E7%9C%81&
		String url = "http://123.139.159.38:9218/API/send/get?ip=weixin&city=weixin&id=" + id;
		HttpGet request = new HttpGet(url);
		String result = "";
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 请求失败处理 */
		if (null == result) {
			return "对不起,失败了，真是奇怪~要不要再试试……或许是因为网络不好~";
		}
		result = new Date().toLocaleString()+"\n"+result.replaceAll("</?[^>]+>", "")+"\n"+new Date().toLocaleString();
		return result;
	}

}
