package server.threadpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkerRunnable implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerRunnable.class);

    private Socket client;
    private String serverText;
    private Pattern pattern;

    public WorkerRunnable(Socket client, String serverText) {
        this.client = client;
        this.serverText = serverText;
        pattern = Pattern.compile("\\?(\\S*)=?(\\S*)");
    }

    @Override
    public void run() {
        try(BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true)) {
            long time = System.currentTimeMillis();
            String inputStr = input.readLine();
            String out = "";
            if(inputStr != null) {
                Matcher matcher = pattern.matcher(inputStr);
                if (matcher.find()) {
                    out = matcher.group(1);
                }
            }
            output.write(String.format("%s %s - %s(ms) \n%s", "HTTP/1.1 200 OK\n\nWorkerRunnable:",
                    this.serverText, time, out));
//            output.write(String.format("%s %s", "HTTP/1.1 200 OK\n\nWorkerRunnable:", inputStr));
        } catch (IOException e) {
            LOGGER.warn(Arrays.toString(e.getStackTrace()));
        }
    }

}
