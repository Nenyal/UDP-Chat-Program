import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

public class Server {

    public static void main(String[] args) {
	try {
        DatagramSocket ds = new DatagramSocket(11111);
        Vector<MyClient> clients = new Vector<>();
        int c = 0;

        while(true){
            byte[] b = new byte[1024];
            DatagramPacket p = new DatagramPacket(b, 1024);
            ds.receive(p);

            String s = new String(p.getData(), 0, p.getLength());
            if (s.startsWith("new_client ")){
                s = s.replace("new_client ", "");
                clients.add(new MyClient(p.getAddress(), p.getPort(), s));
            } else {
                for (MyClient mc:clients){
                    if (!s.startsWith(mc.name+":")){
                        DatagramPacket sp = new DatagramPacket(s.getBytes(), s.getBytes().length, mc.ip, mc.port);
                        ds.send(sp);
                    }
                }
            }
        }
    } catch(Exception ie) {
        System.out.println(ie);
        }
    }
}
class MyClient{
    InetAddress ip;
    int port;
    String name;

    public MyClient(InetAddress ip, int p, String n){
        this.ip = ip;
        this.port = p;
        this.name = n;
    }

}
