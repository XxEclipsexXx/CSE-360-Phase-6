/*
CSE360

Author: Tu25
*/
package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import application.User.UserRole;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {
    // Variables for EncryptionController
	private TextField inputData = new TextField();
    private TextField encryptedData = new TextField();
    private TextField decryptedData = new TextField();
    private EncryptionController controller = new EncryptionController();
    private String userLoggedIn;
    private int effortsLogged;
    private int dataLogged;
    private String [] userStoryNames = new String [10];
    private int userStoryIndex;
    private int userStoryCount = 0;
    private boolean admin;
	
	/*
	Function that starts the login module
	*/
	@Override
    public void start(Stage primaryStage) {
		
		// create button for login application
        Button launchButton = new Button("Login to System");
        launchButton.setOnAction(event -> loginButton(primaryStage)); // Launch your code on button click
        
        //set layout of the screen
        BorderPane root = new BorderPane();
        VBox buttonLayout = new VBox(10);
        buttonLayout.setPadding(new Insets(10));
        buttonLayout.getChildren().add(launchButton);
        root.setCenter(buttonLayout);
        
        // create scene
        Scene scene = new Scene(root, 400, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EffortLoggerV2");
        primaryStage.show();
    }
	
	// Author: Jacob Good
	public void loginButton(Stage primaryStage) {
		
		// Set new text box labeled "username"
		Label usernameLabel = new Label("Username: ");
		TextField usernameField = new TextField ();
		
		// Set new text box labeled "password"
		Label passwordLabel = new Label("Password: ");
		PasswordField passwordField = new PasswordField ();
		
		// Set new dropdown box for User Role
		ComboBox<UserRole> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(UserRole.ADMIN, UserRole.EMPLOYEE);
        comboBox.setValue(UserRole.EMPLOYEE);
        
        // Create button to initialize "login"
        Button loginButton = new Button("Login");
        
        // Set the layout for login screen
        BorderPane root = new BorderPane();
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(10));
        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, comboBox, loginButton);
        root.setCenter(loginLayout);
        
        // Defined action for login button pressed
        loginButton.setOnAction(e -> {
        	
        	// Store user inputed info
            String username = usernameField.getText();
            String password = passwordField.getText();
            UserRole selectedRole = comboBox.getValue();
            
            /*
             *  In full scale system user1 would search database for username, if not found return null
             */
            User user1 = new User ("Jacob", "TEST", UserRole.ADMIN);
            String user = user1.getUsername();
            
            // check credentials
            if (user != null && user1.getPassword().equals(password) && user1.getRole() == selectedRole) {
                System.out.println("Login successful. User role: " + user1.getRole());
                userLoggedIn = user;
                if (user1.getRole() == UserRole.ADMIN) {
                	admin = true;
                }
                else {
                	admin = false;
                }
                mainMenu(primaryStage);
            } else {
                System.out.println("Login failed");
            }
        });
        
        // Create scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
// Author: Jacob Good
// Planning Poker First Round
public void planningPokerFirstRound (Stage primaryStage) {
    primaryStage.setTitle("Planning Poker");
    
    Random generator = new Random();
    int [] estimates = {1,2,3,5,8,13};
    int randomIndex = generator.nextInt(estimates.length);

    // Set up stage to capture data
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 20, 20, 20));

    // Field to enter task info
    Label taskLabel = new Label("User Story: \"" + userStoryNames[userStoryCount] + "\"");
    
    // Dropdown box to enter estimate
    Label estimateLabel = new Label("Estimate:");
    ComboBox<String> estimateComboBox = new ComboBox<>();
    estimateComboBox.getItems().addAll("1", "2", "3", "5", "8", "13");
    
    //Choose data to include
    CheckBox historicalData = new CheckBox ("Historical Data 1");
    historicalData.setOnAction(event ->{
    	if (historicalData.isSelected()) {
    		System.out.println ("Suggested Estimate: " + estimates[randomIndex]);
    	}
    	else {
    		int newRandomIndex = generator.nextInt(estimates.length);
    		System.out.println ("Suggested Estimate: " + estimates[newRandomIndex]);
    	}
    });
    
    // Print out info when button is clicked
    Button logButton = new Button("Log Effort");
    logButton.setOnAction(event -> {
    	userStoryCount++;
        String task = userStoryNames[0];
        String estimate = estimateComboBox.getValue();
        // Store task and estimate data (you can use a data structure or database here)
        System.out.println("Task: " + task + ", Estimate: " + estimate);
        effortsLogged++;
        estimateComboBox.getSelectionModel().clearSelection();
        planningPokerFirstRound (primaryStage);
    });
    
    Button backButton = new Button ("Back");
    backButton.setOnAction(event -> planningPoker(primaryStage));
    //Set up UI
    grid.add(taskLabel, 0, 0);
    grid.add(estimateLabel, 0, 2);
    grid.add(historicalData, 0, 1);
    grid.add(estimateComboBox, 2, 2);
    grid.add(logButton, 0, 3, 3, 1);
    grid.add(backButton, 0, 4, 4, 3);

    Scene scene = new Scene(grid, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.show();
}

