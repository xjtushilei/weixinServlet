package check;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class check {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 System.out.println(get("1494470019"));
		System.out.println();
	}
	public static String get(String id) {
		//httpClient
		    HttpClient httpClient = new DefaultHttpClient();

		    // get method
		    HttpPost httpPost = new HttpPost("http://checkin.9lou.org/signin");    

		    //set params
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("id",id));
		    try{
		        httpPost.setEntity(new UrlEncodedFormEntity(params));
		    }catch (Exception e) {} 

		    //response
		    HttpResponse response = null;  
		    try{
		        response = httpClient.execute(httpPost);
		    }catch (Exception e) {}
		    
		    //get response into String
		    String temp="";
		    try{
		        HttpEntity entity = response.getEntity();
		        temp=EntityUtils.toString(entity,"utf-8");
		    }catch (Exception e) {}
		    temp=unicodeToUtf8(temp);
		    return new Date().toLocaleString()+"\n"+temp;
	    }    
	 
	 public static String unicodeToUtf8(String theString) {
		  char aChar;
		  int len = theString.length();
		  StringBuffer outBuffer = new StringBuffer(len);
		  for (int x = 0; x < len;) {
		   aChar = theString.charAt(x++);
		   if (aChar == '\\') {
		    aChar = theString.charAt(x++);
		    if (aChar == 'u') {
		     // Read the xxxx
		     int value = 0;
		     for (int i = 0; i < 4; i++) {
		      aChar = theString.charAt(x++);
		      switch (aChar) {
		      case '0':
		      case '1':
		      case '2':
		      case '3':
		      case '4':
		      case '5':
		      case '6':
		      case '7':
		      case '8':
		      case '9':
		       value = (value << 4) + aChar - '0';
		       break;
		      case 'a':
		      case 'b':
		      case 'c':
		      case 'd':
		      case 'e':
		      case 'f':
		       value = (value << 4) + 10 + aChar - 'a';
		       break;
		      case 'A':
		      case 'B':
		      case 'C':
		      case 'D':
		      case 'E':
		      case 'F':
		       value = (value << 4) + 10 + aChar - 'A';
		       break;
		      default:
		       throw new IllegalArgumentException(
		         "Malformed   \\uxxxx   encoding.");
		      }
		     }
		     outBuffer.append((char) value);
		    } else {
		     if (aChar == 't')
		      aChar = '\t';
		     else if (aChar == 'r')
		      aChar = '\r';
		     else if (aChar == 'n')
		      aChar = '\n';
		     else if (aChar == 'f')
		      aChar = '\f';
		     outBuffer.append(aChar);
		    }
		   } else
		    outBuffer.append(aChar);
		  }
		  return outBuffer.toString();
		 }
}
