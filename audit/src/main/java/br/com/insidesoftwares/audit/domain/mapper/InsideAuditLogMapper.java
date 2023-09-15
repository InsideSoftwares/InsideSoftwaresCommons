package br.com.insidesoftwares.audit.domain.mapper;

import br.com.insidesoftwares.audit.domain.dto.InsideAuditLogDTO;
import br.com.insidesoftwares.audit.domain.entity.InsideAuditLog;
import br.com.insidesoftwares.commons.utils.GsonUtils;
import br.com.insidesoftwares.commons.utils.filter.SanitizationBodyUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class InsideAuditLogMapper {

    @Autowired
    private SanitizationBodyUtil sanitizationBodyUtil;

    @Mappings({
            @Mapping(source = "insideAuditLogDTO.parameter", target = "parameter", qualifiedByName = "toConvertToJson"),
            @Mapping(source = "insideAuditLogDTO.response", target = "response", qualifiedByName = "toConvertToJson"),
            @Mapping(source = "system", target = "originSystem")
    })
    public abstract InsideAuditLog toEntity(InsideAuditLogDTO insideAuditLogDTO, String system);

    @Named("toConvertToJson")
    public String convertToJson(Object value){
        if(Objects.isNull(value) || value instanceof List values && values.isEmpty()) {
            return null;
        }
        String json = GsonUtils.getInstance().toJson(value);

        return sanitizationBodyUtil.sanitizeBody(json);
    }
}
