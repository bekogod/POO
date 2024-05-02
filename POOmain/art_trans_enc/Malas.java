package art_trans_enc;

public class Malas extends Artigo{
    private double dimensao;
    private String material;
    private int anoDaColecao;


    public Malas(int nDonos, double estadoUtilizacao, String descricao, String marca, double precoBase, int idTransportadora, int idVendedor, double dimensao, String material, int anoDaColecao) {
        super(nDonos, estadoUtilizacao, descricao, marca, precoBase, idTransportadora, idVendedor);
        this.dimensao = dimensao;
        this.material = material;
        this.anoDaColecao = anoDaColecao;
    }

    public Malas (Malas m){
        super(m);
        this.dimensao = m.getDimensao();
        this.material = m.getMaterial();
        this.anoDaColecao = m.getAnoDaColecao();
    }

    public double getDimensao() {
        return dimensao;
    }

    public void setDimensao(double dimensao) {
        this.dimensao = dimensao;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAnoDaColecao() {
        return anoDaColecao;
    }

    public void setAnoDaColecao(int anoDaColecao) {
        this.anoDaColecao = anoDaColecao;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(dimensao);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        result = prime * result + anoDaColecao;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Malas other = (Malas) obj;
        if (Double.doubleToLongBits(dimensao) != Double.doubleToLongBits(other.dimensao))
            return false;
        if (material == null) {
            if (other.material != null)
                return false;
        } else if (!material.equals(other.material))
            return false;
        if (anoDaColecao != other.anoDaColecao)
            return false;
        return true;
    }

    public String toString() {
        return "Malas [ id= " + getId() + "dimensao=" + dimensao + ", material=" + material + ", anoDaColecao=" + anoDaColecao +
        ", nDonos=" + getNDonos() + 
        ", estadoUtilizacao='" + getEstadoUtilizacao() + 
        ", descricao='" + getDescricao() + 
        ", marca='" + getMarca() + 
        ", precoBase='" + getPrecoBase() + 
        ", idTransportadora='" + getIdTransportadora() + "]\n";
    }

    public Malas clone(){
        return new Malas(this);
    }

    public double precoFinalVendedor(){
        if (!this.isNovo())
            return this.getPrecoBase()*((1- this.getDimensao()/15)*0.5);   //capacidade máxima 15l e desconto até 50%
        return this.getPrecoBase();
    }

    

    

}
