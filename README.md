# LogDen Store

Tavoitteena on rakentaa kuvitteellinen full stack verkkokauppasovellus.

## Projektirakenne

```text
logden/
├── .devcontainer/
├── backend/ 
│   ├── src/main/java/com/logden/backend/
│   │   ├── domain/
│   │   ├── security/
│   │   ├── service/
│   │   ├── web/
│   │   └──BackendApplication.java
│   │
│   ├── pom.xml
├── frontend/
└── docker-compose.yml
```

- ```domain/``` sisältää sovelluksen JPA-entiteetit ja repositoryt.

- ```security/``` sisältää Spring Securityn konfiguraation, kuten endpointtien käyttöoikeuksien määrittelyn ja kirjautumisen asetukset.

- ```service/``` sisältää sovelluksen liiketoimintalogiikan.

- ```web/``` sisältää REST-controllerit HTTP-pyynnöille.

- **.devcontainer/** → kehitysympäristö Java/Maven
- **docker-compose.yml** → tietokantapalvelu

## Teknologiat

### Backend

- Java 21
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- Spring Security
- Bean Validation
- Maven
- PostgreSQL


### Kehitysympäristö
- Docker
- VS Code Dev Containers

## API

REST API: Cart, Category, Order, Product, User

### Ostoskori / Cart
#### GET

```http
GET /api/cart/{id}
```
Hakee ostoskorin tunnisteen perusteella.

---
#### POST

```http
POST api/{cartId}/items
```
Lisää tuotteen ostoskoriin.

---
#### PUT

```http
PUT /api/items/{cartItemId}
```
Päivittää ostoskorissa olevan tuotteen määrän.

---
#### DELETE

```http
DELETE /api/items/{cartItemId}
```
Poistaa tuotteen ostoskorista.

---

### Kategoria / Category
#### GET

```http
GET /api/categories
```
Hakee kaikki kategoriat.

---

### Tilaus / Order
#### GET

```http
GET /api/orders
```
Hakee kaikki tilaukset.

---

### Tuote / Product
#### GET

```http
GET /api/products
```
Hakee kaikki tuotteet.

---

### Käyttäjä / User
#### GET

```http
GET /api/users/{id}
```
Hakee käyttäjän tunnisteen perusteella.

---

## Setup ja suoritus

### 1. Kloonataan repositorio

### 2. Käynnistetään Dev Container

```text
Ctrl + Shift + P 
--> Dev Containers: Reopen in Container
```

### 3. Käynnistetään tietokanta

```bash
docker compose up -d
```

### 4. Ajetaan backend

```bash
cd backend

./mvnw spring-boot:run
```
