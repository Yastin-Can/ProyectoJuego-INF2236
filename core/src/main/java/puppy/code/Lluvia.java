package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.Iterator;
import puppy.code.entidades.*;
import puppy.code.niveles.*;

/** Administra gotas polimorficas; no expone su coleccion interna. */
public class Lluvia {
    private final ArrayList<Gota> gotas = new ArrayList<>();
    private final Texture buena;
    private final Texture mala;
    private final Texture dorada;
    private final Sound sonido;
    private final Music musica;
    private final DirectorNivel director = new DirectorNivel();
    private Nivel nivel;
    private float acumulado;
    private boolean tormenta;

    public Lluvia(Texture buena, Texture mala, Sound sonido, Music musica) {
        this(buena, mala, buena, sonido, musica);
    }

    public Lluvia(Texture buena, Texture mala, Texture dorada, Sound sonido, Music musica) {
        this.buena = buena;
        this.mala = mala;
        this.dorada = dorada;
        this.sonido = sonido;
        this.musica = musica;
    }

    public void crear() {
        gotas.clear();
        acumulado = 0;
        tormenta = false;
        nivel = director.construir(new NivelLloviznaBuilder());
        crearGota();
        musica.setLooping(true);
    }

    private void crearGota() {
        float x = MathUtils.random(0f, 736f);
        float azar = MathUtils.random();
        Gota gota;
        if (azar < nivel.getProbabilidadMala()) {
            gota = new GotaMala(x, 480, mala, nivel.crearMovimiento());
        } else if (azar < nivel.getProbabilidadMala() + nivel.getProbabilidadDorada()) {
            gota = new GotaDorada(x, 480, dorada, nivel.crearMovimiento(), sonido);
        } else {
            gota = new GotaBuena(x, 480, buena, nivel.crearMovimiento(), sonido);
        }
        gotas.add(gota);
    }

    public boolean actualizarMovimiento(Tarro tarro, float delta) {
        if (!tormenta && tarro.getPuntos() >= 100) {
            nivel = director.construir(new NivelTormentaBuilder());
            tormenta = true;
            for (Gota gota : gotas) {
                gota.setMovimiento(nivel.crearMovimiento());
            }
        }
        acumulado += delta;
        while (acumulado >= nivel.getIntervalo()) {
            crearGota();
            acumulado -= nivel.getIntervalo();
        }
        Iterator<Gota> iterador = gotas.iterator();
        while (iterador.hasNext()) {
            Gota gota = iterador.next();
            gota.actualizar(delta);
            if (gota.llegoAlSuelo()) {
                iterador.remove();
            } else if (gota.chocaCon(tarro.getArea())) {
                gota.aplicarEfecto(tarro);
                iterador.remove();
                if (!tarro.estaVivo()) {
                    return false;
                }
                if (tarro.estaHerido()) {
                    break;
                }
            }
        }
        return tarro.estaVivo();
    }

    public String getNombreNivel() { return nivel.getNombre(); }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : gotas) {
            gota.dibujar(batch);
        }
    }

    public void destruir() {
        gotas.clear();
        // Texturas y audio pertenecen al Singleton; solo detener la partida.
        musica.stop();
    }

    public void pausar() { musica.pause(); }
    public void continuar() { musica.play(); }
}
