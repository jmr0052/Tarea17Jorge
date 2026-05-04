# Library Management System

## What is this

School project for the Clean Code subject. The idea was to write a Java app with bad code on purpose (10 code smells) and then fix everything step by step using IntelliJ IDEA. Also includes Selenium tests for a small web interface.

## Stack used
- Java 25, Maven
- IntelliJ IDEA
- Selenium WebDriver + JUnit 5
- Git and GitHub

## The 10 code smells and how I fixed them

**1. Duplicate Code** — validation logic for books and members was copy pasted in like 3 different places. ended up extracting two methods with Ctrl+Alt+M so its only written once now.

**2. Dead Code** — there were methods that literally nothing was calling. printAllBooks, isValidEmail, a couple more. just deleted them, no reason to keep dead weight around.

**3. Long Method** — processLoan was doing everything at once, finding the member, finding the book, validating, creating the loan. split it into smaller methods using the same extract shortcut.

**4. Switch Statements** — same switch block appeared twice in different methods. pulled each one into its own method so its not duplicated.

**5. Temporary Fields** — Book had a field tempOverdueDays that was only ever used inside one method in a completely different class. removed it and used a local variable instead, makes much more sense.

**6. Shotgun Surgery** — the fine rate 0.5 was hardcoded in two files. changing it meant editing both. removed the copy in ReportService so theres only one place to touch if it ever changes.

**7. Inappropriate Intimacy** — ReportService was directly accessing a bunch of internal fields from LibraryManager. added a getSummaryInfo() method so the other class doesnt need to dig into its internals.

**8. Feature Envy** — there was a method in LibraryManager that was basically only interested in data from Loan and Book. moved it to ReportService where it actually belongs.

**9. Divergent Change** — LibraryManager was handling loans and also generating reports, two completely different things. moved generateReport to ReportService.

**10. Large Class** — LibraryManager had fields like address, phone, email, director mixed in with all the business logic. created a LibraryInfo class to hold all that separately.

## Selenium tests

10 tests that check the web interface using Selenium with ChromeDriver in headless mode. checks things like the form being there, inputs working, books and members showing up correctly, that kind of stuff. all 10 pass.

## How to run it

1. Clone it: `git clone https://github.com/jmr0052/Tarea17Jorge.git`
2. Open in IntelliJ and wait for Maven to load (takes a moment the first time)
3. Run Main.java for the console app
4. Run LibrarySeleniumTest.java for the tests, need Chrome installed for that one