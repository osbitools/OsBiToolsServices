//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.07 at 01:35:19 AM EDT 
//


package com.osbitools.ws.shared.binding.ds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SqlParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SqlParameter">
 *   &lt;complexContent>
 *     &lt;extension base="{}OrderedList">
 *       &lt;attribute name="req_param" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SqlParameter")
public class SqlParameter
    extends OrderedList
{

    @XmlAttribute(name = "req_param")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object reqParam;

    /**
     * Gets the value of the reqParam property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getReqParam() {
        return reqParam;
    }

    /**
     * Sets the value of the reqParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setReqParam(Object value) {
        this.reqParam = value;
    }

}
