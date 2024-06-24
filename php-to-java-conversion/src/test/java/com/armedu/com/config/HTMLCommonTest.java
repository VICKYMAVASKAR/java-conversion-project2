package com.armedu.com.config;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class HTMLCommonTest {

    @Test
    void testApiVersion() {
        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap(), 0);
        assertEquals(1.7, htmlCommon.apiVersion());
    }
    

	 @Test
	    void testGetAttribute() {
	        Map<String, String> attributes = new HashMap<>();
	        attributes.put("attr1", "value1");
	        HTMLCommon htmlCommon = new MockHTMLCommon(attributes, 0);

	        assertEquals("value1", htmlCommon.getAttribute("attr1"));
	        assertNull(htmlCommon.getAttribute("nonExistingAttr"));
	    }

	    @Test
	    void testSetAttribute() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);

	        htmlCommon.setAttribute("attr1", "value1");
	        assertEquals("value1", htmlCommon.getAttribute("attr1"));

	        // Test null value case
	        htmlCommon.setAttribute("attr2", null);
	        assertEquals("attr2", htmlCommon.getAttribute("attr2"));
	    }

	    @Test
	    void testUpdateAttributes() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);

	        htmlCommon.updateAttributes("attr1=\"value1\"");
	        assertEquals("value1", htmlCommon.getAttribute("attr1"));

	        htmlCommon.updateAttributes("attr2=\"value2\"");
	        assertEquals("value2", htmlCommon.getAttribute("attr2"));
	    }

	    @Test
	    void testRemoveAttribute() {
	        Map<String, String> attributes = new HashMap<>();
	        attributes.put("attr1", "value1");
	        HTMLCommon htmlCommon = new MockHTMLCommon(attributes, 0);

	        htmlCommon.removeAttribute("attr1");
	        assertNull(htmlCommon.getAttribute("attr1"));
	    }

	    @Test
	    void testGetLineEnd() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        htmlCommon.setLineEnd("\r\n");
	        assertEquals("\r\n", htmlCommon.getLineEnd());
	    }

	    @Test
	    void testGetTab() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        htmlCommon.setTab("\t");
	        assertEquals("\t", htmlCommon.getTab());
	    }
	    @Test
	    void testGetTabs() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 3);
	        htmlCommon.setTab("\t");
	        assertEquals("\t\t\t", htmlCommon.getTabs());
	    }

	    @Test
	    void testGetAttributesAsString() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        htmlCommon.setAttribute("attr1", "value1");
	        htmlCommon.setAttribute("attr2", "value2");

	        assertEquals(" attr2=\"value2\" attr1=\"value1\"", htmlCommon.getAttributes(true));
	    }

	    @Test
	    void testGetAttributesAsMap() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        htmlCommon.setAttribute("attr1", "value1");
	        htmlCommon.setAttribute("attr2", "value2");

	        Map<String, String> expectedAttributes = new HashMap<>();
	        expectedAttributes.put("attr1", "value1");
	        expectedAttributes.put("attr2", "value2");

	        assertEquals(expectedAttributes, htmlCommon.getAttributes(false));
	    }

	    @Test
	    void testSetLineEnd() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        
	        htmlCommon.setLineEnd("win");
	        assertEquals("\r\n", htmlCommon.getLineEnd());

	        htmlCommon.setLineEnd("unix");
	        assertEquals("\n", htmlCommon.getLineEnd());

	        htmlCommon.setLineEnd("mac");
	        assertEquals("\r", htmlCommon.getLineEnd());

	        htmlCommon.setLineEnd("custom");
	        assertEquals("custom", htmlCommon.getLineEnd());
	    }

	    @Test
	    void testGetTabOffset() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 5);
	        assertEquals(5, htmlCommon.getTabOffset());
	    }

	    @Test
	    void testSetTab() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        htmlCommon.setTab("  ");
	        assertEquals("  ", htmlCommon.getTab());
	    }

	    @Test
	    void testSetAndGetComment() {
	        HTMLCommon htmlCommon = new MockHTMLCommon(new HashMap<>(), 0);
	        htmlCommon.setComment("Sample comment");
	        assertEquals("Sample comment", htmlCommon.getComment());
	    }

	    @Test
	    void testCharset() {
	        assertEquals("ISO-8859-1", HTMLCommon.charset(null));
	        assertEquals("UTF-8", HTMLCommon.charset("UTF-8"));
	        assertEquals("UTF-8", HTMLCommon.charset(null));
	    }

	    
}
