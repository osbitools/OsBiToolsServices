/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2018-08-04
 * 
 */

package com.osbitools.ws.wp.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import com.osbitools.ws.wp.shared.binding.*;

/**
 * Set of tools for DataSet Maps test
 * 
 */
public class WebPageTestUtils {

  public static void checkDemoWebPage(WebPage wp) {
    assertEquals("Description doesn't match", "Test Web Page #1",
        wp.getDescr());
    assertEquals("Version max doesn't match", 1, wp.getVerMax());
    assertEquals("Version max doesn't match", 0, wp.getVerMin());
    assertEquals("Inc value doesn't match", 2, wp.getInc());
    assertEquals("Copyright value doesn't match", "OsBiTools", wp.getCopyright());
    
    // Test first panel
    ComponentPanels panels = wp.getPanels();
    assertNotNull("Panels is null", panels);
    
    List<ComponentPanel> plist = panels.getPanel();
    assertNotNull("List of panels is null", plist);
    assertEquals("Number of panels doesn't match", 1, plist.size());
    
    List<WebWidget> widgets = plist.get(0).getWwgList().
                    getWwgContainerOrWwgChartOrWwgControl();
    assertNotNull("List of widgets is null", widgets);
    assertEquals("Number of panels doesn't match", 1, widgets.size());
    
    WebWidget widget = widgets.get(0);
    assertEquals("Widget Type is not container", 
        WebWidgetContainer.class, widget.getClass());
    
    WebWidgetContainer wwc = (WebWidgetContainer) widget;
    assertEquals("Widget className doesn't match",
        "com.osbitools.containers.tab_box", wwc.getClassName());
    assertEquals("Widget index doesn't match", 0, wwc.getIdx());
    assertEquals("Widget uid doesn't match", 1, wwc.getUid());
    assertNull("Widget propsGroups doesn't match", wwc.getPropGroups());
    
    WebWidgetProperties wprops = wwc.getProps();
    assertNotNull("Widget properties is null", wprops);
    
    List<WebWidgetProperty> lprops = wprops.getProp();
    assertNotNull("List of widget properties is null", lprops);
    assertEquals("Number of widget properties doesn't match", 1, lprops.size());
    
    WebWidgetProperty wwp = lprops.get(0);
    assertEquals("Widget Property name doesn't match", "id", wwp.getName());
    assertEquals("Widget Property value doesn't match", "wp_test", wwp.getValue());
  }
}
