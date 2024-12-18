/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ldcle
 */
public class PayrollTransaction {
    private int employeeId;
    private String name;
    private String month;
    private String year;
    private double grossSalary;
    private double employeeEPF;
    private double employeeSOSCO;
    private double employeeEIS;
    private double employeeAnnualTax;
    private double employerEPF;
    private double employerSOSCO;
    private double employerEIS;   
    private double netSalary;
    private Date transactionDate;
    
    PayrollTransaction(int employeeId, String month, String year){
        Employee employee = Employee.getEmployeeById(employeeId);
        System.out.print("Gross Salary retrieved: " + employee.getGrossSalary());
        this.employeeId = employeeId;
        this.name = employee.getUsername();
        this.grossSalary = employee.getGrossSalary();
        this.employeeEPF = grossSalary * 0.11;
        this.employeeSOSCO = grossSalary * 0.005;
        this.employeeEIS = grossSalary * 0.002;
        this.employeeAnnualTax = grossSalary * 0.05;
        this.employerEPF = grossSalary * 0.13;
        this.employerSOSCO = grossSalary * 0.018;
        this.employerEIS = grossSalary * 0.002;
        this.netSalary = grossSalary - this.employeeEPF - this.employeeSOSCO - this.employeeEIS - (this.employeeAnnualTax / 12);
        this.year = year;
        this.month = month;
        this.transactionDate = new Date();
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.join(",",
                name, 
                "\"" + employeeId + "\"",
                month,   
                year,
                String.format("%.2f", grossSalary),
                String.format("%.2f", employeeEPF),
                String.format("%.2f", employeeSOSCO),
                String.format("%.2f", employeeEIS),
                String.format("%.2f", employeeAnnualTax),
                String.format("%.2f", employerEPF),
                String.format("%.2f", employerSOSCO),
                String.format("%.2f", employerEIS),
                String.format("%.2f", netSalary),
                "\"" + sdf.format(transactionDate) + "\""
        );
    }
    
    public String[] getPayslipArray() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new String[] {
                name, 
                "\"" + employeeId + "\"",
                month,   
                year,
                String.format("%.2f", grossSalary),             
                String.format("%.2f", employeeEPF),          
                String.format("%.2f", employeeSOSCO),         
                String.format("%.2f", employeeEIS),             
                String.format("%.2f", employeeAnnualTax),       
                String.format("%.2f", employerEPF),            
                String.format("%.2f", employerSOSCO),           
                String.format("%.2f", employerEIS),            
                String.format("%.2f", netSalary),               
                "\"" + sdf.format(transactionDate) + "\""       
        };
    }
    
    
}
