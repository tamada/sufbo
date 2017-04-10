package com.github.sufbo.entities.maven;

import java.util.Comparator;

public class ArtifactComparator implements Comparator<Artifact>{
    private VersionComparator comparator = new VersionComparator();

    @Override
    public int compare(Artifact o1, Artifact o2) {
        Ids ids1 = o1.ids();
        Ids ids2 = o2.ids();
        return compare(ids1, ids2);
    }

    private int compare(Ids ids1, Ids ids2){
        if(ids1.sameArtifact(ids2)){
            Version version1 = ids1.version;
            Version version2 = ids2.version;
            return comparator.compare(version1, version2);
        }
        return ids1.compareTo(ids2);
    }

    public Artifact newer(Artifact artifact1, Artifact artifact2){
        if(compare(artifact1, artifact2) <= 0)
            return artifact1;
        return artifact2;
    }
}
