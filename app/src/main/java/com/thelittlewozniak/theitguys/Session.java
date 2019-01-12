package com.thelittlewozniak.theitguys;

import com.thelittlewozniak.theitguys.pojo.Utilisateur;

/**
 * Created by natha on 1/12/2019.
 */

public class Session {
    private static Session instance;
    
    private Utilisateur user;


    public Utilisateur getUser(){return user;}

    public void setUser(Utilisateur user){this.user=user;}

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
            instance.user=null;
        }
        return instance;
    }


    private Session(){}

}
