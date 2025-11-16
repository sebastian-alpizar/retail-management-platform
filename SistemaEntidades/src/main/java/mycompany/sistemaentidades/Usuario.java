package mycompany.sistemaentidades;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String password;
    
    public Usuario(String id, String password){
        this.id = id;
        this.password = password;
    }
    
    public String getId(){
        return id;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
