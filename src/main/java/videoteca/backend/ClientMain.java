package videoteca.backend;

import videoteca.backend.client.FilmClient;

public class ClientMain {
    public static void main(String[] args) {

        FilmClient client = new FilmClient("localhost", 50051);

        try {
            client.aggiungiNuovoFilm("1", "Il Signore degli Anelli", "Peter Jackson", 2001, "Fantasy");


            Thread.sleep(2000);

            client.aggiungiNuovoFilm("2", "Matrix", "Lana e Lilly Wachowski", 1999, "Fantascienza");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            System.out.println("Client: Chiusura della connessione.");
            client.shutdown();
        }
    }
}
