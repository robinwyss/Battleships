package com.jostrobin.battleships.server;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import com.jostrobin.battleships.server.client.Client;
import com.jostrobin.battleships.server.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The server opens a ServerSocket and waits for connections. For every connection a new thread is started which
 * handles the client and his input.
 *
 * @author joscht
 */
public class BattleshipsServer implements Runnable, ApplicationContextAware
{
    private static final Logger logger = LoggerFactory.getLogger(BattleshipsServer.class);

    private static final int DEFAULT_SERVER_PORT = 11223;

    private static int SERVER_PORT = DEFAULT_SERVER_PORT;

    private IdGenerator clientIdGenerator = new IdGenerator();

    private ServerManager serverManager;

    private static boolean running = true;

    private ApplicationContext context;

    public BattleshipsServer()
    {
        Thread detectionThread = new Thread(new DetectionManager());
        detectionThread.start();
    }

    public static void main(String[] args)
    {
        // parse the startup arguments
        for (String arg : args)
        {
            if (arg.startsWith("port"))
            {
                String port = arg.substring("port=".length());
                try
                {
                    SERVER_PORT = Integer.parseInt(port);
                }
                catch (NumberFormatException e)
                {
                    // ignore it, we use default
                }
            }
        }

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:service-context.xml");
        BattleshipsServer server = context.getBean(BattleshipsServer.class);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Wait for client connections and start their handling in a new thread.
     * This method runs asynchronously.
     */
    @Override
    public void run()
    {
        // start a thread to process command line input
        Thread commandThread = new Thread(new CommandLineHandler(serverManager));
        commandThread.start();

        ServerSocket serverSocket;
        try
        {
            serverSocket = new ServerSocket(BattleshipsServer.SERVER_PORT);
            logger.debug("Server running. Waiting for clients...");

            while (BattleshipsServer.running)
            {
                try
                {
                    Socket clientSocket = serverSocket.accept();
                    Long id = clientIdGenerator.nextId();
                    logger.debug("New client accepted. id=" + id + ", ip=" + clientSocket.getInetAddress());

                    Client client = context.getBean(Client.class);
                    client.init(clientSocket, id, null);
                    client.startup();
                    logger.debug("Done preparing");
                }
                catch (IOException e)
                {
                    logger.warn("Could not accept client", e);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("Could not start server socket", e);
            System.exit(1);
        }
    }

    private class CommandLineHandler implements Runnable
    {
        private ServerManager serverManager;

        public CommandLineHandler(ServerManager manager)
        {
            serverManager = manager;
        }

        public void run()
        {
            Scanner in = new Scanner(System.in);
            while (BattleshipsServer.running)
            {
                String line = in.nextLine();
                if (line.equals("shutdown"))
                {
                    serverManager.sendGlobalChatMessage("Server going down now....");
                    BattleshipsServer.running = false;
                    System.exit(0);
                }
                else if (line.equals("status"))
                {
                    System.out.println(serverManager.getServerStatus());
                }
                else if (line.equals("help"))
                {
                    System.out.println("\nAvailable commands:");
                    System.out.println("\tstatus - displays the current server status");
                    System.out.println("\tsend [msg] - sends [msg] to every connected client");
                    System.out.println("\tshutdown - shuts down the server immediately");
                }
                else if (line.startsWith("send "))
                {
                    String message = line.split(" ", 2)[1];
                    serverManager.sendGlobalChatMessage(message);
                }
            }
        }
    }

    /**
     * Waits for UDP requests and answers them.
     *
     * @author joscht
     */
    private class DetectionManager implements Runnable
    {
        private DatagramSocket socket;

        private static final int CLIENT_UDP_PORT = 11225;

        private static final int SERVER_UDP_PORT = 11224;

        private static final String VERSION = "26102011";

        private static final int BUFFER_SIZE = 64;

        private static final String FIND_SERVER = "findServer";

        private static final String BATTLESHIPS_SERVER = "battleshipsServer";

        public DetectionManager()
        {
            try
            {
                socket = new DatagramSocket(SERVER_UDP_PORT);
            }
            catch (SocketException e)
            {
                logger.error("Could not open UDP socket", e);
            }
        }

        @Override
        public void run()
        {
            while (BattleshipsServer.running)
            {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try
                {
                    socket.receive(packet);
                    // a server has answered, decide on the answer whether it must be forwarded to the callback

                    InetAddress address = packet.getAddress();

                    String message = new String(buffer, "UTF-8").trim();
                    if (message.startsWith(VERSION))
                    {
                        // someone is looking for servers, answer him
                        if (message.startsWith(VERSION + FIND_SERVER))
                        {
                            answer(address);
                        }
                    }
                    else
                    {
                        // discard, wrong version or not meant for us
                    }
                }
                catch (IOException e)
                {
                    logger.error("Could not read UDP request", e);
                }
            }
        }

        /**
         * Sends an answer about our existence back to the specified address.
         *
         * @param address
         */
        private void answer(InetAddress address)
        {
            byte[] buffer;

            // send UDP packets to all the possible server nodes
            DatagramSocket answerSocket = null;
            try
            {
                String message = VERSION + BATTLESHIPS_SERVER;
                buffer = message.getBytes("UTF-8");

                answerSocket = new DatagramSocket();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, CLIENT_UDP_PORT);
                answerSocket.send(packet);
            }
            catch (Exception e)
            {
                logger.warn("Could not respond to client UDP request");
            }
            finally
            {
                if (answerSocket != null)
                {
                    answerSocket.close();
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException
    {
        this.context = context;
    }

    public ServerManager getServerManager()
    {
        return serverManager;
    }

    public void setServerManager(ServerManager serverManager)
    {
        this.serverManager = serverManager;
    }

}
