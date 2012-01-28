package img_selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.mturk.addon.HITQuestion;
import com.amazonaws.mturk.requester.Assignment;
import com.amazonaws.mturk.requester.AssignmentStatus;
import com.amazonaws.mturk.requester.Comparator;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.HITStatus;
import com.amazonaws.mturk.requester.Locale;
import com.amazonaws.mturk.requester.QualificationRequirement;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.util.PropertiesClientConfig;

import com.amazonaws.mturk.addon.QAPValidator;
import com.amazonaws.mturk.dataschema.QuestionFormAnswers;
import com.amazonaws.mturk.dataschema.QuestionFormAnswersType;
import com.amazonaws.mturk.service.exception.ValidationException;

public class ImageSelectionHIT {

    // important parameters of this HIT
    private RequesterService service;
    private String ImageSelectionHITId;
    private int currentLevel;
    private int tag;
    private ArrayList<String> imageUrls;
    private int k1;// the number of assignments for a normal HIT
    private int k2;// the number of assignments for an extra HIT
    private int f;
    private int g;
    private int o;
    private double reward;

    // Defining the attributes of the HIT
    private String title = "Choose most clear images.";
    private String description = "You are supposed to choose one or more most clear images in each HIT.";

    private String keywords = "most clear images, academic, research, crowd-sourcing";
    private long assignmentDurationInSeconds = 60 * 30; // 30 min
    private long autoApprovalDelayInSeconds = 60; // 1 min
    private long lifetimeInSeconds = 60 * 60 * 24 * 7; // 1 week
    private String requesterAnnotation = "test#image_selection";

    // Defining the location of the externalized question (QAP) file.
    private String rootDir = System.getProperty("user.dir");
    private String alg;
    private String shuffleCount;
    private String logFile;
    private String hitLogFile;
    // private String answerLogFile;
    private ImageQuestion question;

    private static HashMap<String, String> rankMap;

    /**
     * 
     * @param service
     * @param alg
     * @param imageUrls
     * @param currentLevel
     * @param tag
     * @param k1
     *            the number of assignments for a normal HIT
     * @param k2
     *            the number of assignments for an extra HIT
     * @param f
     * @param g
     * @param o
     * @param reward
     *            the reward for each pictures
     */
    public ImageSelectionHIT(RequesterService service, String alg,
	    String shuffleCount, ArrayList<String> imageUrls, int currentLevel,
	    int tag, int k1, int k2, int f, int g, int o, double reward,
	    String logFile) {
	this.service = service;
	this.alg = alg;
	this.shuffleCount = shuffleCount;
	this.logFile = logFile;
	// this.logFile = rootDir + java.io.File.separator + alg + "_" +
	// logIndex + shuffleCount + "log.txt";
	// this.hitLogFile = rootDir + java.io.File.separator + alg + "_" +
	// shuffleCount + "hit_log.txt";
	// this.answerLogFile = rootDir + java.io.File.separator + alg + "_" +
	// shuffleCount + "answer_log.txt";
	this.imageUrls = imageUrls;
	this.currentLevel = currentLevel;
	this.tag = tag;
	this.k1 = k1;
	this.k2 = k2;
	this.f = f;
	this.g = g;
	this.o = o;
	this.reward = reward;
	this.question = new ImageQuestion(this.imageUrls, this.f, this.g,
		this.o);
	try {
	    HIT newHit = service
		    .createHIT(null, title, description, keywords,
			    question.getQuestion(), this.reward
				    * this.imageUrls.size(),
			    assignmentDurationInSeconds,
			    autoApprovalDelayInSeconds, lifetimeInSeconds,
			    this.k1, requesterAnnotation, null, null);
	    this.ImageSelectionHITId = newHit.getHITId();
	    String info = "HIT:" + newHit.getHITId() + "\n";
	    // info += "------tag = " + this.tag + "\n";
	    for (int i = 0; i < this.imageUrls.size(); i++) {
		info += "-" + this.imageUrls.get(i) + "\n";
	    }
	    info += service.getWebsiteURL() + "/mturk/preview?groupId="
		    + newHit.getHITTypeId() + "\n";
	    System.out.println(info);
	    // this.writeToLog(info, this.hitLogFile);
	    this.writeToLog(info, this.logFile);
	    System.out.println();
	} catch (Exception e) {
	    System.err.println(e.getLocalizedMessage());
	}

	// initialize the rankMap
	rankMap = new HashMap<String, String>();
	rankMap.put("13", "0");
	rankMap.put("53", "1");
	rankMap.put("43", "2");
	rankMap.put("32", "3");
	rankMap.put("54", "4");
	rankMap.put("25", "5");
	rankMap.put("38", "6");
	rankMap.put("63", "7");
	rankMap.put("24", "8");
	rankMap.put("28", "9");
	rankMap.put("42", "10");
	rankMap.put("55", "11");
	rankMap.put("45", "12");
	rankMap.put("4", "13");
	rankMap.put("58", "14");
	rankMap.put("61", "15");
	rankMap.put("14", "16");
	rankMap.put("56", "17");
	rankMap.put("27", "18");
	rankMap.put("15", "19");
	rankMap.put("49", "20");
	rankMap.put("48", "21");
	rankMap.put("7", "22");
	rankMap.put("18", "23");
	rankMap.put("9", "24");
	rankMap.put("23", "25");
	rankMap.put("51", "26");
	rankMap.put("30", "27");
	rankMap.put("22", "28");
	rankMap.put("36", "29");
	rankMap.put("26", "30");
	rankMap.put("59", "31");
	rankMap.put("33", "32");
	rankMap.put("11", "33");
	rankMap.put("50", "34");
	rankMap.put("37", "35");
	rankMap.put("52", "36");
	rankMap.put("20", "37");
	rankMap.put("29", "38");
	rankMap.put("57", "39");
	rankMap.put("16", "40");
	rankMap.put("5", "41");
	rankMap.put("6", "42");
	rankMap.put("35", "43");
	rankMap.put("47", "44");
	rankMap.put("60", "45");
	rankMap.put("3", "46");
	rankMap.put("40", "47");
	rankMap.put("10", "48");
	rankMap.put("44", "49");
	rankMap.put("2", "50");
	rankMap.put("19", "51");
	rankMap.put("8", "52");
	rankMap.put("41", "53");
	rankMap.put("46", "54");
	rankMap.put("39", "55");
	rankMap.put("12", "56");
	rankMap.put("21", "57");
	rankMap.put("31", "58");
	rankMap.put("34", "59");
	rankMap.put("1", "60");
	rankMap.put("17", "61");
	rankMap.put("62", "62");
	rankMap.put("0", "63");
    }

