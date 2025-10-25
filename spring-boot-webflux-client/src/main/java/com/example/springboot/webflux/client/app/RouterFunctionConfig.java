package com.example.springboot.webflux.client.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

//para que aplique a todos los request
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import com.example.springboot.webflux.client.app.handler.ProductoHandler;

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> rutas(ProductoHandler handler){
		return route(GET("/api/client"), handler::listar)
				.andRoute(GET("api/client/{id}"), handler::ver)
				.andRoute(POST("api/client"), handler::crear)
				.andRoute(PUT("api/client/{id}"), handler::editar)
				.andRoute(DELETE("api/client/{id}"), handler::eliminar)
				.andRoute(POST("api/client/upload/{id}"), handler::upload);

	}
	
	
}
