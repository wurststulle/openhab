package org.openhab.binding.smarthomatic.internal;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.DatatypeConverter;

import org.openhab.binding.smarthomatic.internal.packetData.IntValue;
import org.openhab.binding.smarthomatic.internal.packetData.Packet;
import org.openhab.binding.smarthomatic.internal.packetData.Packet.MessageGroup;
import org.openhab.binding.smarthomatic.internal.packetData.Packet.MessageGroup.Message;
import org.openhab.binding.smarthomatic.internal.packetData.UIntValue;
import org.openhab.core.items.Item;
import org.openhab.core.types.State;
import org.openhab.core.types.Type;

public class SHCMessage {

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
			int i = 0;
			while (dataTok.hasMoreTokens()) {
				tokens[i++] = dataTok.nextToken();
			}
			SenderID = Integer.parseInt(tokens[0].substring(tokens[0]
					.indexOf("=") + 1));
			MessageType = Integer.parseInt(tokens[2].substring(tokens[2]
					.indexOf("=") + 1));
			MessageGroupID = Integer.parseInt(tokens[3].substring(tokens[3]
					.indexOf("=") + 1));
			MessageID = Integer.parseInt(tokens[4].substring(tokens[4]
					.indexOf("=") + 1));
			String msg = tokens[5].substring(tokens[5].indexOf("=") + 1);
			MessageData = DatatypeConverter.parseHexBinary(msg);

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
		private List<Integer> intValues = new ArrayList<Integer>();
		private List<Long> longValues = new ArrayList<Long>();
		private List<Boolean> boolValues = new ArrayList<Boolean>();
		private String messageText;
		private Type openHABType;

		private int toInt(String data, int start, int end, int radix) {
			String dummy = data.substring(start, end);
			return Integer.parseInt(dummy, radix);
		}

		private long toLong(String data, int start, int end, int radix) {
			String dummy = data.substring(start, end);
			return Long.parseLong(dummy, radix);
		}

		public SHCData(SHCHeader header) {
			byte[] data = header.getMessageData();
			MessageGroup group = null;
			Message message = null;

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
					parseData(data, value.getBits(), startBit, false);
					startBit += value.getBits();
				} else if (object instanceof IntValue) {
					IntValue value = (IntValue) object;
					parseData(data, value.getBits(), startBit, true);
					startBit += value.getBits();
				}
			}

		}

		private void parseData(byte[] data, long bits, int startBit,
				boolean signed) {
			// startyte ist das byte welches das startbit gerade noch enthält
			int startByte = startBit / 8;
			// die Anzahl der bytes die betrachtet werden müssen
			int bitsInNextBytes = (int) (bits - (8 - (startBit % 8)));
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
			intValues.add(byteArrayToInt(res, signed));
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

		public Type getOpenHABType() {
			return openHABType;
		}

		public boolean getBooleanValue(int index) {
			return boolValues.get(index);
		}

		public int getIntValue(int index) {
			return intValues.get(index);
		}

		public long getLongValue(int index) {
			return longValues.get(index);
		}

		@Override
		public String toString() {
			return "SHCData [intValues=" + intValues + ", longValues="
					+ longValues + ", boolValues=" + boolValues
					+ ", messageText=" + messageText + ", openHABType="
					+ openHABType + "]";
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

	public State openHABStateFromSHCMessge(Item object) {
		return (State) getData().getOpenHABType();
	}

}
