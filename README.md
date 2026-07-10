# LogDen Store
Kehityksessä.

Tavoitteena on rakentaa kuvitteellinen full stack verkkokauppasovellus.

## Projektirakenne

```text
logden/
├── .devcontainer/
├── backend/ 
│   ├── src/main/java/com/logden/backend/
│   │   ├── domain/
│   │   ├── exception/
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

- ```exception/``` sisältää keskitetyn virheenkäsittelyn sekä sovelluksen poikkeukset.

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

<details>
<summary>Ostoskori / Cart</summary>

#### GET

```http
GET /api/cart/{id}
```

Hakee ostoskorin tunnisteen perusteella.

---

#### POST

```http
POST /api/cart/{cartId}/items?productId={productId}&quantity={quantity}
```

Lisää tuotteen ostoskoriin.

---

#### PUT

```http
PUT /api/cart/items/{cartItemId}?quantity={quantity}
```

Päivittää ostoskorissa olevan tuotteen määrän.

---

#### DELETE

```http
DELETE /api/cart/items/{cartItemId}
```

Poistaa tuotteen ostoskorista.

---

</details>

<details>
<summary>Kategoria / Category</summary>

#### GET

```http
GET /api/categories
```

Hakee kaikki kategoriat.

---
#### POST

```http
POST /api/categories
```

Lisää uuden kategorian.

---
#### PUT
```http
PUT /api/categories/{id}
```

Päivittää olemassa olevan kategorian.

---
#### DELETE
```http
DELETE /api/categories/{id}
```

Poistaa kategorian tunnisteen perusteella.

---

</details>

<details>
<summary>Tilaus / Order</summary>

#### GET

```http
GET /api/orders
```

Hakee kaikki tilaukset.

---
#### GET

```http
GET /api/orders/{id}
```

Hakee tilauksen tunnisteen perusteella.

---
#### POST

```http
POST /api/orders/user/{userId}
```

Luo uuden tilauksen käyttäjän ostoskorin tuotteista.

---
</details>

<details>
<summary>Tuote / Product</summary>

#### GET

```http
GET /api/products
```

Hakee kaikki tuotteet.

---
#### POST

```http
POST /api/products
```

Lisää uuden tuotteen.

---
#### PUT
```http
PUT /api/products/{id}
```

Päivittää olemassa olevan tuotteen.

---
#### DELETE
```http
DELETE /api/products/{id}
```

Poistaa tuotteen tunnisteen perusteella.

---


</details>

<details>
<summary>Käyttäjä / User</summary>

#### GET

```http
GET /api/users/{id}
```

Hakee käyttäjän tunnisteen perusteella.

---
#### POST

```http
POST /api/users
```

Lisää uuden käyttäjän.

---
#### PUT

```http
PUT /api/users/{id}
```

Päivittää olemassa olevan käyttäjän.

---
#### DELETE

```http
DELETE /api/users/{id}
```

Poistaa käyttäjän tunnisteen perusteella.

---
#### GET

```http
GET /api/users
```

Hakee kaikki käyttäjät.

---

</details>

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
