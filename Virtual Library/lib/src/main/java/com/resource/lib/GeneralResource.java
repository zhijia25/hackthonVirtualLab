package com.resource.lib;

public abstract class GeneralResource implements ResourceInterface {
    private String title;
    private String type;
    private String url;

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        String url = this.getUrl();
        String[] snaps = url.split("//");
        return snaps[snaps.length - 1];
    }

    public void fetchResource() {
        System.out.println("fetch " + type + " resource from " + url);
    }
}
