package videoteca.frontend.gui.model.button;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.net.URL;
import java.util.EventObject;

public class EditButton extends JButton implements TableCellRenderer, TableCellEditor {
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return null;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return false;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    @Override
    public boolean stopCellEditing() {
        return false;
    }

    @Override
    public void cancelCellEditing() {

    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return null;
    }

    public EditButton (){

        URL imageURL = getClass().getResource("/images/edit.png");
        if (imageURL != null){
            ImageIcon icon = new ImageIcon(imageURL);
            Image iconEdited = icon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);

            ImageIcon finalIcon = new ImageIcon(iconEdited);
            this.setIcon(finalIcon);
        }
    }
}
