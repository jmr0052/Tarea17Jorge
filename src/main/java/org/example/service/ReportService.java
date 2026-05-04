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
        System.out.println("Library: " + manager.libraryName);
        System.out.println("Members: " + manager.members.size());
        System.out.println("Books: " + manager.books.size());
        System.out.println("Loans processed: " + manager.totalLoansProcessed);
        System.out.println("Fines collected: " + manager.totalFinesCollected);
        // directly accessing manager internals, too intimate
        System.out.println("Revenue: " + manager.totalRevenue);
        System.out.println("Director: " + manager.libraryDirector);
        System.out.println("Address: " + manager.libraryAddress);
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
}