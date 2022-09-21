
import java.net.ServerSocket;
import java.net.Socket;

/*
 *
 * @author J Suarez
 */
public class Servidor {
    public static void main(String[] args) {
        try {
            int pto = 8000;
            ServerSocket s = new ServerSocket(pto);
            s.setReuseAddress(true); //nos permite enlazar un socket aunque una conexión esté en espera
            System.out.println("Servidor iniciado...");
            
            Socket cl = s.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
