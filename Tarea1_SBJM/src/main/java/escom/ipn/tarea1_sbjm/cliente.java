package escom.ipn.tarea1_sbjm;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
/**
 *
 * @author J Suarez
 */
public class cliente {
        public static void main(String[] args){
        try{
            int pto=8000;
            Socket cl = new Socket("127.0.0.1",pto);
            System.out.println("Conexion con servidor establecida, recibiendo datos..");
            
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            int dificultad;
            System.out.print("Seleccione la dificultad:\n");
            System.out.println("[1] Facil | [2] Intermedio | [3] Dificil");
            System.err.print("Opcion: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // leemos la dificultad
            dificultad = Integer.parseInt(br.readLine());
            dos.writeInt(dificultad);
            dos.flush();
            System.err.println("Se mando la dificultad: " + dificultad);
            
            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