public void planningPoker(Stage primaryStage) {
       primaryStage.setTitle("Planning Poker");
      
       // Author: Benjamin Wilcox
       // defines the font size of the planning poker title
       final int PLAN_POKER_FONT_SIZE = 25;
      
       // creates a new object, an arrayList of stories
       ArrayList<UserStories> storiesSet = new ArrayList<>();
       storiesSet.add(new UserStories());
      
       // counter for the arrayList
       Label counter = new Label("0");
       Label counterExLabel = new Label("Stories Count:");
       // Set up stage to capture data
       // borderpane
       BorderPane border = new BorderPane();
       border.setPadding(new Insets(10, 10, 10, 10));
       // vbox
       VBox vbox = new VBox();
       vbox.setSpacing(8);
       vbox.setPadding(new Insets(10, 10, 10, 10));
       // gridpane
       GridPane grid = new GridPane();
       grid.setHgap(10);
       grid.setVgap(10);
       grid.setPadding(new Insets(20, 20, 20, 20));
       // Planning Poker Label
       Label planningPokerLabel = new Label("Planning Poker");
       planningPokerLabel.setFont(new Font(PLAN_POKER_FONT_SIZE)); 	// sets fontsize
      
       // Create new project vbox
       Label newProjectLabel = new Label("Project Name:");
       TextField newProjectTextField = new TextField();
       Button newProjectButton = new Button("Create New Project");
       // new Project Button action
       newProjectButton.setOnAction(event -> {
       	String projectName = newProjectTextField.getText();	// gets project name
       	System.out.println("Project: " + projectName);
       });
      
       // Enter User Story
       Label userStoryLabel = new Label("User Story:");
       TextField userStoryTextField = new TextField();
      
       // Enter Keyword 1
       Label key1Label = new Label("Keyword 1:");
       TextField key1TextField = new TextField();
      
       // Enter Keyword 2
       Label key2Label = new Label("Keyword 2:");
       TextField key2TextField = new TextField();
     
       // Enter Keyword 3
       Label key3Label = new Label("Keyword 3:");
       TextField key3TextField = new TextField();
      
       // Print out info when button is clicked
       Button addButton = new Button("Add");
       addButton.setOnAction(event2 -> {
           String userStoryName = userStoryTextField.getText();
           String key1 = key1TextField.getText();		// gets all inputs
           String key2 = key2TextField.getText();		// from the textfields
           String key3 = key3TextField.getText();	
          
           userStoryNames[userStoryIndex] = userStoryName;
           userStoryIndex++;
           
           // gets the count from the count label
           int c = Integer.parseInt(counter.getText());
           // uses it to input the next story into the set
           storiesSet.add(new UserStories(userStoryName, key1, key2, key3));
           c += 1;	// iterates the entry to the current input
           counter.setText(""+ c);
          
           // clears the inputs and prints the results to the console
           System.out.println("User Story: "  + storiesSet.get(c).getName() +
           		", Keyword 1: " + storiesSet.get(c).getKey1() + ", Keyword 2: " +
           		storiesSet.get(c).getKey2() + ", Keyword 3: " + storiesSet.get(c).getKey3());
           userStoryTextField.clear();
           key1TextField.clear();
           key2TextField.clear();
           key3TextField.clear();
       });
      
       // clear button
       Button clearButton = new Button("Clear");
       clearButton.setOnAction(event3 -> {
           // clears the inputs and prints the results to the console
           userStoryTextField.clear();
           key1TextField.clear();
           key2TextField.clear();
           key3TextField.clear();
       });
      
       // continue button, will continue to the next stage of planning poker
       Button continueButton = new Button("Continue");
       continueButton.setOnAction(event -> planningPokerFirstRound (primaryStage));
      
// Author: Jaocb Good
       // back button goes back to the home page
       Button backButton = new Button ("Back");
       backButton.setOnAction(event4 -> mainMenu(primaryStage));
       //Set up Grid
       grid.add(userStoryLabel, 0, 0);
       grid.add(userStoryTextField, 1, 0);
       grid.add(key1Label, 0, 1);
       grid.add(key1TextField, 1, 1);
       grid.add(key2Label, 0, 2);
       grid.add(key2TextField, 1, 2);
       grid.add(key3Label, 0, 3);
       grid.add(key3TextField, 1, 3);
       grid.add(addButton, 0, 4);
       grid.add(clearButton, 1,  4);
       grid.add(continueButton, 0, 5);
       // setup vbox
       vbox.getChildren().addAll(newProjectLabel, newProjectTextField, newProjectButton, counterExLabel, counter);
      
       // setup Border
       border.setTop(planningPokerLabel);
       border.setLeft(vbox);
       border.setCenter(grid);
       border.setBottom(backButton);
      
       Scene scene = new Scene(border, 500, 350);
       primaryStage.setScene(scene);
       primaryStage.show();
	}
	
	//Author: Mark Ahn
	//Encryption Application
