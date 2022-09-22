import static escom.deleteFiles.deleteFiles;
import escom.tree;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFileChooser;
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
            OutputStream os = cl.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            InputStream is = cl.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            do{
                System.out.println("1. Listar contenido carpeta remota");
                System.out.println("2. Listar contenido carpeta local");
                System.out.println("3. Subir archivos/carpetas");
                System.out.println("4. Descargar archivos/carpetas");
                System.out.println("5. Eliminar archivos/carpetas remotas");
                System.out.println("6. Eliminar archivos/carpetas locales");
                System.out.println("7. Salir");
                System.out.println("Opcion: ");
                Scanner sc = new Scanner(System.in);
                opc = sc.nextInt();
                dos.writeInt(opc); // mandamos la opcion al servidor
                dos.flush();
                JFileChooser jf = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir")+ "/src/cLocal");
                jf.setCurrentDirectory(workingDirectory);
                jf.setMultiSelectionEnabled(true);
                jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                switch(opc){
                    case 1:
                        tree tremoto = (tree) ois.readObject();
                        tremoto.visualizar("Directorio remoto");
                        break;
                    case 2:
                        tLocal.visualizar("Directorio local");
                        break;
                    case 3:
                         int r = jf.showOpenDialog(null);
                         if(r==JFileChooser.APPROVE_OPTION) {
                            File[] f2 = jf.getSelectedFiles();
                            dos.writeInt(f2.length);
                            dos.flush();
                            for (int i = 0; i < f2.length; ++i) {
                                Socket cl2 = new Socket( "127.0.0.2",pto);
                                String nombre = f2[i].getName();
                                String path = f2[i].getAbsolutePath();
                                long tam = f2[i].length();
                                System.out.println("Preparandose pare enviar archivo " + path + " de " + tam + " bytes\n\n");
                                DataOutputStream dos2 = new DataOutputStream(cl2.getOutputStream());
                                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                                dos.writeUTF(nombre);
                                dos.flush();
                                dos.writeLong(tam);
                                dos.flush();
                                long enviados = 0;
                                int l = 0, porcentaje = 0;
                                while (enviados < tam) {
                                    byte[] b = new byte[1500];
                                    l = dis.read(b);
                                    System.out.println("enviados: " + l);
                                    dos2.write(b, 0, l);
                                    dos2.flush();
                                    enviados = enviados + l;
                                    porcentaje = (int) ((enviados * 100) / tam);
                                    System.out.print("\rEnviado el " + porcentaje + " % del archivo");
                                }//while
                                System.out.println("\nArchivo enviado..");
                                dos2.close();
                                dis.close();
                                cl2.close();
                            }
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        int r2 = jf.showOpenDialog(null);
                        if(r2 == JFileChooser.APPROVE_OPTION) {
                            File[] f2 = jf.getSelectedFiles();
                            for (int i = 0; i < f2.length; ++i) {
                               deleteFiles(f2[i]); //eliminamos de forma recursiva por si se encuentran carpetas
                            }
                        }
                        //actualizamos el jtree local
                        tLocal = new tree(flocal);
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        dos.close();
                        ois.close();
                        cl.close();
                        break;
                    default:
                        break;
                }
            }while(opc != 7);
        } catch (Exception e) {
        }
    }
}
