package com.tilin.portaltilin.repositorios;

import com.tilin.portaltilin.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    public Usuario buscarUsuarioPorUsuario(@Param("usuario") String usuario);

    @Query("SELECT MAX(id) FROM Usuario")
    public Long ultimoUsuario();

}