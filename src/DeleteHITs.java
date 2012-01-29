

import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.util.PropertiesClientConfig;
import com.amazonaws.mturk.requester.HIT;

public class DeleteHITs {

    private DeleteHITs() {
	throw new AssertionError();
    }
    
    public static void main(String[] args) {

	String hitgroup = "2FTLWSBRI8IU2F667FAHVTLB3MWE2I";
	String rootDir = System.getProperty("user.dir");
	RequesterService service = new RequesterService(
		new PropertiesClientConfig(rootDir + java.io.File.separator
			+ "real_mturk.properties"));

	System.out.println("Number of active HITs in this account: "
		+ service.getTotalNumHITsInAccount());

	HIT[] hits = service.getAllReviewableHITs(hitgroup);
	for (int i = 0; i < hits.length; i++) {
	    System.out.println("Disable HIT:" + hits[i].getHITId());
	    service.disableHIT(hits[i].getHITId());
	}

	System.out.println("Number of active HITs in this account: "
		+ service.getTotalNumHITsInAccount());
    }

}
