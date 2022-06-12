package skuc.io.skuciocore.seeder;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.csm.configuration.Configuration;
import skuc.io.skuciocore.models.csm.configuration.Context;
import skuc.io.skuciocore.models.csm.configuration.StatusConfig;
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
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var afhStatuses = new HashMap<String, StatusConfig>() {{
            put("windows", new StatusConfig("WindowsClosed"));
            put("boiler", new StatusConfig("BoilerOff"));
            put("lights", new StatusConfig("LightsOff"));
            put("washing", new StatusConfig("WashingOff"));
        }};
        var afhConfiguration = new Configuration(afhThresholds, afhStatuses);
        createContext("AFH", groupId, afhConfiguration);

        var ahThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 500));
            put("homeradius", new ThresholdsConfig(0, 50));
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var ahStatuses = new HashMap<String, StatusConfig>() {{
            put("boiler", new StatusConfig("BoilerOn"));
        }};
        var ahConfiguration = new Configuration(ahThresholds, ahStatuses);
        createContext("AH", groupId, ahConfiguration);

        var ecoThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 500));
            put("homeradius", new ThresholdsConfig(0, 50));
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var ecoStatuses = new HashMap<String, StatusConfig>() {{
            put("ac", new StatusConfig("ACOff"));
            put("heating", new StatusConfig("HeatingOff"));
            put("boiler", new StatusConfig("BoilerOff"));
            put("lights", new StatusConfig("LightsOff"));
            put("washing", new StatusConfig("WashingOff"));
        }};
        var ecoConfiguration = new Configuration(ecoThresholds, ecoStatuses);
        createContext("Eco", groupId, ecoConfiguration);

        var winterThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(15, 28));
            put("humidity", new ThresholdsConfig(20, 35));
            put("co2", new ThresholdsConfig(300, 1400));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 10));
            put("homeradius", new ThresholdsConfig(0, 20));
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var winterStatuses = new HashMap<String, StatusConfig>() {{
            put("heating", new StatusConfig("HeatingOn"));
        }};
        var winterConfiguration = new Configuration(winterThresholds, winterStatuses);
        createContext("Winter", groupId, winterConfiguration);

        var summerThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(24, 30));
            put("humidity", new ThresholdsConfig(30, 60));
            put("co2", new ThresholdsConfig(200, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 50000));
            put("homeradius", new ThresholdsConfig(0, 100));
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var summerStatuses = new HashMap<String, StatusConfig>() {{
            put("ac", new StatusConfig("ACOn"));
            put("heating", new StatusConfig("HeatingOff"));
        }};
        var summerConfiguration = new Configuration(summerThresholds, summerStatuses);
        createContext("Summer", groupId, summerConfiguration);
  
        var dayThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(20, 28));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 1000));
            put("sound", new ThresholdsConfig(10, 60));
            put("movement", new ThresholdsConfig(2, 40));
            put("neighborhoodradius", new ThresholdsConfig(0, 50000));
            put("homeradius", new ThresholdsConfig(0, 100));
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var dayStatuses = new HashMap<String, StatusConfig>() {{
            put("lights", new StatusConfig("LightsOff"));
        }};
        var dayConfiguration = new Configuration(dayThresholds, dayStatuses);
        createContext("Daily", groupId, dayConfiguration);

        var nightThresholds = new HashMap<String, ThresholdsConfig>() {{
            put("temperature", new ThresholdsConfig(15, 20));
            put("humidity", new ThresholdsConfig(30, 50));
            put("co2", new ThresholdsConfig(250, 800));
            put("sound", new ThresholdsConfig(10, 30));
            put("movement", new ThresholdsConfig(2, 10));
            put("neighborhoodradius", new ThresholdsConfig(0, 0));
            put("homeradius", new ThresholdsConfig(0, 2));
            put("prisutnostkupatilo", new ThresholdsConfig(0.3f, 0.7f));
        }};
        var nightStatuses = new HashMap<String, StatusConfig>() {{
            put("ac", new StatusConfig("ACOff"));
            put("boiler", new StatusConfig("BoilerOn"));
            put("lights", new StatusConfig("LightsOff"));
            put("washing", new StatusConfig("WashingOn"));
        }};
        var nightConfiguration = new Configuration(nightThresholds, nightStatuses);
        createContext("Night", groupId, nightConfiguration);
    }

    private void createContext(String name, String groupId, Configuration configuration) {
        var context = new Context(name, groupId, configuration);
        _contextService.create(context);
    }
}
