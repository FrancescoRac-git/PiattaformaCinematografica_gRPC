package videoteca.backend;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

import videoteca.backend.gRPC.FilmServiceImpl;

public class ServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;

        Server server = ServerBuilder.forPort(port)
                .addService(new FilmServiceImpl())
                .build()
                .start();
        System.out.println("Server gRPC avviato con successo sulla porta: " + port);
        server.awaitTermination();
    }
}
