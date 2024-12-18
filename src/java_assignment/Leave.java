/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Leave {
    public enum LeaveType {
        ANNUAL, MEDICAL, UNPAID, MATERNITY
    }

    public enum LeaveStatus {
        PENDING, APPROVED, REJECTED
    }

    private int leaveId;
    private String employeeUsername;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;
    private static int nextId = loadNextId();
    
    public Leave(String employeeUsername, LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
        this.leaveId = nextId++;
        this.employeeUsername = employeeUsername;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
        saveLeaveData();
    }
    
    public Leave(String employeeUsername, String leaveType, String startDate, String endDate, String status) {
        this.leaveId = nextId++;
        this.employeeUsername = employeeUsername;
        this.leaveType = LeaveType.valueOf(leaveType.toUpperCase());  // Assuming LeaveType is an enum
        this.startDate = LocalDate.parse(startDate);  // Assuming startDate is in ISO format (yyyy-MM-dd)
        this.endDate = LocalDate.parse(endDate);      // Assuming endDate is in ISO format (yyyy-MM-dd)
        this.status = LeaveStatus.valueOf(status.toUpperCase());  // Assuming LeaveStatus is an enum
    }
    public int getLeaveId() {
        return leaveId;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
        updateLeaveData();
    }

    private void saveLeaveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("leave.txt", true))) {
            String leaveData = leaveId + "," + employeeUsername + "," + leaveType + "," + startDate + "," + endDate + "," + status;
            bw.write(leaveData);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLeaveData() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == leaveId) {
                    line = leaveId + "," + employeeUsername + "," + leaveType + "," + startDate + "," + endDate + "," + status;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("leave.txt"))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Leave getLeaveById(int leaveId) {
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0].trim()) == leaveId) {
                    String employeeUsername = parts[1].trim();
                    LeaveType leaveType = LeaveType.valueOf(parts[2].trim());
                    LocalDate startDate = LocalDate.parse(parts[3].trim());
                    LocalDate endDate = LocalDate.parse(parts[4].trim());
                    LeaveStatus status = LeaveStatus.valueOf(parts[5].trim());

                    Leave leave = new Leave(employeeUsername, leaveType, startDate, endDate);
                    leave.setStatus(status);
                    return leave;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Leave> getLeavesByEmployee(String employeeUsername) {
        List<Leave> leaves = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].trim().equals(employeeUsername)) {
                    int leaveId = Integer.parseInt(parts[0].trim());
                    LeaveType leaveType = LeaveType.valueOf(parts[2].trim());
                    LocalDate startDate = LocalDate.parse(parts[3].trim());
                    LocalDate endDate = LocalDate.parse(parts[4].trim());
                    LeaveStatus status = LeaveStatus.valueOf(parts[5].trim());

                    Leave leave = new Leave(employeeUsername, leaveType, startDate, endDate);
                    leave.setStatus(status);
                    leaves.add(leave);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    public static List<Leave> getPendingLeaves() {
        List<Leave> pendingLeaves = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                LeaveStatus status = LeaveStatus.valueOf(parts[5].trim());
                if (status == LeaveStatus.PENDING) {
                    int leaveId = Integer.parseInt(parts[0].trim());
                    String employeeUsername = parts[1].trim();
                    LeaveType leaveType = LeaveType.valueOf(parts[2].trim());
                    LocalDate startDate = LocalDate.parse(parts[3].trim());
                    LocalDate endDate = LocalDate.parse(parts[4].trim());

                    Leave leave = new Leave(employeeUsername, leaveType, startDate, endDate);
                    leave.setStatus(status);
                    pendingLeaves.add(leave);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pendingLeaves;
    }
    
    @Override
    public String toString() {
        return "Leave{" +
                "leaveId=" + leaveId +
                ", employeeUsername='" + employeeUsername + '\'' +
                ", leaveType=" + leaveType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
    
     private static int loadNextId() {
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            while (br.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount + 1;
    }
}
