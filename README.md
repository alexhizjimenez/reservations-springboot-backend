# Hotel Management API 🏨

Backend robusto desarrollado con **Spring Boot** para la gestión integral de habitaciones y reservas hoteleras. Este sistema implementa un flujo de seguridad basado en tokens y generación de reportes profesionales.

## 🚀 Tecnologías Principales

* **Java 21** & **Spring Boot 3.x**
* **Spring Security & JWT**: Autenticación y autorización basada en Stateless Tokens.
* **Spring Data JPA**: Gestión de persistencia y relaciones ORM.
* **PostgreSQL**: Base de datos relacional para producción.
* **Lombok**: Biblioteca para reducir código repetitivo (Boilerplate).
* **JasperReports**: Motor para la creación de comprobantes de reserva en formato PDF.

---

## 🔒 Seguridad y Control de Acceso (JWT)

El sistema utiliza **JSON Web Tokens** para asegurar los endpoints. 
- **Flujo**: El cliente se autentica en `/api/login`, recibe un token y debe incluirlo en el header `Authorization: Bearer <token>` para peticiones subsecuentes.
- **Protección**: Todos los endpoints de gestión están protegidos contra acceso no autorizado.

---

## 📊 Modelo de Datos y Entidades

### 1. Room (Habitación)
* `id`: Identificador único.
* `number`: Código único de la habitación.
* `type`: Categoría (Individual, Doble, Suite).
* `price`: Costo por noche (BigDecimal).
* `available`: Estado de ocupación (Boolean).
*  `description`: Descripción (String).

### 2. Reservation (Reserva)
* `id`: Identificador único.
* `customer`: Relación Many-to-One con el cliente.
* `checkInDate` / `checkOutDate`: Rango de fechas.
* `room`: Relación Many-to-One con la habitación asociada.

---

## 🛰️ Endpoints de la API

### Gestión de Habitaciones
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/v1/rooms` | Listar todas las habitaciones. |
| `POST` | `/api/v1/rooms` | Crear una nueva habitación (**Admin Only**). |
| `PUT` | `/api/v1/rooms/{id}` | Actualizar disponibilidad manualmente. |
| `DELETE` | `/api/v1/rooms/{id}` | Eliminar manualmente. |

### Gestión de Clientes
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/v1/customers` | Listar todas los clientes. |
| `POST` | `/api/v1/customers` | Crear un nuevo cliente (**Admin Only**). |
| `PUT` | `/api/v1/customers/{id}` | Actualizar disponibilidad manualmente. |
| `DELETE` | `/api/v1/customers/{id}` | Eliminar manualmente. |

### Gestión de Reservas
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/v1/reservations` | Listar todas las reservas con detalle de habitación. |
| `POST` | `/api/v1/reservations` | Crear reserva (Valida fechas y disponibilidad). |
| `GET` | `/api/v1/reservations/generateReport` | **Generar reporte PDF con JasperReports.** |

---

## ⚙️ Reglas de Negocio Implementadas

1.  **Validación Cronológica**: No se permiten reservas donde la fecha de salida sea previa a la de entrada.
2.  **Validación de Disponibilidad**: Antes de persistir una reserva, el sistema consulta que la habitación no tenga otras reservas confirmadas en el mismo rango de fechas.
3.  **Sincronización de Estado**: Al crear una reserva exitosa, la habitación vinculada cambia automáticamente su estado `available` a `false`.
4.  **Reportes Profesionales**: Los comprobantes PDF incluyen logotipos, detalles del cliente y desglose de precios mediante plantillas `.jrxml`.

---

## 🛠️ Configuración e Instalación

1.  **Clonar repositorio**:
    ```bash
    git clone [https://github.com/tu-usuario/hotel-api.git](https://github.com/tu-usuario/hotel-api.git)
    ```

2.  **Configurar base de datos** en `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/hotel_db
    spring.datasource.username=postgres
    spring.datasource.password=tu_password
    
    # JWT Secret
    jwt.secret=tu_clave_secreta_super_segura_de_64_caracteres
    ```

3.  **JasperReports**:
    Asegúrate de que los archivos `.jasper` o `.jrxml` se encuentren en la ruta `src/main/resources/reports/`.

4.  **Ejecutar**:
    ```bash
    mvn spring-boot:run
    ```
