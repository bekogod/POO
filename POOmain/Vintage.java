import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import art_trans_enc.Artigo;
import art_trans_enc.Encomenda;
import art_trans_enc.Transportadora;
import art_trans_enc.Encomenda.Estado;

import java.io.*;

public class Vintage implements Serializable{
    private Map<Integer, Artigo> artigos;
    private Map<Integer, Utilizador> utilizadores;
    private Map<Integer, Encomenda> encomendas;
    private Map<Integer, Transportadora> transportadoras;
    private LocalDate data = LocalDate.of(2023, 5, 6);


    public Vintage() {
        this.artigos = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.encomendas = new HashMap<>();
        this.transportadoras = new HashMap<>();
    }
    

    public Vintage(Map<Integer,Artigo> artigos, Map<Integer,Utilizador> utilizadores, Map<Integer,Encomenda> encomendas, Map<Integer,Transportadora> transportadoras) {
        this.artigos = new HashMap<>(artigos);
        this.utilizadores = new HashMap<>(utilizadores);
        this.encomendas = new HashMap<>(encomendas);
        this.transportadoras = new HashMap<>(transportadoras);
    }


    public Map<Integer,Artigo> getArtigos() {
        Map<Integer,Artigo> novoMap = new HashMap<>();

        for (Map.Entry<Integer,Artigo> par : this.artigos.entrySet()) {
            novoMap.put(par.getKey(), par.getValue().clone());
        }
        return novoMap;
    }

    public void setArtigos(Map<Integer,Artigo> artigos) {
        this.artigos = new HashMap<>(artigos);
    }

    public Map<Integer,Utilizador> getUtilizadores() {
        Map<Integer,Utilizador> novoMap = new HashMap<>();

        for (Map.Entry<Integer,Utilizador> par : this.utilizadores.entrySet()) {
            novoMap.put(par.getKey(), par.getValue().clone());
        }
        return novoMap;
    }

