
package com.tilin.portaltilin.repositorios;

import com.tilin.portaltilin.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, Long>{
    
    @Query("SELECT MAX(id) FROM Proveedor")
    public Long ultimoProveedor();
    
    
    
}