public void encryption(Stage primaryStage) {
    VBox root = new VBox(10);
    TextArea inputData = new TextArea();
    TextArea encryptedData = new TextArea();
    TextArea decryptedData = new TextArea();
    TextArea historyLog = new TextArea();

    ComboBox<String> algorithmChoice = new ComboBox<>();
    algorithmChoice.getItems().addAll("AES (Advanced Encryption)", "DES (Basic Encryption)", "RSA (Public Key Encryption)");
    algorithmChoice.setValue("AES (Advanced Encryption)"); // Default value

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Enter password (optional)");
    Label passwordStrengthLabel = new Label("Password Strength: N/A");

    // Update password strength in real-time
    passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
        updatePasswordStrength(passwordStrengthLabel, newValue);
    });

    Label encryptionQualityIndicator = new Label("Encryption Quality: N/A");

    Button encryptButton = new Button("Encrypt");
    Button decryptButton = new Button("Decrypt");
    Button backButton = new Button("Back");
    Button clearButton = new Button("Clear");

    encryptButton.setOnAction(e -> {
        String algorithm = algorithmChoice.getValue().split(" ")[0]; // Extracting algorithm name
        String password = passwordField.getText();
        String encryptedText = controller.encrypt(inputData.getText(), algorithm, password);
        encryptedData.setText(encryptedText);
        updateEncryptionQuality(encryptionQualityIndicator, algorithm, encryptedText);
        logHistory(historyLog, "Encrypted", LocalDateTime.now(), encryptedText);
        dataLogged++;
    });

    decryptButton.setOnAction(e -> {
        String algorithm = algorithmChoice.getValue().split(" ")[0]; // Extracting algorithm name
        String decryptedText = controller.decrypt(encryptedData.getText(), algorithm, passwordField.getText());
        decryptedData.setText(decryptedText);
        logHistory(historyLog, "Decrypted", LocalDateTime.now(), decryptedText);
    });

    backButton.setOnAction(event -> mainMenu(primaryStage));
    
    clearButton.setOnAction(e -> {
        // Clear all text areas and the password field
        inputData.clear();
        encryptedData.clear();
        decryptedData.clear();
        historyLog.clear();
        passwordField.clear();
        // Reset the labels
        passwordStrengthLabel.setText("Password Strength: N/A");
        encryptionQualityIndicator.setText("Encryption Quality: N/A");
    });

    root.getChildren().addAll(inputData, algorithmChoice, passwordField, passwordStrengthLabel, encryptButton, encryptedData, decryptButton, decryptedData, encryptionQualityIndicator, historyLog, backButton);
    root.getChildren().add(clearButton);
    
    primaryStage.setTitle("Data Encryption Module");
    primaryStage.setScene(new Scene(root, 400, 550));
    primaryStage.show();
}

