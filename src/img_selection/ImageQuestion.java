/*
 * XML string which formulates the question on
 * Amazon Mechanical Turk.
 */

package img_selection;

import java.util.ArrayList;

public class ImageQuestion {
    private String question = "";

    ImageQuestion(ArrayList<String> imageUrl, int f, int g, int o) {

	// check the size of imageUrl
	if (imageUrl.size() != f * g) {
	    System.err.println("The size of input array is not equal to f*g");
	} else {
	    // the iframe hight is adjustable
	    question += "<?xml version=\"1.0\"?>\n";
	    question += "<ExternalQuestion xmlns=\"http://mechanicalturk.amazonaws.com/AWSMechanicalTurkDataSchemas/2006-07-14/ExternalQuestion.xsd\">\n";
	    question += "<ExternalURL>http://users.soe.ucsc.edu/~khuang/externalpage3.htm?";
	    question += "f=" + f + "&amp;";
	    question += "g=" + g + "&amp;";
	    question += "o=" + o + "&amp;";
	    for (int i = 0; i < imageUrl.size(); i++) {
		question += "url" + i + "=" + imageUrl.get(i) + "&amp;";
	    }
	    question += "</ExternalURL>\n";
	    int hight = 290;
	    question += "<FrameHeight>"
		    + (int) (300 + 150 + 480 + g * Math.ceil(((double) f) / 2)
			    * hight) + "</FrameHeight>\n";
	    question += "</ExternalQuestion>";
	}
    }

    public String getQuestion() {
	return this.question;
    }
}
