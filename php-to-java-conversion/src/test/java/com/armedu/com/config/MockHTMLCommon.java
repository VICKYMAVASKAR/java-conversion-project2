package com.armedu.com.config;

import java.util.Map;

// Mock implementation for testing
public class MockHTMLCommon extends HTMLCommon {
    public MockHTMLCommon(Map<String, String> attributes, int tabOffset) {
        super(attributes, tabOffset);
    }

    @Override
    public String toHtml() {
        // Implement a mock HTML output for testing purposes
        return "<div></div>";
    }
}
