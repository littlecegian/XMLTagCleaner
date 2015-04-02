import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

class RemoveTags{
	
	public static void main(String[] args) throws Exception{
		String filename, tags[];
		if(args.length < 2){
			System.out.println("jar needs atleast one input XML file and one tag to purge as inputs. Run the file as \"filename.jar <path_to_xml> <tag1> <tag2> . . \"");
			return; 
		}
		filename = args[0];
		tags = Arrays.copyOfRange(args, 1, args.length);
		
		System.out.println(args[0]);
		System.out.println(args[1]);
		
		DocumentBuilderFactory  docFactory  = DocumentBuilderFactory.newInstance();
		docFactory.setValidating(false);
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	    Document doc = docBuilder.parse(new FileInputStream(new File(filename)));
		
	    for(int i=0; i<tags.length;i++){
			NodeList nodes = doc.getElementsByTagName(tags[i]);
			
			while(nodes.getLength() > 0){
			    Node node = nodes.item(0);
			    node.getParentNode().removeChild(node);
			}
		}
		doc.normalize();
		
		prettyPrint(doc);
	}
	
	public static final void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        //System.out.println(out.toString());
        PrintWriter writer = new PrintWriter("trimmedXML.xml", "UTF-8");
        writer.println(out.toString());
        writer.close();
  	}

}