package com.armedu.com.config;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void testMonetaryItem() {
        assertEquals("EUR", Config.MONETARY_ITEM);
    }

    @Test
    void testLang() {
        assertEquals("en", Config.LANG.get("0"));
        assertEquals("bs-Latn-BA", Config.LANG.get("1"));
        assertEquals("ba", Config.LANG.get("2"));
    }

    @Test
    void testConfSet() {
        assertEquals("lab-codes", Config.CONF_SET.get("lab-codes"));
        assertEquals("rend", Config.CONF_SET.get("rend"));
        assertEquals("def", Config.CONF_SET.get("def"));
        assertEquals("pre", Config.CONF_SET.get("pre"));
        assertEquals("tab", Config.CONF_SET.get("tab"));
    }

    @Test
    void testModuleSet() {
        assertEquals("pre", Config.MODULE_SET.get("pre"));
        assertEquals("rend", Config.MODULE_SET.get("rend"));
        assertEquals("lab-codes", Config.MODULE_SET.get("lab-codes"));
    }

    @Test
    void testCreateInstance() {
        assertEquals("rend", Config.CREATE_INSTANCE.get("rend"));
        assertEquals("def", Config.CREATE_INSTANCE.get("def"));
    }

    @Test
    void testOwner() {
        assertEquals("www.eba.europa.eu", Config.OWNER);
    }

    @Test
    void testGetPublicDir() {
        String expectedPath = System.getProperty("user.dir") + java.io.File.separator + "storage" + java.io.File.separator + "app/public/tax/";
        assertEquals(expectedPath, Config.getPublicDir());
    }

    @Test
    void testPrefixOwner() {
        assertEquals("fba", Config.PREFIX_OWNER);
    }

    @Test
    void testGetLogoPath() {
        String expectedPath = System.getProperty("user.dir") + java.io.File.separator + "public" + java.io.File.separator + "images" + java.io.File.separator + "logo.svg";
        assertEquals(expectedPath, Config.getLogoPath());
    }

    @Test
    void testGetOwners() {
        Map<String, Map<String, String>> owners = Config.getOwners();

        assertEquals("http://www.fba.ba", owners.get("fba").get("namespace"));
        assertEquals("http://www.fba.ba/xbrl", owners.get("fba").get("officialLocation"));
        assertEquals("fba", owners.get("fba").get("prefix"));
        assertEquals("(C) FBA", owners.get("fba").get("copyright"));

        assertEquals("http://www.eba.europa.eu/xbrl/crr", owners.get("eba").get("namespace"));
        assertEquals("http://www.eba.europa.eu/eu/fr/xbrl/crr", owners.get("eba").get("officialLocation"));
        assertEquals("eba", owners.get("eba").get("prefix"));
        assertEquals("(C) EBA", owners.get("eba").get("copyright"));

        assertEquals("http://www.auditchain.finance/", owners.get("audt").get("namespace"));
        assertEquals("http://www.auditchain.finance/fr/dpm", owners.get("audt").get("officialLocation"));
        assertEquals("audt", owners.get("audt").get("prefix"));
        assertEquals("(C) Auditchain", owners.get("audt").get("copyright"));
    }

    @Test
    void testGetTmpPdfDir() {
        String expectedPath = System.getProperty("user.dir") + java.io.File.separator + "storage" + java.io.File.separator + "logs" + java.io.File.separator;
        assertEquals(expectedPath, Config.getTmpPdfDir());
    }

    @Test
    void testStoragePath() {
        String expectedPath = System.getProperty("user.dir") + java.io.File.separator + "storage" + java.io.File.separator + "app/public/tax/";
        assertEquals(expectedPath, Config.getPublicDir());
    }

    @Test
    void testStoragePathWithoutArgs() {
        String expectedPath = System.getProperty("user.dir") + java.io.File.separator + "storage";
        assertEquals(expectedPath, Config.storagePath());
    }

    @Test
    void testPublicPath() {
        String expectedPath = System.getProperty("user.dir") + java.io.File.separator + "public";
        assertEquals(expectedPath, Config.publicPath());
    }
}
