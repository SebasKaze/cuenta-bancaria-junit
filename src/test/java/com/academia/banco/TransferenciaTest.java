package com.academia.banco;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Transferencias entre cuentas")
class TransferenciaTest {

    private CuentaBancaria origen;
    private CuentaBancaria destino;

    @BeforeEach
    void setUp() {
        origen = new CuentaBancaria("Ana");
        origen.depositar(new BigDecimal("1000.00"));
        
        destino = new CuentaBancaria("Beto");
    }

    @Nested
    @DisplayName("Cuando la transferencia procede")
    class CuandoProcede {

        @Test
        @DisplayName("el dinero sale de una cuenta y llega a la otra")
        void elDineroSaleYLlega() {
            origen.transferir(new BigDecimal("400.00"), destino);

            assertAll(
                () -> assertEquals(new BigDecimal("600.00"), origen.getSaldo()),
                
                () -> assertEquals(new BigDecimal("400.00"), destino.getSaldo()) 
            );
        }

        @Test
        @DisplayName("no cobra comisión ni cuenta como retiro")
        void noCobraComisionNiCuentaComoRetiro() {
            origen.transferir(new BigDecimal("1000.00"), destino);

            assertEquals(new BigDecimal("0.00"), origen.getSaldo());
            assertEquals(0, origen.getRetiros());
        }

        @Test
        @DisplayName("queda en el historial de las dos cuentas")
        void quedaEnElHistorial() {
            origen.transferir(new BigDecimal("400.00"), destino);

            assertEquals(
                new Movimiento(TipoMovimiento.TRANSFERENCIA_ENVIADA, new BigDecimal("400.00")),
                origen.getMovimientos().get(1)
            );

            assertEquals(
                List.of(new Movimiento(TipoMovimiento.TRANSFERENCIA_RECIBIDA, new BigDecimal("400.00"))),
                destino.getMovimientos()
            );
        }
    }

    @Nested
    @DisplayName("Cuando la transferencia se rechaza")
    class CuandoSeRechaza {

        @Test
        @DisplayName("sin saldo suficiente, ninguna de las dos cuentas cambia")
        void sinSaldoSuficienteNingunaCambia() {
            assertThrows(SaldoInsuficienteException.class, () -> origen.transferir(new BigDecimal("1000.01"), destino));

            assertAll(
                () -> assertEquals(new BigDecimal("1000.00"), origen.getSaldo()),
                () -> assertEquals(new BigDecimal("0.00"), destino.getSaldo()),
                () -> assertEquals(1, origen.getMovimientos().size()),
                () -> assertEquals(0, destino.getMovimientos().size())
            );
        }

        @Test
        @DisplayName("no se puede transferir a la misma cuenta")
        void noSePuedeTransferirALaMismaCuenta() {
            assertThrows(IllegalArgumentException.class, () -> origen.transferir(new BigDecimal("50.00"), origen));
        }

        @Test
        @DisplayName("la cuenta destino es obligatoria")
        void laCuentaDestinoEsObligatoria() {
            assertThrows(IllegalArgumentException.class, () -> origen.transferir(new BigDecimal("50.00"), null));
        }

        @Test
        @DisplayName("el monto debe ser mayor que cero")
        void elMontoDebeSerMayorQueCero() {
            assertThrows(IllegalArgumentException.class, () -> origen.transferir(new BigDecimal("0.00"), destino));
        }
    }
}