# API REST de Libros

Sistema de gestión de libros con CRUD completo, base de datos en memoria (H2) y endpoint de estadísticas.

## Tecnologías

- **Java 17**
- **Spring Boot 4.1** (Web MVC, Data JPA)
- **H2** (base de datos en memoria)
- **Maven** (incluye wrapper `mvnw`, no requiere Maven instalado)

## Requisitos previos

- JDK 17 o superior instalado y en el `PATH`.
- (Opcional) Navegador, Postman o Insomnia para probar la API.

## Cómo ejecutar

Desde la raíz del proyecto:

```bash
./mvnw spring-boot:run
```

En Windows:

```cmd
mvnw.cmd spring-boot:run
```

La aplicación arranca en `http://localhost:8080`. Los datos de ejemplo (3 libros) se cargan automáticamente la primera vez que inicia.

## Endpoints

| Método | URL | Descripción |
|---|---|---|
| `GET` | `/libros` | Lista todos los libros |
| `GET` | `/libros/{id}` | Obtiene un libro por id |
| `POST` | `/libros` | Crea un libro |
| `PUT` | `/libros/{id}` | Actualiza un libro existente |
| `DELETE` | `/libros/{id}` | Elimina un libro |
| `GET` | `/libros/estadisticas` | Estadísticas: total, promedio de precios, disponibles y agotados |

`/` redirige a `/libros`.

## Modelo del libro

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Long` | Identificador (autogenerado) |
| `titulo` | `String` | Título del libro (obligatorio) |
| `autor` | `String` | Autor (obligatorio) |
| `cantidadInventario` | `int` | Cantidad en inventario (no negativa) |
| `precioUnitario` | `BigDecimal` | Precio unitario (mayor a 0) |
| `estado` | `String` | Calculado: `Disponible` o `Agotado` |

El campo `estado` no se guarda en base de datos: se calcula en cada respuesta mediante el operador ternario `cantidadInventario > 0 ? "Disponible" : "Agotado"`.

## Ejemplos

### Crear un libro

```http
POST /libros
Content-Type: application/json
```

```json
{
  "titulo": "Cien anos de soledad",
  "autor": "Gabriel Garcia Marquez",
  "cantidadInventario": 10,
  "precioUnitario": 25.50
}
```

Respuesta (`201 Created`):

```json
{
  "titulo": "Cien anos de soledad",
  "autor": "Gabriel Garcia Marquez",
  "cantidadInventario": 10,
  "precioUnitario": 25.50,
  "estado": "Disponible",
  "id": 1
}
```

### Listar libros

```http
GET /libros
```

```json
[
  {
    "titulo": "Cien anos de soledad",
    "autor": "Gabriel Garcia Marquez",
    "cantidadInventario": 10,
    "precioUnitario": 25.50,
    "estado": "Disponible",
    "id": 1
  },
  {
    "titulo": "El Quijote",
    "autor": "Miguel de Cervantes",
    "cantidadInventario": 0,
    "precioUnitario": 18.00,
    "estado": "Agotado",
    "id": 2
  }
]
```

### Actualizar un libro

```http
PUT /libros/1
Content-Type: application/json
```

```json
{
  "titulo": "Cien anos de soledad (ed. revisada)",
  "autor": "Gabriel Garcia Marquez",
  "cantidadInventario": 5,
  "precioUnitario": 28.00
}
```

### Eliminar un libro

```http
DELETE /libros/1
```

Respuesta: `204 No Content`.

### Estadísticas

```http
GET /libros/estadisticas
```

```json
{
  "cantidadTotalLibros": 3,
  "promedioPrecios": 21.83,
  "librosDisponibles": 2,
  "librosAgotados": 1
}
```

## Validaciones

La API rechaza con `400 Bad Request` y un mensaje descriptivo cuando:

- El título o el autor están vacíos.
- El precio unitario es `null` o **menor o igual a 0**.
- La cantidad en inventario es **negativa**.

Ejemplo:

```json
{
  "error": "Bad Request",
  "message": "El precio unitario debe ser mayor a 0",
  "status": 400
}
```

Un id inexistente devuelve `404 Not Found` con el mensaje `No existe un libro con el id {id}`.

## Consola H2

Accesible en `http://localhost:8080/h2-console`:

- **JDBC URL:** `jdbc:h2:mem:librosdb`
- **Usuario:** `sa`
- **Contraseña:** (vacía)

> La base es en memoria: los datos se pierden al detener la aplicación.

## Estructura del proyecto

```
src/main/java/com/examen/libros/
├── LibrosApplication.java        Punto de entrada
├── controller/LibroController.java  Endpoints REST
├── service/LibroService.java     Lógica de negocio y validaciones
├── model/Libro.java              Entidad
├── repository/LibroRepository.java  Acceso a datos (Spring Data JPA)
├── dto/EstadisticasDTO.java      Respuesta de estadísticas
├── exception/                    Excepciones personalizadas y manejo global
└── config/                       Configuración web y datos de ejemplo
```
