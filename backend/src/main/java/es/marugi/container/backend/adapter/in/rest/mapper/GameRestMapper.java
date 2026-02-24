package es.marugi.container.backend.adapter.in.rest.mapper;

import es.marugi.container.backend.adapter.in.rest.dto.GameResponseDTO;
import es.marugi.container.backend.adapter.in.rest.dto.UpdateGameRequestDTO;
import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.application.dto.GameDTO;
import es.marugi.container.backend.adapter.in.rest.dto.CreateGameRequestDTO;
import es.marugi.container.backend.application.dto.UpdateGameDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameRestMapper {
    GameRestMapper INSTANCE = Mappers.getMapper(GameRestMapper.class);
    CreateGameDTO toDto(CreateGameRequestDTO dto);
    UpdateGameDTO toDto(UpdateGameRequestDTO dto);
    GameResponseDTO toResponseDTO(GameDTO dto);
}

