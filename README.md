# tech-jimmy-system
Prototipo funcional en **Java 17 + Spring Boot 3** para la gestión de inventario, clientes, tickets de reparación y ventas de mostrador.  ## Requisitos previos - JDK 17 o superior - Maven 3.8+
## Configuración de base de datos
Por defecto el proyecto utiliza **H2 en memoria** (`jdbc:h2:mem:techjimmydb`) configurado en `src/main/resources/application.properties` y carga datos iniciales desde `src/main/resources/data.sql`. No requiere instalación previa de base de datos para pruebas locales.

## Cómo ejecutar
```bash
mvn spring-boot:run
