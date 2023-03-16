# Reto Rabbit usando arquitectura limpia (plugin Bancolombia)

En el presente proyecto se busca crear un backend en Java el cual pueda dar solución al siguiente problema:

El doctor Ramiro Fernandez nos contactó porque quiere adquirir un sistema para la administración de las historias médicas y el manejo de citas de sus pacientes. 
Por ello nosotros tenemos la tarea de realizar un sistema:

- Que le permita al doctor establecer un horario de disponibilidad por día de la semana. ej (lunes 8am a 5pm, martes 9am a 3 pm ... ) 

- Que le permita a el doctor agregar un paciente nuevo 

- Que le permita al doctor modificar la información personal del paciente de ser necesario 

- Agregar revisión de cita médica.

- Que le permita a los pacientes que están inscritos  agendar citas pero sólo en los horarios disponibles, es decir deben ir saliendo de disponibilidad los horarios que ya han sido tomados.(Hay que definir también cuanto toma cada cita como un estándar para saber qué franja horaria se va ocupando a medida que se van agendando citas)

- Que le permita al doctor cancelar las citas que se van agendando.(Si el doctor cancela la cita el horario debe volver a pasar a estar disponible)

- Que le permita al doctor conocer el estado de su agenda de la semana actual.

- Que le permita al doctor listar todo el historial médico de un  paciente con todas las interacciones que han tenido.

- Por último que le permita eliminar pacientes.


## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**

# Modelamiento usando DDD

