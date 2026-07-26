package videoteca.frontend.client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import videoteca.backend.gRPC.*;

import java.util.List;

public class FilmClient {

    private final ManagedChannel channel;

    private final FilmServiceGrpc.FilmServiceBlockingStub stub;

    public FilmClient(String host,int port){
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = FilmServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() {
        channel.shutdown();
    }


    public void aggiungiNuovoFilm(String titolo, String regista, int annoUscita, String genere,int valutazione, String statovisione) {
        System.out.println("Client: Preparazione dell'invio del film '" + titolo + "'...");


        Film nuovoFilm = Film.newBuilder()
                .setTitolo(titolo)
                .setRegista(regista)
                .setAnnoUscita(annoUscita)
                .setGenere(genere)
                .setValutazione(valutazione)
                .setStatoVisione(statovisione)
                .build();


        AddFilmRequest request = AddFilmRequest.newBuilder()
                .setFilm(nuovoFilm)
                .build();

        try {

            GestioneFilmResponse response = stub.addFilm(request);
            if (response.getSuccess()) {
                System.out.println("Client: SUCCESSO! Il server ha risposto: " + response.getMessage());
            } else {
                System.out.println("Client: ERRORE! Il server ha risposto: " + response.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Client: Errore di comunicazione col server: " + e.getMessage());
        }
    }

    public void modificaFilm(int id, String titolo, String regista, int annoUscita, String genere, int valutazione, String statovisione) {
        System.out.println("Client: Preparazione per la modifica del film ID '" + id + "'...");


        Film filmModificato = Film.newBuilder()
                .setId(id)
                .setTitolo(titolo)
                .setRegista(regista)
                .setAnnoUscita(annoUscita)
                .setGenere(genere)
                .setValutazione(valutazione)
                .setStatoVisione(statovisione)
                .build();

        UpdateFilmRequest request = UpdateFilmRequest.newBuilder()
                .setFilm(filmModificato)
                .build();

        try {

            GestioneFilmResponse response = stub.updateFilm(request);

            if (response.getSuccess()) {
                System.out.println("Client: SUCCESSO! " + response.getMessage());
            } else {
                System.out.println("Client: ERRORE! " + response.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Client: Errore di comunicazione col server: " + e.getMessage());
        }
    }

    public void eliminaFilm(int id) {
        System.out.println("Client: Richiesta eliminazione film ID '" + id + "'...");

        DeleteFilmRequest request = DeleteFilmRequest.newBuilder()
                .setId(id)
                .build();

        try {
            GestioneFilmResponse response = stub.deleteFilm(request);

            if (response.getSuccess()) {
                System.out.println("Client: SUCCESSO! " + response.getMessage());
            } else {
                System.out.println("Client: ERRORE! " + response.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Client: Errore di comunicazione col server: " + e.getMessage());
        }
    }

    public java.util.List<Film> cercaFilm(SearchFilmsRequest request) {
        System.out.println("Client: Avvio ricerca film...");

        try {

            SearchFilmsResponse response = stub.searchFilms(request);

            System.out.println("Client: Ricerca completata. Trovati " + response.getFilmsCount() + " film.");


            return response.getFilmsList();

        } catch (Exception e) {
            System.err.println("Client: Errore durante la ricerca: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public List<Film> getAll(){

        try{
            GetAllFilmsRequest request = GetAllFilmsRequest.newBuilder().build();
            SearchFilmsResponse response = stub.getAll(request);
            System.out.println("Client: Ricerca completata. Trovati " + response.getFilmsCount() + " film.");

            return  response.getFilmsList();

        } catch (Exception e) {
            System.err.println("Client: Errore durante la ricerca: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }



}
