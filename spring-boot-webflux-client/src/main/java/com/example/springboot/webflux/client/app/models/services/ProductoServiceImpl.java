package com.example.springboot.webflux.client.app.models.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springboot.webflux.client.app.models.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private WebClient cliente;
	
	@Override
	public Flux<Producto> findAll() {
		return cliente.get().accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.flatMapMany(respeusta -> respeusta.bodyToFlux(Producto.class));
	}

	@Override
	public Mono<Producto> findById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
//		Collections.singletonMap("id", id)
		return cliente.get().uri("/{id}", params)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve()
				.bodyToMono(Producto.class);
//				.exchange()
//				.flatMap(respeusta -> respeusta.bodyToMono(Producto.class));
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return cliente.post()
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.body(BodyInserters.fromObject(producto))
				.syncBody(producto)
				.retrieve()
				.bodyToMono(Producto.class);
	}

	@Override
	public Mono<Producto> update(Producto producto, String id) {
		return cliente.put().uri("/{id}", Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.body(BodyInserters.fromObject(producto))
				.syncBody(producto)
				.retrieve()
				.bodyToMono(Producto.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		return cliente.delete()
				.uri("/{id}", params)
				.retrieve()
				.bodyToMono(Void.class);
	}

	@Override
	public Mono<Producto> upload(FilePart file, String id) {
		MultipartBodyBuilder parts = new MultipartBodyBuilder();
		
		parts.asyncPart("file", file.content(), DataBuffer.class)
		.headers(h -> {
			h.setContentDispositionFormData("file", file.filename());
		});
		return cliente.post()
				.uri("/upload/{id}", Collections.singletonMap("id", id))
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.syncBody(parts.build())
				.retrieve()
				.bodyToMono(Producto.class);
	}

}
