package puppy.code;

import com.badlogic.gdx.math.Rectangle;
import puppy.code.entidades.*;
import puppy.code.movimiento.*;
import puppy.code.niveles.*;

/** Pruebas ejecutables sin librerias adicionales ni contexto grafico. */
public class VerificacionLogica {
    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    public static void main(String[] args) {
        Rectangle uno = new Rectangle(300, 480, 64, 64);
        Rectangle varios = new Rectangle(uno);
        new CaidaRecta(200).mover(uno, 1);
        MovimientoStrategy recta = new CaidaRecta(200);
        for (int i = 0; i < 10; i++) recta.mover(varios, 0.1f);
        comprobar(Math.abs(uno.y - varios.y) < 0.01f, "Caida recta depende de FPS");
        uno.set(300, 480, 64, 64);
        varios.set(uno);
        new CaidaZigZag(200).mover(uno, 1);
        MovimientoStrategy zigzag = new CaidaZigZag(200);
        for (int i = 0; i < 10; i++) zigzag.mover(varios, 0.1f);
        comprobar(Math.abs(uno.x - varios.x) < 0.01f, "Zigzag depende de FPS");
        comprobar(Math.abs(uno.y - varios.y) < 0.01f, "Caida zigzag depende de FPS");
        NivelBuilder builder = new NivelLloviznaBuilder();
        try {
            builder.obtenerNivel();
            throw new AssertionError("Builder incompleto aceptado");
        } catch (IllegalStateException esperada) { }
        DirectorNivel director = new DirectorNivel();
        Nivel suave = director.construir(builder);
        Nivel fuerte = director.construir(new NivelTormentaBuilder());
        comprobar(suave.crearMovimiento() instanceof CaidaRecta, "Estrategia Llovizna");
        comprobar(fuerte.crearMovimiento() instanceof CaidaZigZag, "Estrategia Tormenta");
        comprobar(fuerte.crearMovimiento() != fuerte.crearMovimiento(), "Fase compartida entre gotas");
        comprobar(fuerte.getIntervalo() < suave.getIntervalo(), "Progresion de frecuencia");
        comprobar(director.construir(builder) != suave, "Builder reutiliza producto mutable");
        try {
            new Nivel("Invalido", 1, 0.8f, 0.5f, () -> recta);
            throw new AssertionError("Probabilidades invalidas aceptadas");
        } catch (IllegalArgumentException esperada) { }
        Tarro tarro = new Tarro(null, null);
        new GotaBuena(0, 0, null, recta).aplicarEfecto(tarro);
        comprobar(tarro.getPuntos() == 10, "Puntos gota buena");
        new GotaMala(0, 0, null, recta).aplicarEfecto(tarro);
        comprobar(tarro.getVidas() == 2, "Dano gota mala");
        new GotaDorada(0, 0, null, recta).aplicarEfecto(tarro);
        comprobar(tarro.getVidas() == 3, "Vida gota dorada");
        Rectangle copia = tarro.getArea();
        copia.x = -100;
        comprobar(tarro.getArea().x >= 0, "Area interna expuesta");
        tarro.danar(99);
        comprobar(!tarro.estaVivo() && tarro.getVidas() == 0, "Limite inferior de vidas");
        System.out.println("OK: movimiento, builders, polimorfismo y encapsulamiento");
    }
}
