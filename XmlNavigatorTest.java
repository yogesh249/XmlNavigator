package abc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlNavigatorTest {

    private Document document;

    @BeforeEach
    void setUp() throws Exception {
        // Set up your XML document as needed
        String xmlContent = "<Message>"
                + "    <WorkInformation>"
                + "        <Address2>"
                + "            <FullAddress>T9-103</FullAddress>"
                + "        </Address2>"
                + "    </WorkInformation>"
                + "    <WorkInformation>"
                + "        <Address>"
                + "            <fullAddress>T9-104</fullAddress>"
                + "        </Address>"
                + "    </WorkInformation>"
                + "    <WorkInformation>"
                + "        <Address>"
                + "            <FullAddress>T9-104</FullAddress>"
                + "        </Address>"
                + "    </WorkInformation>"
                + "    <WorkInformation>"
                + "        <Address>T9-108</Address>"
                + "    </WorkInformation>"
                + "    <Address>"
                + "            <FullAddress>T9-105</FullAddress>"
                + "    </Address>"
                + "    <Address>T9-102</Address>"
                + "    <Address>T9-104</Address>"
                + "</Message>";
        InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes());
        document = XmlNavigator.parseXML(inputStream);
    }

    @Test
    void testNavigateXMLCase1() {
        String xmlPath = "WorkInformation.Address";
        String tagName = "FullAddress";
        String expected = "T9-104";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);

        assertEquals(expected, result);
    }
    @Test
    void testNavigateXMLCase2() {
        String xmlPath = "WorkInformation.Address2";
        String tagName = "FullAddress";
        String expected = "T9-103";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);

        assertEquals(expected, result);
    }

    @Test
    void testNavigateXMLCase3() {
        String xmlPath = "Address";
        String tagName = "FullAddress";
        String expected = "T9-105";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);

        assertEquals(expected, result);
    }



    @Test
    void testNavigateXMLCase5() {
        String xmlPath = "WorkInformation";
        String tagName = "Address";
        String expected = "T9-108";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);

        assertEquals(expected, result);
    }

    @Test
    void testNavigateXMLCase6() {
        String xmlPath = "Message.WorkInformation.Address";
        String tagName = "FullAddress";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);
        assertNull(result);
    }

    @Test
    void testNavigateXMLCase7() {
        String xmlPath = "Message.WorkInformation";
        String tagName = "FullAddress";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);

        assertNull(result);
    }

    @Test
    void testNavigateXMLCase8() {
        String xmlPath = "WorkInformation";
        String tagName = "Address3";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);
        assertNull(result);
    }

    @Test
    void testNavigateXMLCase9() {
        String xmlPath = "WorkInformation.Address";
        String tagName = "InvalidTag";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);

        assertNull(result);
    }

    @Test
    void testNavigateXMLCase10() {
        String xmlPath = "InvalidPath";
        String tagName = "FullAddress";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);
        assertNull(result);
    }
    @Test
    void testNavigateXMLCase11() {
        String xmlPath = "InvalidPath1.InvalidPath2";
        String tagName = "FullAddress";

        String result = XmlNavigator.navigateXML(document, xmlPath, tagName);
        assertNull(result);
    }
}
