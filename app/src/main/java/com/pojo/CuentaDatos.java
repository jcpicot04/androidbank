package com.pojo;

import java.util.ArrayList;

public class CuentaDatos {

    public static ArrayList<Cuenta> CUENTAS = new ArrayList<Cuenta>();

    private static final  Cliente cliente = new Cliente();;

    static{
        CUENTAS.add(new Cuenta(1, "banco1", "sucursal1","dc1","cuenta1",cliente,120));
        CUENTAS.add(new Cuenta(2, "banco2", "sucursal2","dc2","cuenta2",cliente,130));
        CUENTAS.add(new Cuenta(3, "banco3", "sucursal3","dc3","cuenta3",cliente,140));
        CUENTAS.add(new Cuenta(4, "banco4", "sucursal4","dc4","cuenta4",cliente,150));
        CUENTAS.add(new Cuenta(5, "banco5", "sucursal5","dc5","cuenta5",cliente,160));
    }

}
