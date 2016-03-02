package po;

public class TextMessage extends BaseMessage {

	private String Content;// 内容
	private String MsgId;// 消息ID，64位整数

	public TextMessage() {
	}

	public TextMessage(String toUserName, String fromUserName, Long createTime,
			String msgType, String content, String msgId) {
		Content = content;
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}
