package org.example;

import org.example.model.Book;
import org.example.model.Member;
import org.example.service.LibraryManager;
import org.example.service.ReportService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        LibraryManager manager = new LibraryManager("City Library");
        manager.libraryAddress = "123 Main Street";
        manager.libraryPhone = "555-1234";
        manager.libraryEmail = "info@citylibrary.com";
        manager.libraryDirector = "Anna Smith";

        Book b1 = new Book("La sombra del viento", "Carlos Ruiz Zafon", "978-8408163435", true, "Novel", 2001, 17.00);
        Book b2 = new Book("Patria", "Fernando Aramburu", "978-8490665552", true, "Novel", 2016, 15.50);
        Book b3 = new Book("La catedral del mar", "Ildefonso Falcones", "978-8425340917", true, "Novel", 2006, 16.00);

        manager.addBook(b1);
        manager.addBook(b2);
        manager.addBook(b3);

        Member m1 = new Member("John Doe", "STU001", "STUDENT");
        Member m2 = new Member("Dr. Jane Smith", "TEA001", "TEACHER");
        Member m3 = new Member("Bob Guest", "GST001", "GUEST");

        manager.addMember(m1);
        manager.addMember(m2);
        manager.addMember(m3);

        System.out.println(manager.processLoan("STU001", "978-8408163435", LocalDate.now().minusDays(20)));
        System.out.println(manager.processLoan("TEA001", "978-8490665552", LocalDate.now().minusDays(5)));

        ReportService report = new ReportService(manager);
        report.printSummary();
        manager.generateReport();
    }
}