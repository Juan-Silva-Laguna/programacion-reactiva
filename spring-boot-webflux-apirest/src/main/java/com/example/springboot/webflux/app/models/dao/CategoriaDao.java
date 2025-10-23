package com.example.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.springboot.webflux.app.models.documents.Categoria;

import reactor.core.publisher.Mono;

public interface CategoriaDao extends ReactiveMongoRepository<Categoria, String>{
	@Query("{'nombre': ?0}")
	public Mono<Categoria> encontrarNombreCategoria (String nombre);
}
