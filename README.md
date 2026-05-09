# hotel-management-system
This is a hotel management system built in Java with a JavaFX graphical interface. The project was designed to handle the core operations of a hotel, from customer and room registration to reservation control and check-in/check-out flows.

The project focuses on the practical application of advanced Object-Oriented Programming (OOP) and software architecture concepts explored in an academic context.

## Main Features
Customer Management: Complete guest registration with data validation (Tax ID, Email, Phone).

Room Management: Support for different accommodation types (Single, Double, Suite) through inheritance and abstract classes.

Reservation System: Booking engine that checks room availability and validates check-in/check-out dates.

Operations Flow: Reservation status control, allowing check-in, check-out, and payment processing.

Graphical Interface: A modern and intuitive UI built with JavaFX, using dynamic layouts such as BorderPane and VBox.

## Programming Concepts Applied
This project demonstrates the following topics:

Inheritance and Polymorphism: Use of abstract classes (Person, Room) and specializations for code reuse and polymorphic behavior.

Exception Handling: A hierarchy of custom exceptions for business errors (e.g. RoomUnavailableException).

Collections (ArrayList): Dynamic in-memory data management using the Java Collections API.

Encapsulation and Validation: Data protection through access modifiers and centralized validation logic.

## Technologies Used
Java 17+: Base language.

JavaFX: UI framework.

Maven: Dependency management and project build.

## Project Structure
The code is organized with a clear separation of responsibilities:

hotel.model: Data classes (entities).

hotel.service: Business logic and system rules.

hotel.ui: Graphical interface components.

hotel.exception: Specific error definitions.

hotel.util: Helper tools and validators.
