import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import art_trans_enc.Artigo;
import art_trans_enc.Encomenda;
import art_trans_enc.Malas;
import art_trans_enc.Sapatilhas;
import art_trans_enc.T_shirt;
import art_trans_enc.Transportadora;
import art_trans_enc.Encomenda.Estado;
import art_trans_enc.T_shirt.Padrao;
import art_trans_enc.T_shirt.Tamanho;

public class TextUI implements Serializable{
    // o modelo
    private Vintage model;

    // scnanner
    private Scanner sc;

    public TextUI() {
        this.model = new Vintage();
        sc = new Scanner(System.in);
        try {
            this.model = this.model.carregaEstado("estado.txt");
        } 
        catch (FileNotFoundException e) {
            System.out.println("O ficheiro de load não existe. Inicialização vazia.");
        }
        catch (ClassNotFoundException e){
            System.out.println("Classe inválida");
        }
        catch (IOException e){
            System.out.println("Erro a aceder ao ficheiro      " + e.getMessage());
        }

        Utilizador.setNUtilizadores(model.getUtilizadores().size());
        Artigo.setNArtigos(model.getArtigos().size());
        Encomenda.setNEncomendas(model.getEncomendas().size());
        Transportadora.setnTransportadoras(model.getTransportadoras().size());
            
    }

    public Vintage getModel(){
        return this.model;
    }

    public void run() {
        NewMenu menu = new NewMenu(new String[] {
                "Login",
                "Criar User",
                "Admin"
        });

        menu.setHandler(1, () -> login());
        menu.setHandler(2, () -> criarUser());
        menu.setHandler(3, () -> admin());

        menu.run();
    }


    private void login() {
        
        System.out.println("Introduza o seu email: ");
        String email = sc.nextLine();
        if ((model.validUserEmail(email)) == false) {
            System.out.println("Este user não existe");
        }
        else {

            Utilizador user_atual = model.userByEmail(email);
            NewMenu menuUser = new NewMenu(new String[] {
                        "Print info",
                        "Criar Artigo",
                        "Criar Encomenda" });

            menuUser.setHandler(1, () -> System.out.println(user_atual));
            menuUser.setHandler(2, () -> criarArtigo(user_atual));
            menuUser.setHandler(3, () -> criarEncomenda(user_atual));

        menuUser.run();
        }
    }


    private void criarArtigo(Utilizador user_atual) {
        NewMenu menuCriar = new NewMenu(new String[] {
                "T-shirt",
                "Sapatilhas",
                "Malas" });

        menuCriar.setHandler(1, () -> criaTshirt(user_atual));
        menuCriar.setHandler(2, () -> criarSapatilha(user_atual));
        menuCriar.setHandler(3, () -> criarMalas(user_atual));

        menuCriar.run();

    }



    private void criaTshirt(Utilizador user_atual){

                System.out.println("Introduza a marca: ");
                String marca = sc.nextLine();

                System.out.println("Introduza uma descrição: ");
                String descricao = sc.nextLine();

                System.out.println("Introduza o numero de Donos: ");
                int nDonos = Integer.parseInt(sc.nextLine());

                double estadoUtilizacao;
                if(nDonos != 0) {
                    System.out.println("Introduza o estado de utilizacao (0-100): ");
                    estadoUtilizacao = Double.parseDouble(sc.nextLine());
                }
                else estadoUtilizacao = 100;

                System.out.println("Introduza o preço:");
                double preco = Double.parseDouble(sc.nextLine());
                String transp = "";
                System.out.println("Introduza a transportadora:");
                while (!model.getTransportadoras().keySet().contains(model.stringToTransp(transp))){
                    System.out.println("Certifique-se que a transportadora é válida!");
                    transp = sc.nextLine();
                }
                int idTransp = model.stringToTransp(transp);


                Tamanho tamanho = Tamanho.NaT;

                while (tamanho == Tamanho.NaT) {
                    System.out.println("Introduza o tamanho (S/M/L/XL): ");
                    String tm  = sc.nextLine();
                    tamanho = stringToTmh(tm);
                    if (tamanho == Tamanho.NaT)
                        System.out.println("O tamanho introduzido não é valido");

                }

                Padrao padrao = Padrao.NaP;

                while (padrao == Padrao.NaP){
                    System.out.println("Introduza o padrao (Liso/Riscas/Palmeiras): ");
                    String p = sc.nextLine();
                    padrao = stringToPdr(p);
                    if (padrao == Padrao.NaP)
                        System.out.println("O padrão introduzido não é válido");
                }

                T_shirt newT_shirt = new T_shirt(nDonos, estadoUtilizacao, descricao, marca,
                                preco, idTransp, user_atual.getId(), tamanho, padrao);
                
                model.addArtigo(newT_shirt);
                
            }

        
    private Padrao stringToPdr(String p) {
        if (p.equals("Liso")) {return Padrao.LISO;}
        if (p.equals("Riscas")) {return Padrao.RISCAS;}
        if (p.equals("Palmeiras")) {return Padrao.PALMEIRAS;}
        return null;
    }

