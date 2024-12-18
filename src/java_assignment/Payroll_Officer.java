/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ldcle
 */
public class Payroll_Officer extends User{
    
    Payroll_Officer(){
        super();
    }
    
    Payroll_Officer(String username, String password, String role) {
        super(username, password, role);
    }
    
    public boolean createPayrollTransaction(int employeeId, String month, String year){
        if (!Employee.exists(employeeId)) {
            JOptionPane.showMessageDialog(null, "Employee ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        PayrollTransaction transaction = new PayrollTransaction(employeeId, month, year);
        boolean isCreated = false;
        String line = transaction.toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("payroll_transaction.txt", true))) {
            bw.write(line);
            bw.newLine();
            isCreated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isCreated;
    }
    
    public boolean generatePayslip(int employeeId, String month, String year) {
        boolean exists = false;
        

       try (BufferedReader br = new BufferedReader(new FileReader("payroll_transaction.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] payslipData = line.split(",");


                String existingEmployeeId = payslipData[1].trim();
                String existingMonth = payslipData[2].trim();
                String existingYear = payslipData[3].trim(); 
                
                int existingId = Integer.parseInt(existingEmployeeId.replace("\"", "").trim());
                
                if (existingId == employeeId && existingMonth.equalsIgnoreCase(month) && existingYear.equals(year)) {
                    exists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(exists) {
            return true;
        }else{
            return false;
        }

    }
    
    public void loadUserDataIntoCreateTransactionTable(JTable table){
        List<String[]> userData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                String[] relevantDetails = new String[] {
                    userDetails[0].trim(),
                    userDetails[1].trim(),
                    userDetails[11].trim(),
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
    
    public double[] calculateEmployerContribution() {
        double[] sums = new double[3]; 

        try (BufferedReader reader = new BufferedReader(new FileReader("payroll_transaction.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                int[] indices = {9, 10, 11};

                for (int i = 0; i < indices.length; i++) {
                    int index = indices[i];
                    if (index < values.length) {
                        try {
                            sums[i] += Double.parseDouble(values[index].replace("\"", "").trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number format in column index " + (index + 1));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return sums;
       
    }    
    
}   
