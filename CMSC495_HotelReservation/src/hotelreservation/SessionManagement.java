package hotelreservation;

/**
 * File: SessionManagement.java
 * CMSC 495 Section 7380
 * Group 8
 * Author: Mario Bethancourt, Jules Torres, Megan Moore
 * Professor Hung Dao
 * Date: 11/21/2023
 * Description: This class Extends JFrame and serves as the main control for the GUI and user interactions.
 * Manages user registration, login, and other actions using Swing components.
 * Handles the flow of the program, including creating and displaying windows.
 * Revisions
 * 11/21/2023 Everyone - Created the class and added GUI, and Buttons.
 * 11/22/2023 Everyone - Implemented functions of the user creation buttons.
 * 11/23/2023 Jules - Revisions
 * 11/23/2023 Everyone - Implemented more functions, the way to reserve, and viewing reservation info.
 * 11/24/2023 Everyone - Implemented error handling and log in/out.
 * 11/25/2023 Jules, Mario - debugging.
 * 12/01/2023 Mario - Implemented more methods and modified existing ones
 * 12/02/2023 Jules - Worked on the methods implemented 
 * 12/03/2023 Everyone - debugging
 * 12/06/2023 Mario - Made adjustments to methods
 * 12/07/2023 Mario - Made layout adjustments
 * 12/08/2023 Everyone - debugging
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.util.Random;

public class SessionManagement extends JFrame {
	private static final long serialVersionUID = 1L;
	private final static String USER_FILE = "users.txt";
    private Input input;
    private static Map<String, AccountManagement> users = new HashMap<>();
    private AccountManagement currentUser;

    private JPanel mainMenuPanel;
    private JPanel reservationMenuPanel;
    private JPanel reservationAvailabilityPanel;
    private JPanel userAccountPanel;
    private JPanel createAccountMenuPanel;
    private JPanel createLoginMenuPanel;
    private JPanel updateAccountScreenPanel;
    private JPanel deletionScreenPanel;
    private JTextField deletionInputField;
    private JPanel checkInOutScreenPanel;
    private JTextField checkInOutInputField;
    
    private JTextArea reservationTextArea;
    private JTextArea reservationAvailabilityTextArea;
    private JTextArea userAccountTextArea;
    private JTextArea availableRoomsTextArea;
    
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField phoneNumberField;
    private JTextField streetNumberField;
    private JTextField streetNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField stateField;
    private JTextField cityField;
    private JTextField zipCodeField;
    
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    
    /**
     * SessionManagement 
     * @param input
     */
    public SessionManagement(Input input) {
    	
    	loadUsersFromFile(); //invoke loadusersFromFile
        this.input = input;
        this.reservationTextArea = new JTextArea(); 

        setTitle("Hotel Reservation");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameField = new JTextField();
        lastNameField = new JTextField();
        phoneNumberField = new JTextField();
        streetNumberField = new JTextField();
        streetNameField = new JTextField(); 
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        deletionScreenPanel = createDeletionScreenPanel();
        
        createComponents();
    }

    /**
     * components
     * panels
     */
    private void createComponents() {
        mainMenuPanel = createMainMenuPanel();
        reservationMenuPanel = createReservationMenuPanel();
        reservationAvailabilityPanel = createReservationAvailabilityPanel();
        userAccountPanel = createUserAccountPanel();
        createAccountMenuPanel = createCreateAccountMenuPanel();
        createLoginMenuPanel = createLoginMenuPanel();
        deletionScreenPanel = createDeletionScreenPanel();
        deletionScreenPanel.setVisible(false);  // Make sure to set visibility for deletionScreenPanel

        add(mainMenuPanel); //mainMenu
        add(reservationMenuPanel); //reservationMenu
        add(reservationAvailabilityPanel); // reservationAvailability
        add(userAccountPanel); //userAccount
        add(createLoginMenuPanel); //createLogin
        add(deletionScreenPanel); //delete

        //invoke mainMenu
        showMainMenu();
    }

    /**
     * menuPanel
     * @return
     */
    private void setButtonSize(JButton button) {
        Dimension buttonSize = new Dimension(150, 40); // Adjust width and height as needed
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
    }
    
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to GR8's Hotel!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        Dimension buttonSize = new Dimension(200, 50);
        
        // create account button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> showCreateAccountMenu());
        createAccountButton.setPreferredSize(buttonSize);
        createAccountButton.setMinimumSize(buttonSize);
        createAccountButton.setMaximumSize(buttonSize);
        
        // create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> showLoginMenu());
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMinimumSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        
        // create logout button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> handleLogout());
        logoutButton.setPreferredSize(buttonSize);
        logoutButton.setMinimumSize(buttonSize);
        logoutButton.setMaximumSize(buttonSize);
        
        // create reserve button
        JButton reserveButton = new JButton("Make Reservation");
        reserveButton.addActionListener(e -> {
            showReservationMenu();
            updateAvailableRooms();        
        });
        reserveButton.setPreferredSize(buttonSize);
        reserveButton.setMinimumSize(buttonSize);
        reserveButton.setMaximumSize(buttonSize);

        // create viewReservations button
        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.addActionListener(e -> {
            showReservationAvailability();
            updateReservationAvailability();       
        });
        viewReservationsButton.setPreferredSize(buttonSize);
        viewReservationsButton.setMinimumSize(buttonSize);
        viewReservationsButton.setMaximumSize(buttonSize);

        // create viewAccount button
        JButton viewAccountButton = new JButton("View Account");
        viewAccountButton.addActionListener(e -> {
            showUserAccount();
            updateUserAccount();
        });
        viewAccountButton.setPreferredSize(buttonSize);
        viewAccountButton.setMinimumSize(buttonSize);
        viewAccountButton.setMaximumSize(buttonSize);

        // create updateAccount button
        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener(e -> showUpdateUserScreen());
        updateAccountButton.setPreferredSize(buttonSize);
        updateAccountButton.setMinimumSize(buttonSize);
        updateAccountButton.setMaximumSize(buttonSize);

        // create checkInOut button
        JButton checkInOutButton = new JButton("Check In/Out");
        checkInOutButton.addActionListener(e -> {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(SessionManagement.this, "Please log in first.");
            } else {
                showCheckInOutScreen();
            }
        });
        checkInOutButton.setPreferredSize(buttonSize);
        checkInOutButton.setMinimumSize(buttonSize);
        checkInOutButton.setMaximumSize(buttonSize);

        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(createAccountButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(createAccountButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(reserveButton);
        buttonPanel.add(viewReservationsButton);
        buttonPanel.add(viewAccountButton);
        buttonPanel.add(updateAccountButton);
        buttonPanel.add(checkInOutButton);     
        buttonPanel.setBorder(emptyBorder);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * create reservation panel
     * @return
     */
    private JPanel createReservationMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        availableRoomsTextArea = new JTextArea(); // Initialize the JTextArea
        JScrollPane scrollPane = new JScrollPane(availableRoomsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReservation();
                updateAvailableRooms();
            }
        });

        setButtonSize(backButton);
        setButtonSize(reserveButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(reserveButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createReservationAvailabilityPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        reservationAvailabilityTextArea = new JTextArea();
        reservationAvailabilityTextArea.setFont(new Font("Arial", Font.PLAIN, 24));
        JScrollPane scrollPane = new JScrollPane(reservationAvailabilityTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });
        
        setButtonSize(backButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createUserAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        userAccountTextArea = new JTextArea();
        userAccountTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(userAccountTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());

        JButton deleteReservationButton = new JButton("Delete Reservation");
        deleteReservationButton.addActionListener(e -> showDeletionScreen());

        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener(e -> showUpdateUserScreen());

        setButtonSize(backButton);
        setButtonSize(deleteReservationButton);
        setButtonSize(updateAccountButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(deleteReservationButton);
        buttonPanel.add(updateAccountButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCreateAccountMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());        
        
        // Initialize the input fields
        nameField = new JTextField();
        lastNameField = new JTextField();
        phoneNumberField = new JTextField();
        streetNumberField = new JTextField();
        streetNameField = new JTextField();
        emailField = new JTextField();
        stateField = new JTextField();
        cityField = new JTextField();
        zipCodeField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        
        JButton createAccountConfirmButton = new JButton("Create Account");
        createAccountConfirmButton.addActionListener(e -> handleCreateAccount());
        
        // Create a layout for labels and fields 
        JPanel inputPanel = new JPanel(new GridLayout(11, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("Street Number:"));
        inputPanel.add(streetNumberField); 
        inputPanel.add(new JLabel("Street Name:"));
        inputPanel.add(streetNameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("State:"));
        inputPanel.add(stateField);
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(cityField);
        inputPanel.add(new JLabel("Zip Code:"));
        inputPanel.add(zipCodeField);  
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        setButtonSize(backButton);
        setButtonSize(createAccountConfirmButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(createAccountConfirmButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        remove(mainMenuPanel);
        remove(reservationMenuPanel);
        remove(reservationAvailabilityPanel);
        remove(userAccountPanel);

        // Add the new panel to the frame
        add(panel);

        // Validate and repaint the frame to reflect the changes
        validate();
        repaint();
        return panel;
    }
    
    private JPanel createLoginMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        loginUsernameField = new JTextField();
        loginPasswordField = new JPasswordField();
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(e -> handleLogin());
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(loginUsernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(loginPasswordField);
        
        setButtonSize(backButton);
        setButtonSize(loginButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createDeletionScreenPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        deletionInputField = new JTextField();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showUserAccount());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> handleDeletion(deletionInputField.getText()));

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.add(new JLabel("Enter Reservation to Delete:"));
        inputPanel.add(deletionInputField);
        inputPanel.add(new JLabel()); 
        inputPanel.add(deleteButton);

        setButtonSize(backButton);
        setButtonSize(deleteButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createUpdateUserScreenPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create input fields for updating user information
        JTextField newNameField = new JTextField();
        JTextField newLastNameField = new JTextField();
        JTextField newPhoneNumberField = new JTextField();
        JTextField newStreetNumberField = new JTextField();
        JTextField newStreetNameField = new JTextField();
        JTextField newEmailField = new JTextField();
        JTextField newStateField = new JTextField();
        JTextField newCityField = new JTextField();
        JTextField newZipCodeField = new JTextField();
        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField oldPasswordField = new JPasswordField();

        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the method to handle updating the account
                handleUpdateAccount(
                    newNameField.getText(),
                    newLastNameField.getText(),
                    newPhoneNumberField.getText(),
                    newStreetNumberField.getText(),
                    newStreetNameField.getText(),
                    newEmailField.getText(),
                    newStateField.getText(),
                    newCityField.getText(),
                    newZipCodeField.getText(),
                    newUsernameField.getText(),
                    new String(newPasswordField.getPassword()),
                    new String(oldPasswordField.getPassword())
                           
                );
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the user account screen
            	showMainMenu();
            }
        });

        // Create a layout for labels and fields
        JPanel inputPanel = new JPanel(new GridLayout(12, 2));
        inputPanel.add(new JLabel("New Name:"));
        inputPanel.add(newNameField);
        inputPanel.add(new JLabel("New Last Name:"));
        inputPanel.add(newLastNameField);
        inputPanel.add(new JLabel("New Phone Number:"));
        inputPanel.add(newPhoneNumberField);
        inputPanel.add(new JLabel("New Street Number:"));
        inputPanel.add(newStreetNumberField);
        inputPanel.add(new JLabel("New Street Name:"));
        inputPanel.add(newStreetNameField);
        inputPanel.add(new JLabel("New Email:"));
        inputPanel.add(newEmailField);
        inputPanel.add(new JLabel("New State:"));
        inputPanel.add(newStateField);
        inputPanel.add(new JLabel("New City:"));
        inputPanel.add(newCityField);
        inputPanel.add(new JLabel("New Zip Code:"));
        inputPanel.add(newZipCodeField);  
        inputPanel.add(new JLabel("New Username:"));
        inputPanel.add(newUsernameField);
        inputPanel.add(new JLabel("New Password:"));
        inputPanel.add(newPasswordField);
        inputPanel.add(new JLabel("Old Password:"));
        inputPanel.add(oldPasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateAccountButton);
        buttonPanel.add(backButton);
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        updateAccountScreenPanel = panel;

        return panel;
    }    
    
    private JPanel createCheckInOutScreenPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        checkInOutInputField = new JTextField();

        JButton checkInButton = new JButton("Check In");
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckIn();
            }
        });

        JButton checkOutButton = new JButton("Check Out");
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming you have a JTextField for entering room number
                String roomNumber = checkInOutInputField.getText().trim();
                handleCheckOut(roomNumber);
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        
        setButtonSize(checkInButton);
        setButtonSize(checkOutButton);
        setButtonSize(backButton);

        JPanel inputPanel = new JPanel(new GridLayout(4, 4));
        inputPanel.add(new JLabel("Enter Room Number:"));
        inputPanel.add(checkInOutInputField);
        inputPanel.add(new JLabel()); 
        inputPanel.add(checkInButton);
        inputPanel.add(new JLabel()); 
        inputPanel.add(checkOutButton);
        inputPanel.add(new JLabel());
        inputPanel.add(backButton);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inputPanel);

        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }
  
    private void showMainMenu() {
        if (updateAccountScreenPanel != null) {
            updateAccountScreenPanel.setVisible(false);
            updateAccountScreenPanel = null;
        }

        if (checkInOutScreenPanel != null) {
            checkInOutScreenPanel.setVisible(false);
            checkInOutScreenPanel = null;
        }
        mainMenuPanel.setVisible(true);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(false);
        deletionScreenPanel.setVisible(false);

    } 
    
    private void showLoginMenu() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(true);
    } 

    private void showReservationMenu() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(true);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
    }

    private void showReservationAvailability() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(true);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
    }

    private void showUserAccount() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(true);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(false);
        deletionScreenPanel.setVisible(false);
    }

    private void showCreateAccountMenu() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(true);
        createLoginMenuPanel.setVisible(false);
    }
    
    private void showDeletionScreen() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(false);
        deletionScreenPanel.setVisible(true);
    }
    
    private void showUpdateUserScreen() {

    	mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(false);
        deletionScreenPanel.setVisible(false);       

        // Check if the update account panel has been added
        if (updateAccountScreenPanel == null) {
            updateAccountScreenPanel = createUpdateUserScreenPanel();
            add(updateAccountScreenPanel);
            updateAccountScreenPanel.setVisible(true);
        }

        // Refresh the frame
        revalidate();
    }
    
    private void showCheckInOutScreen() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(false);
        deletionScreenPanel.setVisible(false);

        if (checkInOutScreenPanel == null || !checkInOutScreenPanel.isShowing()) {
            checkInOutScreenPanel = createCheckInOutScreenPanel();
            add(checkInOutScreenPanel);
        }

        // Show the checkInOutScreenPanel
        checkInOutScreenPanel.setVisible(true);

        // Refresh the frame
        validate();
        repaint();
    }
    
    private void handleCreateAccount() {
        // Get user information from input fields
        String name = nameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String streetNumber = streetNumberField.getText().trim();
        String streetName = streetNameField.getText().trim();
        String email = emailField.getText().trim();
        String state = stateField.getText().trim();
        String city = cityField.getText().trim();
        String zipCode = zipCodeField.getText().trim(); 
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validate name and last name
        if (!isValidName(name) || !isValidName(lastName)) {
            JOptionPane.showMessageDialog(this, "Invalid name or last name. Please use only letters and limit to 1-25 characters.");
            return;
        }

        // Validate phone number
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(this, "Invalid phone number. Please use only numbers and limit to 10-15 digits.");
            return;
        }

         // Validate street number and street name
        if (!isValidStreetNumber(streetNumber) || !isValidStreetName(streetName)) {
            JOptionPane.showMessageDialog(this, "Invalid street number or street name.");
            return;
        }
                 
        if (!isValidEmail(email) || !isValidState(state)|| !isValidCity(city) || !isValidZipCode(zipCode)) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the provided information.");
            return;
        }
        
        // Validate user name and password
        if (!isValidUsername(username) || !isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password. UserName min of 6 characters, and password a minimum of 8characters max 20.");
            return;
        }

        // Check if any of the required fields is empty
        if (name.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || streetNumber.isEmpty() || streetName.isEmpty() || email.isEmpty() || state.isEmpty() || city.isEmpty()
                || zipCode.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the required details.");
            return;
        }
        
        // Create a new user with the provided information
        AccountManagement newUser = new AccountManagement(name, lastName, phoneNumber, streetNumber, streetName, email, state, city, zipCode, username, password);
        users.put(username, newUser);

        // Display user information
        userAccountTextArea.setText("User Account:\n");
        userAccountTextArea.append("Name: " + newUser.getName() + "\n");
        userAccountTextArea.append("Last Name: " + newUser.getLastName() + "\n");
        userAccountTextArea.append("Phone Number: " + newUser.getPhoneNumber() + "\n");
        userAccountTextArea.append("Street Number: " + newUser.getStreetNumber() + "\n");
        userAccountTextArea.append("Street Name: " + newUser.getStreetName() + "\n");
        userAccountTextArea.append("Email: " + newUser.getEmail() + "\n");
        userAccountTextArea.append("State: " + newUser.getState() + "\n");
        userAccountTextArea.append("City: " + newUser.getCity() + "\n");
        userAccountTextArea.append("Zip Code: " + newUser.getZipCode() + "\n");
        userAccountTextArea.append("Username: " + newUser.getUsername() + "\n");

        // Set the current user to the newly created user
        currentUser = newUser;
        saveUsersToFile();
        
        JOptionPane.showMessageDialog(this, "Account created successfully! You can now log in.");
        // Return to the main menu
        showMainMenu();
    }
    
     // Helper methods for validation
    private boolean isValidName(String value) {
        return value.matches("^[a-zA-Z]{1,25}$");
    }

    private boolean isValidPhoneNumber(String value) {
        return value.matches("^\\d{10,15}$");
    }

    private boolean isValidStreetNumber(String value) {
        return value.matches("^\\d{1,10}$");
    }

    private boolean isValidStreetName(String value) {
        return value.length() >= 1 && value.length() <= 25;
    }
    
    private boolean isValidCity(String value) {
        return value.length() >= 4 && value.length() <= 30;
    }

    private boolean isValidState(String value) {
        return value.length() == 2;
    }

    private boolean isValidZipCode(String value) {
        return value.matches("^\\d{5,9}$");
    }

    private boolean isValidEmail(String value) {
        return value.length() >= 1 && value.length() <= 50;
    }

    private boolean isValidUsername(String value) {
        return value.matches("^[a-zA-Z0-9!@#$%^&*()-_+=<>?]+$") && value.length() >= 6 && value.length() <= 20;
    }

    private boolean isValidPassword(String value) {
        return value.matches("^[a-zA-Z0-9!@#$%^&*()-_+=<>?]+$") && value.length() >= 8 && value.length() <= 20;
    }

    private void handleDeletion(String roomNumber) {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }

        RoomReservation selectedRoom = input.getRoomByNumber(roomNumber);

        if (selectedRoom != null && selectedRoom.isOccupied() && selectedRoom.getOccupant().equals(currentUser)) {
            selectedRoom.vacate();
            JOptionPane.showMessageDialog(this, "Reservation deleted successfully!");
            currentUser.removeReservationByRoomNumber(roomNumber);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid reservation or you don't have permission to delete it.");
        }
        
        updateUserAccount();
        // Clear the input field
        deletionInputField.setText("");
    }    

    private void handleLogin() {
    	 String enteredUsername = loginUsernameField.getText();
    	 String enteredPassword = new String(loginPasswordField.getPassword());

    	    if (users.containsKey(enteredUsername)) {
    	        AccountManagement user = users.get(enteredUsername);
    	        if (user.checkPassword(enteredPassword)) {
    	            currentUser = user;
    	            JOptionPane.showMessageDialog(this, "Login successful!");
    	            showMainMenu();
    	            updateAvailableRooms();
    	        } else {
    	            JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.");
    	        }
    	    } else {
    	        JOptionPane.showMessageDialog(this, "Invalid username. Please try again.");
    	    }
    	}

    private void handleLogout() {
        currentUser = null;
        JOptionPane.showMessageDialog(this, "Log out successful!");
        updateAvailableRooms();
    }

    private void updateAvailableRooms() {
    	availableRoomsTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        availableRoomsTextArea.setText("Room Description: "
        		+ "\nRooms 101, 201, 301 are single queen size bed rooms. "
        		+ "\nRooms 102, 202, 302 are double queen size bed rooms. "
        		+ "\nRooms 103, 203, 303 are deluxe rooms with one king size bed. "
        		+ "\nRooms 104, 204, 304 are suite rooms with two king size beds.\n\n"
        		+ "Available Rooms:\n");
        
        for (RoomReservation room : input.getRooms()) {
            availableRoomsTextArea.append("Room " + room.getNumber() +
                    (room.isOccupied() ? " - Reserved" : " - Available") +
                    " - Price: $" + room.getPricePerNight() + "\n");
        }
    }
    
    private void handleReservation() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }

        // Create a panel for entering reservation details
        JPanel reservationInputPanel = new JPanel(new GridLayout(5, 2));
        reservationInputPanel.add(new JLabel("Select Room Number:"));

        // Create a JComboBox with room numbers
        List<String> roomNumbers = new ArrayList<>();
        for (RoomReservation room : input.getRooms()) {
            roomNumbers.add(room.getNumber());
        }
        
        JComboBox<String> roomNumberComboBox = new JComboBox<>(roomNumbers.toArray(new String[0]));
        reservationInputPanel.add(roomNumberComboBox);

        reservationInputPanel.add(new JLabel("Check-in Date (MM-DD-YYYY):"));
        JTextField checkInDateField = new JTextField();
        reservationInputPanel.add(checkInDateField);
        reservationInputPanel.add(new JLabel("Check-out Date (MM-DD-YYYY):"));
        JTextField checkOutDateField = new JTextField();
        reservationInputPanel.add(checkOutDateField);
        reservationInputPanel.add(new JLabel("Number of People (1-10):"));
        JTextField numberOfPeopleField = new JTextField();
        reservationInputPanel.add(numberOfPeopleField);

        // Show the panel and capture the user's response
        int result = JOptionPane.showConfirmDialog(this, reservationInputPanel, "Enter Reservation Details", JOptionPane.OK_CANCEL_OPTION);

        // Check if the user clicked OK
        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDate currentDate = LocalDate.now();

                LocalDate checkInDate = LocalDate.parse(checkInDateField.getText(), DateTimeFormatter.ofPattern("MM-dd-yyyy"));

                if (checkInDate.isBefore(currentDate)) {
                    JOptionPane.showMessageDialog(this, "Invalid check-in date. Please select a future date.");
                    return;
                }

                LocalDate checkOutDate = LocalDate.parse(checkOutDateField.getText(), DateTimeFormatter.ofPattern("MM-dd-yyyy"));

                if (checkOutDate.isBefore(checkInDate)) {
                    JOptionPane.showMessageDialog(this, "Invalid check-out date. Please select a date after the check-in date.");
                    return;
                }

                int numberOfPeople = Integer.parseInt(numberOfPeopleField.getText());

                // Validate the number of people
                if (numberOfPeople < 1 || numberOfPeople > 10) {
                    JOptionPane.showMessageDialog(this, "Invalid number of people. Please enter a number between 1 and 10.");
                    return;
                }

                String selectedReservation = (String) roomNumberComboBox.getSelectedItem();

                RoomReservation selectedRoom = input.getRoomByNumber(selectedReservation);

                if (selectedRoom != null && !selectedRoom.isOccupied()) {
                    selectedRoom.occupy(currentUser);
                    JOptionPane.showMessageDialog(this, "Reservation successful!");

                    ReservationManagement reservation = new ReservationManagement(selectedRoom, currentUser, checkInDate, checkOutDate, numberOfPeople);
                    currentUser.addReservation(reservation);

                    updateAvailableRooms();
                    saveUsersToFile();
                    // Display the reservation details in the JTextArea
                    reservationTextArea.append("Room " + selectedRoom.getNumber() + " - Reserved - $" + reservation.getRoomPrice() + " per night\n");
                    // Display the number of people in the user account panel
                    userAccountTextArea.append("Number of People: " + reservation.getNumberOfPeople() + "\n");
                } else {
                    JOptionPane.showMessageDialog(this, "Sorry, the room is already occupied or invalid room number.");
                }

            } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid details.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please enter dates in MM-DD-YYYY format.");
            }
        }
    }
    
    private void handleUpdateAccount(
            String newName,
            String newLastName,
            String newPhoneNumber,
            String newStreetNumber,
            String newStreetName,
            String newEmail,
            String newState,
            String newCity,
            String newZipCode,
            String newUsername,
            String newPassword,
            String oldPassword) {
    	
    	 if (currentUser == null) {
    	        JOptionPane.showMessageDialog(this, "Please log in first.");
    	        return;
    	    }

    	    if (!currentUser.checkPassword(oldPassword)) {
    	        JOptionPane.showMessageDialog(this, "Incorrect old password. Please try again.");
    	        return;
    	    }

    	    if (!newName.isEmpty()) {
    	        if (!isValidName(newName)) {
    	            JOptionPane.showMessageDialog(this, "Invalid name. Please use only letters and a maximum of 25 characters.");
    	            return;
    	        }
    	        currentUser.setName(newName);
    	    }

    	    if (!newLastName.isEmpty()) {
    	        if (!isValidName(newLastName)) {
    	            JOptionPane.showMessageDialog(this, "Invalid last name. Please use only letters and a maximum of 25 characters.");
    	            return;
    	        }
    	        currentUser.setLastName(newLastName);
    	    }

    	    if (!newPhoneNumber.isEmpty()) {
    	        if (!isValidPhoneNumber(newPhoneNumber)) {
    	            JOptionPane.showMessageDialog(this, "Invalid phone number. Please enter a number with a minimum of 10 and a maximum of 15 digits.");
    	            return;
    	        }
    	        currentUser.setPhoneNumber(newPhoneNumber);
    	    }

    	    if (!newStreetNumber.isEmpty()) {
    	        if (!isValidStreetNumber(newStreetNumber)) {
    	            JOptionPane.showMessageDialog(this, "Invalid street number. Please enter a number with a minimum of 1 and a maximum of 10 digits.");
    	            return;
    	        }
    	        currentUser.setStreetNumber(newStreetNumber);
    	    }
    	    
    	    if (!newStreetName.isEmpty()) {
    	        if (!isValidStreetName(newStreetName)) {
    	            JOptionPane.showMessageDialog(this, "Invalid Street name. Please use a maximum of 25 characters.");
    	            return;
    	        }
    	        currentUser.setStreetName(newStreetName);
    	    }
    	    
    	    if (!newEmail.isEmpty()) {
    	        if (!isValidEmail(newEmail)) {
    	            JOptionPane.showMessageDialog(this, "Invalid new email. Please use a valid email format.");
    	            return;
    	        }
    	        currentUser.setEmail(newEmail);
    	    }

    	    if (!newState.isEmpty()) {
    	        if (!isValidState(newState)) {
    	            JOptionPane.showMessageDialog(this, "Invalid new state. Please use a valid two-letter state code.");
    	            return;
    	        }
    	        currentUser.setState(newState);
    	    }

    	    if (!newCity.isEmpty()) {
    	        if (!isValidCity(newCity)) {
    	            JOptionPane.showMessageDialog(this, "Invalid new city. Please use a city name with 4 to 30 characters.");
    	            return;
    	        }
    	        currentUser.setCity(newCity);
    	    }

    	    if (!newZipCode.isEmpty()) {
    	        if (!isValidZipCode(newZipCode)) {
    	            JOptionPane.showMessageDialog(this, "Invalid new ZIP code. Please enter a valid ZIP code.");
    	            return;
    	        }
    	        currentUser.setZipCode(newZipCode);
    	    }

    	    if (!newUsername.isEmpty()) {
    	        if (!isValidUsername(newUsername)) {
    	            JOptionPane.showMessageDialog(this, "Invalid username. Please use a maximum of 20 characters and follow the specified requirements.");
    	            return;
    	        }
    	        currentUser.setUsername(newUsername);
    	    }

    	    if (!newPassword.isEmpty()) {
    	        if (!isValidPassword(newPassword)) {
    	            JOptionPane.showMessageDialog(this, "Invalid password. Please use a maximum of 20 characters and follow the specified requirements.");
    	            return;
    	        }
    	        currentUser.setPassword(newPassword);
    	    }
    	    
    	    saveUsersToFile();

    	    JOptionPane.showMessageDialog(this, "Account updated successfully!");

    	    // Return to the main menu
    	    showMainMenu();
    	}
   
    private void handleCheckIn() {
        String input = checkInOutInputField.getText().trim();
        if (!input.isEmpty()) {
            String checkInID = currentUser.getCheckInID();
            if (checkInID == null) {
                // Generate a new check-in ID only if the user doesn't have one
                checkInID = generateRandomID();
                currentUser.setCheckInID(checkInID);
                
                // Save the check-in ID to the users.txt file
                saveUsersToFile();

                JOptionPane.showMessageDialog(this, "Check In Successful!\nYour ID: " + checkInID);
                // Save the ID and show it in the user account information
                updateUserAccount();
            } else {
                // Display the existing check-in ID
                JOptionPane.showMessageDialog(this, "You already have a check-in ID: " + checkInID);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid Room.");
        }
    }

    private void handleCheckOut(String roomNumber) {
        RoomReservation roomToCheckOut = input.getRoomByNumber(roomNumber);

        if (roomToCheckOut != null && roomToCheckOut.isOccupied()) {
            roomToCheckOut.vacate();

            currentUser.setCheckInID(null);

            currentUser.removeReservationByRoomNumber(roomNumber);

            saveUsersToFile();

            updateUserAccount();

            JOptionPane.showMessageDialog(this, "Thanks for Staying!\nThe room is now available for reservation.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid reservation or the room is not occupied.");
        }

        checkInOutInputField.setText("");
    }

    private String generateRandomID() {
        Random random = new Random();
        int randomNum = random.nextInt(900) + 100; // Random number between 100 and 999
        return "U" + randomNum;
    }
   
    private void updateReservationAvailability() {
        reservationAvailabilityTextArea.setText("Reservation Availability:\n");
        for (RoomReservation room : input.getRooms()) {
            String roomStatus = room.isOccupied() ? "Reserved" : "Available";
            double roomPrice = room.getPricePerNight();
            reservationAvailabilityTextArea.append("Room " + room.getNumber() +
                    " - " + roomStatus + " - Price: $" + roomPrice + " per night\n");
        }
    }

    private void updateUserAccount() {
        userAccountTextArea.setText("User Account:\n");
        if (currentUser != null) {
            // Display user information
            userAccountTextArea.append("Name: " + currentUser.getName() + "\n");
            userAccountTextArea.append("Last Name: " + currentUser.getLastName() + "\n");
            userAccountTextArea.append("Phone Number: " + currentUser.getPhoneNumber() + "\n");
            userAccountTextArea.append("Street Number: " + currentUser.getStreetNumber() + "\n");
            userAccountTextArea.append("Street Name: " + currentUser.getStreetName() + "\n");
            userAccountTextArea.append("Email: " + currentUser.getEmail() + "\n");
            userAccountTextArea.append("State: " + currentUser.getState() + "\n"); 
            userAccountTextArea.append("City: " + currentUser.getCity() + "\n");
            userAccountTextArea.append("Zip Code: " + currentUser.getZipCode() + "\n");
            userAccountTextArea.append("Username: " + currentUser.getUsername() + "\n");

            if (currentUser.getCheckInID() != null) {
                userAccountTextArea.append("Check-In ID: " + currentUser.getCheckInID() + "\n");
            }
            
            double totalReservationPrice = 0.0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            
            // Check if reservations are not null before iterating
            if (currentUser.getReservations() != null) {
                // Display reservations made by the user
                for (ReservationManagement reservation : currentUser.getReservations()) {
                    userAccountTextArea.append("Room " + reservation.getRoomNumber() +
                            " - Reserved - $" + reservation.getRoomPrice() + " per night\n");
                    
                    userAccountTextArea.append("Number of People: " + reservation.getNumberOfPeople() + "\n");
                    userAccountTextArea.append("Check-in Date: " + reservation.getCheckInDate().format(formatter) + "\n");
                    userAccountTextArea.append("Check-out Date: " + reservation.getCheckOutDate().format(formatter) + "\n");
                    totalReservationPrice += reservation.getRoomPrice();
                }
            }
            userAccountTextArea.append("Total Reservation Price Per Night: $" + totalReservationPrice + "\n");
        } else {
            userAccountTextArea.append("Not logged in\n");
        }
    }
    
    private static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
               
                if (parts.length >= 11) {
                    String username = parts[9];
                    String password = parts[10];
                    AccountManagement user = new AccountManagement(username, password);

                    // Load the rest of the user details
                    user.setName(parts[0]);
                    user.setLastName(parts[1]);
                    user.setPhoneNumber(parts[2]);
                    user.setEmail(parts[3]);
                    user.setState(parts[4]);
                    user.setCity(parts[5]);
                    user.setZipCode(parts[6]);
                    user.setStreetName(parts[7]);
                    user.setStreetNumber(parts[8]);

                    // Load reservations if available
                    if (parts.length >= 14) {
                        RoomReservation reservedRoom = new RoomReservation(parts[11]);
                        LocalDate checkInDate = LocalDate.parse(parts[12]);
                        LocalDate checkOutDate = LocalDate.parse(parts[13]);
                        int numberOfPeople = Integer.parseInt(parts[14]);
                        String checkInID = parts.length >= 16 ? parts[15] : null;

                        ReservationManagement reservation = new ReservationManagement(reservedRoom, user, checkInDate, checkOutDate, numberOfPeople);
                        // Set checkInID if available
                        if (checkInID != null) {
                            user.setCheckInID(checkInID);
                        }
                        user.addReservation(reservation);
                    }

                    users.put(username, user);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, AccountManagement> entry : users.entrySet()) {
                AccountManagement user = entry.getValue();

                // Save user details
                String line = user.getName() + ":" +
                        user.getLastName() + ":" +
                        user.getPhoneNumber() + ":" +
                        user.getEmail() + ":" +
                        user.getState() + ":" +
                        user.getCity() + ":" +
                        user.getZipCode() + ":" +
                        user.getStreetName() + ":" +
                        user.getStreetNumber() + ":" +
                        user.getUsername() + ":" +
                        user.getPassword();

                // Save user reservations
                if (user.getReservations() != null) {
                    for (ReservationManagement reservation : user.getReservations()) {
                        line += ":" +
                                reservation.getRoomNumber() + ":" +
                                reservation.getCheckInDate() + ":" +
                                reservation.getCheckOutDate() + ":" +
                                reservation.getNumberOfPeople() + ":" +
                                reservation.getRoomPrice(); 

                        if (reservation.getReservedRoom() != null && reservation.getReservedRoom().isOccupied()) {
                            line += ":" + user.getCheckInID();
                        }
                    }
                }

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {       	
        	
            Input hotel = new Input("Hotel", 3);         

            SessionManagement sessionManagement = new SessionManagement(hotel);

            sessionManagement.setLayout(new CardLayout());
            sessionManagement.setVisible(true);         

            loadUsersFromFile();
        });
    }
}