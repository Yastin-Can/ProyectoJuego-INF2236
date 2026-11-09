# Recolecta Gotas — Grupo 27

Proyecto de Programación Avanzada INF2236 desarrollado con Java y LibGDX.
El jugador mueve un tarro para recoger gotas buenas y evitar las dañinas.
Las gotas buenas suman 10 puntos, las malas restan una vida y las doradas
recuperan una vida. La partida comienza con tres vidas. Al alcanzar 100 puntos,
el nivel cambia de Llovizna a Tormenta, con mayor frecuencia de gotas y caída
en zigzag.

## Requisitos

- JDK 11. Las bases especifican Oracle JDK 11; la validación local del
  9 de noviembre de 2026 se realizó con OpenJDK 11.0.32.1 en Linux.
- Entorno gráfico compatible con OpenGL y dispositivo de audio.
- Internet en la primera compilación para descargar Gradle y las dependencias.

Configurar `JAVA_HOME` con la carpeta del JDK 11 y seleccionar esa misma JVM
para Gradle en el IDE. Comprobar `java -version` y `bash gradlew --version`:
ambos deben mostrar Java 11. En Windows, usar `gradlew.bat` en lugar de
`bash gradlew` en los comandos siguientes.

Todos los módulos se compilan con `--release 11` y codificación UTF-8.
La compilación exige que Gradle se ejecute con JDK 11 y muestra un error si se
selecciona otra versión. La entrega contiene el proyecto fuente y el JAR con
sus dependencias y recursos; no incluye un runtime de Java.

## Compilar y ejecutar

Desde la raíz del proyecto:

```sh
bash gradlew clean :core:verificarLogica :lwjgl3:jar
bash gradlew :lwjgl3:run
```

El JAR incluye las dependencias, las imágenes y el audio. Puede copiarse a otra
carpeta y ejecutarse con Java 11, sin copiar la carpeta `assets`:

```sh
java -jar lwjgl3/build/libs/GameLluviaMenu2024-1.0.0.jar
```

Si se mueve el JAR, ajustar su ruta en el comando. En macOS, iniciar con
`java -XstartOnFirstThread -jar ruta/al/archivo.jar`.

## Controles

| Acción | Control |
|---|---|
| Comenzar | Clic en el menú |
| Mover el tarro | Flechas izquierda y derecha |
| Pausar | Escape |
| Reanudar | Escape o clic |
| Reiniciar tras perder | Enter o clic |

## Verificación

```sh
bash gradlew :core:verificarLogica
bash gradlew :lwjgl3:verificarPantallas
```

La primera comprueba estrategias de caída independientes de la tasa de cuadros,
construcción de niveles, efectos de las gotas y encapsulamiento del área del
tarro. La segunda necesita pantalla/OpenGL: abre una ventana breve, desactiva
el audio y comprueba menú, partida, pausa, reanudación, cambio de nivel,
derrota, reinicio, reutilización de texturas y cierre desde pausa.

El 9 de noviembre de 2026 se completaron ambas verificaciones y la generación
del JAR desde una compilación limpia con OpenJDK 11. Se comprobó además el JAR
fuera del proyecto, sin una carpeta `assets` externa, con carga y reproducción
de música y efectos habilitadas. Estas comprobaciones no sustituyen la prueba
manual de una partida completa ni la comprobación en el equipo de entrega.

## Organización del código

- `core`: lógica, entidades, pantallas, recursos, movimientos y niveles.
- `lwjgl3`: aplicación de escritorio, recursos de arranque y empaquetado.
- `assets`: imágenes y audio del juego.

`Gota` define los efectos polimórficos de los tres tipos de gotas y `Dibujable`
es el contrato de dibujo. `GestorRecursos` centraliza la carga y liberación de
recursos mediante Singleton. Los movimientos usan Strategy; los niveles se
construyen mediante Builder y `DirectorNivel`; las pantallas comparten el ciclo
de Template Method en `PantallaBase`.

Las pantallas implementan `dibujar(SpriteBatch, BitmapFont)` sin abrir/cerrar el
batch y usan `procesarEntrada()` para navegar. La lógica se actualiza en
`actualizar(float delta)`. El batch y la fuente pertenecen a `GameLluviaMenu`;
las pantallas solo liberan sus recursos propios. Las coordenadas lógicas son
800 × 480.
