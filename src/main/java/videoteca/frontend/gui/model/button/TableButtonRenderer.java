package videoteca.frontend.gui.model.button;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableButtonRenderer implements TableCellRenderer {
    private final JButton bottonePersonalizzato;

    public TableButtonRenderer(JButton button){
        this.bottonePersonalizzato = button;

    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       return bottonePersonalizzato;
    }
}
