
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

import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.TabExpander;


public class EditableCellEditor implements TableCellEditor{

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getCellEditorValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCellEditable(EventObject anEvent) {
       // throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
      //  throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    public boolean stopCellEditing() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return false;
    }

    public void cancelCellEditing() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addCellEditorListener(CellEditorListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeCellEditorListener(CellEditorListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
