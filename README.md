![Safe Paws Logo](/src/main/resources/Banner%20SP%20Back.png)

# Safe Paws (Backend)

¡Bienvenido al backend de Safe Paws! 🐾

SafePaws es una aplicación web que te permite encontrar a tu nuevo mejor amigo. En SafePaws podrás encontrar perros en adopción, conocer sus características y solicitar su adopción.

## Características
- **Catálogo de animales**: Explora la lista de mascotas en adopción.
- **Mapa interactivo**: Visualiza la ubicación de las mascotas en adopción en un mapa en tiempo real.
- **Sube tus propias mascotas**: Si tienes una mascota que deseas dar en adopción, puedes subirla a la plataforma.
- **Solicita la adopción de una mascota**: Si encuentras una mascota que te interesa, puedes solicitar su adopción.

## Instruciones de uso y ejecución local

### Requisitos previos
Asegurate de tener instalado:
- PostgreSQL
- Cuenta de cloudinary
- SDK Java 21

### Pasos a seguir
1. Clona el repositorio
```bash
git clone https://github.com/MarioPB05/safe-paws-backend.git
```

2. Instala las dependencias
```bash
mvn install
```

3. Configura las variables de entorno

| Variable de Entorno | Descripción                                                                  |
|---------------------|------------------------------------------------------------------------------|
| DB_USERNAME         | Nombre de usuario de la base de datos                                        |
| DB_URL              | URL de la base de datos                                                      |                   
| DB_PASSWORD         | Contraseña de la base de datos                                               |                   
| API_KEY             | Hash aleatorio                                                               |                   
| CLOUD_NAME          | Nombre del cloud en Cloudinary utilizado para almacenar y gestionar imágenes |                   
| CLOUD_API_KEY       | Clave de la API de Cloudinary                                                |                   
| CLOUD_API_SECRET    | Secreto de la API de Cloudinary                                              |                

4. Creación de la base de datos
```postgresql
create table if not exists question
(
    id                serial primary key,
    question          varchar not null,
    type              integer not null,
    required          boolean not null,
    required_question integer constraint fk_question references question,
    deleted           boolean not null,
    placeholder       varchar,
    category          integer
);

create table if not exists address
(
    id           serial primary key,
    coordinate_x double precision,
    coordinate_y double precision,
    road         varchar,
    neighborhood varchar,
    village      varchar,
    province     varchar,
    state        varchar,
    postcode     varchar,
    country      varchar,
    country_code varchar
);

create table if not exists client
(
    id                serial primary key,
    name              varchar            not null,
    surname           varchar            not null,
    birthdate         date               not null,
    dni               varchar            not null,
    registration_date date default now(),
    address_id        integer                     constraint fk_client_address references address,
    photo             varchar
);

create table if not exists post
(
    id            serial primary key,
    client_id     integer                 not null constraint post_client_id_foreign references client,
    name          varchar                 not null,
    description   varchar                 not null,
    photo         varchar                 not null,
    status        integer                 not null,
    type          integer                 not null,
    urgent        boolean,
    creation_date timestamp default now() not null,
    deleted       boolean   default false not null,
    address_id    integer                          constraint fk_client_address references address,
    code          varchar
);

create table if not exists request
(
    id            serial primary key,
    message       varchar(255),
    creation_date timestamp default now() not null,
    status        integer   default 0,
    client_id     integer                          constraint fk_client_id references client,
    post_id       integer                          constraint fk_post_id references post,
    deleted       boolean                 not null,
    code          varchar                          constraint request_pk unique
);

create table if not exists request_answer
(
    id          serial primary key,
    request_id  integer constraint fk_request_id references request,
    question_id integer constraint fk_question_id references question,
    answer      varchar(255)
);

create table if not exists "user"
(
    id        serial primary key,
    username  varchar not null,
    password  varchar not null,
    email     varchar not null,
    rol       varchar not null,
    ban       boolean,
    client_id integer not null constraint user_client_id_foreign references client
);

create table if not exists chat_room
(
    id              serial primary key,
    code            varchar(20) not null,
    user_owner_id   integer     not null,
    user_adopter_id integer     not null,
    created_at      timestamp default CURRENT_TIMESTAMP,
    post_id         integer                             constraint fk_chat_room_post_id references post
);

create table if not exists chat_message
(
    id           serial primary key,
    chat_room_id integer not null references chat_room,
    user_id      integer not null,
    message      text    not null,
    created_at   timestamp default CURRENT_TIMESTAMP
);

create table if not exists adoption_contract
(
    id                     serial primary key,
    post_id                integer not null references post,
    document               text    not null,
    document_sha256        text,
    owner_signature        text,
    owner_signature_date   timestamp,
    adopter_signature      text,
    adopter_signature_date timestamp
);
```

5. Inicia el backend
```bash
mvn spring-boot:run
```

---

## Estrucutra del proyecto

### Componentes
- **Controller**: Clases que se encargan de recibir las peticiones HTTP y devolver una respuesta.
- **Service**: Clases que contienen la lógica de negocio de la aplicación.
- **Repository**: Clases que se encargan de realizar las operaciones de lectura y escritura en la base de datos.
- **Model**: Clases que representan las entidades de la base de datos.
- **DTO**: Clases que representan los objetos de transferencia de datos.

---

## Autores
- [@marioperdiguero](https://github.com/marioperdiguero)
- [@mricofer](https://github.com/mricofer)
- [@raulcruzadodelgado1](https://github.com/raulcruzadodelgado1)
- [@jzayassanroman](https://github.com/jzayassanroman)

