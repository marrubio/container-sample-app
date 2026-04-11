package es.marugi.container.backend.adapter.in.rest.mapper;

import es.marugi.container.backend.adapter.in.rest.dto.GameResponseDTO;
import es.marugi.container.backend.adapter.in.rest.dto.UpdateGameRequestDTO;
import es.marugi.container.backend.adapter.in.rest.dto.CreateGameRequestDTO;
import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.application.dto.GameDTO;
import es.marugi.container.backend.application.dto.UpdateGameDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameRestMapper {
    CreateGameDTO toDto(CreateGameRequestDTO dto);
    UpdateGameDTO toDto(UpdateGameRequestDTO dto);
    GameResponseDTO toResponseDTO(GameDTO dto);
}

