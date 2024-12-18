/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ldcle
 */
public class User {
    protected String username;
    protected String password;
    protected String role;
    protected int loginAttempt;
    protected boolean isLocked;
    
    User(){
        this.username = "";
        this.password = "";
        this.role = "";
        this.loginAttempt = 0;
        this.isLocked = false;
    }
    
    User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
        this.loginAttempt = 0;
        this.isLocked = false;
    }
    
    public String getUsername(){ 
        return this.username;
    }   
    public String getPassword(){ 
        return this.password;
    }  
    public void setUSername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString(){
        return this.username + "," + this.password + "," + this.role + "," + this.loginAttempt + "," + this.isLocked;
    }
    
    public Object[] login(String username, String password){
        username = username.trim();
        password = password.trim();
        boolean loginSuccessful = false;
        String loginRole = null;
        String line;
        List<String> lines = new ArrayList<>();
        String statusMessage = "Login Unsuccessful. Please check your username and password.";
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String lineUsername = credentials[0].trim();
                String linePassword = credentials[1].trim();
                String role = credentials[2].trim();
                int loginAttempt = Integer.parseInt(credentials[3].trim());
                boolean isLocked = Boolean.parseBoolean(credentials[4].trim());
                if (lineUsername.equals(username) && isLocked){
                    statusMessage = "Account is locked. Please contact the system administrator.";
                    return new Object[]{false, null, statusMessage};
                }
                if (lineUsername.equals(username) && linePassword.equals(password)) {
                    loginAttempt = 0;
                    loginRole = role;
                    loginSuccessful = true;
                    statusMessage = "Login Successful";
                }
                if(lineUsername.equals(username) && !loginSuccessful){
                    loginAttempt++;
                }
                if (lineUsername.equals(username) && loginAttempt >= 3){
                    System.out.println("yoooo");
                    isLocked = true;
                    statusMessage = "Account is locked due to 3 failed login attempts. Please contact the system administrator to unlock.";
                }
                String udpatedLine = lineUsername + ", " + linePassword + ", " + role + ", " + loginAttempt + ", " + isLocked;
//                System.out.println(udpatedLine);
                lines.add(udpatedLine); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (!loginSuccessful) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(statusMessage);
        return new Object[]{loginSuccessful, loginRole, statusMessage};
    }
    
    public boolean resetPassword(String username, String currentPassword, String newPassword){
        username = username.trim();
        currentPassword = currentPassword.trim();
        newPassword = newPassword.trim();
        String line;
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;
        if (!isPasswordComplex(newPassword)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, and include at least one uppercase letter, lowercase letter, number and special character", 
                                          "Password Complexity", 
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String lineUsername = credentials[0].trim();
                String linePassword = credentials[1].trim();
                String role = credentials[2].trim();
                int loginAttempt = Integer.parseInt(credentials[3].trim());
                boolean isLocked = Boolean.parseBoolean(credentials[4].trim());
                if (username.equals(lineUsername) && currentPassword.equals(linePassword)){
                    linePassword = newPassword;
                    isUpdated = true;    
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
        return isUpdated;
    }
    
    public static boolean isPasswordComplex(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*\\d.*") && 
               password.matches(".*[!@#$%^&*].*"); 
    }
    
    
}