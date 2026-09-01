package videoteca.frontend.gui.model.button;

import videoteca.frontend.gui.model.FinestraMediator;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class DeleteButton extends JButton {



    public DeleteButton(){
        URL imageURL = getClass().getResource("/images/delete.png");
        if (imageURL != null){
            ImageIcon icon = new ImageIcon(imageURL);
            Image iconEdited = icon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);

            ImageIcon finalIcon = new ImageIcon(iconEdited);
            this.setIcon(finalIcon);
        }


    }



}
