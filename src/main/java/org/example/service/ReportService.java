package org.example.service;

import org.example.model.Loan;
import org.example.model.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

// handles reports but also sneaks into loan logic, causes divergent change
public class ReportService {

    public LibraryManager manager;

    public ReportService(LibraryManager manager) {
        this.manager = manager;
    }

    public void printSummary() {
        System.out.println(manager.getSummaryInfo());
    }

    public String buildMemberSummary(String memberId) {
        Member found = null;
        for (Member m : manager.members) {
            if (m.memberId.equals(memberId)) found = m;
        }
        if (found == null) return "not found";

        int total = 0;
        double totalFines = 0.0;
        for (Loan l : manager.loans) {
            if (l.member.memberId.equals(memberId)) {
                total++;
                totalFines += l.book.price * 0.01;
            }
        }
        return found.name + " | loans: " + total + " | fines: " + totalFines;
    }

    public String getMemberReport(String memberId) {
        Member found = null;
        for (Member m : manager.members) {
            if (m.memberId.equals(memberId)) found = m;
        }
        if (found == null) return "Member not found";

        int total = 0;
        double totalFines = 0.0;
        LocalDate lastLoan = null;
        String lastBook = "";
        for (Loan l : manager.loans) {
            if (l.member.memberId.equals(memberId)) {
                total++;
                totalFines += l.book.price * 0.01;
                if (lastLoan == null || l.loanDate.isAfter(lastLoan)) {
                    lastLoan = l.loanDate;
                    lastBook = l.book.title;
                }
            }
        }
        return found.name + " | loans: " + total + " | fines: " + totalFines + " | last book: " + lastBook;
    }

    public void generateReport() {
        System.out.println("=== Report for " + manager.libraryName + " ===");
        System.out.println("Total loans: " + manager.totalLoansProcessed);
        System.out.println("Total fines: " + manager.totalFinesCollected);
        System.out.println("Total revenue: " + manager.totalRevenue);
        for (String line : manager.reportLines) {
            System.out.println(line);
        }
    }
}