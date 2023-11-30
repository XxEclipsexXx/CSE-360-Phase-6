//
// CSE 360
//
// Author: Benjamin Wilcox


package application;

public class UserStories {

	// Class Attributes
	private String storyName;	//name of User Story
	private String key1;			// Strings for each keyword
	private String key2;			// the user can input
	private String key3;
	
	// empty constructor
	public UserStories() {
		this.storyName = "Story Name";	// creates a defualt object
		this.key1 = "key1";
		this.key2 = "key2";
		this.key3 = "key3";
	}
	
	// Constructor method
	public UserStories(String storyName, String key1, String key2, String key3) {
		this.storyName = storyName;	// sets every input
		this.key1 = key1;			// as the cooresponding
		this.key2 = key2;			// String value
		this.key3 = key3;
	}
	
	// Getter methods
	public String getName() { return this.storyName; }	// StoryName
	
	public String getKey1() { return this.storyName; }	// Key1
	
	public String getKey2() { return this.storyName; }	// Key2
	
	public String getKey3() { return this.storyName; }	// Key 3
	
	// setter methods
	public void setKey1(String setKey1) { this.key1 = setKey1; }	// Key1
	
	public void setKey2(String setKey2) { this.key2 = setKey2; }	// Key2
	
	public void setKey3(String setKey3) { this.key3 = setKey3; }	// Key3
}