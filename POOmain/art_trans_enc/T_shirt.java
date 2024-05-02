package art_trans_enc;

public class T_shirt extends Artigo {
    public enum Tamanho {
        NaT, // Not a Tamanho
        S, // Small
        M, // Medium
        L, // Large
        XL // Extra Large
    }

    public enum Padrao {
        NaP, //not a padrao
        LISO,
        RISCAS,
        PALMEIRAS
    }

    private Tamanho tamanho;
    private Padrao padrao;

    public T_shirt(int nDonos, double estadoUtilizacao, String descricao, String marca,
            double precoBase, int idTransportadora, int idVendedor, Tamanho tamanho, Padrao padrao) {
        super(nDonos, estadoUtilizacao, descricao, marca, precoBase, idTransportadora, idVendedor);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public T_shirt(T_shirt t) {
        super(t);
        this.tamanho = t.getTamanho();
        this.padrao = t.getPadrao();

    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Padrao getPadrao() {
        return padrao;
    }

    public void setPadrao(Padrao padrao) {
        this.padrao = padrao;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
        result = prime * result + ((padrao == null) ? 0 : padrao.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        T_shirt other = (T_shirt) obj;
        if (tamanho != other.tamanho)
            return false;
        if (padrao != other.padrao)
            return false;
        return true;
    }

    public String toString() {
        return "T_shirt [ id= " + getId() + "tamanho=" + tamanho + ", padrao=" + padrao + ", nDonos=" + getNDonos() +
                ", estadoUtilizacao='" + getEstadoUtilizacao() +
                ", descricao='" + getDescricao() +
                ", marca='" + getMarca() +
                ", precoBase='" + getPrecoBase() +
                ", idTransportadora='" + getIdTransportadora() + "]\n";
    }

    public T_shirt clone() {
        return new T_shirt(this);
    }

    public double precoFinalVendedor() {
        // preco t shirt usadas nao lisas
        if (this.getPadrao() != Padrao.LISO && this.getNDonos() != 0)
            return (this.getPrecoBase() * 0.5);
        // preco t shirt nova ou lisa
        return this.getPrecoBase();
    }

}
