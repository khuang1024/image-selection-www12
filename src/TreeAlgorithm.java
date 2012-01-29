

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.requester.Assignment;
import com.amazonaws.mturk.requester.AssignmentStatus;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.HITStatus;

public class TreeAlgorithm {
    // the initial URLs of pictures
    private ArrayList<String> imageUrls;

    // the active HITs at present
    private ArrayList<ImageSelectionHIT> activeHITQueue;

    // the new items produced by HITs
    private ArrayList<Item> newItemQueue;

    // the level queue which stores the items
    private ArrayList<ArrayList<Item>> levelQueue;//

    private RequesterService service;
    // private int k1;
    private int k2;
    private int f;
    private int g;
    private int o;
    private double reward;
    private boolean useTag;

    private String shuffleCount;
    private int[] tag;
    private boolean[][] trigger;

    private String rootDir = System.getProperty("user.dir");
    private String alg = "alg1";
    private String logFile;
    // private String logIndex;
    // private String hitLogFile;
    // private String answerLogFile;

    private boolean checkNewItemQueueThreadIsDone = false;

    private boolean algIsDone = false;

    // for stanford
    private ArrayList<Integer> nAssignmentIndex;

    /**
     * 
     * @param service
     *            this is your "service file" which contains your AWS access key
     *            and secret key
     * @param imageUrls
     *            this is the arraylist which stores the urls of images we want
     *            to post
     * @param k1
     *            how many turkers will do a normal HIT
     * @param k2
     *            how many turkers will do a conflict-solving HIT
     * @param f
     *            how many inputs we have for a mini-task/section
     * @param g
     *            how many mini-tasks/sections we have for a single HIT
     * @param o
     *            how many outputs we have for a mini-task/section
     * @param useTag
     *            if we use tag to build a strict "tree". if useTag=true, we
     *            will implement this algorithm as a strict tree.
     * @param reward
     *            the reward for each image, so for a normal HIT, the reward
     *            should be reward*f*g. but this is already done in
     *            ImageSelectionHIT class itself. So we just need to give out
     *            the atomic reward for an image.
     */
    public TreeAlgorithm(RequesterService service, ArrayList<String> imageUrls,
	    int k2, int f, int g, int o, double reward, boolean useTag,
	    int shuffleCount, ArrayList<Integer> nAssignmentIndex) {
	this.imageUrls = new ArrayList<String>();
	this.activeHITQueue = new ArrayList<ImageSelectionHIT>();
	this.newItemQueue = new ArrayList<Item>();
	this.levelQueue = new ArrayList<ArrayList<Item>>();
	this.service = service;
	this.imageUrls = imageUrls;
	// this.k1 = k1;
	this.k2 = k2;
	this.f = f;
	this.g = g;
	this.o = o;
	this.reward = reward;
	this.useTag = useTag;
	this.shuffleCount = Integer.toString(shuffleCount);
	// this.logIndex = logIndex;
	// this.logFile = rootDir + java.io.File.separator + alg + "_" +
	// logIndex + this.shuffleCount + "log.txt";
	this.logFile = rootDir + java.io.File.separator + alg + "_"
		+ this.shuffleCount + "log.txt";
	// this.hitLogFile = rootDir + java.io.File.separator + alg + "_" +
	// this.shuffleCount + "hit_log.txt";
	// this.answerLogFile = rootDir + java.io.File.separator + alg + "_" +
	// this.shuffleCount + "answer_log.txt";

	this.tag = new int[this.getLayerAmount(imageUrls.size())];
	for (@SuppressWarnings("unused")
	int i : this.tag) {
	    i = 0;
	}
	this.trigger = new boolean[this.getLayerAmount(imageUrls.size()) + f
		* g][imageUrls.size() / (f * g) + 2];
	for (int i = 0; i < trigger.length; i++) {
	    trigger[i][0] = true;// by default, the items with tag 0 at each
				 // level are all triggered
	    for (int j = 1; j < trigger[i].length; j++) {
		trigger[i][j] = false;
	    }
	}
	// this.resetLog(this.hitLogFile);
	// this.resetLog(this.answerLogFile);
	this.resetLog(logFile);

	this.nAssignmentIndex = nAssignmentIndex;
    }

