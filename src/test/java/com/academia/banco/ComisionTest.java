package com.academia.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Comisión por retiro")
class ComisionTest {

    @ParameterizedTest(name = "retiro {0} cobra ${1}")
    @CsvSource({
            "1,   0.00",
            "3,   0.00",
            "4,  10.00",
            "100, 10.00"
    })
    void comisionSegunElNumeroDeRetiro(int numero, BigDecimal esperada) {
        assertEquals(esperada, CuentaBancaria.comisionDelRetiro(numero));
    }

    @ParameterizedTest(name = "el retiro número {0} lanza excepción")
    @ValueSource(ints = {0, -1})
    void numerosDeRetiroInvalidosLanzanExcepcion(int numero) {
        assertThrows(IllegalArgumentException.class, () -> CuentaBancaria.comisionDelRetiro(numero));
    }
}