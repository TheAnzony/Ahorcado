# 🎯 Ahorcado

Juego clásico del ahorcado para Android, desarrollado en **Kotlin** como práctica del ciclo de **Desarrollo de Aplicaciones Multiplataforma (DAM)**.

El jugador tiene que adivinar una palabra de 5 letras introduciendo letras una a una. Cada fallo añade una parte al dibujo del ahorcado. ¡Acierta la palabra antes de agotar los 6 intentos!

## 🕹️ Cómo se juega

1. Al empezar se elige una palabra al azar de 5 letras.
2. Escribe una letra en el campo **Nueva letra** y pulsa **Enviar**.
3. Si la letra está en la palabra, aparece en todas las casillas donde se encuentra.
4. Si no está, se añade a la lista de **Errores**, se resta un intento y el dibujo avanza.
5. Ganas si completas la palabra; pierdes si llegas a 6 errores.
6. Pulsa **Reset** en cualquier momento para empezar una partida nueva.

## ✨ Características

- Palabra aleatoria en cada partida.
- Contador de intentos restantes y lista de letras falladas.
- Dibujo del ahorcado en 7 estados, más una imagen de victoria.
- Imágenes creadas como **vector drawables**, nítidas en cualquier pantalla.
- Interfaz adaptable gracias a **ConstraintLayout** con *guidelines* en porcentaje.
- Textos en `strings.xml` y estilos reutilizables para botones y casillas.

## 🛠️ Tecnologías

| | |
|---|---|
| Lenguaje | Kotlin |
| IDE | Android Studio |
| Layout | XML + ConstraintLayout (guidelines y chains) |
| Activity | `AppCompatActivity` |
| Acceso a vistas | View Binding |
| SDK mínimo | Android 7.0 (API 24) |
| SDK objetivo | API 37 |

## 📁 Estructura principal

```
app/src/main/
├── java/com/example/ahorcado/
│   └── MainActivity.kt          # Lógica del juego
└── res/
    ├── drawable/
    │   ├── ahorcado_0.xml … ahorcado_6.xml   # Estados del dibujo
    │   └── ahorcado_ganado.xml               # Imagen de victoria
    ├── layout/
    │   └── activity_main.xml    # Interfaz
    └── values/
        ├── strings.xml          # Textos de la app
        ├── colors.xml
        └── themes.xml           # Tema y estilos
```

## 🚀 Cómo ejecutarlo

1. Clona el repositorio:
   ```bash
   git clone https://github.com/TheAnzony/Ahorcado.git
   ```
2. Ábrelo con **Android Studio** (*File → Open* y selecciona la carpeta del proyecto).
3. Espera a que termine la sincronización de Gradle.
4. Ejecútalo en un emulador o en un móvil con Android 7.0 o superior pulsando **Run ▶**.

## 👤 Autor

**TheAnzony** — [github.com/TheAnzony](https://github.com/TheAnzony)

Proyecto realizado con fines educativos.
