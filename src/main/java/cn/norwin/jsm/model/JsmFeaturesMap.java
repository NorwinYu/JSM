package cn.norwin.jsm.model;

import cn.norwin.jsm.config.JsmFeatureConfig;

import java.util.HashMap;

public class JsmFeaturesMap {

    private HashMap<String, Long> featuresCounter;

    public JsmFeaturesMap() {
        featuresCounter = new HashMap<>();
        Init();
    }

    // init: add features into the map based on JsmFeature Config file
    public void Init() {
        for (String featureName : JsmFeatureConfig.getFeatureList()) {
            featuresCounter.put(featureName, (long) 0);
        }
    }

    public void increaseCount(String feature) {
        increaseCount(feature, (long)1);
    }

    public void increaseCount(String feature, Long count) {
        if (featuresCounter.containsKey(feature)) {
            featuresCounter.put(feature, featuresCounter.get(feature) + count);
        }
        else {
            featuresCounter.put(feature, count);
        }
    }

    public Long getCount(String feature) {
        if (featuresCounter.containsKey(feature)) {
            return featuresCounter.get(feature);
        }
        else {
            return (long) 0;
        }
    }
}
