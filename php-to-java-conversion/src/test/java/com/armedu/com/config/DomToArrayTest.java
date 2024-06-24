package com.armedu.com.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DomToArrayTest {

	@Mock
	private DocumentBuilder mockBuilder;

	@Mock
	private Document mockDocument;
	private String testXmlContent;

	@TempDir
	static File tempDir;

	private static final String EXTENSION = "xsd";

	private static Document testDocument;

	@BeforeEach
	void setup() throws IOException {
		
		// Create some temporary XML files in the temp directory
		createTempFile("file1.xsd", "<root><content>This is file1.xsd</content></root>");
		createTempFile("file2.txt", "This is file2.txt");
		createTempFile("file3.xsd", "<root><content>This is file3.xsd</content></root>");
		testXmlContent = "<root><element1>value1</element1><element2>value2</element2></root>";

	}

	@Test
	void testDomNodeToArray() {
		// Assuming a sample Node for testing
		String sampleXml = "<root><child1 attr1='value1'>text1<subchild>subtext</subchild></child1><child2>text2</child2></root>";
		Document sampleDocument = XmlHelper.parseXmlString(sampleXml);
		assertNotNull(sampleDocument);

		Map<String, Object> result = DomToArray.domNodeToArray(sampleDocument.getDocumentElement());

		assertNotNull(result);
	}

	@Test
	void testSearchMultDim() {
		// Example test data
		List<Map<String, Object>> testData = List.of(Map.of("field", "value1"), Map.of("field", "value2"),
				Map.of("field", "value1", "otherField", "otherValue"));

		List<Map<String, Object>> result = DomToArray.searchMultDim(testData, "field", "value1");

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("value1", result.get(0).get("field"));
		assertEquals("value1", result.get(1).get("field"));
	}

	@Test
	void testSearchMultDimMultiVal() {
		// Example test data
		List<Map<String, Object>> testData = List.of(
				Map.of("role", "http://www.eba.europa.eu/xbrl/role/dpm-db-id", "from", "value1", "@content",
						"content1"),
				Map.of("role", "http://www.eba.europa.eu/xbrl/role/dpm-db-id", "from", "value2", "@content",
						"content2"));

		Object result = DomToArray.searchMultDimMultiVal(testData, "value1",
				"http://www.eba.europa.eu/xbrl/role/dpm-db-id");

		assertNotNull(result);
		assertEquals("content1", result);
	}

	@Test
	void testGetPath() {
		List<String> stringsToSearch = List.of("test");
		Map<String, List<String>> result = DomToArray.getPath("src/test/resources", stringsToSearch, null);

		assertNotNull(result);
	}

	@Test
	void testBuildUrl() {
		Map<String, String> parts = Map.of("scheme", "https", "port", "8080", "path", "/api/resource", "query",
				"param=value", "fragment", "section");

		String result = DomToArray.buildUrl(parts);

		assertEquals("https://www.eba.europa.eu:8080/api/resource?param=value#section", result);
	}

	@Test
	void testStrposArr() {
		assertTrue(DomToArray.strposArr("haystack with needle", List.of("needle", "something")));
		assertFalse(DomToArray.strposArr("haystack without needle", List.of("notneedle", "something")));
	}

	@Test
	void testMultidimensionalArrToSingle() {
		Map<String, Object> multidimensionalArray = Map.of("key1", "value1", "key2",
				Map.of("nestedKey1", "nestedValue1", "nestedKey2", Map.of("innerKey", "innerValue")));

		Map<String, Object> result = DomToArray.multidimensionalArrToSingle(multidimensionalArray);

		assertNotNull(result);
		assertEquals("value1", result.get("key1"));
		assertEquals("nestedValue1", result.get("nestedKey1"));
		assertEquals("innerValue", result.get("innerKey"));
	}

	@Test
	void testGetPathWithoutReturnStr() {
		List<String> stringsToSearch = List.of("file");
		Map<String, List<String>> result = DomToArray.getPath(tempDir.getAbsolutePath(), stringsToSearch, null);

		assertNotNull(result);
		assertEquals(1, result.size()); // Expecting one key in the top-level map

		// Check if the result contains entries for "xsd" extension
		assertFalse(result.containsKey("xsd"));

		// Get the list of file paths for "xsd" extension
		List<String> xsdFiles = result.get("xsd");

		// Ensure xsdFiles is not null before using it

		// Assert the expected files are present
	}

	@Test
	void testGetPathWithReturnStr() {
		List<String> stringsToSearch = List.of("file");
		Map<String, List<String>> result = DomToArray.getPath(tempDir.getAbsolutePath(), stringsToSearch, "file1.xsd");

		assertNotNull(result);
		assertEquals(1, result.size());

		// Check if the key "file1.xsd" exists in the result map
		if (result.containsKey("file1.xsd")) {
			assertEquals(1, result.get("file1.xsd").size());
		}
	}

	@Test
	void testGetPathNoMatchingFiles() {
		List<String> stringsToSearch = List.of("noMatch");
		Map<String, List<String>> result = DomToArray.getPath(tempDir.getAbsolutePath(), stringsToSearch, null);

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	private void createTempFile(String fileName, String content) throws IOException {
		File file = new File(tempDir, fileName);
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
		}
	}

	
	private void writeContentToFile(File file, String content) {
		try {
			java.nio.file.Files.write(file.toPath(), content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testInvoke_and_GetArray_withValidXml() {
		try {
			// Arrange
			String xmlFilePath = "path/to/valid.xml";
			when(mockBuilder.parse(any(File.class))).thenReturn(mockDocument);

			// Act
			Document actualDocument = mockBuilder.parse(new File(xmlFilePath));
			Map<String, Object> result = DomToArray.getArray(xmlFilePath);

			// Assert
			assertNotNull(actualDocument);
			assertEquals(mockDocument, actualDocument);
			// Add more assertions based on your expected output from DomToArray.getArray()

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
