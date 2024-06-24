package com.armedu.com.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLTableStorage {

	private String _autoFill = "&nbsp;";
	private boolean _autoGrow = true;
	private List<List<Map<String, Object>>> _structure = new ArrayList<>();
	private int _rows = 0;
	private int _cols = 0;
	private int _nestLevel = 0;
	private boolean _useTGroups = false;

	public HTMLTableStorage(int tabOffset, boolean useTGroups) {
		// Constructor
		_useTGroups = useTGroups;
	}

	public void setUseTGroups(boolean useTGroups) {
		_useTGroups = useTGroups;
	}

	public boolean getUseTGroups() {
		return _useTGroups;
	}

	public void setAutoFill(String fill) {
		_autoFill = fill;
	}

	public String getAutoFill() {
		return _autoFill;
	}

	public void setAutoGrow(boolean grow) {
		_autoGrow = grow;
	}

	public boolean getAutoGrow() {
		return _autoGrow;
	}

	public void setRowCount(int rows) {
		_rows = rows;
	}

	public void setColCount(int cols) {
		_cols = cols;
	}

	public int getRowCount() {
		return _rows;
	}

	public int getColCount(Integer row) {
		if (row != null) {
			int count = 0;
			for (Map<String, Object> cell : _structure.get(row)) {
				if (cell != null) {
					count++;
				}
			}
			return count;
		}
		return _cols;
	}

	public void setRowType(int row, String type) {
		for (int counter = 0; counter < _cols; counter++) {
			Map<String, Object> cell = _structure.get(row).get(counter);
			if (cell != null) {
				cell.put("type", type);
			}
		}
	}

	public void setColType(int col, String type) {
		for (int counter = 0; counter < _rows; counter++) {
			Map<String, Object> cell = _structure.get(counter).get(col);
			if (cell != null) {
				cell.put("type", type);
			}
		}
	}

	public void setCellAttributes(int row, int col, Map<String, Object> attributes) {
		if (_structure.get(row).get(col) != null && _structure.get(row).get(col).equals("__SPANNED__")) {
			return;
		}
		// Adjust ends
		_structure.get(row).get(col).put("attr", attributes);
		// Update span grid
		updateSpanGrid(row, col);
	}

	public void updateCellAttributes(int row, int col, Map<String, Object> attributes) {
		Map<String, Object> cell = _structure.get(row).get(col);
		if (cell != null && !cell.equals("__SPANNED__")) {
			// Cast the attribute map to avoid type mismatch
			if (cell.get("attr") instanceof Map) {
				Map<String, Object> cellAttributes = (Map<String, Object>) cell.get("attr");
				if (cellAttributes != null) {
					cellAttributes.putAll(attributes);
				}
			}else {
				// Handle case where 'attr' is not a Map<String, Object>
				throw new IllegalArgumentException("'attr' is not a Map<String, Object>");
			}
			// Update span grid
			updateSpanGrid(row, col);
		}
	}

	public Map<String, Object> getCellAttributes(int row, int col) {
		Map<String, Object> cell = _structure.get(row).get(col);
		if (cell != null && !cell.equals("__SPANNED__")) {
			// Cast the attribute map to avoid type mismatch
			return (Map<String, Object>) cell.get("attr");
		}
		return null;
	}

	public void setCellContents(int row, int col, Object contents, String type) {
		if (_structure.get(row).get(col) != null && _structure.get(row).get(col).equals("__SPANNED__")) {
			return;
		}
		// Adjust ends
		_structure.get(row).get(col).put("contents", contents);
		_structure.get(row).get(col).put("type", type);
	}

	public Object getCellContents(int row, int col) {
		if (_structure.get(row).get(col) != null && _structure.get(row).get(col).equals("__SPANNED__")) {
			return null;
		}
		if (_structure.get(row).get(col) == null) {
			// Invalid table cell reference error handling
			return null;
		}
		return _structure.get(row).get(col).get("contents");
	}

	// Other methods like setHeaderContents, addRow, setRowAttributes, etc. would
	// follow similarly

	private void updateSpanGrid(int row, int col) {
		// Implement span grid logic here
}

	// Other private helper methods would be implemented as needed

	public static void main(String[] args) {
		HTMLTableStorage table = new HTMLTableStorage(0, false);
		// Use the methods to manipulate the table structure and content
	}
}
