package es.marugi.container.backend.mapper;

import es.marugi.container.backend.dto.CreateGameRequestDTO;
import es.marugi.container.backend.dto.GameDTO;
import es.marugi.container.backend.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);
    GameDTO toDTO(Game game);
    Game toEntity(GameDTO dto);
    Game toEntity(CreateGameRequestDTO dto);
}

