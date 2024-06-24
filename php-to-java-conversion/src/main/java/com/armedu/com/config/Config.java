package com.armedu.com.config;

import java.util.HashMap;
import java.util.Map;

public class Config {

	public static final String MONETARY_ITEM = "EUR";

	public static final Map<String, String> LANG = new HashMap<>() {
		{
			put("0", "en");
			put("1", "bs-Latn-BA");
			put("2", "ba");
		}
	};

	public static final Map<String, String> CONF_SET = new HashMap<>() {
		{
			put("lab-codes", "lab-codes");
			put("rend", "rend");
			put("def", "def");
			put("pre", "pre");
			put("tab", "tab");
		}
	};

	public static final Map<String, String> MODULE_SET = new HashMap<>() {
		{
			put("pre", "pre");
			put("rend", "rend");
			put("lab-codes", "lab-codes");
		}
	};

	public static final Map<String, String> CREATE_INSTANCE = new HashMap<>() {
		{
			put("rend", "rend");
			put("def", "def");
		}
	};

	public static final String OWNER = "www.eba.europa.eu";

	public static String getPublicDir() {
		return storagePath("app/public/tax/");
	}

	public static final String PREFIX_OWNER = "fba";

	public static String getLogoPath() {
		return publicPath() + java.io.File.separator + "images" + java.io.File.separator + "logo.svg";
	}

	public static Map<String, Map<String, String>> getOwners() {
		Map<String, Map<String, String>> owners = new HashMap<>();
		owners.put("fba", new HashMap<>() {
			{
				put("namespace", "http://www.fba.ba");
				put("officialLocation", "http://www.fba.ba/xbrl");
				put("prefix", "fba");
				put("copyright", "(C) FBA");
			}
		});
		owners.put("eba", new HashMap<>() {
			{
				put("namespace", "http://www.eba.europa.eu/xbrl/crr");
				put("officialLocation", "http://www.eba.europa.eu/eu/fr/xbrl/crr");
				put("prefix", "eba");
				put("copyright", "(C) EBA");
			}
		});
		owners.put("audt", new HashMap<>() {
			{
				put("namespace", "http://www.auditchain.finance/");
				put("officialLocation", "http://www.auditchain.finance/fr/dpm");
				put("prefix", "audt");
				put("copyright", "(C) Auditchain");
			}
		});
		return owners;
	}

	public static String getTmpPdfDir() {
		return storagePath() + java.io.File.separator + "logs" + java.io.File.separator;
	}

	// Helper methods to mimic the PHP functions storage_path() and public_path()
	private static String storagePath(String path) {
		return System.getProperty("user.dir") + java.io.File.separator + "storage" + java.io.File.separator + path;
	}

	static String storagePath() {
		return System.getProperty("user.dir") + java.io.File.separator + "storage";
	}

	static String publicPath() {
		return System.getProperty("user.dir") + java.io.File.separator + "public";
	}
}
