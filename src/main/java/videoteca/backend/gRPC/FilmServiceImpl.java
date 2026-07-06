package videoteca.backend.gRPC;

import io.grpc.stub.StreamObserver;


public class FilmServiceImpl extends FilmServiceGrpc.FilmServiceImplBase {

    @Override
    public void addFilm(AddFilmRequest request, StreamObserver<GestioneFilmResponse> responseObserver) {
        

        Film filmRicevuto = request.getFilm();
        System.out.println("Il Server ha ricevuto una richiesta di inserimento per il film: " + filmRicevuto.getTitolo());


        GestioneFilmResponse response = GestioneFilmResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Film '" + filmRicevuto.getTitolo() + "' aggiunto con successo!")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    

}