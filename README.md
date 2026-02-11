# 📱 Sistema de Gestión de Incidencias
## Moisés Ortiz y Adán Gavira

Aplicación Android desarrollada en **Java** que permite a los usuarios registrar, gestionar y dar seguimiento a incidencias mediante autenticación y sistema de chat en tiempo real utilizando Firebase.

---

## 🚀 Características

- 🔐 Registro e inicio de sesión de usuarios
- 🔄 Restablecimiento de contraseña
- 📍 Selección de ubicación para incidencias
- 📝 Creación de incidencias
- ✏️ Edición de incidencias
- 📋 Listado y visualización de detalles
- 💬 Chat en tiempo real
- 🔥 Integración con Firebase Firestore
- 💾 Base de datos local SQLite

---

## 🏗️ Estructura del Proyecto

### 📂 Activities

- `LoginActivity` → Inicio de sesión  
- `RegistroActivity` → Registro de usuario  
- `RestablecerContrasenaActivity` → Recuperación de contraseña  
- `IncidenciasActivity` → Listado de incidencias  
- `CrearIncidenciasActivity` → Crear nueva incidencia  
- `EditarIncidenciasActivity` → Editar incidencia  
- `InformacionActivity` → Ver detalles  
- `SeleccionarUbicacionActivity` → Seleccionar ubicación  
- `ChatActivity` → Chat en tiempo real  

---

### 📦 Adapters

- `IncidenciaAdapter` → Adaptador para RecyclerView de incidencias  
- `ChatAdapter` → Adaptador para mensajes del chat  

---

### 🧩 Modelos

- `Incidencia` → Modelo de datos de incidencias  
- `MensajeChat` → Modelo de mensajes  

---

### 🛠️ Helpers

- `DBHelper` → Gestión de SQLite  
- `FirestoreHelper` → Conexión con Firebase Firestore  
- `UiUtils` → Utilidades de interfaz  

---

## 🛠️ Tecnologías Utilizadas

- Java
- Android SDK
- Firebase Authentication
- Firebase Firestore
- SQLite
- RecyclerView
- Material Design

---

## 🔥 Funcionalidades Técnicas

### ✔️ Autenticación
- Registro de usuario
- Inicio de sesión
- Recuperación de contraseña

### ✔️ Gestión de Incidencias
- Crear incidencias
- Editar incidencias
- Listar incidencias
- Asociar ubicación

### ✔️ Chat en Tiempo Real
- Envío de mensajes
- Visualización dinámica con RecyclerView
- Sincronización con Firestore

---

## 📲 Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
```
2. Abrir el proyecto en Android Studio
3. Configurar Firebase:
 - Añadir el archivo google-services.json
 - Activar Authentication y Firestore
4. Ejecutar en emulador o dispositivo físico

## 🧠 Flujo de Usuario

1. Registro o inicio de sesión
2. Visualización de incidencias
3. Creación o edición de incidencia
4. Selección de ubicación
5. Comunicación mediante chat

## 🔒 Seguridad
- Autenticación mediante Firebase
- Validación de formularios
- Gestión segura de datos

## Aclaraciones
- Si la rama principal no funciona, utilizar rama `Key`
