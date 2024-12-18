package java_assignment;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeAttendance {
    private String username;
    private String clockInTime;
    private String clockOutTime;
    private boolean isLate;

    public TimeAttendance(String username) {
        this.username = username;
        this.clockInTime = "";
        this.clockOutTime = "";
        this.isLate = false;
    }

    public void clockIn() {
        if (!clockInTime.isEmpty()) {
            System.out.println("You have already clocked in today.");
            return;
        }

        this.clockInTime = getCurrentTime();
        this.isLate = checkIfLate(clockInTime);
        saveAttendance();
        System.out.println("Clocked in at " + clockInTime);
        if (isLate) {
            System.out.println("You are late today.");
        }
    }

    public void clockOut() {
        if (clockInTime.isEmpty()) {
            System.out.println("You need to clock in first.");
            return;
        }
        if (!clockOutTime.isEmpty()) {
            System.out.println("You have already clocked out today.");
            return;
        }

        this.clockOutTime = getCurrentTime();
        saveAttendance();
        System.out.println("Clocked out at " + clockOutTime);
    }

    private boolean checkIfLate(String clockInTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date clockInDate = sdf.parse(clockInTime);
            Date lateThreshold = sdf.parse("09:30:00");
            return clockInDate.after(lateThreshold);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private void saveAttendance() {
        String date = getCurrentDate();
        String record = username + "," + date + "," + clockInTime + "," + clockOutTime + "," + isLate;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("attendance.txt", true))) {
            bw.write(record);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateAttendanceReport(String username) {
        List<String[]> attendanceRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("attendance.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(username)) {
                    attendanceRecords.add(details);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Attendance Report for " + username + ":");
        System.out.println("Date\t\tClock In\tClock Out\tLate");
        for (String[] record : attendanceRecords) {
            System.out.println(record[1] + "\t" + record[2] + "\t" + record[3] + "\t" + record[4]);
        }
    }
}
