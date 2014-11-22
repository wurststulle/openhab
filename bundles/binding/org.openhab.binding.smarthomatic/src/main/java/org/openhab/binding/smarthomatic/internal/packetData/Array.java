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
 *         &lt;element name="Length">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;group ref="{}ArrayDataValue" maxOccurs="unbounded"/>
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
    "length",
    "arrayDataValue"
})
@XmlRootElement(name = "Array")
public class Array {

    @XmlElement(name = "Length")
    protected int length;
    @XmlElements({
        @XmlElement(name = "UIntValue", type = UIntValue.class),
        @XmlElement(name = "IntValue", type = IntValue.class),
        @XmlElement(name = "BoolValue", type = BoolValue.class),
        @XmlElement(name = "EnumValue", type = EnumValue.class),
        @XmlElement(name = "ByteArray", type = ByteArray.class)
    })
    protected List<Object> arrayDataValue;

    /**
     * Ruft den Wert der length-Eigenschaft ab.
     * 
     */
    public int getLength() {
        return length;
    }

    /**
     * Legt den Wert der length-Eigenschaft fest.
     * 
     */
    public void setLength(int value) {
        this.length = value;
    }

    /**
     * Gets the value of the arrayDataValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrayDataValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrayDataValue().add(newItem);
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
     * 
     * 
     */
    public List<Object> getArrayDataValue() {
        if (arrayDataValue == null) {
            arrayDataValue = new ArrayList<Object>();
        }
        return this.arrayDataValue;
    }

}
