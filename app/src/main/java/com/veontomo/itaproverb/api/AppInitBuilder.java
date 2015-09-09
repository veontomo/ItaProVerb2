package com.veontomo.itaproverb.api;

/**
 * Builder of application initialization class.
 *
 * It takes care of initializing {@link AppInit}  correctly.
 */
public class AppInitBuilder {
    private String fileName;
    private String encoding;

    public AppInitBuilder setSource(String fileName){
        this.fileName = fileName;
        return this;
    }

    public AppInitBuilder setEncoding(String encoding){
        this.encoding = encoding;
        return this;
    }

    public AppInit build(){
        return new AppInit();
    }

}
