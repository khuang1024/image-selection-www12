

import java.util.ArrayList;
import java.util.Collections;

import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.util.PropertiesClientConfig;

public class TreeAlgorithmRunner {

    public static void main(String[] args) {

	int f = Integer.parseInt(args[0]);
	int g = Integer.parseInt(args[1]);
	int o = Integer.parseInt(args[2]);
	// int k1 = 1;
	int k2 = 0;
	double reward = Double.parseDouble(args[3]);
	boolean useTag = true;
	int shuffleCount = Integer.parseInt(args[4]);
	// exclusively for stanford
	ArrayList<Integer> assignments = new ArrayList<Integer>();
	assignments.add(Integer.parseInt(args[5]));
	assignments.add(Integer.parseInt(args[6]));
	assignments.add(Integer.parseInt(args[7]));
	System.out.println("The number of assignments at the 1st level:"
		+ assignments.get(0));
	System.out.println("The number of assignments at the 2nd level:"
		+ assignments.get(1));
	System.out.println("The number of assignments at the 3rd level:"
		+ assignments.get(2));

	String rootDir = System.getProperty("user.dir");
	// test
	RequesterService service = new RequesterService(
		new PropertiesClientConfig(rootDir + java.io.File.separator
			+ "mturk.properties"));

	// real
	// RequesterService service = new RequesterService(new
	// PropertiesClientConfig(rootDir + java.io.File.separator +
	// "real_mturk.properties"));
	ArrayList<String> urls1 = new ArrayList<String>();

	service.blockWorker("A36Z35EEUCKX5F", "previous bad performance");
	service.blockWorker("A107D86V8A6RRX", "previous bad performance");
	service.blockWorker("A11V4122DHY5UJ", "previous bad performance");
	service.blockWorker("A13PBRIZGODM9P", "previous bad performance");
	service.blockWorker("A13WM9LF6I8G4T", "previous bad performance");
	service.blockWorker("A14LT5TAR9QNL8", "previous bad performance");
	service.blockWorker("A14URY9O8EGA7V", "previous bad performance");
	service.blockWorker("A1503BOV7BL0ZK", "previous bad performance");
	service.blockWorker("A16B68L30WQL5", "previous bad performance");
	service.blockWorker("A179VLKURU2LOC", "previous bad performance");
	service.blockWorker("A17K0YAOVIT0JR", "previous bad performance");
	service.blockWorker("A18BBWFTL7ZGFA", "previous bad performance");
	service.blockWorker("A19C5ADMDC26G2", "previous bad performance");
	service.blockWorker("A1AK5IY89GI63W", "previous bad performance");
	service.blockWorker("A1DL2TQENMSR9G", "previous bad performance");
	service.blockWorker("A1DQPERD593S3P", "previous bad performance");
	service.blockWorker("A1E28ELFVJYQ93", "previous bad performance");
	service.blockWorker("A1E3IEF9JNJI53", "previous bad performance");
	service.blockWorker("A1EEMDL1S274FP", "previous bad performance");
	service.blockWorker("A1FUPO70XHU5V8", "previous bad performance");
	service.blockWorker("A1G05O3HM7DNVZ", "previous bad performance");
	service.blockWorker("A1GO0PXWTUNZ3B", "previous bad performance");
	service.blockWorker("A1GSQ7FKZ8YA2Q", "previous bad performance");
	service.blockWorker("A1H83DLB5CIXDE", "previous bad performance");
	service.blockWorker("A1HBQ2V4LN80CK", "previous bad performance");
	service.blockWorker("A1HIYIBN1TD2OV", "previous bad performance");
	service.blockWorker("A1HRQ07E297U3U", "previous bad performance");
	service.blockWorker("A1HXHFX54MRHBZ", "previous bad performance");
	service.blockWorker("A1IQ8YHTZLFEVG", "previous bad performance");
	service.blockWorker("A1IU5OP7BBZHZ7", "previous bad performance");
	service.blockWorker("A1JCPOUTP2PD0", "previous bad performance");
	service.blockWorker("A1JRD82HD0LRTK", "previous bad performance");
	service.blockWorker("A1KXC938I6Y4H3", "previous bad performance");
	service.blockWorker("A1M2AJJSNCAXBJ", "previous bad performance");
	service.blockWorker("A1ML310CKRZDGQ", "previous bad performance");
	service.blockWorker("A1O0BA01KO2X7Y", "previous bad performance");
	service.blockWorker("A1OSVWUU1NOQ84", "previous bad performance");
	service.blockWorker("A1P3YDRJGXWPDT", "previous bad performance");
	service.blockWorker("A1PX8UYPRGZCO9", "previous bad performance");
	service.blockWorker("A1QBJ4BKON5JBJ", "previous bad performance");
	service.blockWorker("A1QS475M5OOPGP", "previous bad performance");
	service.blockWorker("A1SCYZPHZNQU5Q", "previous bad performance");
	service.blockWorker("A1U3PULHJJGGCC", "previous bad performance");
	service.blockWorker("A1UQQWTSPG4AE8", "previous bad performance");
	service.blockWorker("A1V0SRHRVUSNPF", "previous bad performance");
	service.blockWorker("A1VKHE5L6QVFUV", "previous bad performance");
	service.blockWorker("A1WHQSBPUVHE9W", "previous bad performance");
	service.blockWorker("A1ZT6K90UHR5I9", "previous bad performance");
	service.blockWorker("A1ZYSGQDIGQM11", "previous bad performance");
	service.blockWorker("A204NDFTMBY7KJ", "previous bad performance");
	service.blockWorker("A21HP3O7OCRSU", "previous bad performance");
	service.blockWorker("A229ISBMZQMOYC", "previous bad performance");
	service.blockWorker("A250X6C26ZRKA2", "previous bad performance");
	service.blockWorker("A25ZNNCK1WFJHZ", "previous bad performance");
	service.blockWorker("A2645JQFXH75R8", "previous bad performance");
	service.blockWorker("A26X4OPM1QKAUW", "previous bad performance");
	service.blockWorker("A27NRWF6LMMJDZ", "previous bad performance");
	service.blockWorker("A2A3YGFJHOPJHC", "previous bad performance");
	service.blockWorker("A2AV104XONLIK2", "previous bad performance");
	service.blockWorker("A2BALLNAXC9HJF", "previous bad performance");
	service.blockWorker("A2D6PWP2IZ5F82", "previous bad performance");
	service.blockWorker("A2DFJ0VQEAK30O", "previous bad performance");
	service.blockWorker("A2DHZRGBL2KNVI", "previous bad performance");
	service.blockWorker("A2EJEI8Q1HIWC7", "previous bad performance");
	service.blockWorker("A2ESBX4C2Q6TQY", "previous bad performance");
	service.blockWorker("A2F0D6H8W5BLL3", "previous bad performance");
	service.blockWorker("A2H3RZ0D12FHVI", "previous bad performance");
	service.blockWorker("A2HGZOXZ36XKRI", "previous bad performance");
	service.blockWorker("A2I1KEEJNVB2PX", "previous bad performance");
	service.blockWorker("A2I8VBHRESMG9S", "previous bad performance");
	service.blockWorker("A2IX6G85JQH9VO", "previous bad performance");
	service.blockWorker("A2JKCS6UCAUARS", "previous bad performance");
	service.blockWorker("A2JOGZXV5ONAGG", "previous bad performance");
	service.blockWorker("A2JX0UOJHSH1MZ", "previous bad performance");
	service.blockWorker("A2K9VAUQ9I7CLT", "previous bad performance");
	service.blockWorker("A2KEL3H65DWUQT", "previous bad performance");
	service.blockWorker("A2LROPVFMLAHB8", "previous bad performance");
	service.blockWorker("A2M73K9EWXBXTO", "previous bad performance");
	service.blockWorker("A2MTELRZSLPGWQ", "previous bad performance");
	service.blockWorker("A2OCLAT2XQKEM4", "previous bad performance");
	service.blockWorker("A2P6OBH2NKHU7T", "previous bad performance");
	service.blockWorker("A2PO4CTVHBRRPW", "previous bad performance");
	service.blockWorker("A2SKWBR49HEXFY", "previous bad performance");
	service.blockWorker("A2SS0AIL9222K7", "previous bad performance");
	service.blockWorker("A2T7VGUQUGJXVV", "previous bad performance");
	service.blockWorker("A2UAQZROEM6TBH", "previous bad performance");
	service.blockWorker("A2UUF56A4I5J6J", "previous bad performance");
	service.blockWorker("A2V3P1XE33NYC3", "previous bad performance");
	service.blockWorker("A2W4T7J7Z44NZE", "previous bad performance");
	service.blockWorker("A2WL7GE23X3JD4", "previous bad performance");
	service.blockWorker("A2YBK6ZJ2YDYHA", "previous bad performance");
	service.blockWorker("A31YZ8JR422KFR", "previous bad performance");
	service.blockWorker("A32A00HR9X8F7O", "previous bad performance");
	service.blockWorker("A32QBXDPHHSTOD", "previous bad performance");
	service.blockWorker("A33ZR1TP1ZZ18", "previous bad performance");
	service.blockWorker("A346XT1TTXFHQ6", "previous bad performance");
	service.blockWorker("A34DIFRKYMURA8", "previous bad performance");
	service.blockWorker("A35YKA992RH772", "previous bad performance");
	service.blockWorker("A36CLSDZVP9SCA", "previous bad performance");
	service.blockWorker("A36EYLA7ZHRXIA", "previous bad performance");
	service.blockWorker("A36HHS5QD0YR5J", "previous bad performance");
	service.blockWorker("A379YFQ59DTYHL", "previous bad performance");
	service.blockWorker("A37THBDJSTDFIW", "previous bad performance");
	service.blockWorker("A38L1QCI94USEJ", "previous bad performance");
	service.blockWorker("A393TR40V7ALX2", "previous bad performance");
	service.blockWorker("A3ABJN1IL7UCZ8", "previous bad performance");
	service.blockWorker("A3AYFB90I0271", "previous bad performance");
	service.blockWorker("A3D57KSR4XTQ74", "previous bad performance");
	service.blockWorker("A3D5GRZO6HLS9I", "previous bad performance");
	service.blockWorker("A3DCU4RRT0R1IM", "previous bad performance");
	service.blockWorker("A3DL1W081CY8L", "previous bad performance");
	service.blockWorker("A3ELU8CMPR7MBP", "previous bad performance");
	service.blockWorker("A3EQNG4BQVQ70Y", "previous bad performance");
	service.blockWorker("A3F035ZNNDHP0U", "previous bad performance");
	service.blockWorker("A3FXJ21TQ0LRNJ", "previous bad performance");
	service.blockWorker("A3G032T69RFQQL", "previous bad performance");
	service.blockWorker("A3G8NSPRF986WP", "previous bad performance");
	service.blockWorker("A3GF29EXTJHJO7", "previous bad performance");
	service.blockWorker("A3H62DTZG4HV31", "previous bad performance");
	service.blockWorker("A3HXI5ZNSMUYMU", "previous bad performance");
	service.blockWorker("A3J1PP96QX14U0", "previous bad performance");
	service.blockWorker("A3JFW7NXI7HCI6", "previous bad performance");
	service.blockWorker("A3JMXQ9L6I3V3G", "previous bad performance");
	service.blockWorker("A3JMYIL585K6JI", "previous bad performance");
	service.blockWorker("A3JYA8EQU91UOC", "previous bad performance");
	service.blockWorker("A3LCVXMW2COCWD", "previous bad performance");
	service.blockWorker("A3LEUS25QIJE2Z", "previous bad performance");
	service.blockWorker("A3LJJ2OPIANRMC", "previous bad performance");
	service.blockWorker("A3MOK3EOXE5ZHW", "previous bad performance");
	service.blockWorker("A3NK147K2TXO40", "previous bad performance");
	service.blockWorker("A3OVORVFL0XDTW", "previous bad performance");
	service.blockWorker("A3P67WRD144EYI", "previous bad performance");
	service.blockWorker("A3PMZ11FTWTPQS", "previous bad performance");
	service.blockWorker("A3QFKN44F43KOI", "previous bad performance");
	service.blockWorker("A3QNEB9XVXARSP", "previous bad performance");
	service.blockWorker("A3R8YLT00D86ZV", "previous bad performance");
	service.blockWorker("A3RD1U7GY4UWNX", "previous bad performance");
	service.blockWorker("A3RTDA6X7O17DV", "previous bad performance");
	service.blockWorker("A3T3BQZGOC129H", "previous bad performance");
	service.blockWorker("A3TD4VJPN33IWZ", "previous bad performance");
	service.blockWorker("A3UH7FBVDSFAGS", "previous bad performance");
	service.blockWorker("A4SDBFRJ557H4", "previous bad performance");
	service.blockWorker("A52AHJEUWFSA2", "previous bad performance");
	service.blockWorker("A5DTMMW81L4N5", "previous bad performance");
	service.blockWorker("A6R15X3AXU2XS", "previous bad performance");
	service.blockWorker("A7JAO3CZNXB4H", "previous bad performance");
	service.blockWorker("A9HBK8EQZM42S", "previous bad performance");
	service.blockWorker("A9MQC6AIM99HG", "previous bad performance");
	service.blockWorker("A9N7Z0LCRZ16O", "previous bad performance");
	service.blockWorker("AAK150L8VOIKZ", "previous bad performance");
	service.blockWorker("ACOZ2LE1E828L", "previous bad performance");
	service.blockWorker("ADSBJ3LLUVFQS", "previous bad performance");
	service.blockWorker("ADU0ZAHIUSTAH", "previous bad performance");
	service.blockWorker("AGAOPG4H2MBT6", "previous bad performance");
	service.blockWorker("AGXAL8BHCQDGR", "previous bad performance");
	service.blockWorker("AH94PYNLTYT0F", "previous bad performance");
	service.blockWorker("AHGJTOTIPCL8X", "previous bad performance");
	service.blockWorker("AJ12QBIW80KAH", "previous bad performance");
	service.blockWorker("AK7U41KDD6VJQ", "previous bad performance");
	service.blockWorker("ALAO0YU49USSB", "previous bad performance");
	service.blockWorker("AOCME08MZQHA7", "previous bad performance");
	service.blockWorker("APMEOBFFLFRLX", "previous bad performance");
	service.blockWorker("AQ7YOPEHV7VYI", "previous bad performance");
	service.blockWorker("ARNQV3WS239K9", "previous bad performance");
	service.blockWorker("ARRZFQ8ZE0HXW", "previous bad performance");
	service.blockWorker("AT7T1HWMX9EPA", "previous bad performance");
	service.blockWorker("AU59W7RRG9LV", "previous bad performance");
	service.blockWorker("AU5ZIOW35NHLN", "previous bad performance");
	service.blockWorker("AU8QI5QDBDT0Y", "previous bad performance");
	service.blockWorker("AUBC0HIVKBOTF", "previous bad performance");
	service.blockWorker("AUL42X4OSKP67", "previous bad performance");
	service.blockWorker("AURS96Y79WPNT", "previous bad performance");
	service.blockWorker("AVLJQ1HQ61JLS", "previous bad performance");
	service.blockWorker("AWE8DF2ZY0LB2", "previous bad performance");
	service.blockWorker("AXEQ3XKI2IXC2", "previous bad performance");
	service.blockWorker("AZ8KL6F16ZS7G", "previous bad performance");

	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (0).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (1).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (2).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (3).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (4).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (5).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (6).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (7).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (8).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (9).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (10).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (11).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (12).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (13).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (14).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (15).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (16).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (17).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (18).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (19).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (20).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (21).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (22).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (23).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (24).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (25).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (26).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (27).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (28).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (29).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (30).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (31).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (32).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (33).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (34).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (35).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (36).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (37).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (38).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (39).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (40).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (41).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (42).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (43).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (44).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (45).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (46).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (47).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (48).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (49).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (50).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (51).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (52).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (53).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (54).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (55).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (56).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (57).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (58).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (59).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (60).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (61).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (62).JPG");
	urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (63).JPG");

	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (64).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (65).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (66).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (67).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (68).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (69).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (70).JPG");
	// urls1.add("http://users.soe.ucsc.edu/~khuang/testpic/testpic (71).JPG");

	Collections.shuffle(urls1);
	// Alg1Process alg = new Alg1Process(service, urls1, k1, k2, f, g, o,
	// reward, useTag, shuffleCount);
	// alg.run();

	ArrayList<String> originalUrls = (ArrayList<String>) urls1.clone();
	TreeAlgorithm alg;
	for (int i = 1; i <= shuffleCount; i++) {
	    Collections.shuffle(urls1);
	    alg = new TreeAlgorithm(service, urls1, k2, f, g, o, reward,
		    useTag, i, assignments);
	    alg.run();
	    while (!alg.isDone()) {
		try {
		    Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    System.out.println("No." + i + " algorithm instance is done.");
	    urls1 = (ArrayList<String>) originalUrls.clone();
	}

    }

}
