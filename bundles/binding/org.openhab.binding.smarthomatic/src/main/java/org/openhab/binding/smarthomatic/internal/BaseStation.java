package org.openhab.binding.smarthomatic.internal;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.openhab.binding.smarthomatic.internal.SHCMessage.SHCData;
import org.openhab.binding.smarthomatic.internal.SHCMessage.SHCHeader;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseStation implements SerialEventWorker {

	private static final Logger logger = LoggerFactory.getLogger(BaseStation.class);
	
	private SerialDevice serialDevice;
	
	private String[] versionInfo;
	
	private SerialEventWorker bindingEventWorker;
	
	
	/* always 0 for base station */
//	private final int deviceType = 0;
	/* can be 0..4095, normally 0 */
//	private int deviceId = 0;
	
	/* 
	 * supported items are:
	 * StringItem
	 * SwitchItem
	 * 
	 * */
	
	
	public BaseStation(String port, int baud, SerialEventWorker sew) {
		bindingEventWorker = sew;
		serialDevice = new SerialDevice(port, baud);
		serialDevice.setEventWorker(this);
		try {
			serialDevice.initialize();
		} catch (InitializationException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSerialPort() {
		serialDevice.close();
	}
	
	private String genHexDevID(int deviceID) {
		String s = Integer.toHexString(deviceID);
		int len = s.length();
		for (int i = len; i < 4; i++) {
			s = "0"+s;
		}
		return s;
	}
	
	private String getToggleTime(int toggleTime, boolean on) {
		// shift left the on bit, so that the on-bit stands on position 17 from right
		long temp = (on ? 1 : 0) << 16; 
		// patch toggletime into the last 16 bit and be sure to prevent to override on-bit 
		temp |= (toggleTime & 0x0001FFFF);
		// shift temp to left by 15 bits (the on bit should stand at msb position)
		temp <<= 15;
		
		// write hex representation of temp to s
		String s = Long.toHexString(temp);
		
		// fill String with leading zeros until it's minimum 2 chars long
		for (int i = s.length(); i<2; i++) s = "0"+s;
		
		return s;
	}
	
	public void sendCommand(int deviceID, int port, int type, int toggleTime, Command command) {
		String cmd = "";
		// Powerswitch
		if (type == 20) {
			cmd = "s0002"+ genHexDevID(deviceID)+"14"+"01"+ getToggleTime(toggleTime, command.toString().equals("ON")) + "\r";
		}
		
		if (cmd != "") {
			logger.debug("send to serial port:"+cmd);
			serialDevice.writeString(cmd);
		}
	}
	
	
	@Override
	public void eventOccured(String message) {
		
		// normally there comes only one line to the baseStation
		// except when station is restarted
		if (message.contains("Base Station")) {
			StringTokenizer strTok = new StringTokenizer(message, "\n");
			String data = null;
			versionInfo = new String[strTok.countTokens()];
			logger.debug("BaseStation eventOccured - initial message ( "+strTok.countTokens()+ " )");
			int i = 0;
			while (strTok.hasMoreTokens()) {
				versionInfo[i] = strTok.nextToken();
				logger.debug("<BaseStation>["+i+"]: "+versionInfo[i]);
				i++;
			}
		} else {
			logger.debug("BaseStation eventOccured - giving to Binding "+message);
			if (bindingEventWorker != null) {
				bindingEventWorker.eventOccured(message);
			}
		}	
	}
	
	public String toString() {
		return "BaseStation listening on port "+serialDevice.getPort();
	}

}
