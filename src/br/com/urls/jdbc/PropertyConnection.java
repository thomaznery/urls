package br.com.urls.jdbc;

public enum PropertyConnection {
    URL("jdbc:mysql://localhost/urls_data"),
    USERNAME("root"),
    PASSWORD("root");
    
    private String value;
    
    private PropertyConnection(String v){
        this.value = v;
    }

    public String getValue() {
        return value;
    }
    
    
}