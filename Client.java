import java.net.*;
import java.util.Scanner;

public class Client extends Thread {

    public static void main(String[] args) {
        try {
            Client c = new Client(args[0]);
            c.send("new_client "+c.name);
            c.start();

            try (Scanner sc = new Scanner(System.in)) {
                boolean quit = false;
                String input;
                while (quit == false) {
                    input = sc.nextLine();
                    if (input.equals("quit")){
                        quit=true;
                    } else {
                        c.send(c.name+ ":" +input);
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    String name;
    DatagramSocket ds;
    public Client(String n){
        this.name = n;
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                byte[] b = new byte[1024];
                DatagramPacket p = new DatagramPacket(b, 1024);
                ds.receive(p);
                String s = new String(p.getData(), 0, p.getLength());
                System.out.println(">> " + s);
            }

        } catch (Exception ex) {
            System.out.println("Exception in Client:run: " + ex);
        }
    }

    public void send(String msg) {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, 11111);
            ds.send(dp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
