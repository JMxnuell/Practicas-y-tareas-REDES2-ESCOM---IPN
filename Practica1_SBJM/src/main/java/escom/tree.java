package escom;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author J Suarez
 */
public class tree extends JFrame{
    private JTree contenido;
    public tree(File file){
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("cLocal");
        cargarFiles(top, file); // es necesario cargar los archivos y directorios de forma recursiva
        contenido = new JTree(top);
        JScrollPane treeView = new JScrollPane(contenido);
        add(treeView);
    }
    
    void cargarFiles(DefaultMutableTreeNode top, File file){
        File[] lista = file.listFiles();
        for (File f : lista) {
            if(f.isDirectory()){
                DefaultMutableTreeNode Node = new DefaultMutableTreeNode(f.getName());
                top.add(Node);
                File fhijo = new File(f.getAbsolutePath());
                cargarFiles(Node, fhijo);
            } else{
               top.add(new DefaultMutableTreeNode(f.getName()));
            }
        }
    }
    
    public void visualizar(){
        this.setTitle("Directorio local");
        this.setSize(200,200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
}
