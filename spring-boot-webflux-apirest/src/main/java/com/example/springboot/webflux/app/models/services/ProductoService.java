package com.example.springboot.webflux.app.models.services;

import org.springframework.data.mongodb.repository.Query;

import com.example.springboot.webflux.app.models.documents.Categoria;
import com.example.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
	public Flux<Producto> findAll();
	
	public Flux<Producto> findAllConNombreUpperCase();
	
	public Flux<Producto> findAllConNombreUpperCaseRepeat();

	
	public Mono<Producto> findById(String id);
	
	public Mono<Producto> save(Producto producto);

	public Mono<Void> delete(Producto producto);
	

	public Flux<Categoria> findAllCategoria();
	
	public Mono<Categoria> findCategoriaById(String id);
	
	public Mono<Categoria> saveCategoria(Categoria categoria);

	public Mono<Void> deleteCategoria(Categoria categoria);
	
	public Mono<Producto> findByNombre (String nombre);
	
	public Mono<Producto> obtenerPorNombre (String nombre);

	public Mono<Categoria> encontrarNombreCategoria (String nombre);
}
