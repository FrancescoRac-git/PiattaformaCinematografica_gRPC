package videoteca.frontend.gui.model;

import org.springframework.core.annotation.MergedAnnotations;
import videoteca.frontend.gui.model.button.AddButton;
import videoteca.frontend.gui.model.button.SearchButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchBar extends JPanel {

    private JTextField searchBar;
    private SearchButton searchButton;
    private  String PLACEHOLDER = "Cerca per Titolo o Regista";
    private AddButton addButton;
    private JPanel pannelloBottoni;

    public SearchBar(FinestraMediator mediator){
        pannelloBottoni = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pannelloBottoni.setOpaque( false);



        this.addButton  = new AddButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediator.apriFinestraAdd();
            }
        });

        this.searchBar = new JTextField();
        this.searchButton = new SearchButton();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediator.eseguiricerca(searchBar.getText());
                searchBar.setText("");
            }
        });

        pannelloBottoni.add(searchButton);
        pannelloBottoni.add(addButton);

        this.setLayout(new BorderLayout(5,0));
        setUpPlaceHolder();

        add(searchBar,BorderLayout.CENTER);
        add(pannelloBottoni,BorderLayout.EAST);


    }

    private void setUpPlaceHolder(){
        searchBar.setText(PLACEHOLDER);
        searchBar.setForeground(Color.gray);


        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals(PLACEHOLDER)){
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(searchBar.getText().trim().isEmpty()){
                    searchBar.setText(PLACEHOLDER);
                    searchBar.setForeground(Color.gray);
                }
            }
        });

    }









}
