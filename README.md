# java-shop 🛍️
 TP 1: aplicación consola de Java - Tienda de productos comestibles, realizar compras y ventas de productos.

## ℹ️ Consideraciones o criterios utilizados

### 🚀 Arquitectura de 3 capas:
```
* UI: Objetos para mostrar información.
* Domain: Objetos de dominio.
* Service: Lógica de negocio.
```

### Uso de Wrappers en lugar de datos primitivos:
Opté por utilizar Wrappers para los atributos de los objetos en lugar de datos primitivos, con el objetivo de garantizar la seguridad ante valores nulos (null safety), aunque esto conlleva un sacrificio en el rendimiento.

### Manejo de excepciones:
Decidí evitar el uso de excepciones, enfocándome en resolver los posibles errores mediante validaciones. Utilicé excepciones únicamente para manejar factores externos.

### Capa DTO (Data Transfer Object):
Aunque inicialmente tenía planeado implementar una capa DTO para transferir datos entre capas sin interactuar directamente con los objetos de dominio, finalmente decidí simplificar la solución y posponer esta implementación. Tenía la intención de usar un mapper para realizar el mapeo entre objetos de dominio y DTOs.
Interacción con la aplicación:

### Interacción con la aplicación
Se realiza a través de un menú, donde se eligen los casos de prueba, los cuales se centran principalmente en las operaciones de la tienda: comprar y vender.

### Lógica de Servicio
La lógica de servicio se implementó únicamente para la tienda y no para los productos individuales.

### Aclaraciones
* Hay un archivo imagen con el diagrama de clase, se encuentra en la raíz del proyecto
* Está el archivo tp1.pdf, en donde se específica lo que debe realizar la aplicación. Se encuentra en la raíz del proyecto. 