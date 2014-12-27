package org.openhab.binding.smarthomatic.internal;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.bind.DatatypeConverter;

import org.openhab.binding.smarthomatic.internal.packetData.IntValue;
import org.openhab.binding.smarthomatic.internal.packetData.Packet;
import org.openhab.binding.smarthomatic.internal.packetData.Packet.MessageGroup;
import org.openhab.binding.smarthomatic.internal.packetData.Packet.MessageGroup.Message;
import org.openhab.binding.smarthomatic.internal.packetData.UIntValue;
import org.openhab.core.items.Item;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.types.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHCMessage {

	private static final Logger logger = LoggerFactory
			.getLogger(SHCMessage.class);

	public static final String DATA_FLAG = "Packet Data:";
	public static final int DATA_FLAG_SIZE = DATA_FLAG.length();

	// MessageGroupIDs
	public static final int GRP_Generic = 0;
	public static final int GRP_GPIO = 1;
	public static final int GRP_Weather = 10;
	public static final int GRP_Environment = 11;
	public static final int GRP_Powerswitch = 20;
	public static final int GRP_Dimmer = 60;

	@SuppressWarnings("unused")
	private String originalMessage;
	private String editedMessage;
	private SHCHeader header;
	private Packet packet;
	public static Charset charset = Charset.forName("UTF-8");
	public static CharsetEncoder encoder = charset.newEncoder();

	public class SHCHeader {
		private int SenderID; // =
								// tokens[0].substring(tokens[0].indexOf("=")+1);
		private int MessageType; // =
									// tokens[2].substring(tokens[2].indexOf("=")+1);
		private long MessageGroupID;// =
									// tokens[3].substring(tokens[3].indexOf("=")+1);
		private int MessageID; // =
								// tokens[4].substring(tokens[4].indexOf("=")+1);
		private byte[] MessageData; // =

		// tokens[5].substring(tokens[5].indexOf("=")+1);

		public int getSenderID() {
			return SenderID;
		}

		public int getMessageType() {
			return MessageType;
		}

		public long getMessageGroupID() {
			return MessageGroupID;
		}

		public int getMessageID() {
			return MessageID;
		}

		public byte[] getMessageData() {
			return MessageData;
		}

		public SHCHeader(String data) {
			StringTokenizer dataTok = new StringTokenizer(data, ";");
			String[] tokens = new String[dataTok.countTokens()];
			Map<String, String> tokensMap = new HashMap<String, String>();
			int i = 0;
			while (dataTok.hasMoreTokens()) {
				String nextToken = dataTok.nextToken();
				tokens[i++] = nextToken;
				String[] split = nextToken.split("=");
				if (split.length == 2) {
					tokensMap.put(split[0].trim(), split[1].trim());
				}
			}
			SenderID = Integer.parseInt(tokensMap.get("SenderID"));
			MessageType = Integer.parseInt(tokensMap.get("MessageType"));
			if (MessageType == 0 || MessageType == 1 || MessageType == 2
					|| MessageType == 8 || MessageType == 10) {
				MessageGroupID = Integer.parseInt(tokensMap
						.get("MessageGroupID"));
				MessageID = Integer.parseInt(tokensMap.get("MessageID"));
			}
			if (MessageType == 1 || MessageType == 2 || MessageType == 8
					|| MessageType == 10) {
				MessageData = DatatypeConverter.parseHexBinary(tokensMap
						.get("MessageData"));
			}

			/*
			 * logger.debug("BaseStation SenderID:      "+SenderID);
			 * logger.debug("BaseStation MessageType:   "+MessageType);
			 * logger.debug("BaseStation MessageGroupID:"+MessageGroupID);
			 * logger.debug("BaseStation MessageID:     "+MessageID);
			 * logger.debug("BaseStation MessageData:   "+MessageData);
			 */
		}
	}

	public class SHCData {
		private List<Type> openHABTypes = new ArrayList<Type>();

		public SHCData(SHCHeader header) {
			byte[] data = header.getMessageData();
			MessageGroup group = null;
			Message message = null;
			int messageType = header.getMessageType();
			if (messageType == 8 || messageType == 10) {
				for (MessageGroup messageGroup : packet.getMessageGroup()) {
					if (messageGroup.getMessageGroupID() == header
							.getMessageGroupID()) {
						group = messageGroup;
						break;
					}
				}
				for (Message message1 : group.getMessage()) {
					if (message1.getMessageID() == header.getMessageID()) {
						message = message1;
						break;
					}
				}

				int startBit = 0;
				for (Object object : message.getDataValue()) {
					if (object instanceof UIntValue) {
						UIntValue value = (UIntValue) object;
						Integer result = parseData(data, value.getBits(),
								startBit, false);
						openHABTypes.add(new DecimalType(result));
						startBit += value.getBits();
					} else if (object instanceof IntValue) {
						IntValue value = (IntValue) object;
						Integer result = parseData(data, value.getBits(),
								startBit, true);
						openHABTypes.add(new DecimalType(result));
						startBit += value.getBits();
					}
				}
			}

		}

		private Integer parseData(byte[] data, long bits, int startBit,
				boolean signed) {
			// startyte ist das byte welches das startbit gerade noch enth�lt
			int startByte = startBit / 8;
			// die Anzahl der bytes die betrachtet werden m�ssen
			int bitsInNextBytes;
			if (bits < 8) {
				bitsInNextBytes = 0;
			} else {
				bitsInNextBytes = (int) (bits - (8 - (startBit % 8)));
			}
			int numberBytes = (int) 1
					+ ((bitsInNextBytes % 8) == 0 ? ((bitsInNextBytes / 8))
							: ((bitsInNextBytes / 8) + 1));
			int numberBytesInResult = (int) ((bits % 8) == 0 ? ((bits / 8))
					: ((bits / 8) + 1));
			byte[] res = new byte[numberBytesInResult];
			int shiftRight = (int) ((bits + startBit) % 8);
			int diffDataRes = numberBytes - numberBytesInResult;
			for (int i = numberBytesInResult; i > 0; --i) {
				byte dataByte = data[startByte + i - 1 + diffDataRes];
				res[numberBytesInResult - i] = (byte) ((dataByte & 255) >>> (8 - shiftRight));
				if ((i - 1 + diffDataRes) > 0) {
					dataByte = data[startByte + i - 2 + diffDataRes];
					byte h = (byte) ((dataByte & 255) << (shiftRight));
					res[numberBytesInResult - i] = (byte) (res[numberBytesInResult
							- i] | h);
				}
			}
			return byteArrayToInt(res, signed);
		}

		private int byteArrayToInt(byte[] b, boolean signed) {
			int value = 0;
			for (int i = 0; i < b.length; i++) {
				int shift = i * 8;
				value += (b[i] & 0x000000FF) << shift;
				if (signed && i == b.length - 1) {
					int c = (b[i] & 0x00000080);
					if (c > 0) {
						value = value * -1;
					}
				}
			}
			return value;
		}

		public List<Type> getOpenHABTypes() {
			return openHABTypes;
		}

		@Override
		public String toString() {
			String res = "SHCData [";
			for (Type type : openHABTypes) {
				res += type.toString();
			}
			res += "]";
			return res;
		}

	}

	public SHCMessage(String message, Packet packet) {
		originalMessage = message;
		editedMessage = message.substring(message.indexOf(DATA_FLAG)
				+ DATA_FLAG_SIZE);
		header = new SHCHeader(editedMessage);
		this.packet = packet;
	}

	public SHCHeader getHeader() {
		return header;
	}

	public SHCData getData() {
		return new SHCData(header);
	}

	public List<Type> openHABStateFromSHCMessage(Item object) {
		return getData().getOpenHABTypes();
	}
}
