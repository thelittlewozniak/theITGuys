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

    private double latitude;

    private double longitude;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean getSexe() {return sexe;}

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
