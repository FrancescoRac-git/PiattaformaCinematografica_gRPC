package videoteca.frontend.gui.model.button;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JButton bottonePersonalizzato;
    private final JTable table;
    private int rigaSelezionata;

    public TableButtonEditor(JButton bottonePersonalizzato, JTable table, Runnable azioneMediator){
        this.bottonePersonalizzato = bottonePersonalizzato;
        this.table = table;

        this.bottonePersonalizzato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                azioneMediator.run();            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.rigaSelezionata = row;
        return bottonePersonalizzato;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
