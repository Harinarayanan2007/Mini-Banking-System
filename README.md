# Mini Banking System

Java Swing + PostgreSQL desktop application.

## Features
- User registration & login (jBCrypt)
- Admin approval workflow
- Deposit / Withdraw
- Fund transfer between accounts
- Email notifications (JavaMail)

## Tech Stack
- Java (JDK 17)
- Swing + FlatLaf
- PostgreSQL + JDBC
- Maven

## How to Run
1. Create DB `bankdb` and run `schema.sql`
2. Set credentials in `application.properties`
3. `mvn clean install`
4. Run `Main.java`
