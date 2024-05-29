
package com.tilin.portaltilin.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToOne
    private Proveedor proveedor;
    private String observacion;
    private Double importe;
    private String estado;
    @OneToMany
    private List<Valor> valor;
    @OneToOne
    private Usuario usuario;

    public Pago() {
    }

    public Pago(Long id, Date fecha, Proveedor proveedor, String observacion, Double importe, String estado, List<Valor> valor, Usuario usuario) {
        this.id = id;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.observacion = observacion;
        this.importe = importe;
        this.estado = estado;
        this.valor = valor;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Valor> getValor() {
        return valor;
    }

    public void setValor(List<Valor> valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", fecha=" + fecha + ", proveedor=" + proveedor + ", observacion=" + observacion + ", importe=" + importe + ", estado=" + estado + ", valor=" + valor + ", usuario=" + usuario + '}';
    }
    
    
    
    
}