    public void setUtilizadores(Map<Integer,Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public Map<Integer,Encomenda> getEncomendas() {
        Map<Integer,Encomenda> novoMap = new HashMap<>();

        for (Map.Entry<Integer,Encomenda> par : this.encomendas.entrySet()) {
            novoMap.put(par.getKey(), par.getValue().clone());
        }
        return novoMap;
    }

    public void setEncomendas(Map<Integer,Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public Map<Integer,Transportadora> getTransportadoras() {
        Map<Integer,Transportadora> novoMap = new HashMap<>();

        for (Map.Entry<Integer,Transportadora> par : this.transportadoras.entrySet()) {
            novoMap.put(par.getKey(), par.getValue().clone());
        }
        return novoMap;
    }

    public void setTransportadoras(Map<Integer,Transportadora> transportadoras) {
        this.transportadoras = transportadoras;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
            " artigos='" + getArtigos() + "'" +
            ", utilizadores='" + getUtilizadores() + "'" +
            ", encomendas='" + getEncomendas() + "'" +
            ", transportadoras='" + getTransportadoras() + "'" +
            "}";
    }

    public void addTransportadora(Transportadora t){
        transportadoras.put(t.getId(), t);
    }

    public boolean validUserEmail(String email){
        for (Utilizador u : this.getUtilizadores().values()) {
            if (u.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public Utilizador userByEmail(String email){
        for (Utilizador u : this.getUtilizadores().values()) {
            if (u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }

    public Artigo artigoById(int idArt){
        return (this.artigos.get(idArt));
    }

    public void addArtigo(Artigo art){
        this.artigos.put(art.getId(), art);
        Utilizador u = this.utilizadores.get(art.getIdVendedor());
        u.addArtigoStock(art.getId());
        updateUtilizador(u.getId(), u);
    }

    public void addUser(Utilizador newUser){
        this.utilizadores.put(newUser.getId(), newUser);
    }


    public int stringToTransp(String transp) {
        for (Transportadora t : this.getTransportadoras().values()) {
            if (t.getNome().equals(transp)){
                return t.getId();
            }
        }
        return 0;
    }

    public Utilizador maiorUser(){
        double total = -1;
        Utilizador maiorUser = null;
        for (Utilizador u : utilizadores.values()) {
            if (total < u.getReceitaVendas()){
                maiorUser = u;
                total = u.getReceitaVendas();
            }
        }
        return maiorUser;
    }

    public Set<Artigo> artigosDisponiveis(Utilizador user_atual){
        Set<Artigo> lista = new HashSet<>();
        for (Utilizador u : this.utilizadores.values()) {
            if (!u.equals(user_atual)){
                for (int idArt : u.getStock_produtos()) {
                    lista.add(this.artigoById(idArt));
                }
            }
        }
        return lista;
    }

    public void updateUtilizador(int idU , Utilizador u){
        this.utilizadores.replace(idU, u);
    }


    public void updateEncomenda(int idEnc ,Encomenda enc){
        this.encomendas.replace(idEnc, enc);
    }

    public void addEncomenda(Encomenda enc) {
        this.encomendas.put(enc.getId(), enc);
    }

    public void addArtigotoEnc(Artigo art, Encomenda enc){
        enc.addArtigo(art);
        this.updateEncomenda(enc.getId(), enc);
    }

    public void remArtigofromEnc(int idArt, Encomenda enc){
        enc.remArtigo(idArt);
        this.updateEncomenda(enc.getId(), enc);
    }

    public double lucroVintage(){
        double total = 0;
        for (Encomenda enc : this.encomendas.values()) {
            if (!enc.getEstado().equals(Estado.PENDENTE)) {
                total += enc.getTaxaVintage();
            }    
        }
        return total;
    }

    public Transportadora maiorTransportadora(){
        Map<Transportadora, Double> trans_receita = new HashMap<>();
        for (Transportadora t : this.transportadoras.values()) {
            trans_receita.put(t, 0.0);
        }
        
        for (Encomenda enc : encomendas.values()) {
            for (Map.Entry<Integer, Integer> dimensoes : enc.getDimensoes().entrySet()) {
                Transportadora t = this.transportadoras.get(dimensoes.getKey());
                if (dimensoes.getValue() == 1){
                    trans_receita.replace(t, trans_receita.get(t) + Transportadora.getVbS() * t.getMargemDeLucro() * (1 + 0.23) * 0.9);
                }
                else {
                    if (dimensoes.getValue() < 6){
                        trans_receita.replace(t, trans_receita.get(t) + Transportadora.getVbM() * t.getMargemDeLucro() * (1 + 0.23) * 0.9);
                    }
                    else {
                        trans_receita.replace(t, trans_receita.get(t) + Transportadora.getVbL() * t.getMargemDeLucro() * (1 + 0.23) * 0.9);
                    }
                }
            }    
        }

        double max = 0;
        Transportadora maior = null;
        for (Map.Entry<Transportadora, Double> par : trans_receita.entrySet()) {
            if (par.getValue() > max){
                maior = par.getKey();
                max = par.getValue();
            }
        }

        return maior;
    }

    public Set<Encomenda> encEmitidos(String email){
        Set<Encomenda> encVendedor = new HashSet<>();
        Utilizador u = this.userByEmail(email);
        for (Integer idEnc : u.getArtigos_vendidos().values()) {
            encVendedor.add(encomendas.get(idEnc));
        }
        return encVendedor;
    }
    
    public void processamento(){
        for (Encomenda enc : encomendas.values()) {
            if (enc.getEstado().equals(Estado.FINALIZADA)){
                enc.calculoPrecoExpedicao(transportadoras);
                for (Artigo art : enc.getArtigos().values()) {
                    int idArt = art.getId();
                    Utilizador u = utilizadores.get(art.getIdVendedor());
                    u.remArtigoStock(idArt);
                    u.addArtigoVendido(idArt, enc.getId());
                    u.setReceitaVendas(art.precoFinalVendedor() + u.getReceitaVendas());
                    updateUtilizador(u.getId(), u);
                }
                Utilizador comprador = utilizadores.get(enc.getComprador());
                comprador.addFatura(enc, enc.precoFinal());
                updateUtilizador(enc.getComprador(), comprador);
                enc.setEstado(Estado.EXPEDIDA);
            }
        }
    }

    public void timeTravel(int dias){
        setData(data.plusDays(dias));
    }

    public void guardaEstado(String nome_ficheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nome_ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public Vintage carregaEstado(String nome_ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nome_ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Vintage vintage = (Vintage) ois.readObject();
        ois.close();
        return vintage;
    }
}
