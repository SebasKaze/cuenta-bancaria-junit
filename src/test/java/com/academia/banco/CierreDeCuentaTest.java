package com.academia.banco;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Cierre de cuenta (hecho con TDD)")
class CierreDeCuentaTest {

    private CuentaBancaria cuenta;

    @BeforeEach
    void prepararCuenta() {
        cuenta = new CuentaBancaria("Ana");
    }

    @Test
    @DisplayName("1. una cuenta nueva está abierta")
    void cuentaNuevaAbierta() {
        assertFalse(cuenta.estaCerrada());
    }
}