    private Tamanho stringToTmh(String tm) {
        if (tm.equals("S")) {return Tamanho.S;}
        if (tm.equals("M")) {return Tamanho.M;}
        if (tm.equals("L")) {return Tamanho.L;}
        if (tm.equals("XL")) {return Tamanho.XL;}
        return null;
    }


    private void criarSapatilha(Utilizador user_atual) {

                System.out.println("Introduza a marca: ");
                String marca = sc.nextLine();

                System.out.println("Introduza uma descrição: ");
                String descricao = sc.nextLine();

                System.out.println("Introduza o numero de Donos: ");
                int nDonos = Integer.parseInt(sc.nextLine());

                double estadoUtilizacao;
                if(nDonos != 0) {
                    System.out.println("Introduza o estado de utilizacao (0-100): ");
                    estadoUtilizacao = Double.parseDouble(sc.nextLine());
                }
                else estadoUtilizacao = 100;

                System.out.println("Introduza o preço:");
                double preco = Double.parseDouble(sc.nextLine());

                String transp = "";
                System.out.println("Introduza a transportadora:");
                while (!model.getTransportadoras().keySet().contains(model.stringToTransp(transp))){
                    System.out.println("Certifique-se que a transportadora é válida!");
                    transp = sc.nextLine();
                }
                int idTransp = model.stringToTransp(transp);

                System.out.println("Introduza o tamanho:");
                int tam = Integer.parseInt(sc.nextLine());

                System.out.println("Possuiu atacadores[true/false]:");
                Boolean atacadores = sc.nextBoolean();

                System.out.println("Introduza a cor: ");
                String cor = sc.nextLine();
                while (cor.equals("")){
                    System.out.println("Introduza a cor: ");
                    cor = sc.nextLine();
                }

                System.out.println("Introduza o ano de coleção: ");
                int anoColecao = Integer.parseInt(sc.nextLine());

                
                Sapatilhas newSapatilhas = new Sapatilhas(nDonos, estadoUtilizacao, descricao, marca,
                                preco, idTransp, user_atual.getId(), tam, atacadores, cor, anoColecao);
                                
                model.addArtigo(newSapatilhas);


    }

    private void criarMalas(Utilizador user_atual) {
        
        System.out.println("Introduza a marca: ");
        String marca = sc.nextLine();

        System.out.println("Introduza uma descrição: ");
        String descricao = sc.nextLine();

        System.out.println("Introduza o numero de Donos: ");
        int nDonos = Integer.parseInt(sc.nextLine());

        double estadoUtilizacao;
        if(nDonos != 0) {
            System.out.println("Introduza o estado de utilizacao (0-100): ");
            estadoUtilizacao = Double.parseDouble(sc.nextLine());
        }
        else estadoUtilizacao = 100;

        System.out.println("Introduza o preço:");
        double preco = Double.parseDouble(sc.nextLine());

        String transp = "";
            System.out.println("Introduza a transportadora:");
            while (!model.getTransportadoras().keySet().contains(model.stringToTransp(transp))){
                System.out.println("Certifique-se que a transportadora é válida!");
                transp = sc.nextLine();
            }
            int idTransp = model.stringToTransp(transp);

        System.out.println("Introduza as dimensoes da mala:");
        double dim = Double.parseDouble(sc.nextLine());

        System.out.println("Introduza o material: ");
        String material = sc.nextLine();

        System.out.println("Introduza o ano de coleção: ");
        int anoColecao = Integer.parseInt(sc.nextLine());

        Malas newMala = new Malas(nDonos, estadoUtilizacao, descricao, marca,preco, idTransp, user_atual.getId(), dim, material, anoColecao);

        model.addArtigo(newMala);
    }




    private void criarEncomenda(Utilizador user_atual) {
        System.out.println(model.artigosDisponiveis(user_atual));
        Encomenda enc = new Encomenda(model.getData(), user_atual.getId());
        model.addEncomenda(enc);


        NewMenu menuEnc = new NewMenu(new String[] {
            "Adicionar artigo",
            "Ver encomenda",
            "Finalizar encomenda" });

        
        menuEnc.setHandler(1, () -> addArtigoEnc(enc));
        menuEnc.setHandler(2, () -> viewEncomenda(enc));
        menuEnc.setHandler(3, () -> finalizarEnc(enc)); 

        menuEnc.run();
    }



