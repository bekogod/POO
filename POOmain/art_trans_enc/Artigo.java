package art_trans_enc;

import java.io.Serializable;
import java.util.Objects;

public abstract class Artigo implements Serializable{
    private static int nartigos = 0;
    private int id;
    private int nDonos;
    private double estadoUtilizacao;
    private String descricao;
    private String marca;
    private double precoBase;
    private int idTransportadora;
    private int idVendedor;

    public Artigo(){
        this.id = ++nartigos;
    }

    public Artigo(int nDonos, double estadoUtilizacao, String descricao, String marca, double precoBase, int idTransportadora, int idVendedor) {
        this.id = ++nartigos;
        this.nDonos = nDonos;
        this.estadoUtilizacao = estadoUtilizacao;
        this.descricao = descricao;
        this.marca = marca;
        this.precoBase = precoBase;
        this.idTransportadora = idTransportadora;
        this.idVendedor = idVendedor;
    }

    public Artigo(Artigo a){
        this.id = a.getId();
        this.nDonos = a.getNDonos();
        this.estadoUtilizacao = a.getEstadoUtilizacao();
        this.descricao = a.getDescricao();
        this.marca = a.getMarca();
        this.precoBase = a.getPrecoBase();
        this.idTransportadora = a.getIdTransportadora();
        this.idVendedor = a.getIdVendedor();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNDonos() {
        return this.nDonos;
    }

    public void setNDonos(int nDonos) {
        this.nDonos = nDonos;
    }

    public double getEstadoUtilizacao() {
        return this.estadoUtilizacao;
    }

    public void setEstadoUtilizacao(double estadoUtilizacao) {
        this.estadoUtilizacao = estadoUtilizacao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecoBase() {
        return this.precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public int getIdTransportadora() {
        return this.idTransportadora;
    }

    public void setIdTransportadora(int idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    public int getIdVendedor() {
        return this.idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

   
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Artigo)) {
            return false;
        }
        Artigo artigo = (Artigo) o;
        return nDonos == artigo.nDonos && estadoUtilizacao == artigo.estadoUtilizacao && Objects.equals(descricao, artigo.descricao) && Objects.equals(marca, artigo.marca) && precoBase == artigo.precoBase && idTransportadora == artigo.idTransportadora;
    }


    public abstract int hashCode();

    public abstract String toString();

    public abstract Artigo clone();

    public abstract double precoFinalVendedor();


    public boolean isNovo (){
        boolean result  = false;
        if (this.getNDonos() == 0)
            result = true;
        return result;
    }

    public static void setNArtigos(int n){
        nartigos = n;
    }
}