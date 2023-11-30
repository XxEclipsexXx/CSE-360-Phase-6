/*
CSE360

Author: Jacob Good
*/
package application;

public class User {
	// Define user roles
	public enum UserRole {
	    ADMIN, EMPLOYEE;
	}
	
	// set user class attributes
	private String username;
    private String password;
    private UserRole role;
    
    //Constructor method
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Get methods for each attribute
    String getUsername () {
    	return username;
    }
    
    String getPassword () {
    	return password;
    }
    
    UserRole getRole () {
    	return role;
    }
}