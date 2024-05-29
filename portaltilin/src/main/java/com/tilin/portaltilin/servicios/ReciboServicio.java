package com.tilin.portaltilin.servicios;

import com.tilin.portaltilin.entidades.Cliente;
import com.tilin.portaltilin.entidades.Recibo;
import com.tilin.portaltilin.entidades.Usuario;
import com.tilin.portaltilin.entidades.Valor;
import com.tilin.portaltilin.repositorios.ClienteRepositorio;
import com.tilin.portaltilin.repositorios.ReciboRepositorio;
import com.tilin.portaltilin.repositorios.UsuarioRepositorio;
import com.tilin.portaltilin.repositorios.ValorRepositorio;
import com.tilin.portaltilin.util.ReciboComparador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciboServicio {

    @Autowired
    private ReciboRepositorio reciboRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private TransaccionServicio transaccionServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ValorRepositorio valorRepositorio;
    @Autowired
    private ValorServicio valorServicio;

    @Transactional
    public void crearRecibo(Long idCliente, String observacion, Double importe, Long idUsuario, List<Valor> valores) throws ParseException {

        Cliente cliente = new Cliente();
        Optional<Cliente> cte = clienteRepositorio.findById(idCliente);
        if (cte.isPresent()) {
            cliente = cte.get();
        }

        Usuario usuario = new Usuario();
        Optional<Usuario> user = usuarioRepositorio.findById(idUsuario);
        if (user.isPresent()) {
            usuario = user.get();
        }

        String obsMayusculas = observacion.toUpperCase();
        double importeRedondeado = Math.round(importe * 100.0) / 100.0;  //redondeamos total solo a 2 decimales

        Recibo recibo = new Recibo();

        recibo.setCliente(cliente);
        recibo.setFecha(new Date());
        recibo.setObservacion(obsMayusculas);
        recibo.setImporte(importeRedondeado);
        recibo.setEstado("EJECUTADO");
        recibo.setUsuario(usuario);
        recibo.setValor(valores);

        reciboRepositorio.save(recibo);

        transaccionServicio.crearTransaccionRecibo(buscarUltimo());

    }

    public Recibo buscarRecibo(Long idRecibo) {

        return reciboRepositorio.getById(idRecibo);

    }
    
    public Recibo buscarReciboIdValor(Long idValor) {

        return reciboRepositorio.buscarReciboValor(idValor);

    }

    public ArrayList<Recibo> buscarRecibos() {

        ArrayList<Recibo> listaRecibos = new ArrayList();

        listaRecibos = reciboRepositorio.buscarRecibos();

        return listaRecibos;
    }

    @Transactional
    public void modificarRecibo(Long idRecibo, Long idCliente, String observacion, Long idUsuario) throws ParseException { //modificar Cliente u observacion de Recibo

        Recibo recibo = new Recibo();
        Optional<Recibo> rec = reciboRepositorio.findById(idRecibo);
        if (rec.isPresent()) {
            recibo = rec.get();
        }

        Cliente cliente = new Cliente();
        Optional<Cliente> cte = clienteRepositorio.findById(idCliente);
        if (cte.isPresent()) {
            cliente = cte.get();
        }

        Usuario usuario = new Usuario();
        Optional<Usuario> user = usuarioRepositorio.findById(idUsuario);
        if (user.isPresent()) {
            usuario = user.get();
        }

        String obsMayusculas = observacion.toUpperCase();

        recibo.setCliente(cliente);
        recibo.setObservacion(obsMayusculas);
        recibo.setUsuario(usuario);

        reciboRepositorio.save(recibo);

        transaccionServicio.modificarTransaccionRecibo(idRecibo);

    }

    @Transactional
    public void modificarReciboV(Long idRecibo, List<Valor> valores, Long idUsuario) { //modificar Valores de Recibo

        Recibo recibo = new Recibo();
        Optional<Recibo> rec = reciboRepositorio.findById(idRecibo);
        if (rec.isPresent()) {
            recibo = rec.get();
        }

        Usuario usuario = new Usuario();
        Optional<Usuario> user = usuarioRepositorio.findById(idUsuario);
        if (user.isPresent()) {
            usuario = user.get();
        }

        Double total = 0.0;

        for (Valor valor : valores) {
            total = total + valor.getImporte();
        }

        double totalRedondeado = Math.round(total * 100.0) / 100.0;  //redondeamos total solo a 2 decimales

        recibo.setImporte(totalRedondeado);
        recibo.setValor(valores);
        recibo.setUsuario(usuario);

        reciboRepositorio.save(recibo);

        transaccionServicio.modificarTransaccionRecibo(idRecibo);

    }

    @Transactional
    public void eliminarRecibo(Long idRecibo) {

        Recibo recibo = new Recibo();
        Optional<Recibo> rec = reciboRepositorio.findById(idRecibo);
        if (rec.isPresent()) {
            recibo = rec.get();
        }

        transaccionServicio.eliminarTransaccionRecibo(idRecibo);
        valorServicio.modificarValorPorEliminarRecibo(idRecibo);
        
        recibo.setEstado("ELIMINADO");
        recibo.setImporte(0.0);
        recibo.setValor(null);
        recibo.setCliente(null);
        reciboRepositorio.save(recibo);

    }

    public Long buscarUltimo() {

        return reciboRepositorio.ultimoRecibo();
    }

    public ArrayList<Recibo> buscarRecibosIdDesc() {

        ArrayList<Recibo> listaRecibos = buscarRecibos();

        Collections.sort(listaRecibos, ReciboComparador.ordenarIdDesc); //ordena de forma DESC los ID, de mayor a menor

        return listaRecibos;

    }

    public ArrayList<Recibo> buscarRecibosNombreAsc() {

        ArrayList<Recibo> listaRecibos = buscarRecibos();

        Collections.sort(listaRecibos, ReciboComparador.ordenarNombreAsc); //ordena de forma DESC los ID, de mayor a menor

        return listaRecibos;

    }

    public ArrayList<Recibo> buscarRecibosImporteDesc() {

        ArrayList<Recibo> listaRecibos = buscarRecibos();

        Collections.sort(listaRecibos, ReciboComparador.ordenarImporteDesc); //ordena de forma DESC los ID, de mayor a menor

        return listaRecibos;

    }
    
       public ArrayList<Recibo> buscarRecibosFechaDesc() {

        ArrayList<Recibo> listaRecibos = buscarRecibos();

        Collections.sort(listaRecibos, ReciboComparador.ordenarFechaDesc); //ordena de forma DESC los ID, de mayor a menor

        return listaRecibos;

    }

    public Date convertirFecha(String fecha) throws ParseException { //convierte fecha String a fecha Date
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.parse(fecha);
    }

}
