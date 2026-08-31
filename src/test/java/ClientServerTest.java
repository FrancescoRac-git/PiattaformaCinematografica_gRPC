import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.jupiter.api.*;
import videoteca.backend.gRPC.SearchFilmsRequest;
import videoteca.frontend.client.FilmClient;
import videoteca.backend.database.FilmDAODatabase;
import videoteca.backend.gRPC.FilmServiceImpl;
import videoteca.backend.model.Film;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClientServerTest {
    private static Server server;
    private static FilmClient client;
    private static FilmDAODatabase filmDAODatabase;
    private static Connection connection;


    @BeforeAll
    static void startServerAndClient() throws Exception {

        String url = "jdbc:postgresql://localhost:5432/videoteca_db";
        String user = "postgres";
        String password = "postgresql";
        connection = DriverManager.getConnection(url, user, password);
        filmDAODatabase = FilmDAODatabase.getInstance(connection);


        server = ServerBuilder.forPort(8980)
                .addService(new FilmServiceImpl(filmDAODatabase))
                .build()
                .start();

        client = new FilmClient("localhost", 8980);
    }

    @AfterAll
    static void stopServerAndClient() {
        if (client != null) client.shutdown();
        if (server != null) server.shutdown();
    }

    @BeforeEach
    void setUpDatabase() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/videoteca_db";
        String user = "postgres";
        String password = "postgresql";
        connection = DriverManager.getConnection(url, user, password);
        filmDAODatabase = FilmDAODatabase.getInstance(connection);

        List<Film> films = filmDAODatabase.tuttiIfilm();
        for(Film film : films){
            filmDAODatabase.eliminaFilm(film.getId());
        }
    }

    @AfterEach
    void tearDownDatabase() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Integrazione: Aggiunta e Ricerca via gRPC")
    void testAggiungiECercaRete() {

        client.aggiungiNuovoFilm("Il Padrino", "Francis Ford Coppola", 1972, "DRAMMATICO", 5, "VISTO");

        SearchFilmsRequest request = SearchFilmsRequest.newBuilder()
                .setQueryGenerica("Il Padrino")
                .build();

        SearchFilmsRequest request2 = SearchFilmsRequest.newBuilder()
                .setQueryGenerica("Francis Ford Coppola")
                .build();

        var risultati = client.cercaFilm(request);

        var risultatiRegista = client.cercaFilm(request2);

        var tuttiIFilm = client.getAll();


        assertEquals(1,tuttiIFilm.size()," tutti i film sono esattamente 1. Test con database vuoto");
        assertFalse(risultati.isEmpty(), "Il server deve restituire la lista contenente il film inserito");
        assertEquals("Il Padrino", risultati.get(0).getTitolo(), "Il titolo deve corrispondere");
        assertEquals("Francis Ford Coppola", risultatiRegista.get(0).getRegista(), "Il regista deve corrispondere");
    }



}



