package videoteca.frontend.gui.view;

import videoteca.backend.gRPC.Film;
import videoteca.backend.gRPC.SearchFilmsRequest;
import videoteca.frontend.client.FilmClient;
import videoteca.frontend.gui.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame implements FinestraMediator  {

    private CatalogoFilmSubject subject;
    private FilmClient filmClient;

    private SearchBar searchBar;

    private TableFilm tableFilm;


    public MainFrame(CatalogoFilmSubject subject,FilmClient filmClient){
        this.filmClient = filmClient;
        this.subject = subject;



        this.searchBar = new SearchBar(this);
        this.add(searchBar,BorderLayout.NORTH);

        this.tableFilm = new TableFilm(subject,this);

        this.add(tableFilm,BorderLayout.CENTER);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }


    @Override
    public void eseguiricerca(String filmDaCercare) {
        List<Film> films = filmClient.cercaFilm(SearchFilmsRequest.newBuilder()
                .setQueryGenerica(filmDaCercare).
                build());
        subject.setFilms(films);

    }

    @Override
    public void deleteFilm(int id) {
        filmClient.eliminaFilm(id);

        List<Film> listaAggiornata = filmClient.getAll();

        subject.setFilms(listaAggiornata);
    }

    @Override
    public void editFilm(Film f) {
        filmClient.modificaFilm(f.getId(),f.getTitolo(),f.getRegista(),f.getAnnoUscita(),f.getGenere(),f.getValutazione(),f.getStatoVisione());
        List<Film> listaAggiornata = filmClient.getAll();


        subject.setFilms(listaAggiornata);
    }

    @Override
    public void addFilm(Film f) {
        filmClient.aggiungiNuovoFilm(f.getTitolo(),f.getRegista(),f.getAnnoUscita(),f.getGenere(),f.getValutazione(),f.getStatoVisione());
        List<Film> listaAggiornata = filmClient.getAll();


        subject.setFilms(listaAggiornata);
    }



    @Override
    public void apriFinestra(Film f) {
        System.out.println("Sto aprndo il Dialog per il film con ID: "+ f.getId());
        EditFilmDialog filmDialog = new EditFilmDialog(this,this,f);

        filmDialog.setVisible(true);

    }
    @Override
    public void apriFinestraAdd() {
        System.out.println("Sto aprendo il Dialog per aggiungere un nuovo film");
        AddFilmDialog dialog = new AddFilmDialog(this, this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        CatalogoFilmSubject subject = new CatalogoFilmSubject();
        FilmClient client = new FilmClient("localhost",50051);
        MainFrame frame= new MainFrame(subject,client);


        subject.setFilms(client.getAll());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);



    }
}
