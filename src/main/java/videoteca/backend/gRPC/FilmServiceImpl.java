package videoteca.backend.gRPC;

import io.grpc.stub.StreamObserver;
import videoteca.backend.database.FilmDAO;
import videoteca.backend.database.FilmDAODatabase;
import videoteca.backend.model.Genere;
import videoteca.backend.model.StatoVisione;


public class FilmServiceImpl extends FilmServiceGrpc.FilmServiceImplBase {

    private final FilmDAO filmDAO;

    public FilmServiceImpl(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }


    @Override
    public void addFilm(AddFilmRequest request, StreamObserver<GestioneFilmResponse> responseObserver) {


        videoteca.backend.gRPC.Film filmGrpc = request.getFilm();
        GestioneFilmResponse response;

        try {
            videoteca.backend.model.Film mioFilmDaSalvare = new videoteca.backend.model.Film.Builder(filmGrpc.getTitolo(), filmGrpc.getRegista())
                    .id(filmGrpc.getId())
                    .annoUscita(filmGrpc.getAnnoUscita())
                    .genere(videoteca.backend.model.Genere.valueOf(filmGrpc.getGenere().toUpperCase()))
                    .valutazione(filmGrpc.getValutazione())
                    .statoVisione(videoteca.backend.model.StatoVisione.valueOf(filmGrpc.getStatoVisione().toUpperCase()))
                    .build();
            videoteca.backend.model.Film filmSalvato = filmDAO.aggiungi(mioFilmDaSalvare);



            System.out.println("Server: Film '" + filmSalvato.getTitolo() + "' salvato nel DB!");

            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Film salvato con successo! ID assegnato: " + filmSalvato.getId())
                    .build();

        } catch (Exception e) {
            System.err.println("Server: Errore durante il salvataggio: " + e.getMessage());
            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Errore DB: " + e.getMessage())
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}