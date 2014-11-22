//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.11.22 um 10:36:20 AM CET 
//


package org.openhab.binding.smarthomatic.internal.packetData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="Bits">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="32"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MinVal" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="MaxVal" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="DefaultVal" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
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
    "id",
    "description",
    "bits",
    "minVal",
    "maxVal",
    "defaultVal"
})
@XmlRootElement(name = "UIntValue")
public class UIntValue {

    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "Description", required = true)
    protected Object description;
    @XmlElement(name = "Bits")
    protected long bits;
    @XmlElement(name = "MinVal")
    @XmlSchemaType(name = "unsignedInt")
    protected long minVal;
    @XmlElement(name = "MaxVal")
    @XmlSchemaType(name = "unsignedInt")
    protected long maxVal;
    @XmlElement(name = "DefaultVal")
    @XmlSchemaType(name = "unsignedInt")
    protected Long defaultVal;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der description-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDescription(Object value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der bits-Eigenschaft ab.
     * 
     */
    public long getBits() {
        return bits;
    }

    /**
     * Legt den Wert der bits-Eigenschaft fest.
     * 
     */
    public void setBits(long value) {
        this.bits = value;
    }

    /**
     * Ruft den Wert der minVal-Eigenschaft ab.
     * 
     */
    public long getMinVal() {
        return minVal;
    }

    /**
     * Legt den Wert der minVal-Eigenschaft fest.
     * 
     */
    public void setMinVal(long value) {
        this.minVal = value;
    }

    /**
     * Ruft den Wert der maxVal-Eigenschaft ab.
     * 
     */
    public long getMaxVal() {
        return maxVal;
    }

    /**
     * Legt den Wert der maxVal-Eigenschaft fest.
     * 
     */
    public void setMaxVal(long value) {
        this.maxVal = value;
    }

    /**
     * Ruft den Wert der defaultVal-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDefaultVal() {
        return defaultVal;
    }

    /**
     * Legt den Wert der defaultVal-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDefaultVal(Long value) {
        this.defaultVal = value;
    }

}
