package com.armedu.com.config;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static  org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HTMLTableStorageTest {

	private HTMLTableStorage table;

	@BeforeEach
	public void setUp() {
		table = new HTMLTableStorage(0, false);
		table.setRowCount(2); // Ensure enough rows for testing
		table.setColCount(2); // Ensure enough columns for testing
	}

	@Test
	public void testSetAndGetUseTGroups() {
		assertFalse(table.getUseTGroups());
		table.setUseTGroups(true);
		assertTrue(table.getUseTGroups());
	}

	@Test
	public void testSetAndGetAutoFill() {
		assertEquals("&nbsp;", table.getAutoFill());
		table.setAutoFill("FillValue");
		assertEquals("FillValue", table.getAutoFill());
	}

	@Test
	public void testSetAndGetAutoGrow() {
		assertTrue(table.getAutoGrow());
		table.setAutoGrow(false);
		assertFalse(table.getAutoGrow());
	}

	@Test
	public void testSetAndGetRowCount() {
		assertEquals(2, table.getRowCount());
		table.setRowCount(5);
		assertEquals(5, table.getRowCount());
	}

	@Test
	void testSetAndGetColCount() {
		assertEquals(2, table.getColCount(null));
		table.setColCount(3);
		assertEquals(3, table.getColCount(null));
	}

	@Test
	void testGetInvalidCellContents() {
		assertThrows(Exception.class, () -> table.getCellContents(10, 10));
	}

	@Test
	void testGetInvalidCellAttributes() {
		assertThrows(Exception.class, () -> table.getCellAttributes(10, 10));
	}

	  @Test
	    public void testDefaultInitialization() {
	        assertEquals("&nbsp;", table.getAutoFill());
	        assertTrue(table.getAutoGrow());
	        assertFalse(table.getUseTGroups());
	        assertEquals(2, table.getRowCount());
	        assertEquals(2, table.getColCount(null));
	  }


	    @Test
	    public void testSetCellContentsOutOfBounds() {
	        table.setRowCount(3);
	        table.setColCount(3);

	        assertThrows(IndexOutOfBoundsException.class, () -> {
	            table.setCellContents(5, 5, "Hello", "text"); // This should throw IndexOutOfBoundsException
	        });
	    }
	}


