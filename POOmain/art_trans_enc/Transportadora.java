package art_trans_enc;

import java.io.Serializable;

public class Transportadora implements Serializable{
    private static int nTransportadoras = 0;
    private int id;
    private String nome;
    private static double vbS = 2.5;
    private static double vbM = 4.7;
    private static double vbL = 6;
    private double margemDeLucro;


    public Transportadora(Transportadora t) {
        this.id =  t.getId();
        this.margemDeLucro =  t.getMargemDeLucro();
        this.nome = t.getNome();
    }

    public Transportadora(double margemDeLucro, String nome) {
        this.id = ++nTransportadoras;
        this.margemDeLucro = margemDeLucro;
        this.nome = nome;
    }

    public static int getnTransportadoras() {
        return nTransportadoras;
    }

    public static void setnTransportadoras(int nTransportadoras) {
        Transportadora.nTransportadoras = nTransportadoras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public static double getVbS() {
        return vbS;
    }

    public static void setVbS(double vbS) {
        Transportadora.vbS = vbS;
    }

    public static double getVbM() {
        return vbM;
    }

    public static void setVbM(double vbM) {
        Transportadora.vbM = vbM;
    }

    public static double getVbL() {
        return vbL;
    }

    public static void setVbL(double vbL) {
        Transportadora.vbL = vbL;
    }

    public double getMargemDeLucro() {
        return margemDeLucro;
    }

    public void setMargemDeLucro(double margemDeLucro) {
        this.margemDeLucro = margemDeLucro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        long temp;
        temp = Double.doubleToLongBits(margemDeLucro);
        result = prime * result + (int) (temp ^ (temp >>>   2));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transportadora other = (Transportadora) obj;
        if (id != other.id)
            return false;
        if (Double.doubleToLongBits(margemDeLucro) != Double.doubleToLongBits(other.margemDeLucro))
            return false;
        return true;
    }

    public Transportadora clone (){
        return new Transportadora(this);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", margemDeLucro='" + getMargemDeLucro() + "'" +
            "}\n";
    }
    

}
