# LogDen Store
Kehityksessä.

Tavoitteena on rakentaa kuvitteellinen full stack verkkokauppasovellus. Backendin REST API on rakennettu Spring Bootilla, ja siihen on toteutettu muun muassa JWT-pohjainen autentikointi ja roolipohjaiset käyttöoikeudet. API kehitys jatkuu vielä esimerkiksi virheenkäsittelyn viimeistelyn ja frontendin toteutuksen parissa.

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

---

## API

REST API: Auth, Cart, Category, Order, Product, User

**Base URL**

```text
http://localhost:8080/api
```

---

### Autentikaatio

API hyödyntää JWT-pohjaista autentikaatiota. 
Suojatut endpointit tarvitsevat kutsun yhteydessä tokenin:

```http
Authorization: Bearer <token>
```

<details>
<summary>Rekisteröityminen / Register</summary>

### POST - Rekisteröinti
```http
POST /auth/register
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

Request:

**201 Created**

```json
{
  "firstName": "Matti",
  "lastName": "Matilaine",
  "address": "Testikatu 1",
  "email": "matti@example.com",
  "phone": "0401234567",
  "password": "salasana123"
}
```

Response:

```json
{
    "firstName": "Matti",
    "lastName": "Matilaine",
    "address": "Testikatu 1",
    "email": "matti@example.com",
    "phone": "0401234567",
    "role": "USER"
}
```

**409 Conflict** - Sähköposti on jo käytössä

Request: 

```json
{
  "firstName": "Matti",
  "lastName": "Montti",
  "address": "Testikatu 1",
  "email": "matti@example.com",
  "phone": "0401234567",
  "password": "salasana123"
}
```
Response: 

```json
{
    "status": 409,
    "message": "Email already exists",
    "timestamp": "2026-08-10T19:58:27.568087645"
}
```

**400 Bad Request** - Pyyntö tai annetut tiedot ovat virheelliset

Request:

```json
{
  "firstName": "Matti",
  "lastName": "Montti",
  "email": "matti.montti@example.com",
  "password": "salasana123"
}
```
Response:

```json
{
    "status": 400,
    "message": "address: must not be blank",
    "timestamp": "2026-08-10T20:01:33.723412137"
}
```
---
</details>
<details>

<summary>Kirjautuminen / Login</summary>

### POST - Kirjautuminen 

```http
POST /auth/login
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
Request:

**200 OK**

```json
{ 
"email": "eki.esmes@example.com", 
"password": "salasana"
}
```
Response:

```json
{
    "token": "eyJhb..."
}
```

**400 Bad Request** - Pyyntö tai kirjautumistiedot ovat virheelliset.

Request:

```json
{
  "email": "erkki@exampl",
  "password": "salasana"
}
```
Response:

```json
{
    "status": 400,
    "message": "Invalid email",
    "timestamp": "2026-08-10T19:43:58.719788691"
}
```
Request:

```json
{
  "password": "salaine"
}
```
Response:

```json
{
    "status": 400,
    "message": "email: must not be blank",
    "timestamp": "2026-08-10T19:47:29.855858366"
}
```
---

</details>

---

#### Roolit

| Rooli   | Kuvaus                     |
| ------- | -------------------------- |
| `USER`  | Kirjautunut käyttäjä       |
| `ADMIN` | Ylläpitäjä                 |

---

#### HTTP Status-koodit

| Status                      | Kuvaus                                                          |
| --------------------------- | ----------------------------------------------------------------|
| `200 OK`                    | Pyyntö onnistui                                                 |
| `201 Created`               | Resurssi luotiin onnistuneesti                                  |
| `204 No Content`            | Pyyntö onnistui, eikä vastausta palauteta                       |
| `400 Bad Request`           | Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu |
| `401 Unauthorized`          | Käyttäjä ei ole kirjautunut tai JWT-token on virheellinen       |
| `403 Forbidden`             | Käyttäjällä ei ole vaadittua roolia.                            |
| `404 Not Found`             | Pyydettyä tietoa ei löydy                                       |
| `409 Conflict`              | Pyyntö on ristiriidassa resurssin nykyisen tilan kanssa         |
| `500 Internal Server Error` | Palvelimella tapahtui odottamaton virhe                         |

---

#### Virheilmoitukset

Virheilmoituksilla yhteneväinen rakenne:

```json
{
    "status": 400,
    "message": "email: must not be blank",
    "timestamp": "2026-08-10T19:47:29.855858366"
}
```

---
### Resurssit


<details>
<summary>Ostoskori / Cart</summary>

#### GET

```http
GET /cart
```

Hakee ostoskorin käyttäjän perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

200 OK - Käyttäjän ostoskori haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

---

#### POST

```http
POST /cart/items?productId={productId}&quantity={quantity}
```

Lisää tuotteen ostoskoriin.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

200 OK - Tuote lisättiin ostoskoriin onnistuneesti.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

404 Not Found - Tuotetta ei löydy.

---

#### PUT

```http
PUT /cart/items/{cartItemId}?quantity={quantity}
```

Päivittää ostoskorissa olevan tuotteen määrän.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

200 OK - Tuotteen määrä päivitettiin onnistuneesti.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

---

#### DELETE

```http
DELETE /cart/items/{cartItemId}
```

