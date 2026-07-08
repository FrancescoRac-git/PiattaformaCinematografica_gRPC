package videoteca.backend;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import videoteca.backend.database.FilmDAO;
import videoteca.backend.database.FilmDAODatabase;
import videoteca.backend.gRPC.FilmServiceImpl;

public class ServerMain {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        int port = 50051;
        Connection connessioneDB = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/videoteca_db";
            String user = "postgres";
            String password = "postgresql";
            connessioneDB = DriverManager.getConnection(url,user,password);
            System.out.println("Server: Connessione al database stabilita con successo.");

        } catch (SQLException e) {
            System.err.println("Errore fatale: impossibile connettersi al database. " + e.getMessage());
            return;
        }
        try {


            FilmDAO mioDatabase = FilmDAODatabase.getInstance(connessioneDB);
            Server server = ServerBuilder.forPort(port)
                    .addService(new FilmServiceImpl(mioDatabase))
                    .build()
                    .start();
            System.out.println("Server gRPC avviato con successo sulla porta: " + port);
            server.awaitTermination();
        }catch (IOException | InterruptedException e) {
            System.err.println("Errore durante l'esecuzione del Server gRPC: " + e.getMessage());
        }
    }
}
