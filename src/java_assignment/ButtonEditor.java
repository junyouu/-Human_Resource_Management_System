/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author ldcle
 */
public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable userAccountTable, Runnable refreshTable) {
        super(checkBox);
        this.table = userAccountTable;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); 
                int row = table.getSelectedRow();
                String username = table.getValueAt(row, 0).toString();
                User loggedInUser = Session.getInstance().getLoggedInUser();
                System_Administrator admin = (System_Administrator) loggedInUser;
                boolean isUnlocked = admin.unlockUserAccount(username);
                if(isUnlocked){
                    JOptionPane.showMessageDialog(table, "User account unlocked successfully.");
                    refreshTable.run();
                }else{
                    JOptionPane.showMessageDialog(table, "Failed to unlock user account.");
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = value != null ? value.toString() : "";
        button.setText(label);
        isPushed = true;
        return button;
    }

//    @Override
//    public Object getCellEditorValue() {
//        if (isPushed) {
//            JOptionPane.showMessageDialog(button, "Unlock action for row " + button.getText());
//        }
//        isPushed = false;
//        return label;
//    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