//Author: Mark Ahn
private void updatePasswordStrength(Label strengthLabel, String password) {
    int strengthScore = 0;

    if (password.length() >= 8) strengthScore += 2; // Length check; The password length should be longer than 8 chars to run the logic.
    if (password.matches("(?=.*[0-9]).*")) strengthScore += 2; // Contains number
    if (password.matches("(?=.*[a-z]).*")) strengthScore += 1; // Contains lowercase
    if (password.matches("(?=.*[A-Z]).*")) strengthScore += 2; // Contains uppercase
    if (password.matches("(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).*")) strengthScore += 3; // Contains special char

    switch (strengthScore) {
        case 0:
            strengthLabel.setText("Password Strength: Very Weak");
            break;
        case 2:
        case 3:
            strengthLabel.setText("Password Strength: Weak");
            break;
        case 4:
        case 5:
            strengthLabel.setText("Password Strength: Moderate");
            break;
        case 6:
        case 7:
            strengthLabel.setText("Password Strength: Strong");
            break;
        default:
            strengthLabel.setText("Password Strength: Very Strong");
            break;
    }
}

// Author: Mark Ahn
private void updateEncryptionQuality(Label qualityIndicator, String algorithm, String encryptedData) {
    int dataLength = encryptedData.length();

    if (algorithm.equals("AES")) {
        qualityIndicator.setText(dataLength > 50 ? "Encryption Quality: High" : "Encryption Quality: Moderate");
    } else if (algorithm.equals("DES")) {
        qualityIndicator.setText(dataLength > 50 ? "Encryption Quality: Moderate" : "Encryption Quality: Low");
    } else if (algorithm.equals("RSA")) {
        qualityIndicator.setText(dataLength > 100 ? "Encryption Quality: Very High" : "Encryption Quality: High");
    } else {
        qualityIndicator.setText("Encryption Quality: Unknown");
    }
}

// Author: Mark Ahn
private void logHistory(TextArea log, String action, LocalDateTime timestamp, String data) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    log.appendText(String.format("[%s] %s: %s\n", formatter.format(timestamp), action, data));
}
	
	//Author: Trinh Mai
	public void history(Stage primaryStage) {
	       VBox root = new VBox(10);
	       	
	       // Display user credentials
	        Text history = new Text(userLoggedIn + " logged in");
	        root.getChildren().add(history);
	        
	        // Display number of efforts logged by user
	        for (int i = 0; i < effortsLogged; i++) {
	        	Text loggedText = new Text(userLoggedIn + " logged an effort");
	        	root.getChildren().add(loggedText);
	        }
	        
	        // Display if data was changed
	        for (int i = 0; i < dataLogged; i++) {
	        	Text loggedText = new Text(userLoggedIn + " encrypted data");
	        	root.getChildren().add(loggedText);
	        }
	        
	        Button backButton = new Button ("Back");
	        backButton.setOnAction(event -> mainMenu(primaryStage));
	        
	        root.getChildren().addAll(backButton);
	        primaryStage.setTitle("Data Encryption Module");
	        primaryStage.setScene(new Scene(root, 300, 200));
	        primaryStage.show();
		}
	
