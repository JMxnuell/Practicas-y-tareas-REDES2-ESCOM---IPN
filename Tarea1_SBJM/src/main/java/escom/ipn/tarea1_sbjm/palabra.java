package escom.ipn.tarea1_sbjm;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author J Suarez
 */
public class palabra {
    final static String[][] palabras = {
        new String [] {"TCP", "UDP", "IGMP", "FTP", "HTTP", "ICMP", "ARP", "MAC", "IP", "MTU"},
        new String [] {"SEGMENTACION", "DATAGRAMA", "HANDSHAKE", "PROTOCOLO", "BUFFER", "PUERTO",
                       "PUERTO DESTINO", "CHECKSUM", "INTERNET", "SOCKETS"},
        new String [] {"CONTROL DE TRANSMISION",
                       "FLUJO EMISOR Y RECEPTOR",
                        "ENTREGA DE UNO A UNO",
                        "API DE SOCKETS",
                        "CONEXION BLOQUEANTE",
                        "FULL DUPLEX",
                        "PUERTO DE ORIGEN",
                        "PUERTO DESTINO",
                        "SOCKET BLOQUEANTE",
                        "SOCKET NO BLOQUEANTE"
                    }
    };
    
    private String generada;
    ArrayList<Integer> segmentos = new ArrayList<Integer>();    
    public palabra(int dificultad) {
        Random random = new Random();
        generada = palabras[dificultad-1][random.nextInt(10)];
    }

    private void segmentar(){
    int l = generada.length();
    int t = 0;
        for (int i = 0; i < l; i++) {
            if(generada.charAt(i) != ' ')
                t++;
            else{
                segmentos.add(t);
                t = 0;
            }
        }
    }
}
