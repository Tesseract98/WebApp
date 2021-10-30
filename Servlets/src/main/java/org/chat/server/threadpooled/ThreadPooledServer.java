package org.chat.server.threadpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPooledServer.class);

    private final int serverPort;
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private ServerSocket serverSocket;
    private boolean isStopped = false;
    private Thread runningThread;


    public ThreadPooledServer(int port) {
        this.serverPort = port;
    }

    @Override
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        LOGGER.info("ThreadPooledServer run" + this.runningThread);
        try {
            openServerSocket();
            while (!isStopped) {
                Socket clientSocket = this.serverSocket.accept();
                this.threadPool.execute(new WorkerRunnable(clientSocket, "Thread Pooled Server"));

            }
        } catch (IOException e) {
            LOGGER.warn(Arrays.toString(e.getStackTrace()));
            if (isStopped()) {
                System.out.println("Server Stopped.");
            }
            throw new RuntimeException("Error accepting client connection", e);
        }
        this.threadPool.shutdown();
        System.out.println("server stopped!");
    }

    private void openServerSocket() throws IOException {
        this.serverSocket = new ServerSocket(this.serverPort);
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

}
