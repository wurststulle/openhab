/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smarthomatic.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.openhab.binding.smarthomatic.SmarthomaticBindingProvider;
import org.openhab.binding.smarthomatic.internal.SHCMessage.SHCData;
import org.openhab.binding.smarthomatic.internal.SHCMessage.SHCHeader;
import org.openhab.binding.smarthomatic.internal.packetData.Packet;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.framework.Bundle;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implement this class if you are going create an actively polling service like
 * querying a Website/Device.
 * 
 * @author arohde
 * @since 0.1.0
 */
public class SmarthomaticBinding extends
		AbstractActiveBinding<SmarthomaticBindingProvider> implements
		ManagedService, SerialEventWorker {

	private static final Logger logger = LoggerFactory
			.getLogger(SmarthomaticBinding.class);
	private BaseStation baseStation;
	private String serialPortname;
	private int serialBaudrate;
	private Packet packet;

	/**
	 * the refresh interval which is used to poll values from the Smarthomatic
	 * server (optional, defaults to 60000ms)
	 */
	private long refreshInterval = 60000;

	public SmarthomaticBinding() {
	}

	public void activate() {
		// log activate of binding
		if (baseStation != null) {
			logger.info("Smarthomatic Binding activated. BaseStation="
					+ baseStation.toString());
		}

		Bundle bundle = SmarthomaticActivator.getContext().getBundle();
		URL fileURL = bundle.getEntry("xml/packet_layout.xml");
		Packet packet = null;
		try {
			InputStream inputStream = fileURL.openConnection().getInputStream();
			JAXBContext jaxbContext = JAXBContext.newInstance(Packet.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			packet = (Packet) jaxbUnmarshaller.unmarshal(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		this.packet = packet;
	}

	public void deactivate() {
		// deallocate resources here that are no longer needed and
		// should be reset when activating this binding again
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected long getRefreshInterval() {
		return refreshInterval;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected String getName() {
		return "Smarthomatic Refresh Service";
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected void execute() {
		// the frequently executed code (polling) goes here ...
		// logger.debug("execute() method is called!");
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected void internalReceiveCommand(String itemName, Command command) {
		// the code being executed when a command was sent on the openHAB
		// event bus goes here. This method is only called if one of the
		// BindingProviders provide a binding for the given 'itemName'.

		for (SmarthomaticBindingProvider provider : this.providers) {
			if (provider.providesBindingFor(itemName)) {
				if (baseStation != null) {
					baseStation.sendCommand(provider.getDeviceID(itemName),
							provider.getPort(itemName),
							provider.getType(itemName),
							provider.getToggleTime(itemName), command);
				}
			}
		}

		logger.debug("internalReceiveCommand() is called! {},{}", new String[] {
				itemName, command.toString() });
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected void internalReceiveUpdate(String itemName, State newState) {
		// the code being executed when a state was sent on the openHAB
		// event bus goes here. This method is only called if one of the
		// BindingProviders provide a binding for the given 'itemName'.
		logger.debug("internalReceiveUpdate() is called!{},{}", new String[] {
				itemName, newState.toString() });

	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void updated(Dictionary<String, ?> config)
			throws ConfigurationException {
		if (config != null) {

			// to override the default refresh interval one has to add a
			// parameter to openhab.cfg like
			// <bindingName>:refresh=<intervalInMs>
			String refreshIntervalString = (String) config.get("refresh");
			if (StringUtils.isNotBlank(refreshIntervalString)) {
				refreshInterval = Long.parseLong(refreshIntervalString);
			}

			boolean changed = false;

			if (serialPortname != (String) config.get("port")) {
				serialPortname = (String) config.get("port");
				changed = true;
			}
			String dummy = (String) config.get("baud");
			try {
				if (serialBaudrate != Integer.parseInt(dummy)) {
					serialBaudrate = Integer.parseInt(dummy);
					changed = true;
				}
			} catch (NumberFormatException e) {
				logger.info("reading exception");
			}

			if (changed | (baseStation == null)) {
				if (baseStation != null)
					baseStation.closeSerialPort();

				baseStation = new BaseStation(serialPortname, serialBaudrate,
						this);
				logger.debug("Smarthomatic Binding:update creates new basestation");
			}

			Enumeration<String> keys = config.keys();
			for (int i = 0; i < config.size(); i++) {
				String key = keys.nextElement();
				StringTokenizer tokens = new StringTokenizer(key, ":");

				if (tokens.nextToken().equals("device")) {
					if (tokens.hasMoreElements()) {
						dummy = tokens.nextToken();
						int deviceID = Integer.parseInt(dummy);
						String name = (String) config.get(key);
						SmarthomaticGenericBindingProvider.addDevice(name,
								deviceID);
						logger.debug(
								"Smarthomatic device {} can be indexed by name {}",
								new String[] { dummy, name });
					}
				}
				logger.debug("KEY:" + key);
			}

			setProperlyConfigured(true);
		}
	}

	@Override
	public void eventOccured(String message) {
		StringTokenizer strTok = new StringTokenizer(message, "\n");
		String data = null;

		// check incoming data
		int i = 0;
		while (strTok.hasMoreTokens()) {
			String s = strTok.nextToken();
			if (s.contains(SHCMessage.DATA_FLAG)) {
				data = s;
				logger.debug("<BaseStation data>[" + (i - 1) + "]: " + data);
			}
		}
		if (data != null) {
			SHCMessage shcMessage = new SHCMessage(data, packet);
			SHCHeader shcHeader = shcMessage.getHeader();

			logger.debug("BaseStation SenderID:      "
					+ shcHeader.getSenderID());
			logger.debug("BaseStation MessageType:   "
					+ shcHeader.getMessageType());
			logger.debug("BaseStation MessageGroupID:"
					+ shcHeader.getMessageGroupID());
			logger.debug("BaseStation MessageID:     "
					+ shcHeader.getMessageID());
			logger.debug("BaseStation MessageData:   "
					+ shcHeader.getMessageData());

			SHCData info = shcMessage.getData();
			if (info != null) {
				logger.debug(info.toString());
			}

			// search all matching providers where DeviceID == SenderID of
			// Message-Header
			for (SmarthomaticBindingProvider provider : this.providers) {
				for (String itemName : provider.getItemNames()) {
					if (shcHeader.getSenderID() == provider
							.getDeviceID(itemName)) {
						eventPublisher.postUpdate(itemName, shcMessage
								.openHABStateFromSHCMessge(provider
										.getItem(itemName)));
					}
				}
			}
		}
	}

}
