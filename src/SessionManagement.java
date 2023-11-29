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
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;

public class SessionManagement extends JFrame {
	private final static String USER_FILE = "users.txt";
    private Input input;
    private JTextArea outputTextArea;
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

    private JTextArea reservationTextArea;
    private JTextArea reservationAvailabilityTextArea;
    private JTextArea userAccountTextArea;
    private JTextArea createAccountTextArea;
    private JTextArea availableRoomsTextArea;
    
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    
    /**
     * SessionManagement 
     * @param input
     */
    public SessionManagement(Input input) {
    	loadUsersFromFile(); //invoke loadusersFromFile
        this.input = input;
        this.users = new HashMap<>();

        setTitle("Hotel Reservation");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameField = new JTextField();
        lastNameField = new JTextField();
        phoneNumberField = new JTextField();
        addressField = new JTextField();
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
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to GR8's Hotel!");  //GR8's is a pun on Group 8, it's pronounced Great's
        panel.add(welcomeLabel);
                   
        // create account button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateAccountMenu();
            }
        });
        
        // create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginMenu();
            }
        });
        
        // create logout button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        
        // create reserve button
        JButton reserveButton = new JButton("Make Reservation");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReservationMenu();
                updateAvailableRooms();  // Call the method here
            }
        });

        // create viewReservations button
        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReservationAvailability();
                updateReservationAvailability();
            }
        });
        
        // create viewAccount button
        JButton viewAccountButton = new JButton("View Account");
        viewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserAccount();
                updateUserAccount();
            }
        });
        
        // create updateAccount button
        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateUserScreen();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createAccountButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(reserveButton);
        buttonPanel.add(viewReservationsButton);
        buttonPanel.add(viewAccountButton);
        buttonPanel.add(updateAccountButton);
        
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(reserveButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReservationAvailabilityPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        reservationAvailabilityTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(reservationAvailabilityTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createUserAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        userAccountTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(userAccountTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());

        JButton deleteReservationButton = new JButton("Delete Reservation");
        deleteReservationButton.addActionListener(e -> showDeletionScreen());

        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener(e -> showUpdateUserScreen());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(deleteReservationButton);
        buttonPanel.add(updateAccountButton);  // Added button for updating account

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel createUpdateUserScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create input fields for updating user information
        JTextField newNameField = new JTextField();
        JTextField newLastNameField = new JTextField();
        JTextField newPhoneNumberField = new JTextField();
        JTextField newAddressField = new JTextField();
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
                        newAddressField.getText(),
                        newUsernameField.getText(),
                        new String(newPasswordField.getPassword()),
                        new String(oldPasswordField.getPassword())
                );
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("New Name:"));
        inputPanel.add(newNameField);
        inputPanel.add(new JLabel("New Last Name:"));
        inputPanel.add(newLastNameField);
        inputPanel.add(new JLabel("New Phone Number:"));
        inputPanel.add(newPhoneNumberField);
        inputPanel.add(new JLabel("New Address:"));
        inputPanel.add(newAddressField);
        inputPanel.add(new JLabel("New Username:"));
        inputPanel.add(newUsernameField);
        inputPanel.add(new JLabel("New Password:"));
        inputPanel.add(newPasswordField);
        inputPanel.add(new JLabel("Old Password:"));
        inputPanel.add(oldPasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateAccountButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set updateAccountScreenPanel to the created panel
        updateAccountScreenPanel = panel;

        return panel;
    }
       

    private JPanel createCreateAccountMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Initialize the input fields
        nameField = new JTextField();
        lastNameField = new JTextField();
        phoneNumberField = new JTextField();
        addressField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        
        JButton createAccountConfirmButton = new JButton("Create Account");
        createAccountConfirmButton.addActionListener(e -> handleCreateAccount());
        

        // Create a layout for labels and fields (e.g., GridBagLayout or GridLayout)
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(createAccountConfirmButton);

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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Create a layout for labels and fields (e.g., GridBagLayout or GridLayout)
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(loginUsernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(loginPasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel createDeletionScreenPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        deletionInputField = new JTextField();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showUserAccount()); // Adjust the action to go back to the user account screen

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> handleDeletion(deletionInputField.getText()));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Enter Reservation to Delete:"));
        inputPanel.add(deletionInputField);
        inputPanel.add(new JLabel()); // Empty label for layout
        inputPanel.add(deleteButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel createUpdateUserScreenPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create input fields for updating user information
        JTextField newNameField = new JTextField();
        JTextField newLastNameField = new JTextField();
        JTextField newPhoneNumberField = new JTextField();
        JTextField newAddressField = new JTextField();
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
                    newAddressField.getText(),
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
                showUserAccount();
            }
        });

        // Create a layout for labels and fields (e.g., GridBagLayout or GridLayout)
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("New Name:"));
        inputPanel.add(newNameField);
        inputPanel.add(new JLabel("New Last Name:"));
        inputPanel.add(newLastNameField);
        inputPanel.add(new JLabel("New Phone Number:"));
        inputPanel.add(newPhoneNumberField);
        inputPanel.add(new JLabel("New Address:"));
        inputPanel.add(newAddressField);
        inputPanel.add(new JLabel("New Username:"));
        inputPanel.add(newUsernameField);
        inputPanel.add(new JLabel("New Password:"));
        inputPanel.add(newPasswordField);
        inputPanel.add(new JLabel("Old Password:"));
        inputPanel.add(oldPasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateAccountButton);
        buttonPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private void showLoginMenu() {
        mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(true);
    }
    
    private void showMainMenu() {
        mainMenuPanel.setVisible(true);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
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
        // Hide other panels
    	mainMenuPanel.setVisible(false);
        reservationMenuPanel.setVisible(false);
        reservationAvailabilityPanel.setVisible(false);
        userAccountPanel.setVisible(false);
        createAccountMenuPanel.setVisible(false);
        createLoginMenuPanel.setVisible(false);
        deletionScreenPanel.setVisible(false);

        // Check if the update account panel has been added
        if (updateAccountScreenPanel == null || !updateAccountScreenPanel.isShowing()) {
            // If not, create and add the update account panel
            updateAccountScreenPanel = createUpdateUserScreenPanel();
            add(updateAccountScreenPanel);
        }

        // Show the update account panel
        updateAccountScreenPanel.setVisible(true);

        // Refresh the frame
        validate();
        repaint();
    }
    
    private void handleCreateAccount() {
        // Get user information from input fields
        String name = nameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String address = addressField.getText().trim();
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

        // Validate address
        if (!isValidAddress(address)) {
            JOptionPane.showMessageDialog(this, "Invalid address. Please limit to 35 characters or numbers.");
            return;
        }

        // Validate username and password
        if (!isValidUsername(username) || !isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password. UserName min of 6 characters, and password a minimum of 8characters max 20.");
            return;
        }

        // Check if any of the required fields is empty
        if (name.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the required details (Name, Last Name, Phone Number, Address, Username, Password).");
            return;
        }
        
        // Create a new user with the provided information
        AccountManagement newUser = new AccountManagement(name, lastName, phoneNumber, address, username, password);
        users.put(username, newUser);

        // Display user information in the "View Account" panel
        userAccountTextArea.setText("User Account:\n");
        userAccountTextArea.append("Name: " + newUser.getName() + "\n");
        userAccountTextArea.append("Last Name: " + newUser.getLastName() + "\n");
        userAccountTextArea.append("Phone Number: " + newUser.getPhoneNumber() + "\n");
        userAccountTextArea.append("Address: " + newUser.getAddress() + "\n");
        userAccountTextArea.append("Username: " + newUser.getUsername() + "\n");

        // Set the current user to the newly created user
        currentUser = newUser;

        saveUsersToFile();
        
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

    private boolean isValidAddress(String value) {
        return value.length() <= 35;
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

        RoomManagement selectedRoom = input.getRoomByNumber(roomNumber);

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
        availableRoomsTextArea.setText("Available Rooms:\n");
        
        for (RoomManagement room : input.getRooms()) {
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

        String selectedReservation = JOptionPane.showInputDialog("Enter the room number to reserve:");
        handleReservationSelection(selectedReservation);
    }

    private void handleReservationSelection(String selectedReservation) {
    	try {
            LocalDate checkInDate = LocalDate.parse(
                    JOptionPane.showInputDialog("Enter check-in date (MM-DD-YYYY):"),
                    DateTimeFormatter.ofPattern("MM-dd-yyyy")
            );

            LocalDate checkOutDate = LocalDate.parse(
                    JOptionPane.showInputDialog("Enter check-out date (MM-DD-YYYY):"),
                    DateTimeFormatter.ofPattern("MM-dd-yyyy")
            );
            
            RoomManagement selectedRoom = input.getRoomByNumber(selectedReservation);

            if (selectedRoom != null && !selectedRoom.isOccupied()) {
                selectedRoom.occupy(currentUser);
                JOptionPane.showMessageDialog(this, "Reservation successful!");

                ReservationManagement reservation = new ReservationManagement(selectedRoom, currentUser, checkInDate, checkOutDate);
                currentUser.addReservation(reservation);
                
                updateAvailableRooms();

                // Display the reservation details in the JTextArea
                reservationTextArea.append("Room " + selectedRoom.getNumber() + " - Reserved - $" + reservation.getRoomPrice() + " per night\n");

            } else {
                JOptionPane.showMessageDialog(this, "Sorry, the room is already occupied or invalid room number.");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid room number.");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter dates in YYYY-MM-DD format.");
        }
    }
    
    private void handleUpdateUser() {
        // Retrieve information from input fields
        String newName = nameField.getText();
        String newLastName = lastNameField.getText();
        String newPhoneNumber = phoneNumberField.getText();
        String newAddress = addressField.getText();
        String newPassword = new String(passwordField.getPassword());

        // Call the update methods in AccountManagement
        currentUser.updateUserInfo(newName, newLastName, newPhoneNumber, newAddress);

        // If the user provided a new password, updates it
        if (!newPassword.isEmpty()) {
            // Retrieve the old password from an input field
            String oldPassword = JOptionPane.showInputDialog("Enter your old password:");
            if (currentUser.updatePassword(oldPassword, newPassword)) {
                JOptionPane.showMessageDialog(this, "Account updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect old password. Account not updated.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Account updated successfully!");
        }

        // Go back to the user account screen
        showUserAccount();
    }
    
    private void handleUpdateAccount(String newName, String newLastName, String newPhoneNumber, String newAddress, String newUsername, String newPassword, String oldPassword) {
    	 if (currentUser == null) {
    	        JOptionPane.showMessageDialog(this, "Please log in first.");
    	        return;
    	    }

    	    if (!currentUser.checkPassword(oldPassword)) {
    	        JOptionPane.showMessageDialog(this, "Incorrect old password. Please try again.");
    	        return;
    	    }

    	    // Check if each field is empty and update only non-empty fields
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

    	    if (!newAddress.isEmpty()) {
    	        if (!isValidAddress(newAddress)) {
    	            JOptionPane.showMessageDialog(this, "Invalid address. Please use a maximum of 35 characters.");
    	            return;
    	        }
    	        currentUser.setAddress(newAddress);
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

    	    JOptionPane.showMessageDialog(this, "Account updated successfully!");

    	    // Return to the main menu
    	    showMainMenu();
    	}

    private void updateReservationAvailability() {
        reservationAvailabilityTextArea.setText("Reservation Availability:\n");
        for (RoomManagement room : input.getRooms()) {
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
            userAccountTextArea.append("Address: " + currentUser.getAddress() + "\n");
            userAccountTextArea.append("Username: " + currentUser.getUsername() + "\n");

            double totalReservationPrice = 0.0;

            // Display reservations made by the user
            for (ReservationManagement reservation : currentUser.getReservations()) {
                userAccountTextArea.append("Room " + reservation.getRoomNumber() +
                        " - Reserved - $" + reservation.getRoomPrice() + " per night\n");
                
                userAccountTextArea.append("Check-in Date: " + reservation.getCheckInDate() + "\n");
                userAccountTextArea.append("Check-out Date: " + reservation.getCheckOutDate() + "\n");
                totalReservationPrice += reservation.getRoomPrice();
            }
            userAccountTextArea.append("Total Reservation Price: $" + totalReservationPrice + "\n");
        } else {
            userAccountTextArea.append("Not logged in\n");
        }
    }
    
    private static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    users.put(username, new AccountManagement(username, password));
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
                String line = entry.getKey() + ":" + entry.getValue().getPassword();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {       	
        	
            Input hotel = new Input("Sample Hotel", 3);

            SessionManagement sessionManagement = new SessionManagement(hotel);
            sessionManagement.setLayout(new CardLayout());
            sessionManagement.setVisible(true);
            loadUsersFromFile();
        });
    }
}