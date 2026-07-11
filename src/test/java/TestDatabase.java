import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import videoteca.backend.database.FilmDAODatabase;
import videoteca.backend.model.Film;
import videoteca.backend.model.Genere;
import videoteca.backend.model.StatoVisione;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDatabase {
    String url = "jdbc:postgresql://localhost:5432/videoteca_db";
    String user =     "postgres";
    String password = "postgresql";

    private Connection connection;
    private FilmDAODatabase filmDAODatabase;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        connection =DriverManager.getConnection(url,user,password);
        filmDAODatabase = FilmDAODatabase.getInstance(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if(connection!=null){
            connection.close();
        }
    }
    @Test
    public void verificaSalvataggioTest(){
        Film provaSalvataggio = new Film.Builder("prova salvataggio", "Test")
                .valutazione(2)
                        .genere(Genere.FANTASY)
                                .annoUscita(2026)
                                        .statoVisione(StatoVisione.DA_VEDERE)
                                                .build();


        filmDAODatabase.aggiungi(provaSalvataggio);
        Film filmTrovato = filmDAODatabase.cercaPerTitolo(provaSalvataggio.getTitolo());



        Assertions.assertNotNull(filmTrovato,"il film trovato non può essere nullo");
        Assertions.assertEquals(provaSalvataggio.getTitolo(),filmTrovato.getTitolo(),"i titoli corrispondono");
        Assertions.assertEquals(provaSalvataggio.getRegista(),filmTrovato.getRegista(),"i registi corrispondono");

    }

    @Test
    public void verificaRicercaTest(){
        Film film = new Film.Builder("il signore degli anelli","Francesco")
                .genere(Genere.FANTASY)
                .valutazione(4)
                .annoUscita(2026)
                .statoVisione(StatoVisione.DA_VEDERE)
                .build();

        Film film2 = new Film.Builder("che bella giornata","Francesco")
                .genere(Genere.AZIONE)
                        .annoUscita(2025)
                                .statoVisione(StatoVisione.IN_VISIONE)
                                        .valutazione(5)
                                                .build();

        filmDAODatabase.aggiungi(film);
        filmDAODatabase.aggiungi(film2);
        List<Film> films = filmDAODatabase.cercaPerRegista("Francesco");

        Assertions.assertEquals(2,films.size()," i film con regista : Francesco  devono essere 2");
    }


    @Test
    public void eliminafilmTest(){
        List<Film> films = filmDAODatabase.tuttiIfilm();
        for(Film film:films){
            filmDAODatabase.eliminaFilm(film.getId());
        }

        Assertions.assertEquals(0,filmDAODatabase.tuttiIfilm().size(),"nessun film presente nel database");
    }

    @Test
    public void modificaFilmTest(){
        Film filmOriginale = new Film.Builder("Il Gladiatore", "Ridley Scott")
                .valutazione(4)
                .genere(Genere.AZIONE)
                .annoUscita(2000)
                .statoVisione(StatoVisione.DA_VEDERE)
                .build();
        filmDAODatabase.aggiungi(filmOriginale);

        Film filmInserito = filmDAODatabase.cercaPerTitolo(filmOriginale.getTitolo());
        Assertions.assertNotNull(filmInserito,"il film originale deve essere stato salvato");

        Film filmModificato = new Film.Builder("Il Gladiatore ", "Ridley Scott")
                .id(filmInserito.getId())
                .valutazione(5) //modificata 4 -> 5
                .genere(Genere.AZIONE)
                .annoUscita(2000)
                .statoVisione(StatoVisione.VISTO)// modificata DA_VEDERE -> VISTO
                .build();

        filmDAODatabase.updateFilm(filmModificato);

        Film filmAggiornato = filmDAODatabase.cercaPerTitolo(filmModificato.getTitolo());

        Assertions.assertNotNull(filmAggiornato, " il film deve essere stato salvato");
        Assertions.assertEquals(5,filmAggiornato.getValutazionePersonale(), "la valutazione deve essere aggiornata a 5");
        Assertions.assertEquals("VISTO",filmAggiornato.getStatoVisione().toString(), "lo stato di visione deve essere aggiornato a : VISTO");






    }
}
