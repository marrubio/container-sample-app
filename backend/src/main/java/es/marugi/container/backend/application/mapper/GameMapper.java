package es.marugi.container.backend.application.mapper;

import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.domain.model.Game;
import es.marugi.container.backend.application.dto.GameDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDTO toDTO(Game game);
    Game toEntity(GameDTO gameDTO);
    Game toEntity(CreateGameDTO gameDTO);
}

