package com.example.springboot.webflux.app;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springboot.webflux.app.models.documents.Producto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWebfluxApirestApplicationTests {
		
    @Autowired
    private WebTestClient client;
	
    @Test
    void listarTest() {
        System.out.println("Ejecutando test...");

        client.get()
            .uri("/api/v2/productos")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBodyList(Producto.class)
            .consumeWith(response -> {
            	List<Producto> productos = response.getResponseBody();
            	productos.forEach(p -> {
            		System.out.println(p.getNombre());
            	});
            	
            	Assertions.assertThat(productos.size()>0).isTrue();
            });
//            .hasSize(9);
            
    }
}
