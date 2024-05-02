package art_trans_enc;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Encomenda implements Serializable{
    
    public enum Estado{
        PENDENTE,
        FINALIZADA,
        EXPEDIDA
    }
    
    private static int nEncomendas=0;
    private int id;
    private LocalDate data;
    private int comprador;
    private Map<Integer ,Artigo> artigos;
    private Map<Integer,Integer> dimensoes;
    private double precoArtigos;
    private double taxaVintage;
    private double precoExpedicao;
    private Estado estado;

    public Encomenda(LocalDate data, int comprador) {
        this.id = ++nEncomendas;
        this.data = data;
        this.comprador = comprador;
        this.artigos = new HashMap<>();
        this.dimensoes = new HashMap<>();
        this.precoArtigos = 0;
        this.taxaVintage = 0;
        this.precoExpedicao = 0;
        this.estado = Estado.PENDENTE;
    }

    public Encomenda(Encomenda enc) {
        this.id = enc.getId();
        this.data = enc.getData();
        this.comprador = enc.getComprador();
        this.artigos = new HashMap<>(enc.getArtigos());
        this.dimensoes = new HashMap<>(enc.getDimensoes());
        this.precoArtigos = enc.getPrecoArtigos();
        this.taxaVintage = enc.getTaxaVintage();
        this.precoExpedicao = enc.getPrecoExpedicao();
        this.estado = enc.getEstado();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getComprador() {
        return this.comprador;
    }

    public void setComprador(int comprador) {
        this.comprador = comprador;
    }

    public Map<Integer, Artigo> getArtigos() {
        Map<Integer, Artigo> novo = new HashMap<>();
        for (Map.Entry<Integer, Artigo> art : artigos.entrySet()) {
            novo.put(art.getKey(), art.getValue().clone());
        }
        return novo;
    }

    public void setArtigos(Map<Integer, Artigo> artigos) {
        this.artigos = new HashMap<>(artigos);
    }

    public Map<Integer,Integer> getDimensoes() {
        return new HashMap<>(this.dimensoes);
    }

    public void setDimensoes(Map<Integer,Integer> dimensoes) {
        this.dimensoes = new HashMap<>(dimensoes);
    }

    public double getPrecoArtigos() {
        return this.precoArtigos;
    }

    public void setPrecoArtigos(double precoArtigos) {
        this.precoArtigos = precoArtigos;
    }

    public double getTaxaVintage() {
        return this.taxaVintage;
    }

    public void setTaxaVintage(double taxaVintage) {
        this.taxaVintage = taxaVintage;
    }

    public double getPrecoExpedicao() {
        return this.precoExpedicao;
    }

    public void setPrecoExpedicao(double precoExpedicao) {
        this.precoExpedicao = precoExpedicao;
    }

    public Estado getEstado(){
        return this.estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Encomenda)) {
            return false;
        }
        Encomenda encomenda = (Encomenda) o;
        return id == encomenda.id && Objects.equals(data, encomenda.data) && comprador == encomenda.comprador && Objects.equals(artigos, encomenda.artigos) && Objects.equals(dimensoes, encomenda.dimensoes) && precoArtigos == encomenda.precoArtigos && taxaVintage == encomenda.taxaVintage && precoExpedicao == encomenda.precoExpedicao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, comprador, artigos, dimensoes, precoArtigos, taxaVintage, precoExpedicao);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", data='" + getData() + "'" +
            ", comprador='" + getComprador() + "'" +
            ", artigos='" + getArtigos() + "'" +
            ", dimensoes='" + getDimensoes() + "'" +
            ", precoArtigos='" + getPrecoArtigos() + "'" +
            ", taxaVintage='" + getTaxaVintage() + "'" +
            ", precoExpedicao='" + getPrecoExpedicao() + "'" +
            ", ESTADO='" + getEstado() + "'" +
            "}\n";
    }

    public Encomenda clone(){
        return new Encomenda(this);
    }
    
    public void addArtigo(Artigo art){
        this.artigos.put(art.getId(), art);
        int trans = art.getIdTransportadora();
        if (dimensoes.containsKey(trans)){
            int oldvalue = dimensoes.get(trans);
            dimensoes.replace(trans, ++oldvalue);
        }
        else{
            dimensoes.put(trans, 1);
        }
        precoArtigos += art.precoFinalVendedor();
        if (art.isNovo()){
            taxaVintage += 0.5;
        }
        else{
            taxaVintage += 0.25;
        }
    }

    public void remArtigo(int idArt){
        Artigo art = artigos.get(idArt);
        int trans = art.getIdTransportadora();
        int oldvalue = dimensoes.get(trans);
        dimensoes.replace(trans, --oldvalue);
        if (dimensoes.get(trans) == 0){
            dimensoes.remove(trans);
        }
        precoArtigos -= art.precoFinalVendedor();
        if (art.isNovo()){
            taxaVintage -= 0.5;
        }
        else{
            taxaVintage -= 0.25;
        }
        artigos.remove(idArt);
    }

    public double precoFinal(){
        return this.getPrecoArtigos() + this.getTaxaVintage() + this.getPrecoExpedicao();
    }

    public void calculoPrecoExpedicao(Map<Integer, Transportadora> trans){
        List<Double> custosPorPackage = new ArrayList<>();
        for (Map.Entry<Integer, Integer> pacote : this.dimensoes.entrySet()) {
            Transportadora t = trans.get(pacote.getKey());
            if (pacote.getValue() > 0){
                if (pacote.getValue() == 1){
                    custosPorPackage.add(Transportadora.getVbS() * t.getMargemDeLucro() * (1 + 0.23) * 0.9);
                }
                else {
                    if (pacote.getValue() < 6){
                        custosPorPackage.add(Transportadora.getVbM() * t.getMargemDeLucro() * (1 + 0.23) * 0.9);
                    }
                    else {
                        custosPorPackage.add(Transportadora.getVbL() * t.getMargemDeLucro() * (1 + 0.23) * 0.9);
                    }
                }
            }    
        }
        double total = custosPorPackage.stream().mapToDouble(f -> f.doubleValue()).sum();
        this.setPrecoExpedicao(total);
    }

    public static void setNEncomendas(int n){
        nEncomendas = n;
    }
}
