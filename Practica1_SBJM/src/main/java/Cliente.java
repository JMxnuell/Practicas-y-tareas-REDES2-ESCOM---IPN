import escom.tree;
import java.io.File;
import java.net.Socket;
import java.util.Scanner;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author J Suarez
 */
public class Cliente {
    public static void main(String[] args) {
        try {
            int pto = 8000;
            String dir = "127.0.0.1";
            Socket cl = new Socket(dir,pto);
            System.out.println("Conexion establecida con el servidor...");

            int opc = 0;
            
            // Cargamos el directorio local en un JTree
            File flocal = new File("src/cLocal");
            tree tLocal = new tree(flocal);
            
            do{
                System.out.println("1. Listar contendigo carpeta remota");
                System.out.println("2. Listar contendigo carpeta local");
                System.out.println("3. Subir archivos/carpetas");
                System.out.println("4. Descargar archivos/carpetas");
                System.out.println("5. Eliminar archivos/carpetas remotas");
                System.out.println("6. Eliminar archivos/carpetas locales");
                System.out.println("7. Salir");
                
                Scanner sc = new Scanner(System.in);
                opc = sc.nextInt();
                
                switch(opc){
                    case 1:
                        break;
                    case 2:
                        tLocal.visualizar();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Opcion no valida...");
                        break;
                }
            }while(opc != 7);
            
            cl.close();
        } catch (Exception e) {
        }
    }
}