    public void run() {
	// String para = "";
	// para = "f="+this.f+", g="+this.g+", o="+this.o + "\n";
	// para += "Number of active HITs in this account: " +
	// this.service.getTotalNumHITsInAccount();
	// System.out.println(para);
	// writeToLog(para, this.answerLogFile);
	// writeToLog(para, this.logFile);
	this.initActiveHITQueue();
	Runnable cahqt = new CheckActiveHITQueueThread();
	Runnable cniqt = new CheckNewItemQueueThread();
	Thread checkActiveHITQueueThread = new Thread(cahqt);
	Thread checkNewItemQueueThread = new Thread(cniqt);
	checkActiveHITQueueThread.start();
	try {
	    Thread.sleep(1000 * 2);
	} catch (InterruptedException e) {
	}
	checkNewItemQueueThread.start();
    }

    private void initActiveHITQueue() {
	ArrayList<String> urlsForANewHIT = new ArrayList<String>();

	// create normal HITs
	for (int i = 0; i < this.imageUrls.size(); i++) {
	    urlsForANewHIT.add(this.imageUrls.get(i));
	    if (((i + 1) % (this.f * this.g)) == 0) {
		this.activeHITQueue.add(new ImageSelectionHIT(this.service,
			this.alg, this.shuffleCount, urlsForANewHIT, 0,
			this.tag[0]++, this.nAssignmentIndex.get(0), this.k2,
			this.f, this.g, this.o, this.reward, this.logFile));
		// System.out.println("debug:\t The levle is: 0, the number of assignments is: "
		// + nAssignmentIndex.get(0));
		urlsForANewHIT.clear();
	    }
	}

	// put the remaining urls into the newItem queue
	this.levelQueue.add(0, new ArrayList<Item>());
	while (!urlsForANewHIT.isEmpty()) {
	    Item residulItem = new Item(urlsForANewHIT.get(0), 0, this.tag[0]);
	    urlsForANewHIT.remove(0);
	    this.levelQueue.get(0).add(residulItem);
	}
    }

    private void checkActiveHITQueue() {
	// when the activeHITQueue is not null
	System.out.println("checking activeHITQueue at "
		+ (new Date()).toString() + "...");
	if (!this.activeHITQueue.isEmpty()) {

	    // iterate each active HIT in the queue, and see if it is done
	    for (int i = 0; i < this.activeHITQueue.size(); i++) {
		HIT hit = this.service.getHIT(this.activeHITQueue.get(i)
			.getImageSelectionHITID());
		String hitId = this.activeHITQueue.get(i)
			.getImageSelectionHITID();

		// if the hit is done
		if (hit.getHITStatus() == HITStatus.Reviewable) {

		    // if this HIT is done, get the information we need from all
		    // assignments and process these results
		    String[] answers = this.activeHITQueue.get(i)
			    .getAnsweredUrls();

		    int level = this.activeHITQueue.get(i)
			    .getImageSelectionHITCurrentLevel();
		    int tag = this.activeHITQueue.get(i)
			    .getImageSelectionHITTag();

		    // add each answer to the newItemQueue
		    String info = "";
		    for (int j = 0; j < answers.length; j++) {
			this.newItemQueue.add(new Item(answers[j], level + 1,
				tag));// NOTE: level + 1
			info += "Add a new item into the NewItemQueue: "
				+ answers[j] + ", level of the item = "
				+ (level + 1) + "\n";
		    }
		    // info += "\n";
		    // System.out.print(info);
		    // this.writeToLog(info, this.answerLogFile);
		    // this.writeToLog(info, this.hitLogFile);
		    this.writeToLog(info, this.logFile);

		    // update the newItemQueue.
		    /*
		     * NOTE: this changes the size of the ArrayList, don't
		     * forget to minus 1 at the end
		     */
		    this.activeHITQueue.remove(i);
		    // delete the HIT from Mechanical Turk
		    service.disableHIT(hitId);
		    i--;
		}
	    }
	}
    }

