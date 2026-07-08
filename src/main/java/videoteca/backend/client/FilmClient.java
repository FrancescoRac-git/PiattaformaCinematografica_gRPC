package videoteca.backend.client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import videoteca.backend.gRPC.AddFilmRequest;
import videoteca.backend.gRPC.Film;
import videoteca.backend.gRPC.FilmServiceGrpc;
import videoteca.backend.gRPC.GestioneFilmResponse;

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
    public void aggiungiNuovoFilm(int id, String titolo, String regista, int annoUscita, String genere,int valutazione, String statovisione) {
        System.out.println("Client: Preparazione dell'invio del film '" + titolo + "'...");


        Film nuovoFilm = Film.newBuilder()
                .setId(id)
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


}
