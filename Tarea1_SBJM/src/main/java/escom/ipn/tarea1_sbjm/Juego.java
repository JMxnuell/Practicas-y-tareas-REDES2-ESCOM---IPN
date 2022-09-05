package escom.ipn.tarea1_sbjm;
   
import java.util.ArrayList;

/**
 *
 * @author J Suarez
 */
public class Juego{
    
    public int longitud;
    public int avance;
    private ArrayList<Integer> segmentos;
    public String palabra;
    
    public Juego(ArrayList<Integer> segmentos){
        this.segmentos = segmentos;
        avance = 0;
        longitud = 0;
        palabra = "";
        for (Integer si: segmentos){
         longitud += si;
         for(int i = 0; i<si; i++)
            palabra += '_';
         palabra += ' ';
        }
    }
    
    public Boolean ganador(int acertado){
        avance += acertado;
        return avance == longitud;
    }
    
    public void update(String letra, ArrayList<Integer> arr){
        for (Integer i : arr) {
            palabra = palabra.substring(0, i) + letra.charAt(0)
              + palabra.substring(i + 1);
        }
    }
    public void tablero(){
        System.out.println(palabra);
    }
}

