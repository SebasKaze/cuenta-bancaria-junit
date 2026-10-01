package com.academia.banco;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class RetiroTest {

    // ── EJEMPLO 1 (completo): assertEquals ─────────────────────────────
    @Test
    void retirarRestaDelSaldo() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("1000.00"));

        cuenta.retirar(new BigDecimal("300.00"));

        assertEquals(new BigDecimal("700.00"), cuenta.getSaldo());
    }

    // ── EJEMPLO 2 (completo): assertThrows ─────────────────────────────
    @Test
    void retirarCeroSeRechaza() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("1000.00"));

        assertThrows(IllegalArgumentException.class, () -> cuenta.retirar(new BigDecimal("0.00")));
    }

    // ── HUECO 1 ────────────────────────────────────────────────────────
    @Test
    void retirarUnMontoNegativoSeRechaza() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");

        assertThrows(IllegalArgumentException.class, () -> cuenta.retirar(new BigDecimal("-50.00")));
        // TODO: igual que el EJEMPLO 2, pero retirando -50.00
        //fail("HUECO 1 sin completar");
    }

    // ── HUECO 2: assertThrows + assertAll ──────────────────────────────
    @Test
    void sinSaldoSuficienteSeRechazaYNoCambiaNada() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("100.00"));

        assertThrows(SaldoInsuficienteException.class, () -> cuenta.retirar(new BigDecimal("150.00")));

        // TODO: dentro del assertAll agrega las otras dos verificaciones:
        //   - el número de retiros sigue en 0         (cuenta.getRetiros())
        //   - solo hay 1 movimiento: el depósito       (cuenta.getMovimientos().size())
        assertAll(
                () -> assertEquals(new BigDecimal("100.00"), cuenta.getSaldo()),
                () -> assertEquals(0, cuenta.getRetiros()),
                () -> assertEquals(1, cuenta.getMovimientos().size())
            );
        //fail("HUECO 2 sin completar");
    }

    // ── HUECO 3: el mensaje de la excepción ────────────────────────────
    @Test
    void elMensajeDiceCuantoTienesYCuantoNecesitas() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("100.00"));

        SaldoInsuficienteException e = assertThrows(SaldoInsuficienteException.class, () -> cuenta.retirar(new BigDecimal("150.00")));
        assertEquals("Saldo insuficiente: tienes $100.00 y se necesitan $150.00", e.getMessage());
        // TODO: guarda lo que devuelve assertThrows en una variable
        //   SaldoInsuficienteException e = assertThrows(...);
        // y verifica que e.getMessage() sea EXACTAMENTE:
        //   Saldo insuficiente: tienes $100.00 y se necesitan $150.00
        //fail("HUECO 3 sin completar");
    }

    // ── HUECO 4: el valor límite (5,000.00 SÍ se permite) ──────────────
    @Test
    void retirarExactamenteElLimiteSePermite() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("6000.00"));

        cuenta.retirar(new BigDecimal("5000.00"));
        assertEquals(new BigDecimal("1000.00"), cuenta.getSaldo());
        // TODO: retira 5000.00 y verifica que el saldo quede en 1000.00
        //fail("HUECO 4 sin completar");
    }

    // ── HUECO 5: un centavo más del límite ─────────────────────────────
    @Test
    void retirarUnCentavoMasDelLimiteSeRechaza() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("6000.00"));

        LimiteExcedidoException e = assertThrows(LimiteExcedidoException.class, () -> cuenta.retirar(new BigDecimal("5000.01")));
        assertTrue(e.getMessage().contains("límite"));
        // TODO: retirar 5000.01 debe lanzar LimiteExcedidoException,
        //       y con assertTrue verifica que su mensaje contenga la palabra "límite"
        //fail("HUECO 5 sin completar");
    }

    // ── HUECO 6: escríbela tú completa ─────────────────────────────────
    @Test
    void unMontoConFraccionDeCentavoSeRechaza() {
        CuentaBancaria cuenta = new CuentaBancaria("Ana");
        cuenta.depositar(new BigDecimal("1000.00"));

        assertThrows(IllegalArgumentException.class, () -> cuenta.retirar(new BigDecimal("10.005")));
        // TODO: regla 2 del banco — un retiro de 10.005 (fracción de centavo) se rechaza
        //fail("HUECO 6 sin completar");
    }
}