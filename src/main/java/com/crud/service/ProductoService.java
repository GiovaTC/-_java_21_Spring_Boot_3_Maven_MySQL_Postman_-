package com.crud.service;

import com.crud.entity.Producto;
import com.crud.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public Producto buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Producto actualizar(Long id,Producto producto) {

        Producto existente = repository.findById(id)
                .orElseThrow();

        existente.setNombre(producto.getNombre());
        existente.setPrecio(producto.getPrecio());
        existente.setCantidad(producto.getCantidad());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
