package utils;

import java.io.IOException;

/**
 * Created by Lvmoy on 2017/4/4 0004.
 * Mode: - - !
 */

public class OrderUtils {
    public static boolean doCatPing(String string){
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        boolean connected = false;
        try {
            String str = "ping -c 1 -i 0.2 -W 1 " + string;
            process = runtime.exec(str);
            int result = process.waitFor();
            if(result == 0){
                runtime = null;
                process.destroy();
                connected =  true;
            }else {
                runtime = null;
                process.destroy();
                connected = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connected;
    }
}
