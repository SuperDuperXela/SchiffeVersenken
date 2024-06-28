package mainmenu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum MessageTypes implements Serializable{
	SERVER_CLOSING(0), // Server schlieﬂt
	CLIENT_DISCONNECT(1), // Client hat die Verbindung zum Server unterbrochen
	PING(2), // Ping Test
	CHAT_MESSAGE(10), // Chat Nachricht
	READY(11), // Client ist bereit zum Spielstart
	UNREADY(12), //Client ist nicht mehr bereit
	GAME_START(20), // Spielstart signalisieren
	GAME_START_DATA(21), // Schiffe vom Client
	GAME_DATA(22), // Spieldaten (alle Schiffe) schicken
	GAME_END(23), // Spielende signalisieren
	CLIENT_INPUT(29); // wird wahrscheinlich nicht benutzt

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
