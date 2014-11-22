//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.11.22 um 10:36:20 AM CET 
//


package org.openhab.binding.smarthomatic.internal.packetData;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnumValue }
     * 
     */
    public EnumValue createEnumValue() {
        return new EnumValue();
    }

    /**
     * Create an instance of {@link Packet }
     * 
     */
    public Packet createPacket() {
        return new Packet();
    }

    /**
     * Create an instance of {@link Packet.MessageGroup }
     * 
     */
    public Packet.MessageGroup createPacketMessageGroup() {
        return new Packet.MessageGroup();
    }

    /**
     * Create an instance of {@link Array }
     * 
     */
    public Array createArray() {
        return new Array();
    }

    /**
     * Create an instance of {@link UIntValue }
     * 
     */
    public UIntValue createUIntValue() {
        return new UIntValue();
    }

    /**
     * Create an instance of {@link IntValue }
     * 
     */
    public IntValue createIntValue() {
        return new IntValue();
    }

    /**
     * Create an instance of {@link BoolValue }
     * 
     */
    public BoolValue createBoolValue() {
        return new BoolValue();
    }

    /**
     * Create an instance of {@link EnumValue.Element }
     * 
     */
    public EnumValue.Element createEnumValueElement() {
        return new EnumValue.Element();
    }

    /**
     * Create an instance of {@link ByteArray }
     * 
     */
    public ByteArray createByteArray() {
        return new ByteArray();
    }

    /**
     * Create an instance of {@link Packet.Header }
     * 
     */
    public Packet.Header createPacketHeader() {
        return new Packet.Header();
    }

    /**
     * Create an instance of {@link Packet.HeaderExtension }
     * 
     */
    public Packet.HeaderExtension createPacketHeaderExtension() {
        return new Packet.HeaderExtension();
    }

    /**
     * Create an instance of {@link Reserved }
     * 
     */
    public Reserved createReserved() {
        return new Reserved();
    }

    /**
     * Create an instance of {@link Packet.MessageGroup.Message }
     * 
     */
    public Packet.MessageGroup.Message createPacketMessageGroupMessage() {
        return new Packet.MessageGroup.Message();
    }

}
