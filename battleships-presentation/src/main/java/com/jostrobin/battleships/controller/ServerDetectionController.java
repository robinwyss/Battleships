package com.jostrobin.battleships.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.ApplicationController;

public class ServerDetectionController extends Observable implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ServerDetectionController.class);

    private static final int CLIENT_UDP_PORT = 11225;

    private static final int SERVER_UDP_PORT = 11224;

    private static final String VERSION = "26102011";

    private static final String FIND_SERVER = "findServer";

    private static final String BATTLESHIPS_SERVER = "battleshipsServer";

    private static final int BUFFER_SIZE = 64;

    private DatagramSocket socket;

    private boolean running = true;

    private ApplicationController applicationController;

    public void init()
    {
        try
        {
            socket = new DatagramSocket(CLIENT_UDP_PORT);
        }
        catch (SocketException e)
        {
            logger.error("Could not open socket", e);
        }
    }
    
    /**
     * Called when we have a server ip. Initializes controllers and everything needed to start the application.
     *
     * @param address
     */
    public void startApplication(InetAddress address)
    {
        try
        {
            applicationController.init(address);
            this.deleteObservers(); // don't listen for UDP answers anymore
        }
        catch (Exception e)
        {
            logger.error("Could not start application, finding another server", e);
            // retry finding a server...
            retry();
        }
    }

    public void retry()
    {
        broadcastFindServer();
    }

    /**
     * Sends a broadcast to make servers answer us.
     */
    public void broadcastFindServer()
    {
        String message = VERSION + FIND_SERVER;
        broadcastMessage(message);
    }

    @Override
    public void run()
    {
        while (running)
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
                    // we found a server
                    if (message.startsWith(VERSION + BATTLESHIPS_SERVER))
                    {
                        setChanged();
                        notifyObservers(address);
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

    private void broadcastMessage(String message)
    {
        byte[] buffer;

        // send UDP packets to all the possible server nodes
        DatagramSocket socket = null;
        try
        {
            buffer = message.getBytes("UTF-8");

            socket = new DatagramSocket();
            socket.setBroadcast(true);

            // send a broadcast to the broadcast addresses of any network interface available
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets))
            {
                List<InterfaceAddress> interfaceAddresses = netint.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses)
                {
                    if (interfaceAddress.getBroadcast() != null)
                    {
                        InetAddress address = interfaceAddress.getBroadcast();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, SERVER_UDP_PORT);
                        socket.send(packet);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Could not broadcast", e);
        }
        finally
        {
            if (socket != null)
            {
                socket.close();
            }
        }
    }


    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
    }
}
