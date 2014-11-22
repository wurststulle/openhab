package org.openhab.binding.smarthomatic.internal;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.openhab.binding.smarthomatic.internal.packetData.Packet;
import org.openhab.core.items.Item;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
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

	public class SHCHeader {
		private int SenderID; // =
								// tokens[0].substring(tokens[0].indexOf("=")+1);
		private int MessageType; // =
									// tokens[2].substring(tokens[2].indexOf("=")+1);
		private int MessageGroupID;// =
									// tokens[3].substring(tokens[3].indexOf("=")+1);
		private int MessageID; // =
								// tokens[4].substring(tokens[4].indexOf("=")+1);
		private String MessageData; // =
									// tokens[5].substring(tokens[5].indexOf("=")+1);

		public int getSenderID() {
			return SenderID;
		}

		public int getMessageType() {
			return MessageType;
		}

		public int getMessageGroupID() {
			return MessageGroupID;
		}

		public int getMessageID() {
			return MessageID;
		}

		public String getMessageData() {
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
			MessageData = tokens[5].substring(tokens[5].indexOf("=") + 1);
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
		private int[] intValues;
		private long[] longValues;
		private boolean[] boolValues;
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
			String data = header.getMessageData();

			if (header.getMessageGroupID() == GRP_Generic) {
				// Version MessageID == 1
				if (header.getMessageID() == 1) {
					intValues = new int[3];
					longValues = new long[1];
					// major
					intValues[0] = toInt(data, 0, 2, 16);
					// minor
					intValues[1] = toInt(data, 2, 4, 16);
					// patch
					intValues[2] = toInt(data, 4, 6, 16);
					// hash
					longValues[0] = toLong(data, 6, 14, 16);

					messageText = "Version: " + intValues[0] + "."
							+ intValues[1] + "." + intValues[2];

					openHABType = new StringType(messageText);
				} else
				// Battery MessageID == 5
				if (header.getMessageID() == 5) {
					intValues = new int[1];
					// battery status fills only the first (highest) 7 bits
					// so we have to shift all bits one to the right
					intValues[0] = toInt(data, 0, 2, 16) >> 1;

					messageText = "Battery " + intValues[0] + "%";

					openHABType = new PercentType(intValues[0]);
				}
			} else if (header.getMessageGroupID() == GRP_GPIO) {
				messageText = "GPIO: {";
				// boolValues are set in both messages
				boolValues = new boolean[8];
				String dummy = data.substring(0, 2);
				int value = Integer.parseInt(dummy);
				// Test bits and set bool to true, when bit is set
				for (int i = 0; i < 8; i++) {
					if ((value & (1 << i)) == (1 << i)) {
						boolValues[i] = true;
					} else {
						boolValues[i] = false;
					}
					messageText += Boolean.toString(boolValues[i]);
					if (i == 7)
						messageText += "}";
					else
						messageText += ",";
				}
				// Analog Pin MessageID == 2
				if (header.getMessageID() == 2) {
					intValues = new int[8];

					dummy = data.substring(2, 13);
					// BigInteger can hold values with more than 64 bit
					BigInteger bi = new BigInteger(dummy, 16);
					BigInteger b;
					// now this must be tested
					// i consider, that all values are written from
					// "left to right". This means, that the highest 11 bits
					// represent the value of the first ADC. So the lowest 11
					// bits
					// represent the last (8) ADC
					// I HOPE THIS IS TRUE :-)
					for (int i = 0; i < 8; i++) {
						// shift i*11 to left, to unset the high bytes
						b = bi.shiftLeft(i * 11);
						// now shift (8-i)*11 to the right, on the left side
						// the value will be filled with 0
						b = b.shiftRight((8 - i) * 11);
						// now in b there is only the value of i's ADC
						intValues[i] = b.intValue();

						// if my understanding of the algorithm is false we
						// have to do the following statement
						// intValues[7-i] = b.intValue();

					}

					// prepare the header text
					messageText += "Values:";
					for (int i = 0; i < 7; i++)
						messageText += intValues[i] + ",";
					messageText += intValues[7];

					// openHABType has to be set later in
					// openHABStateFromSHCMessage
				}
			} else if (header.getMessageGroupID() == GRP_Weather) {
				// TODO
			} else if (header.getMessageGroupID() == GRP_Environment) {
				// TODO
			} else if (header.getMessageGroupID() == GRP_Powerswitch) {
				// SwitchState MessageID == 1
				if (header.getMessageID() == 1) {
					// first 3 byte (24 bits) into int var
					// and shift right 7 bits, because we only need the first
					// 17 bits
					int value = toInt(data, 0, 6, 16) >> 7;
					boolValues = new boolean[1];
					intValues = new int[1];
					// first bit tells state (on or off)
					boolValues[0] = ((value & 1 << 16) == (1 << 16));
					// now switch off topmost bit and store value in array
					intValues[0] = ~(1 << 16) & value;

					messageText = "SwitchState: "
							+ Boolean.toString(boolValues[0]) + ", Timeout: "
							+ intValues[0];

					openHABType = boolValues[0] ? OnOffType.ON : OnOffType.OFF;
				}
				// SwitchStateExt MessageID == 2
				else if (header.getMessageID() == 2) {

				}
			} else if (header.getMessageGroupID() == GRP_Dimmer) {
				// TODO
			}

		}

		public Type getOpenHABType() {
			return openHABType;
		}

		public boolean getBooleanValue(int index) {
			return boolValues[index];
		}

		public int getIntValue(int index) {
			return intValues[index];
		}

		public long getLongValue(int index) {
			return longValues[index];
		}

		@Override
		public String toString() {
			return "SHCData [intValues=" + Arrays.toString(intValues)
					+ ", longValues=" + Arrays.toString(longValues)
					+ ", boolValues=" + Arrays.toString(boolValues)
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
		// TODO Auto-generated method stub
		return (State) getData().getOpenHABType();
	}

}
