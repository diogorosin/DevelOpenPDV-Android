package br.com.developen.pdv.utils;

import android.app.Application;
import android.content.Context;

import java.util.TimeZone;

import br.com.developen.pdv.repository.CatalogRepository;

public class App extends Application {


    private static App instance;


    public void onCreate() {

        super.onCreate();

        instance = this;

    }


    public void initialize(){

        //AJUSTA O TIMEZONE PARA CALCULOS DE DATA E HORA
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

        //INICIALIZA REPOSITORIOS
        CatalogRepository.getInstance();

    }


    public static App getInstance() {

        return instance;

    }

    public static Context getContext(){

        return instance;

    }

}