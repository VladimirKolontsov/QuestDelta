package ua.com.javarush.quest.khmelov.repository;

import ua.com.javarush.quest.khmelov.entity.Game;

import java.util.Comparator;
import java.util.stream.Stream;

public class GameRepository extends AbstractRepository<Game> implements Repository<Game> {

    private static final GameRepository userRepository = new GameRepository();

    public static GameRepository get() {
        return userRepository;
    }

    private GameRepository() {
    }


    @Override
    public Stream<Game> find(Game pattern) {
        return map.values().stream()
                .filter(entity -> isOk(pattern, entity, Game::getId)
                        && isOk(pattern, entity, Game::getUserId)
                        && isOk(pattern, entity, Game::getQuestId)
                        && isOk(pattern, entity, Game::getCurrentQuestionId)
                        && isOk(pattern, entity, Game::getGameState)
                )
                .sorted(Comparator.comparing(Game::getGameState));
    }

}