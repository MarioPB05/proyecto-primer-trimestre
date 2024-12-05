![Safe Paws Logo](/src/main/resources/Banner%20SP%20Back.png)

# Safe Paws (Backend)

¡Bienvenido al backend de Safe Paws! 🐾

SafePaws es una aplicación web que te permite encontrar a tu nuevo mejor amigo. En SafePaws podrás encontrar perros en adopción, conocer sus características y solicitar su adopción.

## Características
- **Catálogo de animales**: Explora la lista de mascotas en adopción.
- **Mapa interactivo**: Visualiza la ubicación de las mascotas en adopcion en un mapa en tiempo real.
- **Sube tus propias mascotas**: Si tienes una mascota que deseas dar en adopción, puedes subirla a la plataforma.
- **Solicita la adopción de una mascota**: Si encuentras una mascota que te interesa, puedes solicitar su adopción.

## Instruciones de uso y ejecución local

### Requisitos previos
Asegurate de tener instalado:
- PostgreSQL
- Cuenta de cloudinary
- Java 21

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

4. Inicia el backend
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

