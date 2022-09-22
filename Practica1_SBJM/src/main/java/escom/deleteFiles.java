
package escom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import org.apache.commons.io.FileUtils;

/**
 *
 * @author J Suarez
 */
public class deleteFiles {
    public static void deleteFiles(File f) throws IOException {
      File fL[] = f.listFiles();
      for(File file : fL) {
         if(file.isFile()) {
            file.delete();
         } else {
             deleteFiles(file);
         }
      }
      f.delete();
   }
}
