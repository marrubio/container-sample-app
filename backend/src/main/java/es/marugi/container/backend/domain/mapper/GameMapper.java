package es.marugi.container.backend.domain.mapper;

import es.marugi.container.backend.domain.model.Game;
import es.marugi.container.backend.domain.model.GameDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDTO toDTO(Game game);
    Game toEntity(GameDTO gameDTO);
}

