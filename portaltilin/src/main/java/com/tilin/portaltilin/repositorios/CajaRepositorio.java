
package com.tilin.portaltilin.repositorios;

import com.tilin.portaltilin.entidades.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajaRepositorio extends JpaRepository<Caja, Long>{
    
}
