package videoteca.frontend.gui.model;

import videoteca.backend.gRPC.Film;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFilmDialog extends JDialog {
    private JTextField txtTitolo,txtRegista,txtGenere,txtValutazione,txtAnnoDiUscita,txtStatoVisione;


    public AddFilmDialog(JFrame parent, FinestraMediator mediator){
        txtTitolo = new JTextField(20);
        txtRegista = new JTextField(20);
        txtGenere = new JTextField(20);
        txtValutazione= new JTextField(20);
        txtAnnoDiUscita= new JTextField(20);
        txtStatoVisione= new JTextField(20);

        this.setLayout(new GridLayout(7, 2));
        this.add(new JLabel("Titolo:"));
        this.add(txtTitolo);
        this.add(new JLabel("Regista:")); this.add(txtRegista);
        this.add(new JLabel("Anno:")); this.add(txtAnnoDiUscita);
        this.add(new JLabel("Genere:")); this.add(txtGenere);
        this.add(new JLabel("Valutazione (1-5):")); this.add(txtValutazione);
        this.add(new JLabel("Stato (Visto/Da vedere):")); this.add(txtStatoVisione);

        JButton btnSalva = new JButton("Salva nel Database");
        btnSalva.addActionListener(e-> {
            try {

                    String titolo = txtTitolo.getText();
                    String regista = txtRegista.getText();
                    int anno = Integer.parseInt(txtAnnoDiUscita.getText());
                    String genere = txtGenere.getText();
                    int valutazione = Integer.parseInt(txtValutazione.getText());
                    String stato = txtStatoVisione.getText();

                    Film f = Film.newBuilder()
                            .setTitolo(titolo)
                            .setRegista(regista)
                            .setAnnoUscita(anno)
                            .setGenere(genere)
                            .setValutazione(valutazione)
                            .setStatoVisione(stato)
                            .build();

                    mediator.addFilm(f);
                    dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Controlla che Anno e Valutazione siano numeri validi!");
            }

        });
        this.add(btnSalva);
        this.pack();
        this.setLocationRelativeTo(parent);
    }

}
