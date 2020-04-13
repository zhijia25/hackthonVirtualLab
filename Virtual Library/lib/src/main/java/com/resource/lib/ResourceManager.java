package com.resource.lib;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {


    private List<PdfResource> pdf = new ArrayList<>();
    private List<WordResource> word = new ArrayList<>();
    private List<PptResource> ppt = new ArrayList<>();

    private List[] allResources =  new List[] {pdf, word, ppt};

    public ResourceManager() {

    }

    public List<String> getResourceNames() {
        List<String> stringList = new ArrayList<String>();
//        List[] allResources = this.getAllResources();
//        for(List<GeneralResource> generalResourceList : allResources) {
//            for(GeneralResource generalResource : generalResourceList) {
//                stringList.add(generalResource.getName());
//            }
//        }
//        return stringList;
        for(GeneralResource pdfResource : pdf) {
            stringList.add(pdfResource.getTitle());
        }
        return stringList;
    }

    public List<PdfResource> getPdf() {
        return pdf;
    }

    public List<WordResource> getWord() {
        return word;
    }

    public List<PptResource> getPpt() {
        return ppt;
    }

}
