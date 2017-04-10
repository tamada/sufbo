package com.github.sufbo.entities.maven;

import java.util.Comparator;

public class VersionComparator implements Comparator<Version>{
    @Override
    public int compare(Version version1, Version version2){
        String[] strings1 = version1.toString().split(".-");
        String[] strings2 = version2.toString().split(".-");
        return compare(strings1, strings2);
    }

    private int compare(String[] strings1, String[] strings2){
        for(int i = 0; i < strings1.length && i < strings2.length; i++){
            int result = compare(strings1[i], strings2[i]);
            if(result != 0)
                return result;
        }
        return Integer.compare(strings1.length, strings2.length);
    }

    private int compare(String s1, String s2){
        try{
            int v1 = Integer.parseInt(s1);
            int v2 = Integer.parseInt(s2);
            return Integer.compare(v1, v2);
        } catch(NumberFormatException e){
            return s1.compareTo(s2);
        }
    }
}
