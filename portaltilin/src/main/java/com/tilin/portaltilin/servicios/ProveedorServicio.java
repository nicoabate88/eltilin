package com.tilin.portaltilin.servicios;

import com.tilin.portaltilin.entidades.Compra;
import com.tilin.portaltilin.entidades.Pago;
import com.tilin.portaltilin.entidades.Proveedor;
import com.tilin.portaltilin.excepciones.MiException;
import com.tilin.portaltilin.repositorios.CompraRepositorio;
import com.tilin.portaltilin.repositorios.PagoRepositorio;
import com.tilin.portaltilin.repositorios.ProveedorRepositorio;
import com.tilin.portaltilin.util.ProveedorComparador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private PagoRepositorio pagoRepositorio;
    @Autowired
    private CompraRepositorio compraRepositorio;
    @Autowired
    private CuentapServicio cuentapServicio;
    

    @Transactional
    public void crearProveedor(String nombre, Long cuit, String localidad, String direccion, Long telefono, String email) throws MiException {

        validarDatos(nombre);

        Proveedor proveedor = new Proveedor();

        String nombreMayusculas = nombre.toUpperCase();
        String localidadMayusculas = localidad.toUpperCase();
        String direccionMayusculas = direccion.toUpperCase();

        proveedor.setNombre(nombreMayusculas);
        proveedor.setCuit(cuit);
        proveedor.setLocalidad(localidadMayusculas);
        proveedor.setDireccion(direccionMayusculas);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setFechaAlta(new Date());

        proveedorRepositorio.save(proveedor);
        
        cuentapServicio.crearCuenta(buscarUltimo());
    }

    public Proveedor buscarProveedor(Long id) {

        return proveedorRepositorio.getById(id);

    }

    public ArrayList<Proveedor> bucarProveedores() {

        ArrayList<Proveedor> listaProveedores = new ArrayList();

        listaProveedores = (ArrayList<Proveedor>) proveedorRepositorio.findAll();

        return listaProveedores;
    }

    public Long buscarUltimo() {

        Long idProveedor = proveedorRepositorio.ultimoProveedor();

        return idProveedor;

    }

    public ArrayList<Proveedor> buscarProveedoresIdDesc() {

        ArrayList<Proveedor> listaProveedores = bucarProveedores();

        Collections.sort(listaProveedores, ProveedorComparador.ordenarIdDesc); //ordena de forma DESC los ID, de mayor a menor

        return listaProveedores;

    }

    public ArrayList<Proveedor> buscarProveedoresNombreAsc() {

        ArrayList<Proveedor> listaProveedores = bucarProveedores();

        Collections.sort(listaProveedores, ProveedorComparador.ordenarNombreAsc); //ordena por nombre alfabetico los nombres de clientes

        return listaProveedores;

    }

    @Transactional
    public void modificarProveedor(Long id, String nombre, Long cuit, String localidad, String direccion, Long telefono, String email) {

        Proveedor proveedor = new Proveedor();

        Optional<Proveedor> prov = proveedorRepositorio.findById(id);
        if (prov.isPresent()) {
            proveedor = prov.get();
        }

        String nombreMayusculas = nombre.toUpperCase();
        String localidadMayusculas = localidad.toUpperCase();
        String direccionMayusculas = direccion.toUpperCase();

        proveedor.setNombre(nombreMayusculas);
        proveedor.setCuit(cuit);
        proveedor.setLocalidad(localidadMayusculas);
        proveedor.setDireccion(direccionMayusculas);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);

        proveedorRepositorio.save(proveedor);

    }

    @Transactional
    public void eliminarProveedor(Long id) throws MiException {

        ArrayList<Pago> listaPago = pagoRepositorio.buscarPagoIdProveedor(id);
        ArrayList<Compra> listaCompra = compraRepositorio.buscarCompraIdProveedor(id);

        if (listaPago.isEmpty() && listaCompra.isEmpty()) {

            proveedorRepositorio.deleteById(id);

            cuentapServicio.eliminarCuenta(id);
            
        } else {

            throw new MiException("El Proveedor no puede ser eliminado, tiene Compra o Pago asociado");
        }

    }

    public void validarDatos(String nombre) throws MiException {

        ArrayList<Proveedor> listaProveedores = new ArrayList();

        listaProveedores = bucarProveedores();

        for (Proveedor lista : listaProveedores) {
            if (lista.getNombre().equalsIgnoreCase(nombre)) {
                throw new MiException("El NOMBRE de Proveedor ya está registrado");
            }
        }
    }

}
