package ru.job4j.control.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.control.service.MusicType;
/**
 * Класс MusicTypeDAO реализует слой DAO между MusicType и бд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-10
 */
public class MusicTypeDAO {
    /**
     * Драйвер бд.
     */
	private DBDriver db;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Конструктор.
     */
    public MusicTypeDAO() {
    	this.db = DBDriver.getInstance("junior.pack2.p9.ch9.task1");
        this.logger = LogManager.getLogger("MusicTypeDAO");
    }
    /**
     * Получает музыкальные стили по идентификаторам.
     * @param ids массив строк идентификаторов музыкальных стилей.
     * @return список музыкальных стилей.
     * @throws SQLException исключение SQL.
     */
    public LinkedList<MusicType> getMusicTypesByIds(final String[] ids) throws SQLException {
        LinkedList<MusicType> mtypes = new LinkedList<>();
        String query = String.format("select * from musictypes where id in (%s)", String.join(", ", ids));
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                mtypes.add(new MusicType(Integer.parseInt(entry.get("id")), entry.get("name")));
            }
        }
        return mtypes;
    }
    /**
	 * Получает список музыкальных стилей.
     * @return список музыкальных стилей.
     * @throws SQLException исключение SQL.
	 */
    public LinkedList<MusicType> getMusicTypes() throws SQLException {
        LinkedList<MusicType> mtypes = new LinkedList<>();
        String query = "select * from musictypes order by name";
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                mtypes.add(new MusicType(Integer.parseInt(entry.get("id")), entry.get("name")));
            }
        }
        return mtypes;
    }
    /**
     * Получает список музыкальных стилей пользователя по идентификатору пользователя.
     * @param id идентификатор пользователя.
     * @return список музыкальных стилей.
     * @throws SQLException исключение SQL.
     */
    public LinkedList<MusicType> getUserMusicTypesByUserId(final int id) throws SQLException {
        LinkedList<MusicType> mtypes = new LinkedList<>();
        String query = String.format("select musictypes.id as musictype_id, musictypes.name as musictypes_name from musictypes, users_musictypes, users where musictype_id = users_musictypes.musictype_id and users_musictypes.user_id = users.id and users.id = %d group by musictypes.id order by musictypes_name", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                mtypes.add(new MusicType(Integer.parseInt(entry.get("musictype_id")), entry.get("musictypes_name")));
            }
        }
        return mtypes;
    }
}