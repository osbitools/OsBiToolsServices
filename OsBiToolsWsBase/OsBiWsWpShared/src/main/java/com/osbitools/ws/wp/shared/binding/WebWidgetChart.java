//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.02 at 03:05:25 PM EDT 
//


package com.osbitools.ws.wp.shared.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WebWidgetChart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WebWidgetChart">
 *   &lt;complexContent>
 *     &lt;extension base="{}WebWidget">
 *       &lt;attribute name="engine" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WebWidgetChart")
public class WebWidgetChart
    extends WebWidget
{

    @XmlAttribute(name = "engine")
    protected String engine;

    /**
     * Gets the value of the engine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngine() {
        return engine;
    }

    /**
     * Sets the value of the engine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngine(String value) {
        this.engine = value;
    }

}
