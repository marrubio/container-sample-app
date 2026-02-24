package es.marugi.container.backend.adapter.in.rest.mapper;

import es.marugi.container.backend.adapter.in.rest.dto.GameResponseDTO;
import es.marugi.container.backend.domain.model.GameDTO;
import es.marugi.container.backend.adapter.in.rest.dto.CreateGameRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameRestMapper {
    GameRestMapper INSTANCE = Mappers.getMapper(GameRestMapper.class);
    GameDTO toDto(CreateGameRequestDTO dto);
    GameResponseDTO toResponseDTO(GameDTO dto);
}

