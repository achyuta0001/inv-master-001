# inv-master-001

Spring Boot backend for the Invoice Master application.

## Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.3.2 |
| Language | Java 17 |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| Auth | JWT (access + refresh tokens) |
| ORM | Spring Data JPA / Hibernate |

## Getting Started

### 1. Start the database

```bash
docker-compose up -d
```

This starts a PostgreSQL 16 instance on port `5432` with:
- Database: `inv_master_001`
- User: `postgres`
- Password: `postgres`

### 2. Run the application

```bash
./mvnw spring-boot:run
```

Flyway will automatically apply the migration at `src/main/resources/db/migration/V1__create_all_basic_tables.sql` on first run.

The API is available at `http://localhost:8080`.

## Data Model

```
companies
  └── users           (company_id FK, role: ADMIN | MANAGER | SALES)
  └── settings        (1-to-1: gstPercentage, cgstPercentage, sgstPercentage, currency, invoicePrefix, financialYear)
  └── customers       (customerName, billingAddress, shippingAddress, gstNumber)
  └── materials       (materialName, unit, currentPrice, active)
  │     └── material_price_history  (price, effectiveFrom, effectiveTo)
  └── products        (productName, description, active)
  │     └── product_materials       (material_id FK, quantity)
  │     └── product_price_history   (manufacturingCost, sellingPrice, profitMargin)
  └── invoice_sequences
  └── invoices        (invoiceNumber, customerId FK, status, subtotal, gst, discount, grandTotal, remarks)
        └── invoice_line_items  (productId FK, productName, quantity, unitPrice, total)
        └── payments            (paymentDate, amount, paymentMethod, transactionReference, remarks)
```

## API Endpoints

### Auth — `/auth`

| Method | Path | Body | Description |
|---|---|---|---|
| POST | `/auth/login` | `{ email, password }` | Returns `{ accessToken, refreshToken }` |
| POST | `/auth/refresh` | `{ refreshToken }` | Rotate tokens |
| POST | `/auth/company/register` | `RegisterCompanyRequest` | Create a new company |
| POST | `/auth/user/register` | `RegisterUserRequest` | Add a user to a company |

### Products — `/products` (JWT required)

| Method | Path | Role | Description |
|---|---|---|---|
| GET | `/products` | ADMIN, MANAGER, EMPLOYEE | List all products for the authenticated company |
| POST | `/products` | ADMIN, MANAGER | Create product with materials |
| PUT | `/products/{id}` | ADMIN, MANAGER | Update product |
| DELETE | `/products/{id}` | ADMIN | Soft delete product |

### Settings — `/settings` (JWT required)

| Method | Path | Description |
|---|---|---|
| POST | `/settings` | Create settings for a company |
| GET | `/settings` | Get settings for the authenticated company |
| PUT | `/settings` | Update settings |

## Invoice Status Enum

```
GENERATED → PARTIALLY_PAID → PAID
         → CANCELLED
```

## User Roles

| Role | Permissions |
|---|---|
| ADMIN | Full access including delete |
| MANAGER | Create and update; no delete |
| SALES | Read only |

## Frontend

See [inv-master-ui](https://github.com/pjba11-11/inv-master-ui) for the Next.js frontend.
