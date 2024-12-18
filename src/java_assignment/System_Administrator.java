/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ldcle
 */
public class System_Administrator extends User{
    
    System_Administrator(){
        super();
    }
    
    System_Administrator(String username, String password, String role) {
        super(username, password, role);
    }
    
    public void loadUserDataIntoTable(JTable table){
        List<String[]> userData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                userData.add(userDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for (String[] user : userData) {
            model.addRow(user);  
        } 
    }
    
    public void loadUserDataIntoManageUserTable(JTable table){
        List<String[]> userData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                String[] relevantDetails = new String[] {
                    userDetails[0].trim(),
                    userDetails[1].trim(), 
                    userDetails[2].trim(),
                    userDetails[4].trim()
                };
                userData.add(relevantDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for (String[] user : userData) {
            model.addRow(user);  
        } 
    }
    
    public boolean saveNewUserToFile(String line){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean unlockUserAccount(String username){
        boolean isUnlocked = false;
        String line;
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String lineUsername = credentials[0].trim();
                String linePassword = credentials[1].trim();
                String role = credentials[2].trim();
                int loginAttempt = Integer.parseInt(credentials[3].trim());
                boolean isLocked = Boolean.parseBoolean(credentials[4].trim());
                if (username.equals(lineUsername)){
                    loginAttempt = 0;
                    isLocked = false;  
                    isUnlocked = true;
                }
                String udpatedLine = lineUsername + ", " + linePassword + ", " + role + ", " + loginAttempt + ", " + isLocked;
                lines.add(udpatedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isUnlocked;
    }
    
    public boolean disbleUser(String username) {
        boolean isUpdated = false;
        List<String[]> lines = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].trim().equals(username)) {
                    userDetails[4] = "true"; 
                    isUpdated = true;
                }
                lines.add(userDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (isUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"))) {
                for (String[] userDetails : lines) {
                    bw.write(String.join(",", userDetails));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return isUpdated;
    }
    
    public boolean usernameExists(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].trim().equals(username)) {
                    return true;  
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;  
    }
    
    public boolean updateUser(String currentUsername, String newUsername, String newRole) {
        boolean isUpdated = false;
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].trim().equals(currentUsername)) {
                    if (newUsername != null && !newUsername.trim().isEmpty()) {
                        userDetails[0] = newUsername;
                    }
                    if (newRole != null && !newRole.trim().isEmpty()) {
                        userDetails[2] = newRole;
                    }
                    isUpdated = true;
                }
                lines.add(userDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"))) {
                for (String[] userDetails : lines) {
                    bw.write(String.join(",", userDetails));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return isUpdated;
    }
    
    
}
