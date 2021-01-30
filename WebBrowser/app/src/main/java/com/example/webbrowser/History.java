package com.example.webbrowser;

public class History {
    private String url;
    private History back;
    private History forward;

    public History(String url){
        this.url = url;
        this.back = null;
        this.forward = null;
    };

    public History(History past, String url){
        this.url = url;
        past.forward = this;
        this.back = past;
        this.forward = null;
    }

    public History getBack() {
        return back;
    }

    public History getForward() {
        return forward;
    }

    public String getUrl() {
        return url;
    }
}
