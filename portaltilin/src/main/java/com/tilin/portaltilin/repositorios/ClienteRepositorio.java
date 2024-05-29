package com.tilin.portaltilin.repositorios;

import com.tilin.portaltilin.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query("SELECT MAX(id) FROM Cliente")
    public Long ultimoCliente();

}
