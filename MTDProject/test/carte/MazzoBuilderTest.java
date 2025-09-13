package carte;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazzoBuilderTest {

    @Test
    public void testGeneraCarte() {
    Mazzo mazzo = new Mazzo.MazzoBuilder().generaCarte(TipoMazzo.NAPOLETANTE).build();
    assertNotNull(mazzo.getCarte(), "Le carte non dovrebbero essere nulle");
    assertEquals(40, mazzo.getCarte().size(), "Il mazzo dovrebbe contenere 40 carte");
    // Verifica che tutte le carte siano diverse
    long carteUniche = mazzo.getCarte().stream().distinct().count();
    assertEquals(40, carteUniche, "Tutte le carte nel mazzo dovrebbero essere diverse");
    }

    @Test
    public void testMescola() {
        Mazzo mazzo1 = new Mazzo.MazzoBuilder().generaCarte(TipoMazzo.NAPOLETANTE).build();
        Mazzo mazzo2 = new Mazzo.MazzoBuilder().generaCarte(TipoMazzo.NAPOLETANTE).mescola().build();
        assertEquals(mazzo1.getCarte().size(), mazzo2.getCarte().size(), "Entrambi i mazzi dovrebbero avere 40 carte");
        // Non si può garantire che l'ordine sia diverso, ma si può verificare che non sia sempre uguale
        boolean uguali = true;
        for (int i = 0; i < mazzo1.getCarte().size(); i++) {
            if (!mazzo1.getCarte().get(i).equals(mazzo2.getCarte().get(i))) {
                uguali = false;
                break;
            }
        }
        assertFalse(uguali, "Dopo la mescolata, l'ordine delle carte dovrebbe essere diverso");
    }
}
