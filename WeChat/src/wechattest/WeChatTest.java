package wechattest;

import java.io.IOException;
import java.text.ParseException;

import net.sf.json.JSONObject;
import Utils.WeChatUtil;
import po.AccessToken;

public class WeChatTest {

	public static void main(String[] args) throws ParseException, IOException {
		// try{
		AccessToken token = WeChatUtil.getAccessToken();
		// System.out.println("Ʊ�ݣ�"+token.getToken());
		// System.out.println("��Чʱ�䣺"+token.getExpiresIn());
		//
		//
		// String path="C:\\Users\\32891\\Desktop\\123.jpg";
		// String mediaId=WeChatUtil.upload(path, token.getToken(),"image");
		// System.out.println(mediaId);
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		
//		String menu = JSONObject.fromObject(WeChatUtil.initMenu()).toString();
//		int result = WeChatUtil.createMenu(token.getToken(), menu);
//		if (result == 0) {
//			System.out.println("�˵������ɹ�!");
//		} else {
//			System.out.println("�����룺" + result);
//		}
		
//		JSONObject jsonObject=WeChatUtil.queryMenu(token.getToken());
//		System.out.println(jsonObject);
	}

}
