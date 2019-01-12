package com.thelittlewozniak.theitguys.pojo;

/**
 * Created by natha on 1/12/2019.
 */

public class Utilisateur {

    private int id;

    private String pseudo;

    private String motDePasse;

    private boolean sexe;

    private String ville;


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPseudo() {return pseudo;}

    public void setPseudo(String pseudo) {this.pseudo = pseudo;}

    public String getMotDePasse() {return motDePasse;}

    public void setMotDePasse(String motDePasse) {this.motDePasse = motDePasse;}

    public boolean getSexe(){return sexe;}

    public void setSexe(boolean sexe){this.sexe=sexe;}
}
