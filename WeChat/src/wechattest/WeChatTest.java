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
		// System.out.println("票据："+token.getToken());
		// System.out.println("有效时间："+token.getExpiresIn());
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
//			System.out.println("菜单创建成功!");
//		} else {
//			System.out.println("错误码：" + result);
//		}
		
//		JSONObject jsonObject=WeChatUtil.queryMenu(token.getToken());
//		System.out.println(jsonObject);
	}

}
