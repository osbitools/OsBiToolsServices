//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.07 at 01:35:19 AM EDT 
//


package com.osbitools.ws.shared.binding.ds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExColumns complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExColumns">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auto_inc" type="{}AutoIncColumns" minOccurs="0"/>
 *         &lt;element name="calc" type="{}CalcColumns" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExColumns", propOrder = {
    "autoInc",
    "calc"
})
public class ExColumns {

    @XmlElement(name = "auto_inc")
    protected AutoIncColumns autoInc;
    protected CalcColumns calc;

    /**
     * Gets the value of the autoInc property.
     * 
     * @return
     *     possible object is
     *     {@link AutoIncColumns }
     *     
     */
    public AutoIncColumns getAutoInc() {
        return autoInc;
    }

    /**
     * Sets the value of the autoInc property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoIncColumns }
     *     
     */
    public void setAutoInc(AutoIncColumns value) {
        this.autoInc = value;
    }

    /**
     * Gets the value of the calc property.
     * 
     * @return
     *     possible object is
     *     {@link CalcColumns }
     *     
     */
    public CalcColumns getCalc() {
        return calc;
    }

    /**
     * Sets the value of the calc property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalcColumns }
     *     
     */
    public void setCalc(CalcColumns value) {
        this.calc = value;
    }

}
