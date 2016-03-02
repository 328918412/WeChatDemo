package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.faces.util.MessageUtils;

import po.TextMessage;

import Utils.CheckUtil;
import Utils.MessageUtil;
import Utils.WeChatUtil;

public class WeChatServlet extends HttpServlet {
	private PrintWriter out;

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		out = response.getWriter();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String message = null;
			if (msgType.equals(MessageUtil.MESSAGE_TEXT)) {
				// 获得输入的内容并进行判断，分别调用不同的菜单
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.firstMenu());
				} else if ("2".equals(content)) {
					message = MessageUtil.initNewsMessage(toUserName,
							fromUserName);
					// System.out.println(message);
				} else if ("3".equals(content)) {
					message = MessageUtil.initImageMessage(toUserName,
							fromUserName);
					// System.out.println(message);
				} else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}
				// 对event事件响应
			} else if (msgType.equals(MessageUtil.MESSAGE_EVENT)) {
				//获取event事件类型
				String eventType = map.get("Event");
				if (eventType.equals(MessageUtil.MESSAGE_SUBSCRIBE)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				} else if (eventType.equals(MessageUtil.MESSAGE_CLICK)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				} else if (eventType.equals(MessageUtil.MESSAGE_VIEW)) {
					String url = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName,
							url);
				} else if (eventType.equals(MessageUtil.MESSAGE_SCANCODE)) {
					String key = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName,
							key);
				}
				// 对location事件响应
			} else if (msgType.equals(MessageUtil.MESSAGE_LOCATION)) {
				String label = map.get("Label");
				message = MessageUtil.initText(toUserName, fromUserName, label);

			}
			out.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
