package br.com.alura.school.exceptions;

public class EmptyEnrollmentsException extends RuntimeException{
    public EmptyEnrollmentsException(String msg){
        super(msg);
    }
}
