package gwangjang.server.domain.morpheme.application.mapper;


import gwangjang.server.domain.morpheme.application.dto.res.ContentsRes;
import gwangjang.server.domain.morpheme.domain.entity.Contents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface ContentsMapper {
    ContentsMapper INSTANCE = Mappers.getMapper(ContentsMapper.class); // 2

    @Mapping(target = "contents_id", ignore = true)
    Contents toEntity(ContentsRes contentsRes);

    @Mapping(target = "contents_id", ignore = true)
    ContentsRes toDto(Contents contents);
}