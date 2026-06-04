# -_java_21_Spring_Boot_3_Maven_MySQL_Postman_- :.
# Proyecto Backend CRUD Completo:

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/5dba05e1-1a14-49c3-ac6b-2f231fd9e387" />  

<img width="2206" height="1079" alt="image" src="https://github.com/user-attachments/assets/a98bda48-5b77-445f-87c5-7b72c15e1c7f" />  

<img width="2230" height="1079" alt="image" src="https://github.com/user-attachments/assets/f3aaae7d-eb2b-4c20-8aae-3bfccfd9d2a5" />       

<img width="2179" height="1065" alt="image" src="https://github.com/user-attachments/assets/5935d9f3-8511-4304-8b31-9f96928877b8" />       

<img width="2182" height="1076" alt="image" src="https://github.com/user-attachments/assets/80614c82-5dc2-4c70-83fb-7f3a3ec5c86c" />       

<img width="2189" height="1079" alt="image" src="https://github.com/user-attachments/assets/e457e534-e29e-4a1a-a5c2-159beb40e6af" />       

```
## Java 21 + Spring Boot 3 + Maven + MySQL + Postman .

Proyecto Backend CRUD completo en **Java 21 + Spring Boot 3 + Maven + MySQL**, listo para probar desde **Postman**.

---

# Tecnologías Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- MySQL
- Maven
- Postman

---

# Estructura del Proyecto

```text
crud-java-postman/
│
├── src/
│   └── main/
│       ├── java/com/crud/
│       │
│       ├── CrudApplication.java
│       │
│       ├── controller/
│       │      ProductoController.java
│       │
│       ├── entity/
│       │      Producto.java
│       │
│       ├── repository/
│       │      ProductoRepository.java
│       │
│       └── service/
│              ProductoService.java
│
└── resources/
       application.properties
```

---

# 1. Base de Datos MySQL

```sql
CREATE DATABASE crud_java;

USE crud_java;

CREATE TABLE productos(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DOUBLE,
    cantidad INT
);
```

---

# 2. Archivo `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.crud</groupId>
    <artifactId>crud-java-postman</artifactId>
    <version>1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
    </parent>

    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

    </dependencies>

</project>
```

---

# 3. Archivo `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crud_java
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

---

# 4. Entidad Producto

## `Producto.java`

```java
package com.crud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;

    private Integer cantidad;

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
```

---

# 5. Repository

## `ProductoRepository.java`

```java
package com.crud.repository;

import com.crud.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
}
```

---

# 6. Service

## `ProductoService.java`

```java
package com.crud.service;

import com.crud.entity.Producto;
import com.crud.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public Producto buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Producto actualizar(Long id, Producto producto) {

        Producto existente = repository.findById(id)
                .orElseThrow();

        existente.setNombre(producto.getNombre());
        existente.setPrecio(producto.getPrecio());
        existente.setCantidad(producto.getCantidad());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
```

---

# 7. Controller

## `ProductoController.java`

```java
package com.crud.controller;

import com.crud.entity.Producto;
import com.crud.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return service.guardar(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        return service.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return "Producto eliminado";
    }
}
```

---

# 8. Clase Principal

## `CrudApplication.java`

```java
package com.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                CrudApplication.class,
                args
        );
    }
}
```

---

# Ejecución del Proyecto

Desde IntelliJ IDEA:

```bash
mvn clean install
```

Luego:

```bash
mvn spring-boot:run
```

Servidor:

```text
http://localhost:8080
```

---

# Pruebas en Postman

## CREATE

### Método

```http
POST
```

### URL

```http
http://localhost:8080/api/productos
```

### Body → JSON

```json
{
  "nombre": "Laptop Lenovo",
  "precio": 3500,
  "cantidad": 10
}
```

---

## READ ALL

### Método

```http
GET
```

### URL

```http
http://localhost:8080/api/productos
```

---

## READ BY ID

### Método

```http
GET
```

### URL

```http
http://localhost:8080/api/productos/1
```

---

## UPDATE

### Método

```http
PUT
```

### URL

```http
http://localhost:8080/api/productos/1
```

### Body

```json
{
  "nombre": "Laptop HP",
  "precio": 4200,
  "cantidad": 8
}
```

---

## DELETE

### Método

```http
DELETE
```

### URL

```http
http://localhost:8080/api/productos/1
```

---

# Respuesta JSON de Ejemplo

```json
{
  "id": 1,
  "nombre": "Laptop HP",
  "precio": 4200.0,
  "cantidad": 8
}
```

---

# Endpoints Finales

| Método | Endpoint | Descripción |
|----------|-------------|---------------|
| GET | `/api/productos` | Listar |
| GET | `/api/productos/{id}` | Buscar |
| POST | `/api/productos` | Crear |
| PUT | `/api/productos/{id}` | Actualizar |
| DELETE | `/api/productos/{id}` | Eliminar |

---

# Mejoras Futuras

Este proyecto constituye una base profesional para el desarrollo de APIs REST y puede extenderse fácilmente con:

- Validaciones (`@Valid`)
- Swagger / OpenAPI
- JWT Authentication
- Spring Security
- Manejo global de excepciones (`@ControllerAdvice`)
- Paginación y ordenamiento
- Docker
- Pruebas unitarias con JUnit 5
- Integración continua (CI/CD)
- Migración de MySQL a Oracle Database 19c
- Documentación automática de endpoints

---
**Autor:** Proyecto académico de referencia para Java 21 + Spring Boot 3 + Maven + MySQL + Postman .
:. . / .