//Author: Benjamin Wilcox
//Overflow Prototype
public void overflow(Stage primaryStage) {
	 primaryStage.setTitle("Overflow Test");

        // Set up stage to capture data
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        final int maxData = 5;
        
        // Field to see data & count data 
        Label dataLabel = new Label("");
        Label countLabel = new Label("0");	// acts as a counter for the button
        
        // Field to enter task info
        Label taskLabel = new Label("Task:");
        TextField taskTextField = new TextField();
        
        // Print out info when button is clicked
        Button logButton = new Button("Log Effort");
        logButton.setOnAction(event -> {
            String task = taskTextField.getText();
            // Store task and estimate data (you can use a data structure or database here)
            System.out.println("Task: " + task);
            // creates an integer from the count label to know how many entries before this one
            int c = Integer.parseInt(countLabel.getText());
            c += 1;	// iterates the entry to the current input
           
            if (maxData >= c)	// checks if the counter is greater than the overflow value
            {
            	String dataList = dataLabel.getText();	// gets the current list of inputs
            	dataList = dataList + ", " + task;		// adds the new task
            	dataLabel.setText(dataList);			// puts it back into the label
            	countLabel.setText("" + c);		// adds it back into the program
            }
            else {		// if it overflows it sends this error 
            	System.out.println("Maximum Data Entries Reached, please start a new project");
            }
            taskTextField.clear();	// clears the task testbox
        });
        
        // sets up the button UI
        Button backButton = new Button ("Back");
        backButton.setOnAction(event -> mainMenu(primaryStage));
        //Set up UI
        grid.add(taskLabel, 0, 0);
        grid.add(taskTextField, 1, 0);
        grid.add(dataLabel, 0, 1);
        grid.add(countLabel, 1, 1);
        grid.add(logButton, 0, 2, 2, 1);
        grid.add(backButton, 0, 3, 3, 2);
        
        // scene size and setup
        Scene scene = new Scene(grid, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	// Author: Jacob Good
	// Main menu UI
	public void mainMenu(Stage primaryStage) {
		// create button for other applications
	    Button planningButton = new Button("Planning Poker");
	    planningButton.setOnAction(event -> planningPoker(primaryStage)); // Launch your code on button click
	    Button encryptionButton = new Button("Encryption");
	    encryptionButton.setOnAction(event -> encryption(primaryStage)); // Launch your code on button click
	    Button overflowButton = new Button("Overflow");
	    overflowButton.setOnAction(event -> overflow(primaryStage)); // Launch your code on button click
	    Button historyButton = new Button("Modification History");
	    historyButton.setOnAction(event -> history(primaryStage)); // Launch your code on button click
	    Button logoutButton = new Button("Logout");
	    logoutButton.setOnAction(event -> loginButton(primaryStage)); // Launch your code on button click
	    
	    //set layout of the screen
	    BorderPane root = new BorderPane();
	    VBox buttonLayout = new VBox(10);
	    buttonLayout.setPadding(new Insets(10));
	    if (admin) {
	    	buttonLayout.getChildren().addAll(planningButton, encryptionButton, overflowButton, historyButton, logoutButton);
	    }
	    else {
	    	buttonLayout.getChildren().addAll(planningButton, encryptionButton, overflowButton, logoutButton);
	    }
	    root.setCenter(buttonLayout);
	    
	    // create scene
	    Scene scene = new Scene(root, 400, 200);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("EffortLoggerV2");
	    primaryStage.show();
	}
	// Main function
	public static void main(String[] args) {
		launch(args);
	}
}