    private void addArtigoEnc(Encomenda enc) {
        System.out.println("Introduza o id do artigo: ");
        int idArt = Integer.parseInt(sc.nextLine());
        enc.addArtigo(model.artigoById(idArt));
    }

    private void viewEncomenda(Encomenda enc){
        System.out.println(enc.getArtigos());
        System.out.println(enc.getPrecoArtigos()+enc.getTaxaVintage());

        NewMenu menuViewEnc = new NewMenu(new String[] {
            "Remover artigo"});
        
            menuViewEnc.setHandler(1, () -> remArtigo(enc));

        menuViewEnc.run();

    }

    private void remArtigo(Encomenda enc) {
        System.out.println("Introduza o id do artigo: ");
        int idArt = Integer.parseInt(sc.nextLine());
        model.remArtigofromEnc(idArt, enc);
    }

    private void finalizarEnc(Encomenda enc) {
        System.out.println("Quer finalizar a sua encomenda: ");
        NewMenu menuFin = new NewMenu(new String[] {
            "Confimar"});
        
            menuFin.setHandler(1, () -> enc.setEstado(Estado.FINALIZADA));

        menuFin.run();
    }


    private void criarUser() {
        System.out.println("Introduza o seu email: ");
        String email = sc.nextLine();

        System.out.println("Introduza o seu nome: ");
        String nome = sc.nextLine();

        System.out.println("Introduza a sua morada: ");
        String morada = sc.nextLine();

        System.out.println("Introduza o seu NIF: ");
        int NIF = Integer.parseInt(sc.nextLine());

        Utilizador newUser = new Utilizador(email, nome, morada, NIF);

        model.addUser(newUser);
    }
    //    public Utilizador(String email, String nome, String morada, int NIF)

    private void admin() {
        NewMenu menuCriar = new NewMenu(new String[] {
            "Lista de Users",
            "Lista de Artigos",
            "Lista de Encomendas",
            "Lista de Transportadoras",
            "Utilizador que mais faturou",
            "Transportadora com maior volume de facturação",
            "Produtos emitidos por um vendedor",
            "Criar Transportadora",
            "Lucro Vintage",
            "Avanço no tempo"});

        menuCriar.setHandler(1, () -> gerirUsers());
        menuCriar.setHandler(2, () -> gerirArtigos());
        menuCriar.setHandler(3, () -> gerirEncomendas());
        menuCriar.setHandler(4, () -> gerirTransportadoras());
        menuCriar.setHandler(5, () -> maiorUser());
        menuCriar.setHandler(6, () -> maiorTransportadora());
        menuCriar.setHandler(7, () -> encEmitidos());
        menuCriar.setHandler(8, () -> criarTransp());
        menuCriar.setHandler(9, () -> lucroVintage());
        menuCriar.setHandler(10, () -> timeTravel());

    menuCriar.run();
    }

    private void criarTransp() {
        System.out.println("Introduza o seu email: ");
        String nome = sc.nextLine();

        System.out.println("Introduza a margem de lucro (percentagem): ");
        double margemDeLucro= Double.parseDouble(sc.nextLine());

        Transportadora newTransp = new Transportadora(margemDeLucro/100, nome);

        model.addTransportadora(newTransp);
    }

    
    private Object gerirUsers() {
        System.out.println(model.getUtilizadores());
        return null;
    }

    private Object gerirArtigos() {
        System.out.println(model.getArtigos());
        return null;
    }

    private Object gerirEncomendas() {
        System.out.println(model.getEncomendas());
        return null;
    }

    private Object gerirTransportadoras() {
        System.out.println(model.getTransportadoras());
        return null;
    }

    private Object maiorUser() {
        System.out.println(model.maiorUser());
        return null;
    }

    private Object maiorTransportadora() {
        System.out.println(model.maiorTransportadora());
        return null;
    }

    private Object encEmitidos() {
        System.out.println("Email do vendedor do qual pretende consultar encomendas?");
        String email = sc.nextLine();
        if (model.validUserEmail(email)){
            System.out.println(model.encEmitidos(email));
        }
        else {
            System.out.println("Não existe um utilizador com esse email na base de dados");
        }    
        return null;
    }


    private Object lucroVintage() {
        System.out.println(model.lucroVintage());
        return null;
    }

    private Object timeTravel() {
        System.out.println("Efetuar salto de quantos dias? ");
        int dias = Integer.parseInt(sc.nextLine());
        model.processamento();
        model.timeTravel(dias);
        System.out.println("Salto para  --->   " + model.getData());
        return null;
    }

}