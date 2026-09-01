package videoteca.frontend.gui.model.button;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AddButton extends JButton {

    public AddButton(){
        URL imageURL = getClass().getResource("/images/add.png");
        if (imageURL != null){
            ImageIcon icon = new ImageIcon(imageURL);
            Image iconEdited = icon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);

            ImageIcon finalIcon = new ImageIcon(iconEdited);
            this.setIcon(finalIcon);
        }
    }


}
