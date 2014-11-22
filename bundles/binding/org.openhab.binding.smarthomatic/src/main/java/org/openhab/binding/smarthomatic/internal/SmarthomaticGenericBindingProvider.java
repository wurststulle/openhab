/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smarthomatic.internal;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.openhab.binding.smarthomatic.SmarthomaticBindingProvider;
import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;


/**
 * This class is responsible for parsing the binding configuration.
 * 
 * @author arohde
 * @since 0.1.0
 */
public class SmarthomaticGenericBindingProvider extends AbstractGenericBindingProvider implements SmarthomaticBindingProvider {

	// We find the id of an device in this map
	// Therefore this map is static
	private static HashMap<String, Integer> devices = new HashMap<String, Integer>();


	public static void addDevice(String name, int deviceID) {
		devices.put(name, deviceID);
	}
	
	public static int getDevice(String name) {
		return devices.get(name);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public String getBindingType() {
		return "smarthomatic";
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
		//if (!(item instanceof SwitchItem || item instanceof DimmerItem)) {
		//	throw new BindingConfigParseException("item '" + item.getName()
		//			+ "' is of type '" + item.getClass().getSimpleName()
		//			+ "', only Switch- and DimmerItems are allowed - please check your *.items configuration");
		//}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processBindingConfiguration(String context, Item item, String bindingConfig) throws BindingConfigParseException {
		super.processBindingConfiguration(context, item, bindingConfig);
		SmarthomaticBindingConfig config = new SmarthomaticBindingConfig();
		
		config.setItemName(item.getName());
		config.setItem(item);
		
		//parse bindingconfig here ...
		StringTokenizer confItems = new StringTokenizer(bindingConfig, ",");
		while (confItems.hasMoreTokens()) {
			String[] token = confItems.nextToken().split("=");
			// Strip all whitespaces from token[0]
			switch (token[0].replaceAll("\\s", "")) {
			case "device": config.setDevice(token[1].replaceAll("\\s", ""));
				break;
			case "port" : config.setPort(token[1].replaceAll("\\s", ""));
				break;
			case "type" : config.setType(token[1].replaceAll("\\s", ""));
				break;
			case "toggleTime" : config.setToggleTime(token[1].replaceAll("\\s", ""));
				break;
			}
		}
		
		addBindingConfig(item, config);		
	}
	

	@Override
	public int getDeviceID(String itemName) {
		SmarthomaticBindingConfig config = (SmarthomaticBindingConfig) bindingConfigs.get(itemName);
		
		return config.getDevice();
	}

	@Override
	public int getPort(String itemName) {
		SmarthomaticBindingConfig config = (SmarthomaticBindingConfig) bindingConfigs.get(itemName);

		return config.getPort();
	}

	@Override
	public int getType(String itemName) {
		SmarthomaticBindingConfig config = (SmarthomaticBindingConfig) bindingConfigs.get(itemName);

		return config.getType();
	}

	@Override
	public int getToggleTime(String itemName) {
		SmarthomaticBindingConfig config = (SmarthomaticBindingConfig) bindingConfigs.get(itemName);

		return config.getToggleTime();
	}

	@Override
	public Item getItem(String itemName) {
		SmarthomaticBindingConfig config = (SmarthomaticBindingConfig) bindingConfigs.get(itemName);

		return config.getItem();
	}

	
	class SmarthomaticBindingConfig implements BindingConfig {
		// put member fields here which holds the parsed values
		private String itemName;
		private int device = -1;
		private int port = 0;
		private int type = 0;
		private int toggleTime = 0;
		private Item item;

		public int getDevice() {
			return device;
		}

		public void setDevice(String device) {
			// now there are two possibilities:
			// 1. we have a number in device => store it directly
			try {
				this.device = Integer.parseInt(device);
			} catch (NumberFormatException e) {
				this.device = SmarthomaticGenericBindingProvider.getDevice(device);
			}
		}

		public int getToggleTime() {
			return toggleTime;
		}

		public void setToggleTime(String toggleTime) {
			try {
				this.toggleTime = Integer.parseInt(toggleTime);
			} catch (NumberFormatException e) {
				this.toggleTime = 0;
			}
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public int getPort() {
			return port;
		}

		public void setPort(String port) {
			try {
				this.port = Integer.parseInt(port);
			} catch (NumberFormatException e) {
				this.port = 0;
			}
		}
		
		/**
		 * 
		 * @return the MessageGroupID of the item
		 */
		public int getType() {
			return this.type;
		}
		
		/**
		 * 
		 * @param type is a MessageGroupID - number or a corresponding type like
		 * Generic, GPIO, Weather, Environment, Powerswitch, Dimmer or Digiboard
		 * For more information see the smarthomatic homepage
		 */
		public void setType(String type) {
			try {
				this.type = Integer.parseInt(type);
			} catch (NumberFormatException e) {
				switch (type.toUpperCase()) {
				case "GENERIC" : this.type = 0; break;
				case "GPIO"  : this.type = 1; break;
				case "WEATHER" : this.type = 10; break;
				case "ENVIRONMENT" : this.type = 11; break;
				case "POWERSWITCH" : this.type = 20; break;
				case "DIMMER" : this.type = 60; break;
				case "DIGIBOARD" : this.type = 99; break;
				}
				
			}
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}
		
	}

}
