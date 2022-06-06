package skuc.io.skuciocore.seeder;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.csm.configuration.Configuration;
import skuc.io.skuciocore.models.csm.configuration.Context;
import skuc.io.skuciocore.models.csm.configuration.ThresholdsConfig;
import skuc.io.skuciocore.services.ContextService;

@Service
public class ContextSeeder {
    
    private final ContextService _contextService;

    @Autowired
    public ContextSeeder(ContextService contextService) {
        _contextService = contextService;
    }

    public void seed(String groupId) {
        var afhThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 20));
            put("movement", new ThresholdsConfig(2, 5));
            put("neighborhoodradius", new ThresholdsConfig(0, 50000));
            put("homeradius", new ThresholdsConfig(0, 5000));
        }};
        var afhConfiguration = new Configuration(afhThresholds, new HashMap<>());
        createContext("AFH", groupId, afhConfiguration);

        var ahThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 500));
            put("homeradius", new ThresholdsConfig(0, 50));
        }};
        var ahConfiguration = new Configuration(ahThresholds, new HashMap<>());
        createContext("AH", groupId, ahConfiguration);

        var ecoThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 500));
            put("homeradius", new ThresholdsConfig(0, 50));
        }};
        var ecoConfiguration = new Configuration(ecoThresholds, new HashMap<>());
        createContext("Eco", groupId, ecoConfiguration);

        var winterThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(15, 28));
            put("humidity", new ThresholdsConfig(20, 35));
            put("co2", new ThresholdsConfig(300, 1400));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 10));
            put("homeradius", new ThresholdsConfig(0, 20));
        }};
        var winterConfiguration = new Configuration(winterThresholds, new HashMap<>());
        createContext("Winter", groupId, winterConfiguration);

        var summerThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(24, 30));
            put("humidity", new ThresholdsConfig(30, 60));
            put("co2", new ThresholdsConfig(200, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 50000));
            put("homeradius", new ThresholdsConfig(0, 100));
        }};
        var summerConfiguration = new Configuration(summerThresholds, new HashMap<>());
        createContext("Summer", groupId, summerConfiguration);
  
        var dayThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 50000));
            put("homeradius", new ThresholdsConfig(0, 100));
        }};
        var dayConfiguration = new Configuration(dayThresholds, new HashMap<>());
        createContext("Day", groupId, dayConfiguration);

        var nightThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(15, 20));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 800));
            put("sound", new ThresholdsConfig(10, 30));
            put("movement", new ThresholdsConfig(2, 10));
            put("neighborhoodradius", new ThresholdsConfig(0, 0));
            put("homeradius", new ThresholdsConfig(0, 2));
        }};
        var nightConfiguration = new Configuration(nightThresholds, new HashMap<>());
        createContext("Night", groupId, nightConfiguration);
    }

    private void createContext(String name, String groupId, Configuration configuration) {
        var context = new Context(name, groupId, configuration);
        _contextService.create(context);
    }
}
