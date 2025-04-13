# Car Center API

**Car Center API** es una aplicación backend desarrollada con Spring Boot para gestionar un taller de reparación de vehículos. Permite administrar clientes, vehículos, mecánicos, mantenimientos, repuestos, servicios de mano de obra, fotos de estado y facturación automática con descuentos e IVA. Además, envía notificaciones SMS al cliente cuando se agregan repuestos o servicios, y alerta si se supera el presupuesto.

---

## 📋 Características principales

- **Gestión de Clientes**: CRUD completo con validaciones (email, campos obligatorios).  
- **Gestión de Vehículos**: CRUD básico, cada vehículo asociado a un cliente.  
- **Gestión de Mecánicos**: CRUD con validaciones y endpoint `/api/mechanics/top-ten` para asignación automática.  
- **Gestión de Mantenimientos**: CRUD, asignación de mecánico, estados (`PENDING`, `IN_PROGRESS`, `COMPLETED`).  
- **Fotos de Mantenimiento**: Subida y listado de imágenes asociadas a un mantenimiento.  
- **Servicios de Mano de Obra**:   relación con mantenimientos (tiempo estimado, valor unitario).  
- **Repuestos**: relación con mantenimientos (cantidad, precio unitario).  
- **Facturación Automática**: Genera factura para mantenimientos `COMPLETED`, aplica 50% de descuento en mano de obra si el total de repuestos supera 3 000 000 COP y añade 19% de IVA.  
- **Notificaciones SMS**: Envía mensajes al cliente cuando se agregan repuestos o servicios y alerta de presupuesto excedido.

---

## 🛠️ Tecnologías y dependencias

- **Java 21**  
- **Spring Boot 3.4.4** (Spring Data JPA, Spring Web, Spring Validation)  
- **Hibernate**  
- **MySQL / PostgreSQL** (configurable en `application.properties`)  
- **Twilio Java SDK** para envío de SMS  
- **Lombok** para generación de boilerplate  
- **Maven** como sistema de construcción  

---

## ⚙️ Requisitos previos

- Java 21 instalado  
- Maven
- Base de datos relacional (MySQL, PostgreSQL, etc.)  
- Cuenta de Twilio con credenciales  número habilitado para SMS  

---

## 🚀 Instalación y puesta en marcha

1. **Clona el repositorio**  
   ```bash
   https://github.com/oscarfiscal/car-center-api/tree/master

2. **Clona el repositorio**  
   ```bash
   export TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   export TWILIO_AUTH_TOKEN=your_auth_token
   export TWILIO_FROM_NUMBER=+57XXXXXXXXXXX
    
3. **Construye la aplicación**  
 Con Maven:
 ```bash
  ./mvnw clean package
  ```
  

4. **Ejecuta la aplicación**
 Con Maven:
 ```bash
./mvnw spring-boot:run
