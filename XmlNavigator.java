package abc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlNavigator { 
	public static List<Node> navigateXML2(Document document, String path)
	{
		if(document==null || path==null)
    	{
    		return null;
    	}
        String[] elements = path.split("\\.");

        Node currentNode = document.getDocumentElement(); // Start from the root element
        NodeList nodeList = currentNode.getChildNodes();
        List<Node> tempList = convertNodeListToListOfNodes(nodeList);
        List<Node> result = new ArrayList<Node>();
        for(String element: elements)
        {
        	System.out.println("Element = " + element);        	
        	if(!result.isEmpty())
        	{
        		// This is not the first iteration of the loop.
        		tempList = tempList.stream()
            	        .map(node -> convertNodeListToListOfNodes(node.getChildNodes()))
            	        .flatMap(List::stream)
            	        .collect(Collectors.toList());
        	}
        	
        	tempList = tempList
        				.stream()
        				.filter(s->s.getNodeType()==Node.ELEMENT_NODE)
        				.filter(s-> s.getNodeName().equals(element))
        				.collect(Collectors.toList());
        	System.out.println("Elements matching " + element + " = " + tempList.size());
        	
            if (tempList.isEmpty()) {
                // If no matching nodes found, break out of the loop
                break;
            }
            else
            {
            	result.clear();
            	result.addAll(tempList);
            }
        }
        if(result.isEmpty())
        {
        	return null;
        }
        return result;
	}
	
	
	
    private static List<Node> convertNodeListToListOfNodes(NodeList nodeList) {
		if(nodeList==null) return null;
		List<Node> result=new ArrayList<Node>();
		for(int i=0;i<nodeList.getLength(); i++)
		{
			result.add(nodeList.item(i));
			
		}
		return result;
	}
	
    private static int countElementChildNodes(NodeList nodeList)
    {
    	int count=0;
    	for (int i = 0; i < nodeList.getLength(); i++) {
        	Node childNode = nodeList.item(i);
        	if (childNode.getNodeType() == Node.ELEMENT_NODE)
        	{
        		count++;
        	}
    	}
    	return count;
    }
    private static String findTagNameInChildren(List<Node> nodes, String tagName) {
//    	System.out.println("Searching for " + tagName + " in " + nodes.size() + " nodes");
    	for(Node node: nodes)
    	{
	        NodeList childNodes = node.getChildNodes();
//	        System.out.println("Number of child nodes = " + countElementChildNodes(childNodes));
	        for (int i = 0; i < childNodes.getLength(); i++) {
	        	Node childNode = childNodes.item(i);
	        	if (childNode.getNodeType() == Node.ELEMENT_NODE)
	        	{
		            
//		            System.out.println("childNode.getNodeName()="+childNode.getNodeName());
//		            System.out.println("childNode.getNodeType()="+childNode.getNodeType());
//		            System.out.println("childNode.hasChildNodes()="+childNode.hasChildNodes());
//		            System.out.println("**************************************************");
		            int childCount = countElementChildNodes (childNode.getChildNodes());
		            if (childNode.getNodeName().equals(tagName) && childCount==0) {
//		            	System.out.println(tagName + " found..Returning  "+ childNode.getTextContent());
		                return childNode.getTextContent();
		            }
	        	}
	        }
    	}

        // Tag not found, return an appropriate message or handle accordingly
        return null;
    }

    public static Document parseXML(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputStream);
    }

    public static void main(String[] args) throws Exception {
        String xmlContent = "<Message>"
                + "    <WorkInformation>"
                + "        <Address2>"
                + "            <FullAddress>T9-103</FullAddress>"
                + "        </Address2>"
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

        Document document = parseXML(inputStream);
        String xmlPath = "WorkInformation.Address";

        // Example invocation:
        List<Node> result = navigateXML2(document, xmlPath);
        System.out.println(result);
    }
}
