package com.autoflex.inventory;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class InventoryApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
