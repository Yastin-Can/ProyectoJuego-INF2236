package puppy.code.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestor centralizado de recursos multimedia (Texturas, Sonidos y Música)
 * implementado mediante el patrón de diseño Singleton (GM-6) y encapsulamiento (GM-5).
 *
 * Evita la carga duplicada de archivos en memoria en cada reinicio de partida
 * y centraliza la liberación (dispose) al cerrar la aplicación.
 *
 * @author Benja
 */
public final class GestorRecursos {

    public static final String RUTA_TARRO = "bucket.png";
    public static final String RUTA_GOTA_BUENA = "drop.png";
    public static final String RUTA_GOTA_MALA = "dropBad.png";
    public static final String RUTA_GOTA_DORADA = "dropGold.png";
    public static final String RUTA_SONIDO_GOTA = "drop.wav";
    public static final String RUTA_SONIDO_HERIDO = "hurt.ogg";
    public static final String RUTA_MUSICA_LLUVIA = "rain.mp3";

    private static GestorRecursos instancia;

    private final Map<String, Texture> texturas;
    private final Map<String, Sound> sonidos;
    private final Map<String, Music> musicas;

    /**
     * Constructor privado para impedir instanciación externa con 'new' (GM-6).
     */
    private GestorRecursos() {
        this.texturas = new HashMap<String, Texture>();
        this.sonidos = new HashMap<String, Sound>();
        this.musicas = new HashMap<String, Music>();
    }

    /**
     * Punto de acceso global a la única instancia de GestorRecursos (Lazy Initialization).
     *
     * @return instancia única de GestorRecursos
     */
    public static GestorRecursos getInstancia() {
        if (instancia == null) {
            instancia = new GestorRecursos();
        }
        return instancia;
    }

    /**
     * Alias en inglés por compatibilidad con llamadas estándar getInstance().
     *
     * @return instancia única de GestorRecursos
     */
    public static GestorRecursos getInstance() {
        return getInstancia();
    }

    /**
     * Precarga todos los recursos base del juego en memoria una sola vez.
     */
    public void cargarRecursos() {
        getTexturaTarro();
        getTexturaGotaBuena();
        getTexturaGotaMala();
        getTexturaGotaDorada();
        getSonidoGota();
        getSonidoHerido();
        getMusicaLluvia();
    }

    /**
     * Obtiene una textura desde la caché o la carga desde disco si aún no ha sido registrada.
     *
     * @param ruta nombre o ruta interna del archivo en assets/
     * @return instancia única de Texture asociada a la ruta
     */
    public Texture getTextura(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta de la textura no puede ser nula ni vacía.");
        }
        if (!this.texturas.containsKey(ruta)) {
            this.texturas.put(ruta, new Texture(Gdx.files.internal(ruta)));
        }
        return this.texturas.get(ruta);
    }

    /**
     * Obtiene un efecto de sonido desde la caché o lo carga desde disco si aún no existe.
     *
     * @param ruta nombre o ruta interna del archivo en assets/
     * @return instancia única de Sound asociada a la ruta
     */
    public Sound getSonido(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta del sonido no puede ser nula ni vacía.");
        }
        if (!this.sonidos.containsKey(ruta)) {
            this.sonidos.put(ruta, Gdx.audio.newSound(Gdx.files.internal(ruta)));
        }
        return this.sonidos.get(ruta);
    }

    /**
     * Obtiene una pista de música desde la caché o la carga desde disco si aún no existe.
     *
     * @param ruta nombre o ruta interna del archivo en assets/
     * @return instancia única de Music asociada a la ruta
     */
    public Music getMusica(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta de la música no puede ser nula ni vacía.");
        }
        if (!this.musicas.containsKey(ruta)) {
            this.musicas.put(ruta, Gdx.audio.newMusic(Gdx.files.internal(ruta)));
        }
        return this.musicas.get(ruta);
    }

    public Texture getTexturaTarro() {
        return getTextura(RUTA_TARRO);
    }

    public Texture getTexturaGotaBuena() {
        return getTextura(RUTA_GOTA_BUENA);
    }

    public Texture getTexturaGotaMala() {
        return getTextura(RUTA_GOTA_MALA);
    }

    /**
     * Obtiene la textura de la gota dorada. Si aún no existe 'dropGold.png' en assets/,
     * reutiliza 'drop.png' de forma segura para mantener el juego ejecutable.
     *
     * @return Texture para GotaDorada
     */
    public Texture getTexturaGotaDorada() {
        FileHandle archivoDorada = Gdx.files.internal(RUTA_GOTA_DORADA);
        if (archivoDorada.exists()) {
            return getTextura(RUTA_GOTA_DORADA);
        }
        return getTextura(RUTA_GOTA_BUENA);
    }

    public Sound getSonidoGota() {
        return getSonido(RUTA_SONIDO_GOTA);
    }

    public Sound getSonidoHerido() {
        return getSonido(RUTA_SONIDO_HERIDO);
    }

    public Music getMusicaLluvia() {
        return getMusica(RUTA_MUSICA_LLUVIA);
    }

    /**
     * Libera todos los recursos nativos (OpenGL y audio) almacenados en caché.
     * Debe invocarse al cerrar el juego en GameLluviaMenu.dispose().
     */
    public void liberarRecursos() {
        for (Texture tex : this.texturas.values()) {
            if (tex != null) {
                tex.dispose();
            }
        }
        this.texturas.clear();

        for (Sound snd : this.sonidos.values()) {
            if (snd != null) {
                snd.dispose();
            }
        }
        this.sonidos.clear();

        for (Music msc : this.musicas.values()) {
            if (msc != null) {
                msc.dispose();
            }
        }
        this.musicas.clear();

        instancia = null;
    }

    /**
     * Alias estándar de LibGDX para liberar recursos.
     */
    public void dispose() {
        liberarRecursos();
    }
}