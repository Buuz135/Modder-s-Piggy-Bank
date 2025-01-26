package com.buuz135.modderspiggybank;

import java.util.List;

public record AuthorPiggyBank(String author, int primary_color, int secondary_color, List<String> alternate, List<Link> links) {

    public record Link (String type, String url){

    }
}
