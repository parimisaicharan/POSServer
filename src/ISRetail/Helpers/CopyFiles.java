/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.Helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author Administrator
 */
public class CopyFiles {

    public void copyFile(String fname1, String fname2) {
        int BSIZE = 1024;
        FileChannel in = null;
        FileChannel out = null;
        ByteBuffer buffer = null;
        try {
            in = new FileInputStream(fname1).getChannel();
            out = new FileOutputStream(fname2).getChannel();
            buffer = ByteBuffer.allocate(BSIZE);
            while (in.read(buffer) != -1) {
                buffer.flip(); // Prepare for writing
                out.write(buffer);
                buffer.clear(); // Prepare for reading
            }
        } catch (Exception e) {

        } finally{
            try{
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
            }catch(Exception e){                
            }
            in = null;
            out = null;
            buffer = null;
        }
    }
    

}
