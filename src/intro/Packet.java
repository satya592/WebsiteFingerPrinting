package intro;
public class Packet {

	public static final int UP = 0;
	public static final int DOWN = 1;

	public static final int HEADER_ETHERNET = 0; // is actulally 14 on the LAN
	public static final int HEADER_IP = 20;
	public static final int HEADER_TCP_REQUIRED = 20;
	public static final int HEADER_TCP_OPTIONAL = 12;
	public static final int HEADER_TCP = HEADER_TCP_REQUIRED
			+ HEADER_TCP_OPTIONAL;

	// Packet format for SSHv1
	public static final int HEADER_SSH_PACKET_FIELD_LENGTH = 4;
	public static final int HEADER_SSH_PACKET_TYPE = 1;
	public static final int HEADER_SSH_PADDING = 7; // We already know that our
													// payload is 0 mod 8
	public static final int HEADER_SSH_CRC = 4;
	public static final int HEADER_SSH = HEADER_SSH_PACKET_FIELD_LENGTH
			+ HEADER_SSH_PACKET_TYPE + HEADER_SSH_PADDING + HEADER_SSH_CRC;

	public static final int HEADER_LENGTH = HEADER_ETHERNET + HEADER_IP
			+ HEADER_TCP;
	public static final int MTU = 1500 + HEADER_ETHERNET;

	private int __direction;
	private int __time;
	private int __length;

	public Packet(int direction, int time, int length) {
		this.__direction = direction;
		this.__time = time;
		this.__length = length;
	}

	public int getDirection() {
		return this.__direction;
	}

	public int getTime() {
		return this.__time;
	}

	public int getLength() {
		return this.__length;
	}

	public void setLength(int length) {
		this.__length = length;
	}

	public void setTime(int time) {
		this.__time = time;
	}

}