![Análisis](https://i.ibb.co/0Y6k19d/analisys.png)

![Modelamiento](https://i.ibb.co/bv9jHDc/modelamiento.png)

![Eventos](https://i.ibb.co/VWSP2tw/eventos.png)

# Entradas y respuestas de endpoints
- Crear paciente:\
Método: POST\
Endpoint: /api/create/patient\
Body:
{
    "patientId": "idPatient",\
    "personalData": "danielperezvitola2@gmail.com",\
    "clinicHistory": "clinicHistory",\
    "reviewId": "idReview",\
    "annotation": "anotation",\
    "available": "true"\
}\
Respuesta: [\
    {\
        "when": "2023-03-16T16:39:19.158088Z",\
        "uuid": "34d339a6-36e6-4612-be0b-c90a030fd79b",\
        "type": "perez.daniel.patientadded",\
        "id": "idPatient",\
        "identity": "danielperezvitola2@gmail.com",\
        "clinicHistory": "clinicHistory",\
        "reviewId": "idReview",\
        "annotation": "anotation",\
        "available": "true",\
        "aggregateName": "patient"\
    }
]

- Actualizar datos personales:\
Método: POST\
Endpoint: /api/update/personalData\
Body:
{
    "idPatient":"idPatient",\
    "personalData":"danielperezvitola@gmail.com"\
}\
Respuesta: [\
    {\
          "when": "2023-03-16T16:42:31.991723100Z",\
        "uuid": "fdd02a03-ee00-47e5-a173-88003498c87d",\
        "type": "perez.daniel.personaldataupdated",\
        "id": "idPatient",\
        "personalData": "danielperezvitola@gmail.com",\
        "aggregateName": "patient"

    }
]

- Agregar semana:\
Método: POST\
Endpoint: /api/add/week\
Body:\
{\
     "weekId": "idWeek",\
    "citaId": "citaId",\
    "information": "information h",\
    "citationState": "citationState",\
    "patientId": "patientId",\
    "availability": "[2022-03-14T08:00,2022-03-14T09:00]",\
    "date":"2022-03-14;2022-03-20"\
}\
Respuesta: [\
    {\
         "when": "2023-03-16T16:44:48.755547100Z",\
        "uuid": "0bb19e07-0095-45b8-9136-619d09f9bd43",\
        "type": "perez.daniel.weekadded",\
        "id": "idWeek",\
        "citationId": "citaId",\
        "information": "information h",\
        "citationState": "citationState",\
        "patientId": "patientId",\
        "availability": "[2022-03-14T08:00, 2022-03-14T09:00]",\
        "date": "2022-03-14;2022-03-20",\
        "aggregateName": "week"


    }
]

- Agregar cita:
Método: POST\
Endpoint: /api/add/week\
Body:\
{\
    "weekId":"idWeek",\
    "patientId": "idPatient",\
    "citaId":"citaId",\
    "information":"2022-03-14 09:00",\
    "citationState":"true"\
}\
Respuesta: [\
    {\
        "when": "2023-03-16T16:46:45.930883100Z",\
        "uuid": "bc548493-c602-483e-95a5-79c80a83884d",\
        "type": "perez.daniel.citationadded",\
        "id": "citaId",\
        "information": "2022-03-14 09:00",\
        "citationState": "true",\
        "patientId": "idPatient",\
        "weekId": "idWeek",\
        "aggregateName": "week"\
    },\
    {
         "when": "2023-03-16T16:46:45.930883100Z",\
        "uuid": "543f7477-c72f-441d-9c88-68d6deb95c9f",\
        "type": "perez.daniel.availabilityupdated",\
        "idWeek": "idWeek",\
        "availability": "[2022-03-14T08:00]",\
        "aggregateName": "week"\
    }\
]

- Agregar revisión:
Método: POST\
Endpoint: /api/add/review\
Body:
{
    "patientId": "idPatient",\
    "reviewId": "ewviewId",\
    "annotation": "anotation"
}\
Respuesta: [\
    {\
        "when": "2023-03-16T16:51:07.158103900Z",\
        "uuid": "aff62947-962e-4d75-8af0-e0a08619363b",\
        "type": "perez.daniel.reviewadded",\
        "idReview": "ewviewId",\
        "annotation": "anotation",\
        "patientId": "idPatient",\
        "aggregateName": "patient"\
    }
]

- Obtener ocupación de la semana:\
Método: GET\
Endpoint: /api/get/citation\
Body:
{
"idWeek":"idWeek"\
}\
Respuesta: [\
    {\
        "when": "2023-03-16T16:54:14.263647600Z",\
        "uuid": "42d9671c-c46b-4226-8e0a-0d3c0cdcfda1",\
        "type": "perez.daniel.citationadded",\
        "id": "citaId",\
        "information": "2022-03-14 09:00",\
        "citationState": "true",\
        "patientId": "idPatient",\
        "weekId": "idWeek",\
        "aggregateName": "default"\
    }
]

- Cancelar una cita:\
Método: POST\
Endpoint: /api/cancel/citation\
Body:
{
    "weekId":"idWeek",\
    "citaId":"citaId",\
    "citationState":"false"\
}\
Respuesta: [\
    {\
        "when": "2023-03-16T16:57:10.527438600Z",\
        "uuid": "9b9c8cc3-264a-4e8d-843b-ea1421482cf1",\
        "type": "perez.daniel.citationcanceled",\
        "weekId": "idWeek",\
        "citaId": "citaId",\
        "citationState": "false",\
        "aggregateName": "week"\
    }
]

- Eliminar paciente:\
Método: DELETE\
Endpoint: /api/cancel/citation\
Body:
{
    "idPatient":"idPatient",\
    "available":"false"\
}\
Respuesta: [\
    {\
        "when": "2023-03-16T17:00:00.358531300Z",\
        "uuid": "ba39215a-f172-45c0-b412-19edfa150498",\
        "type": "perez.daniel.patientdeleted",\
        "idPatient": "idPatient",\
        "available": "false",\
        "aggregateName": "patient"\
    }
]

- Obtener historia clínica:\
Método: GET\
Endpoint: /api/cancel/citation\
Body:
{
"idPatient":"idPatient"\
}\
Respuesta: [\
    {\
        "when": "2023-03-16T16:51:07.158103900Z",\
        "uuid": "aff62947-962e-4d75-8af0-e0a08619363b",\
        "type": "perez.daniel.reviewadded",\
        "idReview": "ewviewId",\
        "annotation": "anotation",\
        "patientId": "idPatient",\
        "aggregateName": "patient"\
    }
]




