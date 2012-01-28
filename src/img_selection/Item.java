package img_selection;

public class Item {
	String url;
	int currentLevel;
	int tag;
	Item(String url, int level, int tag){
		this.url = url;
		this.currentLevel = level;
		this.tag = tag;
	}
	String getUrl(){
		return this.url;
	}
	int getCurrentLevel(){
		return this.currentLevel;
	}
	int getTag(){
		return this.tag;
	}
	public void setTag(int newTag){
		this.tag = newTag;
	}
}
