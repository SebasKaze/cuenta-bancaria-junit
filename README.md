# Cuenta bancaria — pruebas unitarias con JUnit 5

**Autor:** Hilario Sebastian Espinoza Garcia

## Cómo correr las pruebas

    ./probar.sh final

## Qué prueba cada clase

| Clase de prueba | Qué prueba | Cuántas pruebas |
|---|---|---|
| DepositoTest | Valida que el saldo inicial sea cero y que los depósitos se sumen correctamente. | 2 |
| RetiroTest | Valida límites de retiro, montos inválidos/negativos y excepciones por saldo insuficiente. | 8 |
| TransferenciaTest | Envío de dinero entre cuentas, rechazos por falta de fondos, cuentas inválidas e impacto en los historiales de origen y destino. | 7 |
| InteresTest | Cálculo de interés mensual verificando distintos rangos de saldo mediante pruebas parametrizadas y rechazo de saldos negativos. | 8 |
| ComisionTest | Cobro de comisiones según el número de retiro (gratis los 3 primeros, cobro desde el 4.º) y rechazo de números inválidos (0 o negativos). | 6 |
| QuejasTest | Bugs específicos reportados (límites exactos, historial inmodificable, cobro correcto en el 3.er retiro y comisiones en el historial). | 5 |
| CierreDeCuentaTest | Ciclo de vida de la cuenta (TDD): apertura, cierre válido (sin saldo) y bloqueos de operaciones (retiro, depósito, transferencias) en cuentas cerradas. | 7 |

## Boleto de salida

1. ¿Por qué una prueba que nunca viste en rojo no es confiable? Da un ejemplo de hoy.

Porque si una prueba pasa desde el primer momento sin haber implementado o roto el código, es muy probable que sea un falso positivo (esté mal escrita, no esté evaluando la condición real o le falten aserciones). Un ejemplo de hoy fue cuando modificaste TransferenciaTest: si hubieras olvidado poner el assertEquals que verificaba el saldo de la cuenta destino, la prueba habría estado en verde aunque la transferencia no sumara el dinero a la otra persona. Romper el código temporalmente te demostró que la prueba sí atrapaba el error.


2. ¿Qué error se escondía en un valor frontera, y qué prueba lo atrapó?

Hubo dos. El primero fue en el límite de retiros: el código usaba >= en lugar de >, lo que provocaba que retirar exactamente el límite de $5,000.00 fuera rechazado. Esto lo atrapó la prueba retiroExactoDelLimite (Queja 2). El segundo fue en el cálculo de intereses: usar < 0 en lugar de <= 0 provocaba que un saldo de exactamente $1000.00 calculara un interés incorrecto. Esto lo atrapó el renglón "1000.00, 5.00" de tu prueba parametrizada en InteresTest.

3. ¿Qué cambió en tu forma de programar al escribir la prueba ANTES del código (MP-6)?

TDD invierte el proceso: primero defines qué debe hacer el código con una prueba, y luego escribes solo lo necesario para que pase. Así evitas código innecesario y tu funcionalidad nace probada y con los errores contemplados
