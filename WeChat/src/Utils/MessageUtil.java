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
	 * XMLתΪMap����
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
			// ��ȡxml�ĸ��ڵ㲢����list��
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
			// �ر�������
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
	 * ���ı���Ϣ����תΪXML
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
	 * ��ͼ����Ϣ����תΪXML
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
	 * ��ͼƬ��Ϣ����תΪXML
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
	 * ������Ϣ����װ
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
	 *ͼ����Ϣ����װ
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		// ����һ��ͼ����Ϣ,����ӵ�newsList������
		News news = new News();
		news.setTitle("���ںŽ���");
		news.setDescription("������ƵĲ��Թ��ں�");
		news.setPicUrl("http://yby1231994.ngrok.natapp.cn/WeChat/image/456.jpg");
		news.setUrl("www.baidu.com");
		newsList.add(news);

		// ����ͼ����Ϣ������
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		// ��ͼ����Ϣת��XML������
		return newsMessageToXml(newsMessage);
	}
	
	/**
	 * ͼƬ��Ϣ����װ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		Image image=new Image();
		ImageMessage imageMessage=new ImageMessage();
		//����MediaId
		image.setMediaId("cCTRQ49qy2E5BOlRiLquqnTVfP98_tNjc1GhfW3ZOrpWN3xlb7lbLa7wpC4EUWB4");
		//����imageMessage����
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		return imageMessageToXml(imageMessage);
	}

	/**
	 * ���˵�
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
		sb.append("1.���ںŽ���\n");
		sb.append("2.���ݽ���\n");
		sb.append("3.ͼƬ\n");
		sb.append("�ظ�?�����˲˵�");
		return sb.toString();
	}

	/**
	 * ��һ���˵������ںŽ���
	 * 
	 * @return
	 */
	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("���ںŽ��ܣ��˹��ں����ڲ��Կ�����ģʽ");
		return sb.toString();
	}

	/**
	 * �ڶ����˵������ݽ���
	 * 
	 * @return
	 */
//	public static String secondMenu() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("���ݽ��ܣ��������ڽ�����");
//		return sb.toString();
//	}

}
