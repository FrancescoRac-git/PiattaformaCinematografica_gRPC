package videoteca.frontend.gui.model;

import videoteca.backend.gRPC.Film;

import javax.swing.*;
import java.awt.*;

public class EditFilmDialog extends JDialog {
    private JTextField txtTitolo,txtRegista,txtGenere,txtValutazione,txtAnnoDiUscita,txtStatoVisione;
    private int iDFilmOriginale;

    public EditFilmDialog(JFrame parent, FinestraMediator mediator, Film filmattuale){
        super(parent,"Modifica Film",true);
        this.iDFilmOriginale = filmattuale.getId();
        txtTitolo = new JTextField(20);
        txtRegista = new JTextField(20);
        txtGenere = new JTextField(20);
        txtValutazione= new JTextField(20);
        txtAnnoDiUscita= new JTextField(20);
        txtStatoVisione= new JTextField(20);

        txtTitolo.setText(filmattuale.getTitolo());
        txtRegista.setText(filmattuale.getRegista());
        txtAnnoDiUscita.setText(String.valueOf(filmattuale.getAnnoUscita()));
        txtGenere.setText(filmattuale.getGenere());
        txtValutazione.setText(String.valueOf(filmattuale.getValutazione()));
        txtStatoVisione.setText(filmattuale.getStatoVisione());

        this.setLayout(new GridLayout(3,2));
        this.add(new JLabel("Titolo: "));
        this.add(txtTitolo);

        this.add(new JLabel("Regista "));
        this.add(txtRegista);

        this.add(new JLabel("Genere: "));
        this.add(txtGenere);

        this.add(new JLabel("Valutazione: "));
        this.add(txtValutazione);

        this.add(new JLabel("Anno Di Uscita: "));
        this.add(txtAnnoDiUscita);

        this.add(new JLabel("StatoVisione: "));
        this.add(txtStatoVisione);




        JButton btnConferma = new JButton("Conferma Modifiche");
        btnConferma.addActionListener(e ->{
            Film filmAggiornato = Film.newBuilder()
                    .setId(iDFilmOriginale)
                    .setTitolo(txtTitolo.getText())
                    .setRegista(txtRegista.getText())
                    .setGenere(txtGenere.getText())
                    .setValutazione(Integer.parseInt(txtValutazione.getText()))
                    .setStatoVisione(txtStatoVisione.getText())
                    .setAnnoUscita(Integer.parseInt(txtAnnoDiUscita.getText()))
                    .build();
            mediator.editFilm(filmAggiornato);

            dispose();

        });
        this.add(btnConferma);
        this.pack();
        this.setLocationRelativeTo(parent);


    }

}
