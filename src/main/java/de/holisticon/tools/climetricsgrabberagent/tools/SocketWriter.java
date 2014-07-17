package de.holisticon.tools.climetricsgrabberagent.tools;

import java.io.BufferedWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by TGI on 04.07.14.
 */
public class SocketWriter extends FilterWriter {

    private final Socket socket;

    /**
     * @param inetSocketAddress host and port of the underlying {@linkplain Socket}
     * @param charset           charset of the {@linkplain java.io.OutputStream} underlying  {@linkplain Socket}
     * @throws IOException
     */
    public SocketWriter(InetSocketAddress inetSocketAddress, Charset charset) throws IOException {
        this(
                new Socket(inetSocketAddress.getAddress(),
                        inetSocketAddress.getPort()),
                charset
        );
    }

    public SocketWriter(Socket socket, Charset charset) throws IOException {
        super(
                new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), charset)
                )
        );
        this.socket = socket;
    }

    /**
     * Return the underlying {@linkplain java.net.Socket}
     */
    public Socket getSocket() {
        return socket;
    }

}
