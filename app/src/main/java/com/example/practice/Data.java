package com.example.practice;

public class Data {
    private String tmenu;
    private String tcode;
    private String tname;
    private String tidx;
    private String tgbn;
    private String ticon;
    private String tvalid;

    public Data(String tmenu, String tcode, String tname, String tidx, String tgbn, String ticon, String tvalid){
        this.tmenu = tmenu;
        this.tcode = tcode;
        this.tname = tname;
        this.tidx = tidx;
        this.tgbn = tgbn;
        this.ticon = ticon;
        this.tvalid = tvalid;
    }

    public String getTmenu(){
        return this.tmenu;
    }
    public String getTcode(){
        return this.tcode;
    }
    public String getTname(){
        return this.tname;
    }
    public String getTidx(){
        return this.tidx;
    }
    public String getTgbn(){
        return this.tgbn;
    }
    public String getTicon(){
        return this.ticon;
    }
    public String getTvalid(){
        return this.tvalid;
    }
}
