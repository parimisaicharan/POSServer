/*
 * Copyright Titan Industries Limited  All Rights Reserved. * 
 * This code is written by Enteg Info tech Private Limited for the Titan Eye+ Project * 
 *
 * 
 * 
 * VERSION
 * Initial Version
 * Date of Release
 * 
 * 
 * Change History
 * Version <vvv>
 * Date of Release <dd/mm/yyyy>
 * To be filled by the code Developer in Future
 * 
 * 
 * Utility Class for the JTable 
 * 
 * 
 */
package ISRetail.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class CustomCellRenderer extends DefaultTableCellRenderer {

    /**         * The current row being rendered         */
    protected int row;
    /**         * The current column being rendered         */
    protected int col;
    /**         * If this cell is part of the "selected" row         */
    protected boolean isSelected;
    /**         * If this cell is in focus (the one clicked upon)         */
    protected boolean isFocused;
    protected Component comp;

    /**         * Fetch the component which renders the cell ordinarily         * @param JTable The current JTable the cell is in         * @param Object The value in the cell         * @param boolean If the cell is in the selected row         * @param boolean If the cell is in focus         * @param int The row number of the cell         * @param int The column number of the cell         * @return Component         */
    @Override
    public Component getTableCellRendererComponent(JTable tbl, Object v, boolean isSelected, boolean isFocused, int row, int col) {
        //Store this info for later use               
        Component cell = super.getTableCellRendererComponent(tbl, v, isSelected, isFocused, row, col);
        this.row = row;
        this.col = col;
        this.isSelected = isSelected;
        this.isFocused = isFocused;
        setEnabled(tbl == null || tbl.isEnabled());
        if (this.isFocused) {
            this.setBackground(new Color(255, 255, 150));

        } else if (!this.isSelected) {
            if ((row % 2) == 0 || isSelected) {
                setBackground(Color.white);
            } else 
            {
                setBackground(new Color(237,247,252));
            }
        }
        
        return cell;
    //and then allow the usual component to be returned 
    // return super.getTableCellRendererComponent(tbl, v, isSelected, isFocused, row, col);
    }

    /**         * Set the contents of the cell to v         * @param Object The value to apply to the cell         */
    @Override
    protected void setValue(Object v) {
        super.setValue(v); //Set the value as requested    
        //Set colors dependant upon if the row is selected or not     
        //if (!this.isSelected) {
        //     this.setBackground(Color.white);
        // } else {
        //    this.setBackground(Color.LIGHT_GRAY);
        //}
        //Set a special highlight color if this actual cell is focused             
//        if (this.isFocused) {
//            this.setBackground(new Color(255, 255, 150));
//
//        } else if (!this.isSelected) {
//            this.setBackground(new Color(255, 255, 255));
//        }
    }
}


