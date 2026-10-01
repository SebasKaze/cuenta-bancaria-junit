# Errores encontrados en la versión del compañero

Mi suite (MP-1 a MP-4) atrapó sola **3** de los 5 errores: (nombres de las pruebas que fallaron en mp5-companero.txt)

| Queja | Prueba que la reproduce | Qué estaba mal en el código (la línea y por qué) | ¿Mi suite ya lo atrapaba? |
|---|---|---|---|
| 1 | tercerRetiroGratis | En comisionDelRetiro, la condición decía numeroDeRetiro < RETIROS_GRATIS. Al usar < en lugar de <=, el 3.er retiro no entraba como gratis. | Sí (en ComisionTest validamos que el retiro 3 cobrara 0.00). |
| 2 | retiroExactoDelLimite | En retirar, validaba el límite con m.compareTo(LIMITE_POR_RETIRO) >= 0. El >= provocaba que retirar exactamente $5,000 fuera rechazado. | Sí (en RetiroTest teníamos retirarExactamenteElLimiteSePermite). |
| 3 | transferenciaRechazadaNoCambiaElSaldo | En transferir, hacía saldo = saldo.subtract(m); antes de verificar si el saldo alcanzaba, perdiendo el dinero si lanzaba la excepción. | Sí (en TransferenciaTest validamos que origen y destino no cambiaran con sinSaldoSuficienteNingunaCambia). |
| 4 | laComisionApareceEnElHistorial | En retirar, se guardaba el movimiento del retiro, pero faltaba el bloque if (comision.signum() > 0) para agregar la comisión a la lista de movimientos. | No (ninguna de nuestras pruebas anteriores verificaba los movimientos de un 4.º retiro). |
| 5 | elHistorialNoSePuedeModificar | getMovimientos() retornaba return movimientos; directamente (la referencia a la lista original), en lugar de protegerla usando List.copyOf(movimientos) | No |