    public String getImageSelectionHITID() {
	return this.ImageSelectionHITId;
    }

    public int getImageSelectionHITCurrentLevel() {
	return this.currentLevel;
    }

    public int getImageSelectionHITTag() {
	return this.tag;
    }

    public String[] getAnsweredUrls() {
	Assignment[] assignments = service
		.getAllAssignmentsForHIT(this.ImageSelectionHITId);
	ArrayList<String> rawAnswers = new ArrayList<String>();

	for (Assignment assignment : assignments) {

	    String answerLog = assignment.getWorkerId() + ", "
		    + assignment.getHITId() + ", ";
	    String answerXML = assignment.getAnswer();
	    QuestionFormAnswers qfa = RequesterService.parseAnswers(answerXML);
	    ArrayList<QuestionFormAnswersType.AnswerType> answers = (ArrayList<QuestionFormAnswersType.AnswerType>) qfa
		    .getAnswer();
	    for (QuestionFormAnswersType.AnswerType answer : answers) {
		String assignmentId = assignment.getAssignmentId();
		String answerValues = RequesterService.getAnswerValue(
			assignmentId, answer);
		String[] rawAnswerValues = null;
		if (answerValues != null) {
		    rawAnswerValues = answerValues.split("\\|");
		}

		for (String ans : rawAnswerValues) {
		    if (ans.startsWith("http://users.soe.ucsc.edu/~khuang/testpic/testpic")) {
			rawAnswers.add(ans);
			answerLog += rankMap.get(ans.substring(
				ans.indexOf("(") + 1, ans.indexOf(")"))) + "\n";
		    } else {
			// if(!ans.equals("Submit")&&!ans.equals("emptyanswer")){
			// answerLog += "Feedback:\n" + ans + "\n";
			// }
		    }
		}
	    }
	    // this.writeToLog(answerLog, this.answerLogFile);
	    this.writeToLog(answerLog, this.logFile);
	}

	// DEBUG
	if (rawAnswers.size() < o * g) {
	    String info = "\nrawAnswer size < o*g, return null in getAnsweredUrls.  rawAnswers are:\n";
	    for (int i = 0; i < rawAnswers.size(); i++) {
		info += rawAnswers.get(i) + "\n";
	    }
	    this.writeToLog(info, this.hitLogFile);
	    this.writeToLog(info, this.logFile);
	    return null;
	}

	// //DEBUG
	// String info2 =
	// "Before calling getSortedAnswers, in getAnsweredUrls, rawAnswers are:\n";
	// for(int i = 0; i < rawAnswers.size(); i++){
	// info2 += rawAnswers.get(i)+"\n";
	// }
	// info2 += "Then, jump into getSortedAnswers.\n\n";
	// this.writeToLog(info2, this.hitLogFile);
	// this.writeToLog(info2, this.logFile);

	String[] finalAnswers = getSortedAnswers(rawAnswers);

	// //DEBUG
	// String info3 =
	// "After calling getSortedAnswers, in getAnsweredUrls, finalAnswers are:\n";
	// for(int i = 0; i < finalAnswers.length; i++){
	// info3 += finalAnswers[i]+"\n";
	// }
	// info3 += "\n\n";
	// this.writeToLog(info3, this.hitLogFile);
	// this.writeToLog(info3, this.logFile);

	// DEBUG
	if (finalAnswers == null) {
	    String info = "\n\nFinal Answers in getAnsweredUrls are null, return null!!\n\n";
	    this.writeToLog(info, this.hitLogFile);
	    this.writeToLog(info, this.logFile);
	    return null;
	}

	String finalAnswer = "HIT=" + this.ImageSelectionHITId + ":answers=";
	for (String ans : finalAnswers) {
	    finalAnswer += ans + ",";
	}
	this.writeToLog(finalAnswer, this.logFile);

	// String finalAnswer =
	// "Answers for HIT("+this.ImageSelectionHITId+")\n";
	// for(String ans:finalAnswers){
	// finalAnswer += "----" + ans + "\n";
	// }
	// System.out.println(finalAnswer);
	// this.writeToLog(finalAnswer, this.answerLogFile);
	// this.writeToLog(finalAnswer, this.logFile);
	return finalAnswers;
    }

