package hms.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class utilities {

    static void writeErrorLogs(Exception ex) {
        try {
            FileWriter fileWriter = new FileWriter("error_log", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ex.printStackTrace(printWriter);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
