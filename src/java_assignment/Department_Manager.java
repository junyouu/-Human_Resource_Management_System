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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ldcle
 */

public class Department_Manager extends User {

    public Department_Manager(String username, String password, String role) {
        super(username, password, role);
    }

    public List<Leave> viewPendingLeaves() {
        List<Leave> pendingLeaves = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] leaveData = line.split(",");
                String status = leaveData[5].trim();  // Status is the last field

                if ("PENDING".equalsIgnoreCase(status)) {
                    String username = leaveData[1].trim();  // Username is the second field
                    String leaveType = leaveData[2].trim();  // LeaveType is the third field
                    String startDate = leaveData[3].trim();  // Start date is the fourth field
                    String endDate = leaveData[4].trim();    // End date is the fifth field

                    // Create a new Leave object using the new constructor
                    Leave leave = new Leave(username, leaveType, startDate, endDate, status);
                    pendingLeaves.add(leave);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pendingLeaves;
    }

    public void loadPendingLeavesIntoTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows in the table

        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] leaveData = line.split(",");
                String status = leaveData[5].trim();  // Status is the last field

                if ("PENDING".equalsIgnoreCase(status)) {
                    String leaveId = leaveData[0].trim();     // Leave ID is the first field
                    String username = leaveData[1].trim();    // Username is the second field
                    String leaveType = leaveData[2].trim();   // LeaveType is the third field
                    String startDate = leaveData[3].trim();   // Start date is the fourth field
                    String endDate = leaveData[4].trim();     // End date is the fifth field

                    // Add this data as a new row in the table
                    model.addRow(new Object[]{leaveId, username, leaveType, startDate, endDate, status});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean updateLeaveStatus(String leaveId, String newStatus) {
        boolean isUpdated = false;
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] leaveData = line.split(",");
                String currentLeaveId = leaveData[0].trim();
                if (currentLeaveId.equals(leaveId)) {
                    leaveData[5] = newStatus;  // Update status (6th field)
                    isUpdated = true;
                }
                lines.add(String.join(",", leaveData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("leave.txt"))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }
    
    public void loadAllLeavesIntoTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows in the table

        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] leaveData = line.split(",");

                String leaveId = leaveData[0].trim();     // Leave ID is the first field
                String username = leaveData[1].trim();    // Username is the second field
                String leaveType = leaveData[2].trim();   // LeaveType is the third field
                String startDate = leaveData[3].trim();   // Start date is the fourth field
                String endDate = leaveData[4].trim();     // End date is the fifth field
                String status = leaveData[5].trim();      // Status is the sixth field

                // Add this data as a new row in the table
                model.addRow(new Object[]{leaveId, username, leaveType, startDate, endDate, status});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
//    public void approveLeave(String employeeUsername, String startDate) {
//        boolean isApproved = updateLeaveStatus(employeeUsername, startDate, "Approved");
//        if (isApproved) {
//            System.out.println("Leave approved for " + employeeUsername + " starting on " + startDate);
//        } else {
//            System.out.println("Leave approval failed for " + employeeUsername);
//        }
//    }
//
//    public void rejectLeave(String employeeUsername, String startDate) {
//        boolean isRejected = updateLeaveStatus(employeeUsername, startDate, "Rejected");
//        if (isRejected) {
//            System.out.println("Leave rejected for " + employeeUsername + " starting on " + startDate);
//        } else {
//            System.out.println("Leave rejection failed for " + employeeUsername);
//        }
//    }
//
//    private Leave parseLeave(String[] leaveData) {
//        // Assuming Leave has a constructor that takes these parameters
//        return new Leave(leaveData[0].trim(), leaveData[1].trim(), leaveData[2].trim(), leaveData[3].trim(), leaveData[4].trim());
//    }
 
}