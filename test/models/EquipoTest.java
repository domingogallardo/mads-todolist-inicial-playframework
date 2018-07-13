package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class EquipoTest {
    @Test
    public void testEqualsEquiposConId() {
        Equipo equipo1 = new Equipo("Equipo A");
        Equipo equipo2 = new Equipo("Equipo B");
        Equipo equipo3 = new Equipo("Equipo C");
        equipo1.setId(1L);
        equipo2.setId(1L);
        equipo3.setId(2L);
        assertEquals(equipo1, equipo2);
        assertNotEquals(equipo1, equipo3);
    }

    @Test
    public void testEqualsEquiposSinId() {
        Equipo equipo1 = new Equipo("Equipo A");
        Equipo equipo2 = new Equipo("Equipo A");
        Equipo equipo3 = new Equipo("Equipo B");
        assertEquals(equipo1, equipo2);
        assertNotEquals(equipo1, equipo3);
    }
}
