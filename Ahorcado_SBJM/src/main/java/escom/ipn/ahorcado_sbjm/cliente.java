package escom.ipn.tarea1_sbjm;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author J Suarez
 */
public class cliente extends javax.swing.JFrame{
        
        public static void main(String[] args){
        try{
            int pto=8000;
            Socket cl = new Socket("127.0.0.1",pto);
            System.out.println("Conexion con servidor establecida, recibiendo datos..");
            
            OutputStream os = cl.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            int dificultad;
            System.out.print("Seleccione la dificultad:\n");
            System.out.println("[1] Facil | [2] Intermedio | [3] Dificil");
            System.out.print("Opcion: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            dificultad = Integer.parseInt(br.readLine()); 
            dos.writeInt(dificultad); // mandamos la dificultad al servidor
            dos.flush();
           
            System.out.println("Se mando la dificultad: " + dificultad);    
            ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
            ArrayList<Integer> segmentos = (ArrayList<Integer>)ois.readObject(); //leemos los segmentos de la palabra generada
  
            System.out.println("Se recibieron los segmentos: ");
            for (Integer si: segmentos) { // podemos recibir oraciones y no solo palabras
                System.out.println(si); // por lo que recibiremos por parte del server numeros de palabras y su longitud
            }
            Juego juegoNuevo = new Juego(segmentos);
            int intentos = 5;
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
            for(;;){
                System.out.println("Palabra: ");
                juegoNuevo.tablero();
                System.out.println("Intentos: " + intentos);
                System.out.println("\nIngresa una letra: ");
                String letra = br.readLine();
                pw.println(letra); // mandamos la letra para verificar que se encuentre
                pw.flush();
                ArrayList<Integer> posiciones = (ArrayList<Integer>)ois.readObject(); //recibimos la respuesta del servidor (indices encontrados)
                if(posiciones.get(0) != -1){ // al recibir -1 significa que no hay indices encontrados con la letra mandada
                    juegoNuevo.update(letra, posiciones);
                    System.out.println("se encontro la letra en la palabra!");
                    if(juegoNuevo.ganador(posiciones.size())){ // revisamos el avance que llevamos en la palabra a adivinar
                        System.out.println("Felicidades, has ganado!!");
                        break;   
                    }
                }else{ // si no se encontraron indices con dicha palabra
                    System.out.println("No se encontro la letra en la palabra :(");
                    intentos--;
                 if(intentos == 0){
                     System.out.println("Perdiste, no te quedan mas intentos :( ");
                     break;
                 }
                }
            }
            String f = "FINISH";
            pw.println(f);
            pw.flush();
            juegoNuevo.tablero();
            BufferedReader brserv = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            System.out.println("La palabra era: " + brserv.readLine()); //finalmente recibimos la palabra por si no se adivino
            System.out.println("Juego finalizado");
            ois.close();
            brserv.close();
            br.close();
            pw.close();
            cl.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
