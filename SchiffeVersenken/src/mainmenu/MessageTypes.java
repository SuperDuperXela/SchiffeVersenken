package mainmenu;

import java.util.HashMap;
import java.util.Map;

public enum MessageTypes {
	SERVER_CLOSING(0), //
	CLIENT_DISCONNECT(1), //
	PING(2), //
	CHAT_MESSAGE(10), //
	GAME_OPTIONS(11), //
	READY(12), //
	GAME_DATA(21), //
	CLIENT_INPUT(22);

	public final byte messageType;

	private static final Map<Byte, MessageTypes> lookup = new HashMap<>();

	static {
		for (MessageTypes m : MessageTypes.values()) {
			lookup.put(m.getMessageType(), m);
		}
	}

	private MessageTypes(int messageType) {
		this.messageType = (byte) messageType;
	}

	public byte getMessageType() {
		return messageType;
	}

	public static MessageTypes get(byte messageTypeByte) {
		return lookup.get(messageTypeByte);
	}

	public static MessageTypes getValueForLoop(byte value) {
		for (MessageTypes e : MessageTypes.values()) {
			if (e.messageType == value) {
				return e;
			}
		}
		return null;
	}
}
