package br.com.insidesoftwares.commons.lgpd.specification;

import br.com.insidesoftwares.commons.lgpd.model.DataMask;

import java.util.Set;

public interface DataMaskingValues {

    default Set<DataMask> headersValue() {
        return Set.of();
    }
    default Set<DataMask> bodyValue() {
        return Set.of();
    }

}