    private String[] getSortedAnswers(ArrayList<String> rawAnswers) {

	// //DEBUG
	// String infox =
	// "The first line in getSortedAnswers, see the rawAnswers passed in:\n";
	// for(int i = 0; i < rawAnswers.size(); i++){
	// infox += rawAnswers.get(i)+"\n";
	// }
	// infox +=
	// "Therefore, the size of rawAnswers is:"+rawAnswers.size()+"\n";
	// infox += "While, the f="+f+", g="+g+", o="+o+"\n\n";
	// this.writeToLog(infox, this.hitLogFile);
	// this.writeToLog(infox, this.logFile);

	String[] finalAnswers = new String[o * g];
	String[] content = new String[rawAnswers.size()];
	int[] count = new int[rawAnswers.size()];
	for (int c : count) {
	    c = 0;
	}

	// put the rawAnswers in the content array
	for (int i = 0; i < rawAnswers.size(); i++) {
	    boolean found = false;
	    int j = 0;
	    while (j < content.length && (!found) && content[j] != null) {
		if (content[j].equals(rawAnswers.get(i))) {
		    found = true;
		    count[j]++;
		    break;
		}
		j++;
	    }
	    if (!found && j < content.length) {
		content[j] = rawAnswers.get(i);
		count[j]++;
	    }
	}

	// //DEBUG
	// String info1 =
	// "In getSortedAnswers, before sorting, the contents/counts are:\n";
	// for(int i = 0; i < content.length; i++){
	// info1 += content[i]+", "+count[i]+"\n";
	// }
	// info1 += "\n";
	// this.writeToLog(info1, this.answerLogFile);
	// this.writeToLog(info1, this.logFile);

	for (int i = 0; i < count.length; i++) {
	    for (int j = i; j < count.length; j++) {
		if (count[i] < count[j]) {
		    String tempString = content[i];
		    int tempInt = count[i];
		    content[i] = content[j];
		    count[i] = count[j];
		    content[j] = tempString;
		    count[j] = tempInt;
		}
	    }
	}

	// //DEBUG
	// String info2 =
	// "In getSortedAnswers, after sorting, the contents/counts are:\n";
	// for(int i = 0; i < content.length; i++){
	// info2 += content[i]+", "+count[i]+"\n";
	// }
	// info2 += "\n";
	// this.writeToLog(info2, this.answerLogFile);
	// this.writeToLog(info2, this.logFile);

	// DEBUG
	if (content[o * g - 1] == null) {
	    String info = "\ncontent[o*g-1]=null, return null in getSortedAnswers\n\n";
	    this.writeToLog(info, this.hitLogFile);
	    this.writeToLog(info, this.logFile);
	    return null;
	}

	// in 2.6 version, it was wrong, since it was "content.length < o*g "
	// conflict
	if (content.length > o * g && count[o * g - 1] == count[o * g]
		&& content[o * g] != null) {

	    // if there is an conflict, randomly return k1.

	    ArrayList<String> imageUrlExtra = new ArrayList<String>();
	    int beginIndex = 0;
	    for (int i = 0; i < count.length; i++) {
		if (count[i] == count[o * g]) {
		    imageUrlExtra.add(content[i]);
		} else if (i < o * g) {
		    finalAnswers[beginIndex] = content[i];
		    beginIndex++;
		}
	    }

	    // add the first k1 items as the answer
	    int len = imageUrlExtra.size();
	    int x = 0;
	    HashSet<Integer> hs = new HashSet<Integer>();
	    Random rd = new Random();
	    for (int i = 0; i < finalAnswers.length; i++) {
		x = rd.nextInt(len);// uniformly distributed
		while (hs.contains(x)) {
		    x = rd.nextInt(len);
		}
		finalAnswers[i] = imageUrlExtra.get(x);
		hs.add(x);
	    }

	    //
	    //
	    // //DEBUG
	    // String infoxx = "";
	    // if(imageUrlExtra.size() == 0){
	    // infoxx +=
	    // "\n iamgeUrlExtra's size = 0, return null in getSortedAnswers\n\n";
	    // this.writeToLog(infoxx, this.hitLogFile);
	    // this.writeToLog(infoxx, this.logFile);
	    // return null;
	    // }
	    // else{
	    // // infoxx +=
	    // "\n iamgeUrlExtra's size = "+imageUrlExtra.size()+", return null in getSortedAnswers\n\n";
	    // // this.writeToLog(infoxx, this.hitLogFile);
	    // // this.writeToLog(infoxx, this.logFile);
	    // }
	    //
	    //
	    // String conflictLog = "Conflict! Another HIT is issued to get " +
	    // (o*g - beginIndex) + " extra answers to the comparison among:\n";
	    // for(int j = 0; j < imageUrlExtra.size(); j++){
	    // conflictLog += "----"+imageUrlExtra.get(j)+"\n";
	    // }
	    // System.out.println(conflictLog);
	    // this.writeToLog(conflictLog, this.answerLogFile);
	    // this.writeToLog(conflictLog, this.logFile);
	    //
	    // ImageSelectionHIT extra = new ImageSelectionHIT(this.service,
	    // this.alg, this.shuffleCount, imageUrlExtra, this.currentLevel,
	    // this.tag, this.k2, 0, imageUrlExtra.size(), 1, o*g-beginIndex,
	    // this.reward*imageUrlExtra.size());
	    // String extraHitId = extra.getImageSelectionHITID();
	    // HIT extraHIT = service.getHIT(extraHitId);
	    // while(extraHIT.getHITStatus() != HITStatus.Reviewable){
	    // try{
	    // Thread.sleep(1000*5);
	    // }catch(InterruptedException e){}
	    // extraHIT = service.getHIT(extraHitId);
	    // }
	    //
	    // String[] extraAnswers = extra.getAnsweredUrls();
	    // for(int i = beginIndex; i < finalAnswers.length; i++){
	    // finalAnswers[i] = extraAnswers[i-beginIndex];
	    // }
	    // this.service.disableHIT(extraHitId);
	    // // extra.disableImageSelectionHit();
	    //
	    // // //DEBUG
	    // // String info
	    // ="\n\ngetSortedAnswers returns the following urls as the finalAnswers to getAnsweredUrls\n";
	    // // for(int i = 0; i < finalAnswers.length; i++){
	    // // info += finalAnswers[i];
	    // // }
	    // // info += "\n\n";
	    // // this.writeToLog(info, this.hitLogFile);
	    // // this.writeToLog(info, this.logFile);

	    return finalAnswers;
	} else {
	    for (int i = 0; i < finalAnswers.length; i++) {
		finalAnswers[i] = content[i];
	    }
	    return finalAnswers;
	}
    }

    private void writeToLog(String log, String file) {
	BufferedWriter bw = null;
	try {
	    bw = new BufferedWriter(new FileWriter(file, true));
	    bw.write(log);
	    bw.newLine();
	    bw.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		bw.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {

    }
}
