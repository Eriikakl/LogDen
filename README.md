# LogDen Store

Tavoitteena on rakentaa kuvitteellinen full stack verkkokauppasovellus.

## Projektirakenne

```text
logden/
├── .devcontainer/
├── backend/ 
│   ├── src/
│   ├── pom.xml
├── frontend/ 
```

## Teknologiat

### Backend

- Java 21
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- Spring Security
- Bean Validation
- Maven


### Kehitysympäristö
- Docker
- VS Code Dev Containers


## Setup ja suoritus

### 1. Kloonataan repositorio

### 2. Käynnistetään Dev Container

```text
Ctrl + Shift + P 
--> Dev Containers: Reopen in Container
```

### 3. Ajetaan backend

```bash
cd backend

./mvnw spring-boot:run
```
