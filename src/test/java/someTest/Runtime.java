package someTest;

import com.fs.frame.client.TCPClient;
import com.fs.frame.server.TCPServer;
import org.junit.Test;

import java.io.IOException;

public class Runtime {
    @Test
    public void testClient() throws IOException {
        TCPClient tcpClient = new TCPClient("localhost", 12321);
        tcpClient.start();
    }
    @Test
    public void testServer() throws IOException {
        TCPServer tcpServer = new TCPServer(12321).init();
        tcpServer.start();
    }

}
