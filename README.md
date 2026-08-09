# LogDen Store
Kehityksessä.

Tavoitteena on rakentaa kuvitteellinen full stack verkkokauppasovellus.

## Teknologiat

### Backend

- Java 21
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token) -pohjainen autentikointi
- Bean Validation
- Maven
- PostgreSQL


### Kehitysympäristö
- Docker
- VS Code Dev Containers

## Projektirakenne

```text
logden/
├── .devcontainer/
├── backend/ 
│   ├── src/main/java/com/logden/backend/
│   │   ├── domain/
│   │   ├── dto/
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

- ```dto/``` sisältää tiedonsiirto-oliot (DTO), joita käytetään API-vastauksissa ja API-pyynnöissä.

- ```exception/``` sisältää keskitetyn virheenkäsittelyn sekä sovelluksen poikkeukset.

- ```security/``` sisältää Spring Securityn konfiguraation, kuten endpointtien käyttöoikeuksien määrittelyn sekä JWT-tokenien luonnin, validoinnin, autentikointifilterin ja käyttäjätietojen lataamisen.

- ```service/``` sisältää sovelluksen liiketoimintalogiikan.

- ```web/``` sisältää REST-controllerit HTTP-pyynnöille.

- **.devcontainer/** → kehitysympäristö Java/Maven
- **docker-compose.yml** → tietokantapalvelu

## API

REST API: Auth, Cart, Category, Order, Product, User

<details>
<summary>Autentikointi / Authentication</summary>

#### POST - Rekisteröinti

```http
POST /api/auth/register
```

Rekisteröidään uusi käyttäjä.

Käyttöoikeus: ``Julkinen``

Request body: JSON
```json
{ 
"firstName": "Eki", 
"lastName": "Esimerkki", 
"address": "Esimerkkikatu 12", 
"email": "eki.esmes@example.com", 
"phone": "0401234567", 
"password": "salasana" 
}
```

---

#### POST - Kirjautuminen

```http
POST /api/auth/login
```

Kirjaudutaan sähköpostilla ja salasanalla.

Käyttöoikeus: ``Julkinen``

Request body: JSON
```json
{ 
"email": "eki.esmes@example.com", 
"password": "salasana"
}
```

- Kirjautumisen jälkeen palvelin palauttaa JWT-tokenin.

- Token lähetetään endpoint-kutsujen yhteydessä:
Authorization: Bearer [JWT-token].

---

</details>

<details>
<summary>Ostoskori / Cart</summary>

#### GET

```http
GET /api/cart
```

Hakee ostoskorin käyttäjän perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---

#### POST

```http
POST /api/cart/items?productId={productId}&quantity={quantity}
```

Lisää tuotteen ostoskoriin.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---

#### PUT

```http
PUT /api/cart/items/{cartItemId}?quantity={quantity}
```

Päivittää ostoskorissa olevan tuotteen määrän.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---

#### DELETE

```http
DELETE /api/cart/items/{cartItemId}
```

Poistaa tuotteen ostoskorista.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---

</details>

<details>
<summary>Kategoria / Category</summary>

#### GET

```http
GET /api/categories
```

Hakee kaikki kategoriat.

Käyttöoikeus: ``Julkinen``

---
#### POST

```http
POST /api/categories
```

Lisää uuden kategorian.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### PUT
```http
PUT /api/categories/{id}
```

Päivittää olemassa olevan kategorian.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### DELETE
```http
DELETE /api/categories/{id}
```

Poistaa kategorian tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---

</details>

<details>
<summary>Tilaus / Order</summary>

#### GET

```http
GET /api/orders
```

Hakee kaikki tilaukset.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### GET

```http
GET /api/orders/user
```
Hakee kaikki käyttäjän tilaukset.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---

#### GET

```http
GET /api/orders/{id}
```

Hakee tilauksen tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### GET

```http
GET /api/orders/user/{id}
```

Hakee käyttäjän tilauksen tilaustunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---
#### POST

```http
POST /api/orders
```

Luo uuden tilauksen käyttäjän ostoskorin tuotteista.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

---
</details>

<details>
<summary>Tuote / Product</summary>

#### GET

```http
GET /api/products
```

Hakee kaikki tuotteet.

Käyttöoikeus: ``Julkinen``

---
#### POST

```http
POST /api/products
```

Lisää uuden tuotteen.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### PUT
```http
PUT /api/products/{id}
```

Päivittää olemassa olevan tuotteen.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### DELETE
```http
DELETE /api/products/{id}
```

Poistaa tuotteen tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---


</details>

<details>
<summary>Käyttäjä / User</summary>

#### GET

```http
GET /api/users/{id}
```

Hakee käyttäjän tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### PUT

```http
PUT /api/users/{id}
```

Päivittää olemassa olevan käyttäjän.

Käyttöoikeus: Vaatii kirjautumisen. ``USER`` ``ADMIN``

---
#### DELETE

```http
DELETE /api/users/{id}
```

Poistaa käyttäjän tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---
#### GET

```http
GET /api/users
```

Hakee kaikki käyttäjät.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

---

</details>

## Setup ja suoritus

### Käynnistetään Dev Container

```text
Ctrl + Shift + P 
--> Dev Containers: Reopen in Container
```

**Sovellus voidaan myös suorittaa ilman Dev Containeria, jos koneelle asennettu Java 21 ja Maven.**

### Käynnistetään tietokanta

PostgreSQL-tietokanta suoritetaan Docker-kontissa.

```bash
docker compose up -d
```

###  Ajetaan backend

#### Ympäristömuuttujat

- Ennen sovelluksen käynnistämistä määritetään ympäristömuuttujat:

```bash
export JWT_SECRET="oma-base64-muotoinen-avain"
export JWT_EXPIRATION="3600000"
```

```bash
cd backend

./mvnw spring-boot:run
```
