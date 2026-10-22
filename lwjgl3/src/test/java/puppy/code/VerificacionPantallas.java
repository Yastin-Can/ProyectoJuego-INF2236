package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import puppy.code.recursos.GestorRecursos;

/** Humo grafico: inicia y cierra solo; no reemplaza una partida manual completa. */
public class VerificacionPantallas extends GameLluviaMenu {
    private int paso;
    private GameScreen partida;
    private Object textura;
    private static Throwable fallo;

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    private static Object campo(Object objeto, String nombre) throws Exception {
        Field campo = objeto.getClass().getDeclaredField(nombre);
        campo.setAccessible(true);
        return campo.get(objeto);
    }

    private void tecla(Runnable accion, int tecla) {
        Input original = Gdx.input;
        Gdx.input = (Input) Proxy.newProxyInstance(Input.class.getClassLoader(),
                new Class<?>[]{Input.class}, (proxy, metodo, args) -> {
                    if (metodo.getName().equals("isKeyJustPressed")) return (int) args[0] == tecla;
                    if (metodo.getName().equals("justTouched")) return false;
                    return metodo.invoke(original, args);
                });
        try { accion.run(); } finally { Gdx.input = original; }
    }

    @Override
    public void render() {
        try {
            super.render();
            comprobar(!getBatch().isDrawing(), "Batch abierto al terminar render");
            switch (paso++) {
                case 0:
                    comprobar(getScreen() instanceof MainMenuScreen, "Menu inicial");
                    comprobar(getFont().getData().scaleX == 1, "Menu altera la fuente compartida");
                    partida = new GameScreen(this);
                    setScreen(partida);
                    textura = GestorRecursos.getInstancia().getTexturaTarro();
                    break;
                case 1:
                    tecla(() -> partida.procesarEntrada(), Input.Keys.ESCAPE);
                    comprobar(getScreen() instanceof PausaScreen, "Entrada Escape no pausa");
                    break;
                case 2:
                    PausaScreen pausa = (PausaScreen) getScreen();
                    tecla(() -> pausa.procesarEntrada(), Input.Keys.ESCAPE);
                    comprobar(getScreen() == partida, "No conserva partida");
                    ((Tarro) campo(partida, "tarro")).sumarPuntos(100);
                    break;
                case 3:
                    comprobar(((Lluvia) campo(partida, "lluvia")).getNombreNivel().equals("Tormenta"), "Cambio de nivel");
                    Tarro tarro = (Tarro) campo(partida, "tarro");
                    tarro.danar(99);
                    Field herido = Tarro.class.getDeclaredField("herido");
                    herido.setAccessible(true);
                    herido.setBoolean(tarro, false);
                    break;
                case 4:
                    comprobar(getScreen() instanceof GameOverScreen, "Derrota sin cambio de pantalla");
                    comprobar(getHigherScore() >= 100, "Record no conservado");
                    GameOverScreen fin = (GameOverScreen) getScreen();
                    tecla(() -> fin.procesarEntrada(), Input.Keys.ENTER);
                    comprobar(getScreen() instanceof GameScreen && getScreen() != partida, "No reinicia");
                    comprobar(textura == GestorRecursos.getInstancia().getTexturaTarro(), "Recarga recursos al reiniciar");
                    break;
                case 5:
                    GameScreen nueva = (GameScreen) getScreen();
                    comprobar(((Tarro) campo(nueva, "tarro")).getVidas() == 3, "Reinicio sin vidas iniciales");
                    nueva.pause();
                    break;
                case 6:
                    System.out.println("OK: menu, juego, pausa, reanudacion, nivel, derrota, reinicio y cierre pausado");
                    Gdx.app.exit();
                    break;
                default: break;
            }
        } catch (Throwable error) {
            fallo = error;
            Gdx.app.exit();
        }
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Verificacion automatica del juego");
        config.setWindowedMode(800, 480);
        config.disableAudio(true);
        config.setForegroundFPS(30);
        new Lwjgl3Application(new VerificacionPantallas(), config);
        if (fallo != null) throw new AssertionError("Fallo de integracion", fallo);
    }
}
