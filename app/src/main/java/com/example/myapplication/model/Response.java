package com.example.myapplication.model;

import java.util.List;

public class Response {
    public String name;
    public main main;
    public List<weather> weather;
    public String displayIconUrl(){
        if (weather.size()>0){
            return "https://openweathermap.org/img/wn/"+ weather.get(0).icon +"@2x.png";
        }
        else return  "";
    }
    public String displayTemperature(){
        return main.temp+ " \u2103";
    }

    public String displayHumidity(){
        return "Độ ẩm: "+main.humidity;
    }

    public String displayPressure(){
        return "Áp suất: "+main.pressure;
    }
    public class weather{
        public Integer id;
        public String main,icon;


    }

    public class main {
        public String temp, humidity, pressure;
    }
}
