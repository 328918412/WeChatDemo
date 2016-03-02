package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import po.Image;
import po.ImageMessage;
import po.News;
import po.NewsMessage;
import po.TextMessage;

public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";

	/**
	 * XML转为Map集合
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = null;
		try {
			ins = request.getInputStream();
			Document doc = reader.read(ins);
			Element root = doc.getRootElement();
			// 获取xml的根节点并存入list中
			List<Element> list = root.elements();
			for (Element element : list) {
				map.put(element.getName(), element.getText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭输入流
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 将文本消息对象转为XML
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 将图文消息对象转为XML
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("item", new News().getClass());
		xstream.alias("xml", newsMessage.getClass());
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 将图片消息对象转为XML
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("item", new Image().getClass());
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 文字消息的组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName,
			String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 *图文消息的组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		// 设置一条图文消息,并添加到newsList集合中
		News news = new News();
		news.setTitle("公众号介绍");
		news.setDescription("这是杨拨云的测试公众号");
		news.setPicUrl("http://yby1231994.ngrok.natapp.cn/WeChat/image/456.jpg");
		news.setUrl("www.baidu.com");
		newsList.add(news);

		// 设置图文消息的属性
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		// 把图文消息转成XML并返回
		return newsMessageToXml(newsMessage);
	}
	
	/**
	 * 图片消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		Image image=new Image();
		ImageMessage imageMessage=new ImageMessage();
		//设置MediaId
		image.setMediaId("cCTRQ49qy2E5BOlRiLquqnTVfP98_tNjc1GhfW3ZOrpWN3xlb7lbLa7wpC4EUWB4");
		//设置imageMessage对象
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		return imageMessageToXml(imageMessage);
	}

	/**
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1.公众号介绍\n");
		sb.append("2.内容介绍\n");
		sb.append("3.图片\n");
		sb.append("回复?调出此菜单");
		return sb.toString();
	}

	/**
	 * 第一个菜单：公众号介绍
	 * 
	 * @return
	 */
	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("公众号介绍：此公众号用于测试开发者模式");
		return sb.toString();
	}

	/**
	 * 第二个菜单：内容介绍
	 * 
	 * @return
	 */
//	public static String secondMenu() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("内容介绍：内容正在建设中");
//		return sb.toString();
//	}

}
