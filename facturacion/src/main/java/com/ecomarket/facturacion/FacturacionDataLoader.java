package com.ecomarket.facturacion;

import com.ecomarket.facturacion.model.FacturacionEntity;
import com.ecomarket.facturacion.repository.FacturacionRespository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Configuration
public class FacturacionDataLoader {

    @Bean
    public CommandLineRunner loadFacturas(FacturacionRespository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("es"));

                IntStream.range(0, 20).forEach(i -> {
                    FacturacionEntity factura = new FacturacionEntity();
                    factura.setTipoDocumento(faker.options().option("Factura", "Boleta", "Nota de crédito"));
                    factura.setIdVenta(faker.number().numberBetween(1, 50));
                    factura.setIdUsuario(faker.number().numberBetween(1, 50));
                    factura.setFechaEmision(randomDateBetween(LocalDate.now().minusMonths(6), LocalDate.now()));

                    repository.save(factura);
                });

                System.out.println("Se generaron 20 facturas con DataFaker.");
            }
        };
    }

    private LocalDate randomDateBetween(LocalDate start, LocalDate end) {
        long minDay = start.toEpochDay();
        long maxDay = end.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}