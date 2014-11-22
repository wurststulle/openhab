//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.11.22 um 10:36:20 AM CET 
//


package org.openhab.binding.smarthomatic.internal.packetData;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HeaderExtension" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageType" maxOccurs="unbounded">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
 *                   &lt;element name="ContainsMessageData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MessageGroup" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MessageGroupID">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="127"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Message" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="MessageID">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="15"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="MessageType" maxOccurs="unbounded">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Validity">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;pattern value="test|released|deprecated"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "headerExtension",
    "messageGroup"
})
@XmlRootElement(name = "Packet")
public class Packet {

    @XmlElement(name = "Header", required = true)
    protected Packet.Header header;
    @XmlElement(name = "HeaderExtension")
    protected List<Packet.HeaderExtension> headerExtension;
    @XmlElement(name = "MessageGroup", required = true)
    protected List<Packet.MessageGroup> messageGroup;

    /**
     * Ruft den Wert der header-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Packet.Header }
     *     
     */
    public Packet.Header getHeader() {
        return header;
    }

    /**
     * Legt den Wert der header-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Packet.Header }
     *     
     */
    public void setHeader(Packet.Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the headerExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the headerExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeaderExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Packet.HeaderExtension }
     * 
     * 
     */
    public List<Packet.HeaderExtension> getHeaderExtension() {
        if (headerExtension == null) {
            headerExtension = new ArrayList<Packet.HeaderExtension>();
        }
        return this.headerExtension;
    }

