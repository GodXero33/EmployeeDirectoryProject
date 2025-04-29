# Employee Directory Project

A full-stack web application designed to manage employee records efficiently for corporate HR departments. Built as a one-day rapid prototype using **Spring Boot** (Backend), **Angular** (Frontend), and **MySQL** (Database).

---

## Project Structure

```
EmployeeDirectoryProject/
├── EmployeeDirectoryProject_Backend     # Spring Boot REST API
├── EmployeeDirectoryProject_Frontend    # Angular SPA
├── EmployeeDirectoryProject_Docs        # Task brief, DB script
├── LICENSE
└── README.md
```

---

## Objective

Deliver a corporate-grade CRUD system to handle:
- Employee data entry
- Updates & deletions
- Fast retrieval
- Audit trails (created/updated timestamps)

Designed for a typical **mid-sized organization** (100–500 employees), with a clean UI & strict backend validation to support non-technical HR staff.

---

## Tech Stack

| Layer     | Tech              |
|-----------|-------------------|
| Backend   | Spring Boot, Java |
| Frontend  | Angular           |
| Database  | MySQL             |

---

## Key Features

- **Create Employee** – Add name, email, and department
- **View Employees** – List all records with full details
- **Update** – Edit info with validation
- **Delete** – Remove employee with safety checks
- **Validation Rules**
  - Name: Required, max 100 chars, alphabet only
  - Email: Valid format, must be unique
  - Department: Must be one of HR, IT, Finance, Operations
- **Timestamps** – Auto-generated `createdAt` and `updatedAt` fields

---

## API Endpoints

| Method | Endpoint              | Description             |
|--------|-----------------------|-------------------------|
| GET    | `/api/employees`      | Fetch all employees     |
| POST   | `/api/employees`      | Add new employee        |
| PUT    | `/api/employees/{id}` | Update existing record  |
| DELETE | `/api/employees/{id}` | Delete an employee      |

---

## Documentation

Check the [`EmployeeDirectoryProject_Docs/`](./EmployeeDirectoryProject_Docs) folder for:
- `Task.pdf` – Project briefing & business logic
- `db.sql` – MySQL schema setup

---

## Future Enhancements

- OAuth2-based login for HR roles
- Search/filter functionality
- Export data (CSV/Excel)
- Mobile responsiveness

---

## Project Context

Built to simulate a **Fortium Partners** engagement – rapid tech solutions for companies in transition. This project mirrors how a fractional CTO/CIO could deploy HR systems with minimal overhead but high impact.

---

## License

This project is licensed under the terms of the [MIT License](./LICENSE).

---

## Author

Made by **GodXero**
