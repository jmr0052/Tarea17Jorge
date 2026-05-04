package org.example.service;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.example.model.Book;
import org.example.model.Loan;
import org.example.model.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// this class does everything, probably too much but it works
public class LibraryManager {

    public List<Book> books = new ArrayList<>();
    public List<Member> members = new ArrayList<>();
    public List<Loan> loans = new ArrayList<>();
    public List<String> reportLines = new ArrayList<>();
    public double totalFinesCollected = 0.0;
    public double totalRevenue = 0.0;
    public int totalLoansProcessed = 0;
    public String libraryName;
    public String libraryAddress;
    public String libraryPhone;
    public String libraryEmail;
    public String libraryDirector;
    public int maxBooksPerMember;
    public double fineRatePerDay = 0.5;

    public LibraryManager(String libraryName) {
        this.libraryName = libraryName;
        this.maxBooksPerMember = 3;
    }

    // this method is way too long, does too many things at once
    public String processLoan(String memberId, String isbn, LocalDate loanDate) {
        // validate member
        Member foundMember = findMemberById(memberId);
        if (foundMember == null) {
            return "ERROR: member not found";
        }
        if (foundMember.memberId == null || foundMember.memberId.isEmpty()) {
            return "ERROR: invalid member id";
        }
        if (foundMember.name == null || foundMember.name.isEmpty()) {
            return "ERROR: member has no name";
        }

        // validate book
        Book foundBook = findBookByIsbn(isbn);
        if (foundBook == null) {
            return "ERROR: book not found";
        }
        if (foundBook.isbn == null || foundBook.isbn.isEmpty()) {
            return "ERROR: invalid isbn";
        }
        if (foundBook.title == null || foundBook.title.isEmpty()) {
            return "ERROR: book has no title";
        }
        if (!foundBook.available) {
            return "ERROR: book not available";
        }

        // check how many books this member already has
        int currentLoans = 0;
        for (Loan l : loans) {
            if (l.member.memberId.equals(memberId) && !l.returned) {
                currentLoans++;
            }
        }

        // calculate max books allowed based on member type
        int maxAllowed = getMaxLoansForMember(foundMember);

        if (currentLoans >= maxAllowed) {
            return "ERROR: member has reached loan limit";
        }

        // create the loan
        Loan loan = new Loan(foundBook, foundMember, loanDate);
        loans.add(loan);
        foundBook.available = false;
        totalLoansProcessed++;

        // log it
        reportLines.add("Loan: " + foundMember.name + " borrowed " + foundBook.title + " on " + loanDate);

        return "OK: loan created";
    }

    private static int getMaxLoansForMember(Member foundMember) {
        int maxAllowed;
        switch (foundMember.memberType) {
            case "TEACHER":
                maxAllowed = 6;
                break;
            case "STUDENT":
                maxAllowed = 3;
                break;
            case "GUEST":
                maxAllowed = 1;
                break;
            default:
                maxAllowed = 2;
        }
        return maxAllowed;
    }

    private @Nullable Book findBookByIsbn(String isbn) {
        Book foundBook = null;
        for (Book b : books) {
            if (b.isbn.equals(isbn)) {
                foundBook = b;
            }
        }
        return foundBook;
    }

    private @Nullable Member findMemberById(String memberId) {
        Member foundMember = null;
        for (Member m : members) {
            if (m.memberId.equals(memberId)) {
                foundMember = m;
            }
        }
        return foundMember;
    }

    public double calculateFine(Loan loan, LocalDate today) {
        if (loan.returned) return 0.0;
        long days = ChronoUnit.DAYS.between(loan.loanDate, today);
        int allowedDays = getAllowedDaysForMember(loan);
        if (days <= allowedDays) return 0.0;
        long overdueDays = days - allowedDays;
        return overdueDays * fineRatePerDay;
    }

    private static int getAllowedDaysForMember(Loan loan) {
        int allowedDays;
        // duplicated logic again
        switch (loan.member.memberType) {
            case "TEACHER":
                allowedDays = 30;
                break;
            case "STUDENT":
                allowedDays = 14;
                break;
            case "GUEST":
                allowedDays = 7;
                break;
            default:
                allowedDays = 10;
        }
        return allowedDays;
    }

    public void returnBook(Loan loan, LocalDate returnDate) {
        loan.returned = true;
        loan.returnDate = returnDate;
        loan.book.available = true;
        double fine = calculateFine(loan, returnDate);
        totalFinesCollected += fine;
        totalRevenue += fine;
        // log it
        reportLines.add("Return: " + loan.member.name + " returned " + loan.book.title);
    }

    public void addBook(Book book) {
        // validate book - same validation as in processLoan, copy pasted
        if (isInvalidBook(book)) return;
        books.add(book);
    }

    private static boolean isInvalidBook(Book book) {
        if (book.isbn == null || book.isbn.isEmpty()) {
            System.out.println("ERROR: invalid isbn");
            return true;
        }
        if (book.title == null || book.title.isEmpty()) {
            System.out.println("ERROR: book has no title");
            return true;
        }
        return false;
    }

    public void addMember(Member member) {
        // validate member - same validation again, copy pasted
        if (isInvalidMember(member)) return;
        members.add(member);
    }

    private static boolean isInvalidMember(Member member) {
        if (member.memberId == null || member.memberId.isEmpty()) {
            System.out.println("ERROR: invalid member id");
            return true;
        }
        if (member.name == null || member.name.isEmpty()) {
            System.out.println("ERROR: member has no name");
            return true;
        }
        return false;
    }

    public String getSummaryInfo() {
        return "Library: " + libraryName +
                "\nMembers: " + members.size() +
                "\nBooks: " + books.size() +
                "\nLoans processed: " + totalLoansProcessed +
                "\nFines collected: " + totalFinesCollected +
                "\nRevenue: " + totalRevenue +
                "\nDirector: " + libraryDirector +
                "\nAddress: " + libraryAddress;
    }
}