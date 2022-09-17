package escom.ipn.tarea1_sbjm;

import java.io.*;
import java.net.*;

/**
 *
 * @author J Suarez
 */
public class servidor {
     public static void main(String[] args){
      try{
          int pto = 8000;
          ServerSocket s = new ServerSocket(pto);
          s.setReuseAddress(true);
          System.out.println("Servidor iniciado...");
          
          for(;;){
            System.out.println("Esperando cliente");
            Socket cl = s.accept();
            System.out.println("Cliente conectado desde "+cl.getInetAddress()+":"+cl.getPort());
            DataInputStream dis = new DataInputStream(cl.getInputStream());

            int dificultad = dis.readInt();

            System.out.println("Se recibio la dificultad: " + dificultad);
            palabra pGenerada = new palabra(dificultad);
            ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
            oos.writeObject(pGenerada.getSegmentos()); // mandamos el total de segmentos (ya que puede que se tengan espacios)
            System.out.println("Se mandaron los segmentos de la palabra");
            BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
              for(;;){
              System.out.println("Esperando respuesta del cliente");
              String lRecibida = br.readLine();
              System.out.println("recibido: " + lRecibida);
              if(lRecibida.compareToIgnoreCase("FINISH") == 0){
                  System.out.println("JUEGO FINALIZADO");
                  // mandamos la palabra que se debió adivinar
                  PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                  pw.println(pGenerada.getGenerada()); 
                  pw.close();
                  dis.close();
                  oos.close(); 
                  cl.close();
                  break; // El juego a finalizado
              }else{
                  System.out.println("Mandado respuesta al cliente");
                  oos.writeObject(pGenerada.encontrados(lRecibida));
              } //mandamos los indices en los que se encontraron la letra
              }
          }
      }catch(Exception e){
          e.printStackTrace();
      }  
    }//main
}
