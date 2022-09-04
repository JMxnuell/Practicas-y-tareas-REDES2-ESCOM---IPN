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
          
          Socket cl = s.accept();
          System.out.println("Cliente conectado desde "+cl.getInetAddress()+":"+cl.getPort());
          DataInputStream dis = new DataInputStream(cl.getInputStream());
          
          int dificultad = dis.readInt();
          System.out.println("Se recibio la dificultad: " + dificultad);
          
          
      }catch(Exception e){
          e.printStackTrace();
      }  
    }//main
}
