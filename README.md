# Comparacion_REST_SOAP

Proyecto para el curso de Desarrollo Web por Luis Morán que implementa la misma operación (**sumar dos números**) mediante dos estilos de servicios distintos: **SOAP** (Contract First) y **REST**, con el objetivo de comparar ambos enfoques de forma práctica.

## 📁 Estructura del proyecto

```
Comparacion_REST_SOAP/
│
├── README.md
├── .gitignore
├── pom.xml                  # POM padre (agrupa los módulos)
├── soap/                    # Módulo SOAP (Spring-WS, Contract First)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/soap/
│       │   ├── SoapApplication.java
│       │   ├── SumaEndpoint.java
│       │   └── WebServiceConfig.java
│       └── resources/
│           ├── suma.xsd
│           └── application.properties
├── rest/                    # Módulo REST (Spring Boot Web)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/rest/
│       │   ├── RestApplication.java
│       │   ├── SumaController.java
│       │   ├── SumaRequest.java
│       │   └── SumaResponse.java
│       └── resources/
│           └── application.properties
└── wsdl/
    └── servicio.wsdl        # Copia del WSDL generado por el servicio SOAP
```

## ⚙️ Requisitos del proyecto

| Requisito | Versión |
|---|---|
| Java (JDK) | **17** |
| Build tool | **Maven** |
| Spring Boot | 3.2.5 |
| IDE recomendado | IntelliJ IDEA |

Herramientas usadas para pruebas:
- **Postman** (para el servicio REST)
- **SoapUI** (para el servicio SOAP)

## ▶️ Instrucciones para ejecutar el proyecto

Cada módulo es una aplicación Spring Boot independiente y debe ejecutarse por separado.

### Ejecutar el servicio SOAP (puerto 8080)

Desde IntelliJ:
1. Abrir `soap/src/main/java/com/example/soap/SoapApplication.java`
2. Ejecutar el método `main` (botón ▶️)

Desde terminal (en la raíz del proyecto):
```bash
mvn -pl soap spring-boot:run
```

### Ejecutar el servicio REST (puerto 8081)

Desde IntelliJ:
1. Abrir `rest/src/main/java/com/example/rest/RestApplication.java`
2. Ejecutar el método `main` (botón ▶️)

Desde terminal (en la raíz del proyecto):
```bash
mvn -pl rest spring-boot:run
```

Ambos servicios pueden correr **simultáneamente**, ya que usan puertos distintos.

## 🧼 Cómo consumir el servicio SOAP

### 1. Contrato / WSDL

El WSDL se genera dinámicamente a partir de `soap/src/main/resources/suma.xsd`. Con el servicio corriendo, se puede consultar en:

```
http://localhost:8080/ws/suma.wsdl
```

También se incluye una copia estática en [`wsdl/servicio.wsdl`](./wsdl/servicio.wsdl).

### 2. Endpoint del servicio

```
http://localhost:8080/ws
```

### 3. Ejemplo de petición (SOAP Envelope)

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                   xmlns:sum="http://example.com/soap/suma">
   <soapenv:Header/>
   <soapenv:Body>
      <sum:sumaRequest>
         <sum:a>10</sum:a>
         <sum:b>15</sum:b>
      </sum:sumaRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### 4. Ejemplo de respuesta

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Body>
      <ns2:sumaResponse xmlns:ns2="http://example.com/soap/suma">
         <ns2:resultado>25</ns2:resultado>
      </ns2:sumaResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

### 5. Cómo probarlo

**Con SoapUI:**
1. File → New SOAP Project
2. En "Initial WSDL" pegar: `http://localhost:8080/ws/suma.wsdl`
3. Expandir el árbol generado → `suma` → `Request 1`
4. Completar los valores de `a` y `b` y ejecutar (▶️)

## 🌐 Cómo consumir el servicio REST

### Endpoint

```
POST http://localhost:8081/api/suma
Content-Type: application/json
```

### Cuerpo de la petición (JSON)

```json
{
  "a": 50,
  "b": 3
}
```

### Respuesta (JSON, HTTP 200 OK)

```json
{
  "resultado": 53
}
```

### Cómo probarlo

**Con Postman:**
1. Método: `POST`
2. URL: `http://localhost:8081/api/suma`
3. Body → raw → JSON → pegar el JSON de ejemplo
4. Send


## 📸 Capturas de las pruebas

 — Prueba del servicio SOAP con SoapUI (resultado 25)

<img width="1432" height="355" alt="Prueba SOAP" src="https://github.com/user-attachments/assets/50738625-25b2-4250-9f11-894cb01402ac" />


 — Prueba del servicio REST con Postman (200 OK, resultado 53)
 <img width="1365" height="657" alt="prueba REST}" src="https://github.com/user-attachments/assets/614876f9-2064-4286-81d1-551adf9347c3" />


## 🔍 Comparación entre SOAP y REST

Al implementar la misma operación con ambos enfoques, la diferencia más notable fue el punto de partida: en SOAP primero se definió un contrato rígido (`suma.xsd`), del cual nacieron automáticamente tanto las clases Java como el WSDL, mientras que en REST se escribió directamente el código y el "contrato" quedó implícito en el JSON enviado y recibido. REST resultó considerablemente más sencillo y rápido de implementar, ya que no requiere generación de clases a partir de un esquema ni configuración de bindings o mensajes; con una sola clase controladora fue suficiente. SOAP, en cambio, exigió más pasos (XSD, generación JAXB, Endpoint, configuración del WSDL), pero a cambio ofrece un contrato explícito y verificable, útil en integraciones empresariales donde la validación estricta de datos y la interoperabilidad formal son prioritarias (por ejemplo, sistemas bancarios o gubernamentales). REST, por su simplicidad, ligereza y uso natural de HTTP/JSON, es preferible para APIs públicas, aplicaciones web y móviles, y microservicios donde se prioriza la velocidad de desarrollo y el bajo acoplamiento sobre la rigidez del contrato.

## 🧩 Sobre el uso de IA

Este proyecto fue desarrollado con apoyo de IA (Claude, Anthropic) como guía paso a paso durante la implementación y para la generación de esta documentación. La lógica, configuración y pruebas fueron ejecutadas y verificadas manualmente por el desarrollador.
