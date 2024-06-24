package com.armedu.com.config;

import java.util.HashMap;
import java.util.Map;

public abstract class HTMLCommon {

    // Associative array of attributes
    protected static Map<String, String> _attributes = new HashMap<>();

    // Tab offset of the tag
    private int _tabOffset = 0;

    // Tab string
    private String _tab = "\t";

    // Contains the line end string
    private String _lineEnd = "\n";

    // HTML comment on the object
    private String _comment = "";

    // Static charset value
    private static String charset = "ISO-8859-1";

    /**
     * Class constructor
     * @param attributes Associative array of table tag attributes or HTML attributes name="value" pairs
     * @param tabOffset Indent offset in tabs
     */
    public HTMLCommon(Map<String, String> attributes, int tabOffset) {
        this.setAttributes(attributes);
        this.setTabOffset(tabOffset);
    }
	/**
     * Returns the current API version
     * @return double
     */
    public double apiVersion() {
        return 1.7;
    }

    /**
     * Returns the lineEnd
     * @return String
     */
    String getLineEnd() {
        return this._lineEnd;
    }

    /**
     * Returns a string containing the unit for indenting HTML
     * @return String
     */
    String getTab() {
        return this._tab;
    }

    /**
     * Returns a string containing the offset for the whole HTML code
     * @return String
     */
    String getTabs() {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < this._tabOffset; i++) {
            tabs.append(this.getTab());
        }
        return tabs.toString();
    }

    /**
     * Returns an HTML formatted attribute string
     * @param attributes Map of attributes
     * @return String
     */
    private String getAttrString(Map<String, String> attributes) {
        StringBuilder strAttr = new StringBuilder();

        if (attributes != null) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                strAttr.append(" ").append(entry.getKey()).append("=\"")
                        .append(entry.getValue()).append("\"");
            }
        }
        return strAttr.toString();
    }

    /**
     * Returns a valid attributes array from either a string or array
     * @param attributes Either a typical HTML attribute string or an associative array
     * @return Map<String, String>
     */
    private Map<String, String> parseAttributes(Object attributes) {
        Map<String, String> ret = new HashMap<>();

        if (attributes instanceof Map) {
            Map<?, ?> attrMap = (Map<?, ?>) attributes;
            for (Map.Entry<?, ?> entry : attrMap.entrySet()) {
                String key = entry.getKey().toString().toLowerCase();
                String value = entry.getValue().toString();
                ret.put(key, value);
            }
        } else if (attributes instanceof String) {
            String attrString = (String) attributes;
            String[] attrPairs = attrString.split("\\s+");
            for (String attrPair : attrPairs) {
                String[] keyValue = attrPair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0].toLowerCase();
                    String value = keyValue[1].replaceAll("\"", "");
                    ret.put(key, value);
                } else if (keyValue.length == 1) {
                    String key = keyValue[0].toLowerCase();
                    ret.put(key, key);
                }
            }
        }
        return ret;
    }

    /**
     * Returns the value of the given attribute
     * @param attr Attribute name
     * @return String|null returns null if an attribute does not exist
     */
    public String getAttribute(String attr) {
        return this._attributes.getOrDefault(attr.toLowerCase(), null);
    }

    /**
     * Sets the value of the attribute
     * @param name Attribute name
     * @param value Attribute value (will be set to name if omitted)
     */
    public void setAttribute(String name, String value) {
        name = name.toLowerCase();
        if (value == null) {
            value = name;
        }
        this._attributes.put(name, value);
    }

    /**
     * Sets the HTML attributes
     * @param attributes Either a typical HTML attribute string or an associative array
     */
    public void setAttributes(Object attributes) {
        this._attributes = this.parseAttributes(attributes);
    }

    /**
     * Returns the assoc array (default) or string of attributes
     * @param asString Whether to return the attributes as string
     * @return Map<String, String> or String
     */
    public Object getAttributes(boolean asString) {
        if (asString) {
            return this.getAttrString(this._attributes);
        } else {
            return this._attributes;
        }
    }

    /**
     * Updates the passed attributes without changing the other existing attributes
     * @param attributes Either a typical HTML attribute string or an associative array
     */
    public void updateAttributes(Object attributes) {
        this._attributes.putAll(this.parseAttributes(attributes));
    }

    /**
     * Removes an attribute
     * @param attr Attribute name
     */
    public void removeAttribute(String attr) {
        this._attributes.remove(attr.toLowerCase());
    }

    /**
     * Sets the line end style to Windows, Mac, Unix or a custom string.
     * @param style "win", "mac", "unix" or custom string.
     */
    public void setLineEnd(String style) {
        switch (style) {
            case "win":
                this._lineEnd = "\r\n";
                break;
            case "unix":
                this._lineEnd = "\n";
                break;
            case "mac":
                this._lineEnd = "\r";
                break;
            default:
                this._lineEnd = style;
                break;
        }
    }

    /**
     * Sets the tab offset
     * @param offset int
     */
    public void setTabOffset(int offset) {
        this._tabOffset = offset;
    }

    /**
     * Returns the tabOffset
     * @return int
     */
    public int getTabOffset() {
        return this._tabOffset;
    }

    /**
     * Sets the string used to indent HTML
     * @param string String used to indent ("\t", "\t", "  ", etc.).
     */
    public void setTab(String string) {
        this._tab = string;
    }

    /**
     * Sets the HTML comment to be displayed at the beginning of the HTML string
     * @param comment String
     */
    public void setComment(String comment) {
        this._comment = comment;
    }

    /**
     * Returns the HTML comment
     * @return String
     */
    public String getComment() {
        return this._comment;
    }

    /**
     * Abstract method. Must be extended to return the object's HTML
     * @return String
     */
    public abstract String toHtml();

    /**
     * Displays the HTML to the screen
     */
    public void display() {
        System.out.print(this.toHtml());
    }

    /**
     * Sets the charset to use by htmlspecialchars() function
     * Since this parameter is expected to be global, the function is designed
     * to be called statically
     * @param newCharset New charset to use. Omit if just getting the current value.
     * @return String Current charset
     */
    public static String charset(String newCharset) {
        if (newCharset != null) {
            charset = newCharset;
        }
        return charset;
    }
}
