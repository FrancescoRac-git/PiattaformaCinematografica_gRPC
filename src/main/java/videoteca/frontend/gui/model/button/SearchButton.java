package videoteca.frontend.gui.model.button;

import videoteca.frontend.gui.model.FinestraMediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class SearchButton extends JButton {
    private FinestraMediator mediator;

    public SearchButton(){

        URL imageURL = getClass().getResource("/images/search.png");

        if (imageURL != null) {

            ImageIcon icon = new ImageIcon(imageURL);
            Image scalata = icon.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
            ImageIcon iconaFinale = new ImageIcon(scalata);

            this.setIcon(iconaFinale);
        }
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(40,40));


    }


}


