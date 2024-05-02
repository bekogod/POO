//import java.lang.*;
import java.io.Serializable;
import java.util.*;

import art_trans_enc.Encomenda;

public class Utilizador implements Serializable{
    private static int n_utilizadores = 0;
    private int id; 
    private String email;
    private String nome;
    private String morada;
    private int NIF;
    private Set<Integer> stock_produtos;
    private Map<Integer, Integer> artigos_vendidos; /* Map<idArtigo, nEncomenda>*/
    private double receitaVendas;
    private Map<Encomenda, Double> faturas;

    public Utilizador(String email, String nome, String morada, int NIF){
        this.id = ++n_utilizadores;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.NIF = NIF;
        this.stock_produtos = new HashSet<>();
        this.artigos_vendidos = new HashMap<>();
        this.receitaVendas = 0;
        this.faturas = new HashMap<>();
    }

    public Utilizador(String email, String nome, String morada, int NIF, Set<Integer> stock_produtos, Map<Integer,Integer> artigos_vendidos, double receitaVendas, Map<Encomenda, Double> faturas) {
        this.id = ++n_utilizadores;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.NIF = NIF;
        this.stock_produtos = new HashSet<>(stock_produtos);
        this.artigos_vendidos = new HashMap<>(artigos_vendidos);
        this.receitaVendas = receitaVendas;
        this.faturas = new HashMap<>(faturas);
    }

    public Utilizador (Utilizador x){
        this.id = x.getId();
        this.email = x.getEmail();
        this.nome = x.getNome();
        this.morada = x.getMorada();
        this.NIF = x.getNIF();
        this.stock_produtos = x.getStock_produtos();
        this.artigos_vendidos = x.getArtigos_vendidos();
        this.receitaVendas = x.getReceitaVendas();
        this.faturas = x.getFaturas();   
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getNIF() {
        return this.NIF;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public Set<Integer> getStock_produtos() {
        return new HashSet<>(this.stock_produtos);
    }

    public void setStock_produtos(Set<Integer> stock_produtos) {
        this.stock_produtos = new HashSet<>(stock_produtos);
    }

    public Map<Integer,Integer> getArtigos_vendidos() {
        return new HashMap<>(this.artigos_vendidos);
    }

    public void setArtigos_vendidos(Map<Integer,Integer> artigos_vendidos) {
        this.artigos_vendidos = new HashMap<>(artigos_vendidos);
    }

    public double getReceitaVendas() {
        return this.receitaVendas;
    }

    public void setReceitaVendas(double receitaVendas) {
        this.receitaVendas = receitaVendas;
    }

    public Map<Encomenda, Double> getFaturas() {
        Map<Encomenda, Double> novoMap = new HashMap<>();

        for (Map.Entry<Encomenda, Double> fatura : this.faturas.entrySet()) {
            novoMap.put(fatura.getKey().clone(), fatura.getValue());
        }
        return novoMap;
    }

    public void setFaturas(Map<Encomenda, Double> faturas) {
        this.faturas = new HashMap<>(faturas);
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Utilizador)) {
            return false;
        }
        Utilizador utilizador = (Utilizador) o;
        return Objects.equals(email, utilizador.email) && Objects.equals(nome, utilizador.nome) && Objects.equals(morada, utilizador.morada) && NIF == utilizador.NIF && Objects.equals(stock_produtos, utilizador.stock_produtos) && Objects.equals(artigos_vendidos, utilizador.artigos_vendidos) && receitaVendas == utilizador.receitaVendas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nome, morada, NIF, stock_produtos, artigos_vendidos, receitaVendas, faturas);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", nome='" + getNome() + "'" +
            ", morada='" + getMorada() + "'" +
            ", NIF='" + getNIF() + "'" +
            ", stock_produtos='" + getStock_produtos() + "'" +
            ", artigos_vendidos='" + getArtigos_vendidos() + "'" +
            ", receitaVendas='" + getReceitaVendas() + "'" +
            ", faturas='" + getFaturas() + "'" +
            "}\n\n";
    }

    public void addArtigoStock(int idArt){
        try {
            this.stock_produtos.add(idArt);
        } catch (Exception e) {
            System.out.println("Erro    ------>     " + e.getMessage());
        } 
    }

    public void remArtigoStock(int idArt){
        this.stock_produtos.remove(idArt);
    }

    public void addArtigoVendido(int idArt, int idEnc){
        this.artigos_vendidos.put(idArt, idEnc);
    }

    public void addFatura(Encomenda enc, Double preco){
        this.faturas.put(enc, preco);
    }

    public static void setNUtilizadores(int n){
        n_utilizadores = n;
    }
}
