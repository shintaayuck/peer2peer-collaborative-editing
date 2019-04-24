import controller.Controller;
import controller.Messenger;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

public class Main{
    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {
        BasicConfigurator.configure();

        if (args.length < 1) {
            System.out.println("Usage for first node : Main <Server port>");
            System.out.println("Usage for other node : Main <Server port> <Destination URI> ");
            java.lang.System.exit(1);
        }

        int port = Integer.valueOf(args[0]);

        Controller controller = new Controller(InetAddress.getLocalHost().getHostAddress(), port);

        if (args.length==2) {
            String URI = args[1];
            System.out.println(URI);
            Messenger.ConnectToNode(URI);
        }
    }
}
