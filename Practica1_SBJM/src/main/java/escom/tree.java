package escom;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author J Suarez
 */
public class tree extends JFrame{
    private JTree contenido;
    DefaultMutableTreeNode top1;
    public tree(File file){
        top1 = new DefaultMutableTreeNode(file.getName());
        cargarFiles(top1, file); // es necesario cargar los archivos y directorios de forma recursiva
        contenido = new JTree(top1);
        JScrollPane treeView = new JScrollPane(contenido);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        contenido.setCellRenderer(renderer);
        contenido.setShowsRootHandles(true);
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
 
    public void visualizar(String dir){
        
        this.setTitle(dir);
        this.setSize(200,200);
        this.setVisible(true);
    }   
}
