
import escom.tree;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
             // Cargamos el directorio remoto en un JTree
            File fremoto = new File("src/cRemota");
            tree tremoto = new tree(fremoto);
            File f = new File("");
            String ruta = f.getAbsolutePath()+"\\src\\cRemota\\";
            System.out.println(ruta);
        for(;;){
            System.out.println("Esperando cliente...");
            Socket cl = s.accept();
            System.out.println("Cliente conectado desde "+cl.getInetAddress()+":"+cl.getPort());
            InputStream is = cl.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = cl.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            int opc;
            do{ //mientras la opcion no sea salir
                opc = dis.readInt(); //leemos la opción escogida desde el cliente
                switch(opc){
                    case 1:
                         oos.writeObject(tremoto); // mandamos nuestro jTree para que sea visualizado desde el cliente
                         oos.flush();
                        break;
                    case 3:
                         int nArch = dis.readInt();
                         for(int i = 0; i<nArch; i++) {
                            Socket cl2 = s.accept();
                            String nombre = dis.readUTF();
                            long tam = dis.readLong();
                            System.out.println("Comienza descarga del archivo " + nombre + " de " + tam + " bytes\n\n");
                            DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta+nombre));
                            DataInputStream dis2 = new DataInputStream(cl2.getInputStream());
                            long recibidos = 0;
                            int l = 0, porcentaje = 0;
                            while (recibidos < tam) {
                                byte[] b = new byte[1500];
                                l = dis2.read(b);
                                System.out.println("leidos: " + l);
                                dos.write(b, 0, l);
                                dos.flush();
                                recibidos = recibidos + l;
                                porcentaje = (int) ((recibidos * 100) / tam);
                                System.out.println("\rRecibido el " + porcentaje + " % del archivo");
                            }//while
                            System.out.println("Archivo recibido..");
                            tremoto = new tree(fremoto);
                            System.out.println("Jtree actualizado..");
                            dos.close();
                            dis2.close();
                            cl2.close();
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 7:
                        System.out.println("Cerrando cliente...");
                        oos.close();
                        dis.close();
                        cl.close();
                        break;
                    default:
                        break;
                }
            }while(opc != 7);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