    /**
     * Gets the value of the messageGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Packet.MessageGroup }
     * 
     * 
     */
    public List<Packet.MessageGroup> getMessageGroup() {
        if (messageGroup == null) {
            messageGroup = new ArrayList<Packet.MessageGroup>();
        }
        return this.messageGroup;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dataValue"
    })
    public static class Header {

        @XmlElements({
            @XmlElement(name = "UIntValue", type = UIntValue.class),
            @XmlElement(name = "IntValue", type = IntValue.class),
            @XmlElement(name = "BoolValue", type = BoolValue.class),
            @XmlElement(name = "EnumValue", type = EnumValue.class),
            @XmlElement(name = "ByteArray", type = ByteArray.class),
            @XmlElement(name = "Reserved", type = Reserved.class),
            @XmlElement(name = "Array", type = Array.class)
        })
        protected List<Object> dataValue;

        /**
         * Gets the value of the dataValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dataValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDataValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UIntValue }
         * {@link IntValue }
         * {@link BoolValue }
         * {@link EnumValue }
         * {@link ByteArray }
         * {@link Reserved }
         * {@link Array }
         * 
         * 
         */
        public List<Object> getDataValue() {
            if (dataValue == null) {
                dataValue = new ArrayList<Object>();
            }
            return this.dataValue;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MessageType" maxOccurs="unbounded">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
     *         &lt;element name="ContainsMessageData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "messageType",
        "dataValue",
        "containsMessageData"
    })
    public static class HeaderExtension {

        @XmlElement(name = "MessageType", type = Integer.class)
        protected List<Integer> messageType;
        @XmlElements({
            @XmlElement(name = "UIntValue", type = UIntValue.class),
            @XmlElement(name = "IntValue", type = IntValue.class),
            @XmlElement(name = "BoolValue", type = BoolValue.class),
            @XmlElement(name = "EnumValue", type = EnumValue.class),
            @XmlElement(name = "ByteArray", type = ByteArray.class),
            @XmlElement(name = "Reserved", type = Reserved.class),
            @XmlElement(name = "Array", type = Array.class)
        })
        protected List<Object> dataValue;
        @XmlElement(name = "ContainsMessageData")
        protected boolean containsMessageData;

        /**
         * Gets the value of the messageType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the messageType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMessageType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getMessageType() {
            if (messageType == null) {
                messageType = new ArrayList<Integer>();
            }
            return this.messageType;
        }

        /**
         * Gets the value of the dataValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dataValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDataValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UIntValue }
         * {@link IntValue }
         * {@link BoolValue }
         * {@link EnumValue }
         * {@link ByteArray }
         * {@link Reserved }
         * {@link Array }
         * 
         * 
         */
        public List<Object> getDataValue() {
            if (dataValue == null) {
                dataValue = new ArrayList<Object>();
            }
            return this.dataValue;
        }

        /**
         * Ruft den Wert der containsMessageData-Eigenschaft ab.
         * 
         */
        public boolean isContainsMessageData() {
            return containsMessageData;
        }

        /**
         * Legt den Wert der containsMessageData-Eigenschaft fest.
         * 
         */
        public void setContainsMessageData(boolean value) {
            this.containsMessageData = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MessageGroupID">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="127"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Message" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="MessageID">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="15"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="MessageType" maxOccurs="unbounded">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Validity">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;pattern value="test|released|deprecated"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "name",
        "description",
        "messageGroupID",
        "message"
    })
    public static class MessageGroup {

        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Description", required = true)
        protected String description;
        @XmlElement(name = "MessageGroupID")
        protected long messageGroupID;
        @XmlElement(name = "Message", required = true)
        protected List<Packet.MessageGroup.Message> message;

        /**
         * Ruft den Wert der name-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Legt den Wert der name-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Ruft den Wert der description-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Legt den Wert der description-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Ruft den Wert der messageGroupID-Eigenschaft ab.
         * 
         */
        public long getMessageGroupID() {
            return messageGroupID;
        }

        /**
         * Legt den Wert der messageGroupID-Eigenschaft fest.
         * 
         */
        public void setMessageGroupID(long value) {
            this.messageGroupID = value;
        }

        /**
         * Gets the value of the message property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the message property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMessage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Packet.MessageGroup.Message }
         * 
         * 
         */
        public List<Packet.MessageGroup.Message> getMessage() {
            if (message == null) {
                message = new ArrayList<Packet.MessageGroup.Message>();
            }
            return this.message;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="MessageID">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="15"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="MessageType" maxOccurs="unbounded">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Validity">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;pattern value="test|released|deprecated"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;group ref="{}DataValue" maxOccurs="unbounded"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "name",
            "description",
            "messageID",
            "messageType",
            "validity",
            "dataValue"
        })
        public static class Message {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "Description", required = true)
            protected String description;
            @XmlElement(name = "MessageID")
            protected long messageID;
            @XmlElement(name = "MessageType", type = Integer.class)
            protected List<Integer> messageType;
            @XmlElement(name = "Validity", required = true)
            protected String validity;
            @XmlElements({
                @XmlElement(name = "UIntValue", type = UIntValue.class),
                @XmlElement(name = "IntValue", type = IntValue.class),
                @XmlElement(name = "BoolValue", type = BoolValue.class),
                @XmlElement(name = "EnumValue", type = EnumValue.class),
                @XmlElement(name = "ByteArray", type = ByteArray.class),
                @XmlElement(name = "Reserved", type = Reserved.class),
                @XmlElement(name = "Array", type = Array.class)
            })
            protected List<Object> dataValue;

            /**
             * Ruft den Wert der name-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Legt den Wert der name-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Ruft den Wert der description-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * Legt den Wert der description-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

            /**
             * Ruft den Wert der messageID-Eigenschaft ab.
             * 
             */
            public long getMessageID() {
                return messageID;
            }

            /**
             * Legt den Wert der messageID-Eigenschaft fest.
             * 
             */
            public void setMessageID(long value) {
                this.messageID = value;
            }

            /**
             * Gets the value of the messageType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the messageType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMessageType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Integer }
             * 
             * 
             */
            public List<Integer> getMessageType() {
                if (messageType == null) {
                    messageType = new ArrayList<Integer>();
                }
                return this.messageType;
            }

            /**
             * Ruft den Wert der validity-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValidity() {
                return validity;
            }

            /**
             * Legt den Wert der validity-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValidity(String value) {
                this.validity = value;
            }

            /**
             * Gets the value of the dataValue property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the dataValue property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDataValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link UIntValue }
             * {@link IntValue }
             * {@link BoolValue }
             * {@link EnumValue }
             * {@link ByteArray }
             * {@link Reserved }
             * {@link Array }
             * 
             * 
             */
            public List<Object> getDataValue() {
                if (dataValue == null) {
                    dataValue = new ArrayList<Object>();
                }
                return this.dataValue;
            }

        }

    }

}
