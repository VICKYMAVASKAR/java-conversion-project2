package com.armedu.com.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomToArray {

	public static Document invoke(String path) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, Object> getArray(String path) {
		Document dom = invoke(path);
		Element root = dom.getDocumentElement();
		Map<String, Object> output = domNodeToArray(root);
		output.put("@root", root.getTagName());
		return output;
	}

	static Map<String, Object> domNodeToArray(Node node) {
		Map<String, Object> output = new HashMap<>();
		switch (node.getNodeType()) {
		case Node.CDATA_SECTION_NODE:
		case Node.TEXT_NODE:
			return Collections.singletonMap("text", node.getTextContent().trim());
		case Node.ELEMENT_NODE:
			NodeList childNodes = node.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				Map<String, Object> childMap = domNodeToArray(child);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					String tagName = child.getNodeName();
					if (!output.containsKey(tagName)) {
						output.put(tagName, new ArrayList<>());
					}
					((List<Object>) output.get(tagName)).add(childMap);
				} else if (childMap.containsKey("text")) {
					output.put("text", childMap.get("text"));
				}
			}
			if (node.hasAttributes()) {
				if (!output.containsKey("text")) {
					output = new HashMap<>(Map.of("@content", output));
				}
				NamedNodeMap attributes = node.getAttributes();
				Map<String, String> attrMap = new HashMap<>();
				for (int i = 0; i < attributes.getLength(); i++) {
					Attr attr = (Attr) attributes.item(i);
					attrMap.put(attr.getName(), attr.getValue());
				}
				output.put("@attributes", attrMap);
			}
			break;
		}
		return output;
	}

	public static List<Map<String, Object>> searchMultDim(List<Map<String, Object>> arr, String field, String value) {
		if (arr != null && !arr.isEmpty()) {
			return arr.stream().filter(row -> row.containsKey(field) && row.get(field).equals(value))
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public static Object searchMultDimMultiVal(List<Map<String, Object>> arr, String value, String role) {
		List<Map<String, Object>> foundKey = arr.stream()
				.filter(element -> element.containsKey("role") && element.get("role").equals(role))
				.collect(Collectors.toList());

		if (!foundKey.isEmpty()) {
			switch (role) {
			case "http://www.eba.europa.eu/xbrl/role/dpm-db-id":
				List<Map<String, Object>> result = searchMultDim(foundKey, "from", value);
				if (!result.isEmpty()) {
					return result.get(0).get("@content");
				}
				break;
			}
		}
		return null;
	}

	public static Map<String, List<String>> getPath(String path, List<String> string, String returnStr) {
		Map<String, List<String>> dir = new HashMap<>();
		try {
			File folder = new File(path);
			List<File> files = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
			List<String> ext = List.of("xsd");

			for (File file : files) {
				if (file.isDirectory())
					continue;
				String content = file.getPath();
				String extension = content.substring(content.lastIndexOf(".") + 1);

				for (String str : string) {
					if (content.contains(str) && ext.contains(extension)) {
						if (returnStr == null) {
							dir.computeIfAbsent(str, k -> new ArrayList<>()).add(content);
						} else {
							return Map.of(str, List.of(content));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dir;
	}

	public static String buildUrl(Map<String, String> parts) {
		String scheme = parts.getOrDefault("scheme", "") + "://";
		String host = Config.OWNER;
		String port = parts.containsKey("port") ? ":" + parts.get("port") : "";
		String user = parts.getOrDefault("user", "");
		String path = parts.getOrDefault("path", "");
		String query = parts.containsKey("query") ? "?" + parts.get("query") : "";
		String fragment = parts.containsKey("fragment") ? "#" + parts.get("fragment") : "";

		return String.join("", scheme, user, host, port, path, query, fragment);
	}

	public static boolean strposArr(String haystack, List<String> needle) {
		for (String n : needle) {
			if (haystack.contains(n)) {
				return true;
			}
		}
		return false;
	}

	public static Map<String, Object> multidimensionalArrToSingle(Map<String, Object> array) {
		if (array == null) {
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		array.forEach((key, value) -> {
			if (value instanceof Map) {
				result.putAll(multidimensionalArrToSingle((Map<String, Object>) value));
			} else {
				result.put(key, value);
			}
		});
		return result;
	}
	
}
