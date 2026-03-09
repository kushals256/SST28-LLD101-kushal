package com.example.reports;

/**
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - Viewer depends directly on concrete ReportFile
 * - No Proxy involved
 */
public class ReportViewer {

    public void open(ReportProxy report, User user) {
        report.display(user);
    }
}
