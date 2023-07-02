package com.example.weather.model;

public class StandardError {
    private String cod;
    private String message;

     public StandardError(String cod, String message){
        this.cod = cod;
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass()!= obj.getClass()) return false;
        StandardError that = (StandardError) obj;
        return this.cod.equalsIgnoreCase(that.cod);
    }

    @Override
    public int hashCode(){
        return this.message.hashCode();
    }
}
