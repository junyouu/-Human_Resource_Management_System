/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ldcle
 */
public class LeaveManager {
    private static final Map<String, Integer> leaveEntitlements = new HashMap<>();
    static {
        leaveEntitlements.put("ANNUAL", 14);
        leaveEntitlements.put("MEDICAL", 14);
        leaveEntitlements.put("UNPAID", 0);
        leaveEntitlements.put("MATERNITY", 60);
    }

    // Method to get the remaining leave days for a specific username
    public static List<LeaveEntitlement> getFormattedLeaveData(String username) {
        // Initialize used days map
        Map<String, Integer> usedDays = new HashMap<>();
        for (String leaveType : leaveEntitlements.keySet()) {
            usedDays.put(leaveType, 0);
        }

        // Read leave data and calculate used days
        try (BufferedReader reader = new BufferedReader(new FileReader("leave.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 2 && data[1].equals(username) && !"PENDING".equals(data[5])) {
                    String leaveType = data[2];
                    // Calculate the number of days for each leave record
                    // Here, you might need a more complex calculation depending on your leave data format
                    // For simplicity, let's assume each record represents 1 day of leave
                    usedDays.put(leaveType, usedDays.getOrDefault(leaveType, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build the formatted leave data list
        List<LeaveEntitlement> formattedLeaveDataList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : leaveEntitlements.entrySet()) {
            String leaveType = entry.getKey();
            int totalDays = entry.getValue();
            int used = usedDays.getOrDefault(leaveType, 0);
            int remaining = totalDays - used;
            formattedLeaveDataList.add(new LeaveEntitlement(leaveType, remaining));
        }

        return formattedLeaveDataList;
    }
}
