package servlets;

import org.chat.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class RunServerTest {

    @Test
    public void run() throws Exception {
        Thread server = new Thread(() -> Main.main(null));
        server.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        Assertions.assertTrue(server.isAlive());
    }

}
