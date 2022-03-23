package com.techelevator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuditLog {
    private final List<String> audits;

    public AuditLog() {
        audits = readAudits();
    }

    public void writeAudit(String audit) {
        File auditFile = new File("./Log.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(auditFile, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String time = LocalDateTime.now().format(formatter);
            pw.println(time + " " + audit);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> readAudits() {
        List<String> auditLogs = new ArrayList<>();
        File auditFile = new File("./Log.txt");
        if (auditFile.exists() && auditFile.isFile()) {
            try (Scanner readFile = new Scanner(auditFile)) {
                while (readFile.hasNext()) {
                    String nextLine = readFile.nextLine();
                    auditLogs.add(nextLine);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File \"./log.txt\" not found!");
            }
        } else {
            try {
                auditFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create audit file!");
            }
        }

        return auditLogs;
    }

    public List<String> getAudits() {
        return audits;
    }
}
