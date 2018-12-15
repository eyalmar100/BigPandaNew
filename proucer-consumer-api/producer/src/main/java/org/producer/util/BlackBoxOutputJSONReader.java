package org.producer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class BlackBoxOutputJSONReader {
    private static final Logger logger = Logger.getLogger(BlackBoxOutputJSONReader.class);
    private static Process p;
    private static BufferedReader is;
    public static volatile boolean initated = false;

    public BlackBoxOutputJSONReader() {
    }

    public static void initBlackBoxReader(String path) throws Exception {
        Runtime r = Runtime.getRuntime();
        p = r.exec(path + "/generator-windows-amd64.exe");
        System.out.println("In Main after exec");
        is = new BufferedReader(new InputStreamReader(p.getInputStream()));
        initated = true;

        try {
            p.waitFor();
        } catch (InterruptedException var4) {
            System.err.println(var4);
        }
    }

    public static void main(String[] argv) throws IOException {
        argv = new String[1];
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("/eyal/generator-windows-amd64.exe");
        System.out.println("In Main after exec");
        new BufferedReader(new InputStreamReader(p.getInputStream()));
        System.out.println("In Main after EOF");
        System.out.flush();

        try {
            p.waitFor();
        } catch (InterruptedException var6) {
            System.err.println(var6);
            return;
        }

        System.err.println("Process done, exit status was " + p.exitValue());
    }

    public static String readBlackBoxOutput() {
        if (is == null) {
            logger.info("could not instatinate process..");
            return null;
        } else {
            try {
                String line;
                if ((line = is.readLine()) != null) {
                    System.out.println(line);
                    return line;
                }
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            return null;
        }
    }
}
