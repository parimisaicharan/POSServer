/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.serverconsole;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author enteg
 */
public class StagingStatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value!=null){
        String val = value.toString();
        if (val.equals("Completed")) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ISRetail/images/small/accept.png"));
            setText(val);
            setIcon(icon);
        }else if (val.equals("Waiting")) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ISRetail/images/small/next1.gif"));
            setText(val);
            setIcon(icon);
        }else if (val.equals("Cancelled")) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ISRetail/images/small/deleteSmall.png"));
            setText(val);
            setIcon(icon);
        }else if (val.equals("Not Completed")) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ISRetail/images/small/next1.gif"));
            setText(val);
            setIcon(icon);
        }
        
        }
        return cell;

    }
}