    private void checkNewItemQueue() {
	// when the newItemQueue is not empty, get each Item and put them into
	// the right levelQueue
	System.out.println("checking newItemQueue at "
		+ (new Date()).toString() + "...");
	if (!this.newItemQueue.isEmpty()) {
	    int level = this.newItemQueue.get(0).getCurrentLevel();
	    while (!this.newItemQueue.isEmpty()) {

		Item item = this.newItemQueue.get(0);
		level = item.getCurrentLevel();
		this.newItemQueue.remove(0);

		// when there is no queue for that level, create it
		while (this.levelQueue.size() < level + 1) {
		    this.levelQueue.add(new ArrayList<Item>());
		}

		// put it into the right levelQueue
		this.levelQueue.get(level).add(item);

		try {
		    Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		// //trigger the item with next tag
		this.trigger[level][item.tag + 1] = true;

		// when the newItemQueue is null
		// update the levelQueue
		if (this.newItemQueue.isEmpty()) {
		    this.checkLevelQueue(level);
		}
		// when the level of the next item is different from this one
		// update the levelQueue too
		else if (this.newItemQueue.get(0).getCurrentLevel() != level) {
		    this.checkLevelQueue(level);
		}

		// trigger the item with next tag
		// this.trigger[level][item.tag+1]=true;
	    }

	    // even though newItemQueue is empty
	    // we still have to check the entire levelQueue, there may be some
	    // new items to be processed
	    for (int i = 0; i < this.levelQueue.size(); i++) {
		if (!this.levelQueue.get(i).isEmpty()) {
		    this.checkLevelQueue(i);
		}
	    }
	}
    }

    private void checkLevelQueue(int level) {
	System.out.println("checking levelQueue at " + (new Date()).toString()
		+ "...");
	// if we don't care about the tag, then we just create a HIT as long as
	// we have enough HIT no matter where they come from
	if (!this.useTag) {

	    // when we have enough items (f*g) to create a normal HIT
	    while (this.levelQueue.get(level).size() >= this.f * this.g) {

		// make a new array which stores the new urls of images for
		// creating the new HIT
		ArrayList<String> imageUrlsForANewHIT = new ArrayList<String>();
		for (int i = 0; i < this.f * this.g; i++) {
		    imageUrlsForANewHIT.add(this.levelQueue.get(level).get(0)
			    .getUrl());
		    this.levelQueue.get(level).remove(0);
		}

		// create a new HIT with that array and other parameters
		// don't forget to update the tag at that level
		this.activeHITQueue.add(new ImageSelectionHIT(this.service,
			this.alg, this.shuffleCount, imageUrlsForANewHIT,
			level, tag[level]++, nAssignmentIndex.get(level),
			this.k2, this.f, this.g, this.o, this.reward,
			this.logFile));
		System.out.println("debug:\t The levle is: " + level
			+ ", the number of assignments is: "
			+ nAssignmentIndex.get(level));
	    }
	}

	else {
	    // firts, we have to re-sort the queue
	    this.sortLevelQueueByTag(this.levelQueue.get(level));

	    // get the first item in the queue and see if it is triggered
	    Item firstItem = this.levelQueue.get(level).get(0);

	    // if it is triggered, we may be able to create a HIT
	    if (this.trigger[level][firstItem.getTag()]) {

		// iterate this levelQueue and create HITs if we can
		while (!this.levelQueue.get(level).isEmpty()) {

		    // check if the items we have are in sequence, and we have
		    // enough
		    boolean canCreateAHIT = false;

		    if (this.levelQueue.get(level).size() >= this.f * this.g) {
			canCreateAHIT = true;
			for (int i = 0; i < this.f * this.g; i++) {
			    if (i != 0
				    && this.levelQueue.get(level).get(i)
					    .getTag()
					    - this.levelQueue.get(level)
						    .get(i - 1).getTag() > 1) {
				canCreateAHIT = false;
				System.out.println("Order Wrong!");
			    }
			}
		    }

		    // if the items cannot meet the "order" requirement, we
		    // don't create a HIT
		    if (!canCreateAHIT) {
			break;
		    } else {

			// get the requirement-met items and put them into an
			// array
			ArrayList<String> imageUrlsForANewHIT = new ArrayList<String>();
			for (int i = 0; i < this.f * this.g; i++) {
			    imageUrlsForANewHIT.add(this.levelQueue.get(level)
				    .get(0).getUrl());
			    // NOTE: this operation updates the size of
			    // levelQueue
			    this.levelQueue.get(level).remove(0);
			}

			// create the new HIT and put it into the activeHITQueue
			this.activeHITQueue.add(new ImageSelectionHIT(service,
				alg, shuffleCount, imageUrlsForANewHIT, level,
				this.tag[level]++, nAssignmentIndex.get(level),
				this.k2, this.f, this.g, this.o, this.reward,
				this.logFile));
			System.out.println("debug:\t The levle is: " + level
				+ ", the number of assignments is: "
				+ nAssignmentIndex.get(level));
		    }
		}

	    } else {
		System.out.println("Not Triggered.");
	    }
	}
    }

    // activeHITQueue is empty
    // newItemQueue is empty
    // other levelQueue except the topmost one have more than or equal f*g
    private boolean isOver() {
	boolean isOver = false;
	if (this.activeHITQueue.isEmpty() && this.newItemQueue.isEmpty()) {
	    isOver = true;
	    // if we still have enough items for creating a normal HIT at a
	    // certain levelQueue, it is not over
	    // we should not check the top level, since there is a special
	    // operation for it.
	    // this one is just for checking other levels have no items and
	    // then, we can start moving items to their upper levels
	    for (int i = 0; i < this.levelQueue.size() - 1; i++) {
		if (this.levelQueue.get(i).size() >= this.f * this.g) {
		    isOver = false;
		    break;
		}
	    }
	}
	return isOver;
    }

    private boolean isCompletelyOver() {
	boolean isCompletelyOver = true;
	if (!this.activeHITQueue.isEmpty()
		|| !this.newItemQueue.isEmpty()
		|| this.levelQueue.get(this.levelQueue.size() - 1).size() >= this.f) {
	    isCompletelyOver = false;
	}
	for (int i = 0; i < this.levelQueue.size() - 1; i++) {
	    if (!this.levelQueue.get(i).isEmpty()) {
		// System.out.println("levelQueue("+i+") is not empty");
		isCompletelyOver = false;
	    }
	}
	// System.out.println("activeHITQueue is empty:\t"+this.activeHITQueue.isEmpty());
	// System.out.println("newItemQueue is empty:\t"+this.newItemQueue.isEmpty());
	// System.out.println("top level has:\t"+this.levelQueue.get(this.levelQueue.size()-1).size()+" (<f?)");
	// System.out.println("isCompletelyOver?\t"+isCompletelyOver);
	return isCompletelyOver;
    }

    // use the function to indicate the whole algorithm is done!
    public boolean isDone() {
	return this.algIsDone;
    }

    private boolean allAssignmentDone(String hitId) {
	Assignment[] assignments = service.getAllAssignmentsForHIT(hitId);
	for (int i = 0; i < assignments.length; i++) {
	    if (assignments[i].getAssignmentStatus() != AssignmentStatus.Submitted) {
		return false;
	    }
	}
	return true;
    }

    private void resetLog(String file) {
	FileOutputStream f;
	try {
	    f = new FileOutputStream(file);
	    f.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
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

    private int getLayerAmount(int len) {
	int res;
	double temp = (double) this.o / (double) this.f;
	double temp2 = Math.log(1 / (double) len) / Math.log(temp);
	res = (int) temp2 + 1;
	return res;
    }

    private ArrayList<Item> sortLevelQueueByTag(ArrayList<Item> al) {
	for (int i = 0; i < al.size(); i++) {
	    for (int j = i; j < al.size(); j++) {
		if (al.get(i).getTag() > al.get(j).getTag()) {
		    Item temp = al.get(i);
		    al.set(i, al.get(j));
		    al.set(j, temp);
		}
	    }
	}
	return al;
    }

    // using inner class to implement multi-thread
    private class CheckActiveHITQueueThread implements Runnable {
	public void run() {

	    while (!checkNewItemQueueThreadIsDone) {
		checkActiveHITQueue();
		try {
		    Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		// wait enough time to make sure the whole algorithm is really
		// over!!
		if (isCompletelyOver()) {
		    try {
			Thread.sleep(1000 * 5);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	    System.out.println("Thread: CheckActiveHITQueueThread is done.\n");
	    algIsDone = true;
	}
    }

    // using inner class to implement multi-thread
    private class CheckNewItemQueueThread implements Runnable {
	public void run() {
	    while (!isCompletelyOver()) {
		while (!isOver()) {
		    checkNewItemQueue();
		    try {
			Thread.sleep(1000 * 5);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}

		// deal with the remaining items at each level, except the last
		// levelQueue
		for (int level = 0; level < levelQueue.size(); level++) {

		    String info = "";

		    // if this levelQueue is not empty
		    if (!levelQueue.get(level).isEmpty()) {

			// check if there are enough items to create a "small"
			// HIT
			if (levelQueue.get(level).size() >= f) {
			    int tempG = levelQueue.get(level).size() / f;
			    ArrayList<String> urlsForANewHIT = new ArrayList<String>();
			    for (int j = 0; j < tempG * f; j++) {
				urlsForANewHIT.add(levelQueue.get(level).get(0)
					.getUrl());
				levelQueue.get(level).remove(0);
			    }
			    activeHITQueue.add(new ImageSelectionHIT(service,
				    alg, shuffleCount, urlsForANewHIT, level,
				    tag[level], nAssignmentIndex.get(level),
				    k2, f, tempG, o, reward, logFile));
			    System.out.println("debug:\t The levle is: "
				    + level
				    + ", the number of assignments is: "
				    + nAssignmentIndex.get(level));
			}
			// put the residual items into the upper level

			// ///******************************************************************************
			while (!activeHITQueue.isEmpty()) {
			    try {
				// System.out.println("Sleep...zzz...");
				Thread.sleep(1000 * 3);
			    } catch (InterruptedException e) {
				e.printStackTrace();
			    }
			}
			// when new results come up, check out the new results
			// immediately
			checkNewItemQueue();
			// /******************************************************************************

			if (!isCompletelyOver()) {

			    // if there are still some item left, just raise it
			    // to the upper level
			    while (!levelQueue.get(level).isEmpty()) {

				// check if we have enough levelQueue, since the
				// top level may have items of this kind
				// of course, it creates a new HIT at the
				// current top level, but the HIT is not done
				// yet,
				// but we have to create a new levelQueue for
				// the remaining items
				while (levelQueue.size() <= level + 1) {
				    levelQueue.add(new ArrayList<Item>());
				}

				Item item = levelQueue.get(level).get(0);
				levelQueue.get(level).remove(0);
				item.setTag(tag[level + 1]);// don't forget to
							    // update the tag
				levelQueue.get(level + 1).add(item);
				info += "Move " + item.getUrl()
					+ " to level No." + (level + 1) + "\n";
			    }

			    info += "\n";
			    // System.out.print(info);
			    // writeToLog(info, hitLogFile);
			    writeToLog(info, logFile);

			    // //////**************************************
			    // break;//this break is very important! it prevent
			    // the program keeping put items into the upper
			    // level. Instead, it stops here and wait the
			    // results from the new HIT
			    // /////////********************************************

			}
		    }
		}
	    }

	    // add an extra HIT to get only one answer//
	    String finalAnswer = "";
	    if (levelQueue.get(levelQueue.size() - 1).size() > 1) {

		// for stanford
		throw new RuntimeException(
			"Algorithm wrong! More than one items left at the top level!");

		// to rollback, just comment in the code block below! Kerui
		// int num = levelQueue.get(levelQueue.size()-1).size();//get
		// all elements at the last level
		// ArrayList<String> urlsForANewHIT = new ArrayList<String>();
		// for(int i = 0; i < num; i++){
		// urlsForANewHIT.add(levelQueue.get(levelQueue.size()-1).get(0).getUrl());
		// levelQueue.get(levelQueue.size()-1).remove(0);
		// }
		// ImageSelectionHIT imgHit = new ImageSelectionHIT(service,
		// alg, shuffleCount, urlsForANewHIT, 0, 0, k1, k2, num, 1, 1,
		// reward);
		// String hitId = imgHit.getImageSelectionHITID();
		// HIT hit = service.getHIT(hitId);
		// while(hit.getHITStatus() != HITStatus.Reviewable){
		// try {
		// Thread.sleep(1000*10);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// hit = service.getHIT(hitId);
		// }
		// String[] answers = imgHit.getAnsweredUrls();
		// levelQueue.add(new ArrayList<Item>());
		// levelQueue.get(levelQueue.size()-1).add(new
		// Item(answers[0],levelQueue.size()-1,0));
		// finalAnswer = "The final answer to algorithm 1 is:" +
		// answers[0] + "\n";
		// service.disableHIT(hitId);//don't forget to disable the HIT
		// when it is done.
	    } else {
		finalAnswer = "Final answer:"
			+ levelQueue.get(levelQueue.size() - 1).get(0).getUrl()
			+ "\n";
	    }

	    checkNewItemQueueThreadIsDone = true;

	    // output the status of each queue and the final answer
	    // System.out.println("Thread: CheckNewItemQueueThread is done.");
	    String info = "";
	    // for(int i = 0; i < levelQueue.size(); i++){
	    // info += "Residual elements in queue No."+i+" are:\n";
	    // for(int j = 0; j < levelQueue.get(i).size(); j++){
	    // info += "\t"+levelQueue.get(i).get(j).getUrl()+"\n";
	    // }
	    // info += "\n";
	    // }
	    info += finalAnswer;
	    // info +=
	    // "Number of active HITs in this account: "+service.getTotalNumHITsInAccount();
	    // System.out.println(info);
	    // writeToLog(info, answerLogFile);
	    writeToLog(info, logFile);
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	//

    }

}
