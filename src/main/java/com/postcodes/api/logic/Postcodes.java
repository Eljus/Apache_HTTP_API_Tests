package com.postcodes.api.logic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@Accessors(fluent = true)
public class Postcodes {
    @JsonProperty("status")
    private String status;
    @JsonProperty("query")
    private String query;
    @JsonProperty("result")
    private List result;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("quality")
    private String quality;
    @JsonProperty("eastings")
    private String eastings;
    @JsonProperty("northings")
    private String northings;
    @JsonProperty("country")
    private String country;
    @JsonProperty("nhs_ha")
    private String nhs_ha;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("european_electoral_region")
    private String european_electoral_region;
    @JsonProperty("primary_care_trust")
    private String primary_care_trust;
    @JsonProperty("region")
    private String region;
    @JsonProperty("lsoa")
    private String lsoa;
    @JsonProperty("msoa")
    private String msoa;
    @JsonProperty("incode")
    private String incode;
    @JsonProperty("outcode")
    private String outcode;
    @JsonProperty("parliamentary_constituency")
    private String parliamentary_constituency;
    @JsonProperty("admin_district")
    private String admin_district;
    @JsonProperty("parish")
    private String parish;
    @JsonProperty("admin_county")
    private String admin_county;
    @JsonProperty("admin_ward")
    private String admin_ward;
    @JsonProperty("ced")
    private String ced;
    @JsonProperty("ccg")
    private String ccg;
    @JsonProperty("nuts")
    private String nuts;
    @JsonProperty("codes")
    private List codes;
}
