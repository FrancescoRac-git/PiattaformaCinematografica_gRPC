package videoteca.backend;

import videoteca.backend.client.FilmClient;
import videoteca.backend.database.FilmDAO;
import videoteca.backend.database.FilmDAODatabase;

public class ClientMain {
    public static void main(String[] args) {

        FilmClient client = new FilmClient("localhost", 50051);

        try {
            client.aggiungiNuovoFilm(1, "Il Signore degli Anelli", "Peter Jackson", 2001, "Fantasy",2,"da_vedere");


            Thread.sleep(2000);

            client.aggiungiNuovoFilm(2, "Matrix", "Lana e Lilly Wachowski", 1999, "Fantascienza",5,"visto");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            System.out.println("Client: Chiusura della connessione.");
            client.shutdown();
        }
    }
}
