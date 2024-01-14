package abc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertNotNull;
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
		        + "        <FullAddress>T9-105</FullAddress>"
		        + "    </Address>"
		        + "    <Address>T9-102</Address>"
		        + "    <Address>T9-104</Address>"
		        + "</Message>";


		InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes());
		document = XmlNavigator.parseXML(inputStream);
	}

	@Test
	void testnavigateXML2Case1() {
		String xmlPath = "WorkInformation.Address";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	void testnavigateXML2Case123() {
		String xmlPath = "WorkInformation.Address.FullAddress";
		String expected = "T9-104";

		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(expected, result.get(0).getTextContent());
	}

	@Test
	void testnavigateXML2Case2() {
		String xmlPath = "WorkInformation.Address2";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void testnavigateXML2Case3() {
		String xmlPath = "Address";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	void testnavigateXML2Case5() {
		String xmlPath = "WorkInformation";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);

		assertNotNull(result);
		assertEquals(4, result.size());
	}

	@Test
	void testnavigateXML2Case6() {
		String xmlPath = "Message.WorkInformation.Address";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNull(result);
	}

	@Test
	void testnavigateXML2Case7() {
		String xmlPath = "Message.WorkInformation";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);

		assertNull(result);
	}

	@Test
	void testnavigateXML2Case8() {
		String xmlPath = "WorkInformation";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNotNull(result);
		assertEquals(4, result.size());
	}

	@Test
	void testnavigateXML2Case9() {
		String xmlPath = "WorkInformation.Address";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);

		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	void testnavigateXML2Case10() {
		String xmlPath = "InvalidPath";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNull(result);

	}

	@Test
	void testnavigateXML2Case11() {
		String xmlPath = "InvalidPath1.InvalidPath2";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNull(result);
	}
	@Test
	void testnavigateXML2Case116() {
		String xmlPath = "InvalidPath1.InvalidPath2";
		List<Node> result = XmlNavigator.navigateXML2(document, xmlPath);
		assertNull(result);
	}
}
