package java_assignment;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Employee extends User {
    private static int nextId = loadNextId();
    private int employeeId;
    private String name;
    private String department;
    private String position;
    private String phoneNumber;
    private double grossSalary = 0.0;
    private boolean isClockedIn = false;
    private LocalDateTime lastClockInTime;
    private static final double PENALTY_AMOUNT = 100.0;
    
    private String emergencyContact;
    private String workingExperience;
    private List<String> salaryHistory;
    private List<LeaveEntitlement> leaveEntitlements;
    private List<String> positionHistory;

    public Employee() {
        super();
        this.employeeId = nextId++;
        this.name = "";
        this.department = "";
        this.position = "";
        this.phoneNumber = "";
        this.grossSalary = 0.0; // Default gross salary
        this.workingExperience = "";
        this.salaryHistory = new ArrayList<>();
        this.leaveEntitlements = new ArrayList<>();
        this.positionHistory = new ArrayList<>();
        this.emergencyContact = "";
        initializeLeaveEntitlements();
    }

    public Employee(String username, String password, String role) {
        super(username, password, role);
        this.employeeId = nextId++;
        this.name = "";
        this.department = "";
        this.position = "";
        this.grossSalary = 0.0; 
        this.phoneNumber = "";
        this.workingExperience = "";
        this.salaryHistory = new ArrayList<>();
        this.leaveEntitlements = new ArrayList<>();
        this.positionHistory = new ArrayList<>();
        this.emergencyContact = "";
        initializeLeaveEntitlements();
    }

    public Employee(String username, String password, String role, String department, String position, double grossSalary) {
        super(username, password, role);
        this.employeeId = nextId++;
        this.name = "";
        this.department = department;
        this.position = position;
        this.grossSalary = grossSalary;
        this.workingExperience = "";
        this.phoneNumber = "";
        this.salaryHistory = new ArrayList<>();
        this.leaveEntitlements = new ArrayList<>();
        this.positionHistory = new ArrayList<>();
        this.emergencyContact = "";
        initializeLeaveEntitlements();
    }

    public Employee(String username, String password, String role, String department, String position, double grossSalary, String emergencyContact, String workingExperience, List<String> salaryHistory, List<String> positionHistory) {
        super(username, password, role);
        this.employeeId = nextId++;
        this.name = "";
        this.department = department;
        this.position = position;
        this.grossSalary = grossSalary;
        this.workingExperience = workingExperience;
        this.phoneNumber = "";
        this.salaryHistory = salaryHistory != null ? salaryHistory : new ArrayList<>();
        this.leaveEntitlements = LeaveManager.getFormattedLeaveData(username);
        this.positionHistory = positionHistory != null ? positionHistory : new ArrayList<>();
        this.emergencyContact = emergencyContact;
        initializeLeaveEntitlements();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isIsClockedIn() {
        return isClockedIn;
    }

    public void setIsClockedIn(boolean isClockedIn) {
        this.isClockedIn = isClockedIn;
    }

    public LocalDateTime getLastClockInTime() {
        return lastClockInTime;
    }

    public void setLastClockInTime(LocalDateTime lastClockInTime) {
        this.lastClockInTime = lastClockInTime;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(String workingExperience) {
        this.workingExperience = workingExperience;
    }

    public List<String> getSalaryHistory() {
        return salaryHistory;
    }

    public void setSalaryHistory(List<String> salaryHistory) {
        this.salaryHistory = salaryHistory;
    }

    public List<LeaveEntitlement> getLeaveEntitlements() {
        return leaveEntitlements;
    }

    public void setLeaveEntitlements(List<LeaveEntitlement> leaveEntitlements) {
        this.leaveEntitlements = leaveEntitlements;
    }

    private void initializeLeaveEntitlements() {
        leaveEntitlements.add(new LeaveEntitlement("ANNUAL", 14));
        leaveEntitlements.add(new LeaveEntitlement("MEDICAL", 14));
        leaveEntitlements.add(new LeaveEntitlement("UNPAID", 0));
        leaveEntitlements.add(new LeaveEntitlement("MATERNITY", 60));
    }
    
    public static Employee getEmployeeById(int employeeId) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0].trim()) == employeeId) {
                    String username = parts[1].trim();
                    String name = parts[2].trim();
                    String department = parts[5].trim();
                    String position = parts[4].trim();
                    double grossSalary = Double.parseDouble(parts[11].trim());
                    System.out.print("Gross Salary retrieved: " + grossSalary);

                    Employee employee = new Employee(username, "password", "Employee", department, position, grossSalary);
                    return employee;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public void updateLeaveEntitlement(String leaveType, int days) {
        for (LeaveEntitlement entitlement : leaveEntitlements) {
            if (entitlement.getLeaveType().equals(leaveType)) {
                entitlement.setDays(days);
                return;
            }
        }
        leaveEntitlements.add(new LeaveEntitlement(leaveType, days));
    }
    
    public void viewProfile() {
        System.out.println("Employee Profile:");
        System.out.println("Name: " + this.name);
        System.out.println("Department: " + this.department);
        System.out.println("Position: " + this.position);
        System.out.println("Gross Salary: " + this.grossSalary);
        System.out.println("Emergency Contact: " + this.emergencyContact);
        System.out.println("Working Experience: " + String.join(", ", this.workingExperience));
        System.out.println("Salary History: " + String.join(", ", this.salaryHistory));
        System.out.println("Leave Entitlements: ");
        for (LeaveEntitlement entitlement : leaveEntitlements) {
            System.out.println(entitlement);
        }
    }

    
    public void saveEmployeeData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employee.txt", true))) {
            // Format: employeeId,username,name,phone number,position,department,emergency_contact,workingExperience,historfOfSalary,ChangeofPosition,leaveEntitlement,GrossSalary
            String salaryHistoryStr = String.join(";", this.salaryHistory); // Use ";" as a delimiter
            String positionHistoryStr = String.join(";", this.positionHistory); // Use ";" as a delimiter
            String leaveEntitlementsStr = this.leaveEntitlements.stream()
                    .map(LeaveEntitlement::toString)
                    .reduce((a, b) -> a + ";" + b)
                    .orElse(""); // Join leave entitlements

            String employeeData = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f",
                    this.employeeId, 
                    getUsername(), // Include username
                    this.name, 
                    this.phoneNumber, 
                    this.position, 
                    this.department, 
                    this.emergencyContact,
                    this.workingExperience, 
                    salaryHistoryStr, 
                    positionHistoryStr, 
                    leaveEntitlementsStr, 
                    this.grossSalary);

            bw.write(employeeData);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewAttendanceReport(YearMonth month, DefaultTableModel model) {
        System.out.println("Attendance Report for " + month);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader("attendance.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Ensure that all parts are not empty
                if (parts.length >= 3 && !parts[1].trim().isEmpty() && !parts[2].trim().isEmpty()) {
                    try {
                        // Parse the clock-in and clock-out times
                        LocalDateTime clockInTime = LocalDateTime.parse(parts[1].trim(), dateTimeFormatter);
                        LocalDateTime clockOutTime = LocalDateTime.parse(parts[2].trim(), dateTimeFormatter);

                        // Check if the entry belongs to the selected month
                        if (YearMonth.from(clockInTime).equals(month)) {
                            String date = clockInTime.format(dateFormatter);  // Extract Date
                            String clockIn = clockInTime.format(timeFormatter);  // Extract Clock In Time
                            String clockOut = clockOutTime.format(timeFormatter);  // Extract Clock Out Time
                            String late = isLate(clockInTime) ? "Yes" : "No";  // Determine if late

                            // Add row to the table model
                            model.addRow(new Object[]{date, clockIn, clockOut, late});
                        }
                    } catch (DateTimeParseException e) {
                        // Handle parsing errors for this specific line
                        System.err.println("Error parsing date/time in line: " + line);
                        e.printStackTrace();
                    }
                } else {
                    // If parts are missing or empty, skip this line
                    System.err.println("Skipping invalid or incomplete entry: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void viewAnnualAttendanceReport(int year, DefaultTableModel model) {
        System.out.println("Attendance Report for year: " + year);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader("attendance.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 3 && !parts[1].trim().isEmpty() && !parts[2].trim().isEmpty()) {
                    try {
                        LocalDateTime clockInTime = LocalDateTime.parse(parts[1].trim(), dateTimeFormatter);
                        LocalDateTime clockOutTime = LocalDateTime.parse(parts[2].trim(), dateTimeFormatter);

                        if (clockInTime.getYear() == year) {
                            String date = clockInTime.format(dateFormatter);  
                            String clockIn = clockInTime.format(timeFormatter); 
                            String clockOut = clockOutTime.format(timeFormatter);  
                            String late = isLate(clockInTime) ? "Yes" : "No";  

                            model.addRow(new Object[]{date, clockIn, clockOut, late});
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing date/time in line: " + line);
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Skipping invalid or incomplete entry: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   private void saveAttendanceRecord(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("attendance.txt", true))) {
            bw.write(message);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void applyForLeave(Leave.LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
//        Leave leaveApplication = new Leave(this.getUsername(), leaveType, startDate, endDate);
//        System.out.println("Leave application submitted: " + leaveApplication);
//    }
   
   public boolean applyForLeave(Leave.LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
        // Check if leave is available
        if (isLeaveAvailable(leaveType)) {
            Leave leaveApplication = new Leave(this.getUsername(), leaveType, startDate, endDate);
            System.out.println("Leave application submitted: " + leaveApplication);

            // Deduct leave from employee profile
            deductLeaveFromProfile(leaveType);
            return true;
        } else {
            System.out.println("Insufficient leave balance for type: " + leaveType);
        return false;
        }
    }

    private boolean isLeaveAvailable(Leave.LeaveType leaveType) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[1].equals(this.getUsername())) {
                    // Assume leave data is at index 11
                    String leaveData = data[10];
                    String[] leaveEntries = leaveData.split(";");
                    for (String entry : leaveEntries) {
                        String[] parts = entry.split(":");
                        if (parts.length == 2) {
                            String type = parts[0].trim();
                            int days = Integer.parseInt(parts[1].trim().split(" ")[0]);
                            if (type.equals(leaveType.toString())) {
                                System.out.println(days);
                                return days > 0;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void deductLeaveFromProfile(Leave.LeaveType leaveType) {

        // Read all lines from the file
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the relevant lines
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("here");
            String line = lines.get(i);
            String[] data = line.split(",");
            if (data.length > 0 && data[1].equals(this.getUsername())) {
                String leaveData = data[10];
                StringBuilder updatedLeaveData = new StringBuilder();
                String[] leaveEntries = leaveData.split(";");
                for (String entry : leaveEntries) {
                    System.out.println("there");
                    String[] parts = entry.split(":");
                    if (parts.length == 2) {
                        String type = parts[0].trim();
                        int days = Integer.parseInt(parts[1].trim().split(" ")[0]);
                        if (type.equals(leaveType.toString())) {
                            days--;
                        }
                        updatedLeaveData.append(type).append(": ").append(days).append(" days; ");
                    }
                }
                // Remove trailing "; " and update the leave data
                if (updatedLeaveData.length() > 0) {
                    updatedLeaveData.setLength(updatedLeaveData.length() - 2);
                }
                data[10] = updatedLeaveData.toString();
                lines.set(i, String.join(",", data));
            }
        }

        // Write all lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee.txt"))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     public boolean cancelLeaveApplication(String leaveId) {
        boolean isRemoved = false;
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] leaveData = line.split(",");
                String currentLeaveId = leaveData[0].trim();

                // Check if this is the leave application to be canceled
                if (!currentLeaveId.equals(leaveId)) {
                    lines.add(line);  // Add line to list if it does not match the Leave ID
                } else {
                    isRemoved = true;  // Flag that the leave application was found and removed
                    System.out.println("Leave application canceled: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite the file excluding the removed leave application
        if (isRemoved) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("leave.txt"))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show error message if leave ID is not found
            JOptionPane.showMessageDialog(null, "Leave application with ID " + leaveId + " not found or cannot be canceled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return isRemoved; 
     }
    
    public void loadPendingLeavesIntoTable(JTable table, String currentUsername) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows in the table

        try (BufferedReader br = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] leaveData = line.split(",");
                String status = leaveData[5].trim();  // Status is the last field
                String username = leaveData[1].trim(); // Username is the second field

                if (currentUsername.equals(username)) {
                    String leaveId = leaveData[0].trim();     // Leave ID is the first field
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

    
     public void clockIn() {
        if (!isClockedIn) {
            lastClockInTime = LocalDateTime.now();
            isClockedIn = true;
            boolean late = isLate(lastClockInTime);
            String formattedDateTime = lastClockInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String clockInMessage = getUsername() + ", " + formattedDateTime + ", , " + late;
            saveAttendanceRecord(clockInMessage);
            System.out.println(clockInMessage);

            // Apply penalty if needed
//            applyPenaltyIfLate(late);
        } else {
            System.out.println("You are already clocked in.");
        }
    }

    public void clockOut() {
        if (isClockedIn) {
            LocalDateTime clockOutTime = LocalDateTime.now();
            isClockedIn = false;
            String formattedDateTime = clockOutTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String clockOutMessage = getUsername() + ", " + lastClockInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ", " + formattedDateTime + ", false";
            saveAttendanceRecord(clockOutMessage);
            System.out.println(clockOutMessage);
        } else {
            System.out.println("You haven't clocked in yet.");
        }
    }


     private boolean isLate(LocalDateTime clockInTime) {
        // Assuming the official start time is 9:00 AM
        LocalTime officialStartTime = LocalTime.of(9, 0);
        return clockInTime.toLocalTime().isAfter(officialStartTime.plusMinutes(30));
    }

    private void applyPenaltyIfLate(boolean late) {
        if (late) {
            YearMonth currentMonth = YearMonth.now();
            Map<YearMonth, Integer> lateDaysPerMonth = loadLateDays();

            int lateDaysThisMonth = lateDaysPerMonth.getOrDefault(currentMonth, 0) + 1;
            lateDaysPerMonth.put(currentMonth, lateDaysThisMonth);

            // Save the updated late days count
            saveLateDays(lateDaysPerMonth);

            // Check if penalty needs to be applied
            if (lateDaysThisMonth >= 3) {
                System.out.println("Penalty applied for " + currentMonth + ": RM100");
                this.grossSalary -= PENALTY_AMOUNT;
            }
        }
    }
    
     private Map<YearMonth, Integer> loadLateDays() {
        Map<YearMonth, Integer> lateDaysPerMonth = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("attendance.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(this.getUsername()) && Boolean.parseBoolean(parts[3].trim())) { // If this record is for this user and is marked late
                    LocalDateTime clockInTime = LocalDateTime.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    YearMonth ym = YearMonth.from(clockInTime);
                    lateDaysPerMonth.put(ym, lateDaysPerMonth.getOrDefault(ym, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lateDaysPerMonth;
    }

    private void saveLateDays(Map<YearMonth, Integer> lateDaysPerMonth) {
        // This implementation does not modify the attendance file but updates the in-memory map
    }


    private double calculateNetSalary() {
        double epfContribution = this.grossSalary * 0.11;
        double socsoContribution = this.grossSalary * 0.01;
        double eisContribution = this.grossSalary * 0.003;
        double annualTax = this.grossSalary * 0.03;
        double pcb = annualTax / 12;
        return this.grossSalary - (epfContribution + socsoContribution + eisContribution + pcb);
    }
    
      private static int loadNextId() {
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("employee.txt"))) {
            while (br.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount + 1;
    }
      
    
    public static boolean exists(int employeeId) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split(",");
                if (employeeDetails.length > 0 && Integer.parseInt(employeeDetails[0].trim()) == employeeId) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }
}
