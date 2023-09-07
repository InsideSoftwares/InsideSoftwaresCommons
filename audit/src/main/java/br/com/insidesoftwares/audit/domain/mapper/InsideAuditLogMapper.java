package br.com.insidesoftwares.audit.domain.mapper;

import br.com.insidesoftwares.audit.domain.dto.InsideAuditLogDTO;
import br.com.insidesoftwares.audit.domain.entity.InsideAuditLog;
import br.com.insidesoftwares.commons.utils.GsonUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface InsideAuditLogMapper {

    @Mappings({
            @Mapping(source = "insideAuditLogDTO.parameter", target = "parameter", qualifiedByName = "toConvertToJson"),
            @Mapping(source = "insideAuditLogDTO.response", target = "response", qualifiedByName = "toConvertToJson"),
            @Mapping(source = "system", target = "originSystem")
    })
    InsideAuditLog toEntity(InsideAuditLogDTO insideAuditLogDTO, String system);

    @Named("toConvertToJson")
    default String convertToJson(Object value){
        if(Objects.isNull(value) || value instanceof List values && values.isEmpty()) {
            return null;
        }
        return GsonUtils.getInstance().toJson(value);
    }
}
