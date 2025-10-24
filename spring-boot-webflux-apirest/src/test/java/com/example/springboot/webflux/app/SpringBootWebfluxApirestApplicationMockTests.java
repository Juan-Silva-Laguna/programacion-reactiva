package com.example.springboot.webflux.app;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springboot.webflux.app.models.documents.Categoria;
import com.example.springboot.webflux.app.models.documents.Producto;
import com.example.springboot.webflux.app.models.services.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringBootWebfluxApirestApplicationMockTests {
    @Autowired
    private WebTestClient client;
    
	@Autowired
	private ProductoService service;

	@Value("${config.base.endpoint}")
	private String ruta_endpoint;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApirestApplicationTests.class);

    @Test
    void listarTest()   throws InterruptedException  {

		//Esperar a que se carguen los datos
	    Thread.sleep(5000); 
	    
        client.get()
            .uri(ruta_endpoint)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
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
    
    
    
    @Test
    void  verTest()   throws InterruptedException  {

		//Esperar a que se carguen los datos
	    Thread.sleep(5000); 
	    
//    	    List<Producto> prods = service.findAll()
//    	            .collectList()
//    	            .block();

    	    Producto producto  =  service.obtenerPorNombre("Computador").block();
        	log.error(producto==null?"no encontro":"encontro");
    	    
    	    
        client.get()
            .uri(ruta_endpoint + "/{id}", Collections.singletonMap("id",producto.getId()))
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
            
            .expectBody(Producto.class)
            .consumeWith(response -> {
	            	Producto prod = response.getResponseBody();
	            	Assertions.assertThat(prod.getId()).isNotEmpty();
	            	//Assertions.assertThat(prod.getId().length()>0).isTrue();
	            	Assertions.assertThat(prod.getNombre()).isEqualTo("Computador");
            });
            /*.expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.nombre").isEqualTo("Bafles");*/
            
    }
    

//    @Test
//    void crearTestV1() {
//		Categoria categoria = service.encontrarNombreCategoria("visual").block();
//
//    		Producto prod = new Producto("Silla Gamer", 123.5, categoria);
//        client.post()
//            .uri(ruta_endpoint)
//            .contentType(MediaType.APPLICATION_JSON_UTF8)
//            .accept(MediaType.APPLICATION_JSON_UTF8)
//            .body(Mono.just(prod), Producto.class)
//            .exchange()
//            .expectStatus().isCreated()
//            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
//            .expectBody()
//            .jsonPath("$.producto.id").isNotEmpty()
//            .jsonPath("$.producto.nombre").isEqualTo("Silla Gamer")
//            .jsonPath("$.producto.categoria.nombre").isEqualTo("visual");
//            
//    }
    
//    @Test
//    void crearTest2V1() {
//		Categoria categoria = service.encontrarNombreCategoria("visual").block();
//
//    		Producto prod = new Producto("Silla Gamer2", 123.2, categoria);
//        client.post()
//            .uri(ruta_endpoint)
//            .contentType(MediaType.APPLICATION_JSON_UTF8)
//            .accept(MediaType.APPLICATION_JSON_UTF8)
//            .body(Mono.just(prod), Producto.class)
//            .exchange()
//            .expectStatus().isCreated()
//            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
//            .expectBody(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {
//			})
//            .consumeWith(response -> {
//	            	Object o = response.getResponseBody().get("producto");
//	            	Producto p = new ObjectMapper().convertValue(o, Producto.class);
//	            	Assertions.assertThat(p.getId()).isNotEmpty();
//	            	Assertions.assertThat(p.getNombre()).isEqualTo("Silla Gamer2");
//	            	Assertions.assertThat(p.getCategoria().getNombre()).isEqualTo("visual");
//            });
//    }
//    
    
    @Test
    void crearTest() {
		Categoria categoria = service.encontrarNombreCategoria("visual").block();

    		Producto prod = new Producto("Silla Gamer", 123.5, categoria);
        client.post()
            .uri(ruta_endpoint)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(prod), Producto.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.nombre").isEqualTo("Silla Gamer")
            .jsonPath("$.categoria.nombre").isEqualTo("visual");
            
    }
    
    @Test
    void crearTest2() {
		Categoria categoria = service.encontrarNombreCategoria("visual").block();

    		Producto prod = new Producto("Silla Gamer2", 123.2, categoria);
        client.post()
            .uri(ruta_endpoint)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(prod), Producto.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
            .expectBody(Producto.class)
            .consumeWith(response -> {
	            	Producto p = response.getResponseBody();
	            	Assertions.assertThat(p.getId()).isNotEmpty();
	            	Assertions.assertThat(p.getNombre()).isEqualTo("Silla Gamer2");
	            	Assertions.assertThat(p.getCategoria().getNombre()).isEqualTo("visual");
            });
    }
    
    @Test
    void editarTest()    throws InterruptedException  {

		//Esperar a que se carguen los datos
	    Thread.sleep(5000); 
	    
		Producto producto = service.findByNombre("Bafles").block();
		Categoria categoria = service.encontrarNombreCategoria("visual").block();
		Producto prodEditado = new Producto("Bafles Mega Sonido", 40.2, categoria);

        client.put()
            .uri(ruta_endpoint + "/{id}", Collections.singletonMap("id", producto.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(prodEditado), Producto.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
            .expectBody(Producto.class)
            .consumeWith(response -> {
	            	Producto p = response.getResponseBody();
	            	Assertions.assertThat(p.getId()).isNotEmpty();
	            	Assertions.assertThat(p.getNombre()).isEqualTo("Bafles Mega Sonido");
	            	Assertions.assertThat(p.getCategoria().getNombre()).isEqualTo("visual");
            });
    }

    @Test
    void eliminarTest()    throws InterruptedException  {

		//Esperar a que se carguen los datos
	    Thread.sleep(5000); 
	    
		Producto producto = service.findByNombre("Monitor").block();
		
        client.delete()
            .uri(ruta_endpoint + "/{id}", Collections.singletonMap("id", producto.getId()))
            .exchange()
            .expectStatus().isNoContent()
            .expectBody().isEmpty();
        	    
        client.get()
	        .uri(ruta_endpoint + "/{id}", Collections.singletonMap("id", producto.getId()))
	        .exchange()
	        .expectStatus().isNotFound()
	        .expectBody().isEmpty();
    }
}
