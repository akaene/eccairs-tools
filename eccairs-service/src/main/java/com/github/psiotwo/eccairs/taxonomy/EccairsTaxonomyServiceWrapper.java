package com.github.psiotwo.eccairs.taxonomy;

import com.akaene.eccairs.EccairsTaxonomyService;
import com.github.psiotwo.eccairs.core.model.EccairsValue;
import com.github.psiotwo.eccairs.rdf.TaxonomyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EccairsTaxonomyServiceWrapper implements TaxonomyService {

    private final EccairsTaxonomyService taxonomyService;

    public EccairsTaxonomyServiceWrapper(@Value("${eccairs.taxonomyService.url}") String taxonomyServiceUrl) {
        this.taxonomyService = new EccairsTaxonomyService(taxonomyServiceUrl);
    }

    @Override
    public boolean hasHierarchicalValueList(int attributeId) {
        return taxonomyService.hasHierarchicalValueList(attributeId);
    }

    @Override
    public List<EccairsValue> getValueList(int attributeId) {
        final List<com.akaene.eccairs.EccairsValue> valueList = taxonomyService.getValueList(attributeId);
        return valueList.stream().map(EccairsTaxonomyServiceWrapper::mapEccairsValue).collect(Collectors.toList());
    }

    private static EccairsValue mapEccairsValue(com.akaene.eccairs.EccairsValue source) {
        return new EccairsValue(source.getId(), source.getDescription(), source.getDetailedDescription(), source.getExplanation(), source.getDomains(), source.getLevel(), source.getValues() != null ? source.getValues()
                                                                                                                                                                                                              .stream()
                                                                                                                                                                                                              .map(EccairsTaxonomyServiceWrapper::mapEccairsValue)
                                                                                                                                                                                                              .collect(Collectors.toList()) : null);
    }
}
