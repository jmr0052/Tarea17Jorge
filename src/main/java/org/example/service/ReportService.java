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

    // fine rate hardcoded again, same as in LibraryManager - shotgun surgery
    public double recalculateFine(Loan loan, LocalDate today) {
        long days = ChronoUnit.DAYS.between(loan.loanDate, today);
        int allowedDays;
        switch (loan.member.memberType) {
            case "TEACHER": allowedDays = 30; break;
            case "STUDENT": allowedDays = 14; break;
            case "GUEST":   allowedDays = 7;  break;
            default:        allowedDays = 10;
        }
        if (days <= allowedDays) return 0.0;
        return (days - allowedDays) * 0.5;
    }
}