Poistaa tuotteen ostoskorista.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

204 No Content - Tuote poistettiin ostoskorista onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

404 Not Found - Tuotetta ei löydy.

---

</details>

<details>
<summary>Kategoria / Category</summary>

#### GET

```http
GET /categories
```

Hakee kaikki kategoriat.

Käyttöoikeus: ``Julkinen``

Response:

200 OK - Kategoriat haettiin onnistuneesti.

---
#### GET

```http
GET /categories/{id}
```

Hakee kategorian tunnisteen perusteella.

Käyttöoikeus: ``Julkinen``

Response:

200 OK - Kategoriat haettiin onnistuneesti.

404 Not Found - Kategoriaa ei löydy.

---
#### POST

```http
POST /categories
```

Lisää uuden kategorian.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

201 Created - Kategoria lisättiin onnistuneesti.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

409 Conflict - Kategorian nimi on jo käytössä.

---
#### PUT
```http
PUT /categories/{id}
```

Päivittää olemassa olevan kategorian.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Kategoria päivitettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Kategoriaa ei löydy.

409 Conflict - Kategorian nimi on jo käytössä.

---
#### DELETE
```http
DELETE /categories/{id}
```

Poistaa kategorian tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

204 No Content - Kategoria poistettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Kategoriaa ei löydy.

409 Conflict - Kategoriaa ei voida poistaa, koska siihen liittyy tuotteita.

---

</details>

<details>
<summary>Tilaus / Order</summary>

#### GET

```http
GET /orders
```

Hakee kaikki tilaukset.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Tilaukset haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.


---
#### GET

```http
GET /orders/user
```
Hakee kaikki käyttäjän tilaukset.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

200 OK - Käyttäjän tilaukset haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

---

#### GET

```http
GET /orders/{id}
```

Hakee tilauksen tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Tilaus haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Tilausta ei löydy.

---
#### GET

```http
GET /orders/user/{id}
```

Hakee käyttäjän tilauksen tilaustunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

200 OK - Käyttäjän tilaus haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

404 Not Found - Tilausta ei löydy.



---
#### POST

```http
POST /orders
```

Luo uuden tilauksen käyttäjän ostoskorin tuotteista.

Käyttöoikeus: Vaatii kirjautumisen. ``USER``

Response:

201 Created - Käyttäjän tilaus luotiin onnistuneesti.

400 Bad Request - Ostoskori on tyhjä.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``ADMIN``.

---
</details>

<details>
<summary>Tuote / Product</summary>

#### GET

```http
GET /products
```

Hakee kaikki tuotteet.

Käyttöoikeus: ``Julkinen``

Response:

200 OK - Tuotteet haettiin onnistuneesti.

---
#### GET

```http
GET /products/{id}
```

Hakee tuotteen tunnisteen perusteella.

Käyttöoikeus: ``Julkinen``

Response:

200 OK - Tuote haettiin onnistuneesti.

404 Not Found - Tuotetta ei löydy.

---
#### POST

```http
POST /products
```

Lisää uuden tuotteen.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

201 Created - Tuote lisättiin onnistuneesti.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Kategoriaa ei löydy.

409 Conflict - Tuotteen nimi on jo käytössä.

---
#### PUT
```http
PUT /products/{id}
```

Päivittää olemassa olevan tuotteen.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Tuote päivitettiin onnistuneesti.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Kategoriaa ei löydy.

409 Conflict - Tuotteen nimi on jo käytössä.

---
#### DELETE
```http
DELETE /products/{id}
```

Poistaa tuotteen tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

204 No Content - Tuote poistettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Tuotetta ei löydy.

409 Conflict - Tuotetta ei voida poistaa, koska sitä käytetään tilauksissa.

---


</details>

<details>
<summary>Käyttäjä / User</summary>

#### GET

```http
GET /users/{id}
```

Hakee käyttäjän tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Käyttäjä haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Käyttäjää ei löydy.

---
#### PUT

```http
PUT /users/{id}/role
```

Päivittää käyttäjän roolin.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Käyttäjän rooli päivitettiin onnistuneesti.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

404 Not Found - Käyttäjää ei löydy.


---
#### PUT

```http
PUT /users/me
```

Päivittää kirjautuneen käyttäjän tietoja.

Käyttöoikeus: Vaatii kirjautumisen. ``USER`` ``ADMIN``

Response:

200 OK - Käyttäjän tiedot päivitettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

400 Bad Request - Pyynnön tiedot ovat virheelliset tai vaadittu parametri puuttuu.

409 Conflict - Sähköposti on jo käytössä.

---
#### DELETE

```http
DELETE /users/{id}
```

Poistaa käyttäjän tunnisteen perusteella.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

204 No Content - Käyttäjä poistettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.

409 Conflict - Käyttäjää ei voida poistaa, koska käyttäjällä on tilauksia tai luotu ostoskori.

---
#### GET

```http
GET /users
```

Hakee kaikki käyttäjät.

Käyttöoikeus: Vaatii kirjautumisen. ``ADMIN``

Response:

200 OK - Käyttäjät haettiin onnistuneesti.

401 Unauthorized - Käyttäjä ei ole kirjautunut.

403 Forbidden - Käyttäjän rooli on ``USER``.


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
