package api;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {

    String baseGetUrl();

    String baseGetQueryUrl();

    String basePostUrl();

    boolean logging();

    long timeLimitTestHasToPass();
}
