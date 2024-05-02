package art_trans_enc;

//import javax.print.attribute.SupportedValuesAttribute;

public class Sapatilhas extends Artigo{
    private int tamanho;
    private boolean atacadores;
    private String cor;
    private int anoDaColecao;


    public Sapatilhas(int nDonos, double estadoUtilizacao, String descricao, String marca, double precoBase, int idTransportadora, int idVendedor, int tamanho, boolean atacadores, String cor, int anoDaColecao) {
        super(nDonos, estadoUtilizacao, descricao, marca, precoBase, idTransportadora, idVendedor);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.anoDaColecao = anoDaColecao;
    }

    public Sapatilhas (Sapatilhas s){
        super(s);
        this.tamanho = s.getTamanho();
        this.atacadores = s.getAtacadores();
        this.cor = s.getCor();
        this.anoDaColecao = s.getAnoDaColecao();
    }


    public int getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isAtacadores() {
        return this.atacadores;
    }

    public boolean getAtacadores() {
        return this.atacadores;
    }

    public void setAtacadores(boolean atacadores) {
        this.atacadores = atacadores;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoDaColecao() {
        return this.anoDaColecao;
    }

    public void setAnoDaColecao(int anoDaColecao) {
        this.anoDaColecao = anoDaColecao;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + tamanho;
        result = prime * result + (atacadores ? 1231 : 1237);
        result = prime * result + ((cor == null) ? 0 : cor.hashCode());
        result = prime * result + anoDaColecao;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sapatilhas other = (Sapatilhas) obj;
        if (tamanho != other.tamanho)
            return false;
        if (atacadores != other.atacadores)
            return false;
        if (cor == null) {
            if (other.cor != null)
                return false;
        } else if (!cor.equals(other.cor))
            return false;
        if (anoDaColecao != other.anoDaColecao)
            return false;
        return true;
    }

    

    
    public String toString() {
        return "Sapatilhas [ id= " + getId() + "tamanho=" + tamanho + ", atacadores=" + atacadores + ", cor=" + cor + ", anoDaColecao="
                + anoDaColecao +  ", nDonos=" + getNDonos() + 
                ", estadoUtilizacao='" + getEstadoUtilizacao() + 
                ", descricao='" + getDescricao() + 
                ", marca='" + getMarca() + 
                ", precoBase='" + getPrecoBase() + 
                ", idTransportadora='" + getIdTransportadora() +
                ", Vendedor   " + getIdVendedor() + "]\n";
    }

    public Sapatilhas clone(){
        return new Sapatilhas(this);
    }

    public double precoFinalVendedor(){
        if (this.getNDonos() != 0){
            if ((this.getPrecoBase() - (this.getPrecoBase()/this.getNDonos()*this.getEstadoUtilizacao())) < this.getPrecoBase()*0.3){
                return this.getPrecoBase()*0.3;
            }
            return this.getPrecoBase() - (this.getPrecoBase()/this.getNDonos()*this.getEstadoUtilizacao());
        }
        return this.getPrecoBase();
    }
}
