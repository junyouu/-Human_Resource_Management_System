package java_assignment;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Human_Resource_Officer extends User {

    Human_Resource_Officer() {
        super();
    }

    Human_Resource_Officer(String username, String password, String role) {
        super(username, password, role);
    }

    public void hrOfficerUpdateEmployeeProfileButtonActionPerformed(ActionEvent evt) {
        // Prompt for username
        String username = JOptionPane.showInputDialog(null, "Enter the username of the employee to update:");

        if (username != null && !username.trim().isEmpty()) {
            // Load employee data if username is found
            if (loadEmployeeData(username)) {
                // Show update window
                showEmployeeUpdateWindow(username);
            } else {
                JOptionPane.showMessageDialog(null, "Employee not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
        }
    }
    
    public boolean loadEmployeeData(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[1].equals(username)) {
                    // Employee found
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void loadEmployeeDataForUpdate(String username, JTextField txtName, JTextField txtPhoneNumber, JTextField txtPosition, JTextField txtDepartment, JTextField txtEmergencyContact, JTextField txtWorkingExperience, JTextField txtHistoryOfSalary, JTextField txtChangeOfPosition, JTextField txtGrossSalary) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[1].equals(username)) {
                    // Populate the fields with existing employee data
                    txtName.setText(data[2]);
                    txtPhoneNumber.setText(data[3]);
                    txtPosition.setText(data[4]);
                    txtDepartment.setText(data[5]);
                    txtEmergencyContact.setText(data[6]);
                    txtWorkingExperience.setText(data[7]);
                    txtHistoryOfSalary.setText(data[8]);
                    txtChangeOfPosition.setText(data[9]);
                    txtGrossSalary.setText(data[11]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void showEmployeeUpdateWindow(String username) {
        JFrame frame = new JFrame("Update Employee Information");
        frame.setSize(600, 400); // Adjusted size for more fields
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 2, 10, 10));

        // Labels and fields
        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(username);
        txtUsername.setEditable(false);

        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        JTextField txtPhoneNumber = new JTextField();

        JLabel lblPosition = new JLabel("Position:");
        JTextField txtPosition = new JTextField();

        JLabel lblDepartment = new JLabel("Department:");
        JTextField txtDepartment = new JTextField();

        JLabel lblEmergencyContact = new JLabel("Emergency Contact:");
        JTextField txtEmergencyContact = new JTextField();

        JLabel lblWorkingExperience = new JLabel("Working Experience:");
        JTextField txtWorkingExperience = new JTextField();

        JLabel lblHistoryOfSalary = new JLabel("History of Salary:");
        JTextField txtHistoryOfSalary = new JTextField();

        JLabel lblChangeOfPosition = new JLabel("Change of Position:");
        JTextField txtChangeOfPosition = new JTextField();

        JLabel lblGrossSalary = new JLabel("Gross Salary:");
        JTextField txtGrossSalary = new JTextField();

        // Fetch existing employee data
        loadEmployeeDataForUpdate(username, txtName, txtPhoneNumber, txtPosition, txtDepartment, txtEmergencyContact, txtWorkingExperience, txtHistoryOfSalary, txtChangeOfPosition, txtGrossSalary);

        JButton btnUpdate = new JButton("Update");

        // Add components to the frame
        frame.add(lblUsername);
        frame.add(txtUsername);
        frame.add(lblName);
        frame.add(txtName);
        frame.add(lblPhoneNumber);
        frame.add(txtPhoneNumber);
        frame.add(lblPosition);
        frame.add(txtPosition);
        frame.add(lblDepartment);
        frame.add(txtDepartment);
        frame.add(lblEmergencyContact);
        frame.add(txtEmergencyContact);
        frame.add(lblWorkingExperience);
        frame.add(txtWorkingExperience);
        frame.add(lblHistoryOfSalary);
        frame.add(txtHistoryOfSalary);
        frame.add(lblChangeOfPosition);
        frame.add(txtChangeOfPosition);
        frame.add(lblGrossSalary);
        frame.add(txtGrossSalary);
        frame.add(new JLabel()); // Empty cell
        frame.add(btnUpdate);

        // Add action listener for the update button
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve updated values from text fields
                String name = txtName.getText();
                String phoneNumber = txtPhoneNumber.getText();
                String position = txtPosition.getText();
                String department = txtDepartment.getText();
                String emergencyContact = txtEmergencyContact.getText();
                String workingExperience = txtWorkingExperience.getText();
                String historyOfSalary = txtHistoryOfSalary.getText();
                String changeOfPosition = txtChangeOfPosition.getText();
                String grossSalary = txtGrossSalary.getText();

                // Update the employee data
                updateEmployeeData(username, name, phoneNumber, position, department, emergencyContact, workingExperience, historyOfSalary, changeOfPosition, grossSalary);
                JOptionPane.showMessageDialog(frame, "Employee information updated successfully.");
                frame.dispose(); // Close the window after update
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    public void updateEmployeeData(String username, String name, String phoneNumber, String position, String department, String emergencyContact, String workingExperience, String historyOfSalary, String changeOfPosition, String grossSalary) {
        File file = new File("employee.txt");
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[1].equals(username)) {
                    // Update the relevant fields while preserving employeeId and leaveEntitlement
                    content.append(data[0]).append(",") // employeeId (unchanged)
                           .append(username).append(",") // username (unchanged)
                           .append(name).append(",") // name
                           .append(phoneNumber).append(",") // phone number
                           .append(position).append(",") // position
                           .append(department).append(",") // department
                           .append(emergencyContact).append(",") // emergency contact (updated)
                           .append(workingExperience).append(",") // working experience (updated)
                           .append(historyOfSalary).append(",") // history of salary (updated)
                           .append(changeOfPosition).append(",") // change of position (updated)
                           .append(data[10]).append(",") // leave entitlement (unchanged)
                           .append(grossSalary); // gross salary (updated)
                } else {
                    content.append(line);
                }
                content.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


