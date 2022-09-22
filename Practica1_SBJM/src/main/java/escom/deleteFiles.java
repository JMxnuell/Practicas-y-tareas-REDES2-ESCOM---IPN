
package escom;

import java.io.File;

/**
 *
 * @author J Suarez
 */
public class deleteFiles {
    public static void deleteFiles(File f) {
      File fL[] = f.listFiles();
      for(File file : fL) {
         if(file.isFile()) {
            file.delete();
         } else {
            deleteFiles(file);
         }
      }
   }
}
