package org.example.model;

import java.time.LocalDate;

public class Loan {
    public Book book;
    public Member member;
    public LocalDate loanDate;
    public LocalDate returnDate;
    public boolean returned;

    public Loan(Book book, Member member, LocalDate loanDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.returned = false;
    }
}