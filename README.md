# GameLluviaMenu2024

## Pantallas y entorno de desarrollo

Para ejecutar el wrapper actual, configurar `JAVA_HOME` con JDK 17 o superior:
`construo` 1.4.1 requiere esa JVM. El destino del código se mantiene en Java 11.
Guardar los fuentes en UTF-8.

- Compilar: `bash gradlew :core:compileJava :lwjgl3:compileJava`.
- Ejecutar: `bash gradlew lwjgl3:run` (requiere entorno gráfico y audio).

### Contrato para adaptar las pantallas

La referencia implementada es `MainMenuScreen`. La clase base se importa desde
`puppy.code.pantallas.PantallaBase`.

1. Extender `PantallaBase` y llamar `super(game)` en el constructor.
2. Implementar `dibujar(SpriteBatch batch, BitmapFont font)` para el contenido.
   No llamar `begin()` ni `end()` y no cambiar de pantalla dentro de este método.
3. Usar `procesarEntrada()` para navegar mediante `getGame().setScreen(...)`.
   Se ejecuta cuando el batch ya está cerrado.
4. Si hay lógica por frame, sobrescribir `actualizar(float delta)`. Se ejecuta antes
   del dibujo; si cambia la pantalla, la base cancela el dibujo de la anterior.
5. Para otro fondo, sobrescribir `limpiarPantalla()`. La cámara está disponible
   mediante `getCamera()` y mantiene coordenadas lógicas de 800 por 480.
6. No sobrescribir `render()`: es final. No crear otro batch ni otra fuente.
   `dispose()` debe liberar únicamente recursos propios; batch y fuente pertenecen
   a `GameLluviaMenu`. `hide()` no destruye automáticamente la pantalla.

PausaScreen, GameOverScreen y GameScreen todavía no están migradas. La variante
`dibujar(SpriteBatch, float)` del borrador del informe no es este contrato.

### Verificación del 19 de octubre de 2026

- Compilación de core y lwjgl3 correcta con Gradle y JDK 17.
- Prueba temporal con el backend LWJGL3: dibujado del menú, entrada simulada a
  través de `procesarEntrada()`, transición a GameScreen y dibujado de la partida.
- Verificado que el batch compartido queda cerrado tras la transición y que el
  menú vuelve a dibujarse usando los recursos compartidos tras cerrar la partida.
- Capturas del framebuffer revisadas. La vuelta al menú fue provocada por la
  prueba; no es una nueva opción del juego. No se validó una partida completa,
  reinicio tras derrota ni pausa/reanudación. Pendientes para la integración final.


A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
