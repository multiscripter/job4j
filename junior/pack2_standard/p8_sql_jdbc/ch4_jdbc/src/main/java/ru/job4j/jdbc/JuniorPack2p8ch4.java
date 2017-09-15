package ru.job4j.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
/**
 * Класс JuniorPack2p8ch4 реализует функционал для решения задачи #20459.
 * Для того, чтобы JVM нашла драйвер БД при выполнении программы нужно
 * закинуть jar-файл драйвера БД в папку jdk/jre/lib/ext.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-09-12
 */
class JuniorPack2p8ch4 {
	/**
     * Соединение с бд.
     */
	private Connection conn;
	/**
     * Директория.
     */
	private String dir;
	/**
     * Объект документа.
     */
	private Document doc;
	/**
     * Объект DOMSource.
     */
	private DOMSource source;
	/**
     * Поток выходного файла.
     */
	private  StreamResult outFile;
	/**
     * Путь.
     */
	private String path;
	/**
     * Подготовленный запрос.
     */
	private PreparedStatement pStat;
	/**
     * Набор данных результата запроса к бд.
     */
	private ResultSet resultSet;
	/**
     * Путь источника.
     */
	private final String srcPath;
	/**
     * Преобразователь.
     */
	private Transformer transformer;
	/**
     * Фабрика преобразователя.
     */
	private TransformerFactory transformerFactory;
	/**
     * Конструктор.
     * @throws URISyntaxException ошибка синтаксиса URI.
     */
	JuniorPack2p8ch4() throws URISyntaxException {
		this.srcPath = SQLiteJDBCDriver.class.getResource(".").getPath().replaceFirst("^/(.:/)", "$1");
        this.path = String.format("%s../../../../../src/main/resources/juniorPack2p8ch4.db", this.srcPath);
        this.path = new URI(this.path).normalize().getPath();
        this.path = this.path.replace("/", "\\");
	}
	/**
     * Закрывает открытые дискрипторы.
     * @throws SQLException ошибка SQL.
     */
	public void close() throws SQLException {
		this.pStat.close();
        this.conn.close();
	}
	/**
     * Создаёт таблицу.
     * @throws SQLException ошибка SQL.
     */
	public void createTable() throws SQLException {
		StringBuilder query = new StringBuilder();
        query.append("create table if not exists jp2p8ch4 (");
        query.append("id integer not null primary key autoincrement,");
        query.append("unit integer not null");
        query.append(");");
        this.pStat = this.conn.prepareStatement(query.toString());
        this.pStat.execute();
	}
	/**
     * Создаёт XML.
     * @throws ParserConfigurationException ошибка конфигурирования парсера.
     * @throws SQLException ошибка SQL.
     */
	public void createXML() throws ParserConfigurationException, SQLException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        this.doc = docBuilder.newDocument();
        Element root = this.doc.createElement("entries");
        this.doc.appendChild(root);
        while (this.resultSet.next()) {
            Element entry = this.doc.createElement("entry");
            Element field = this.doc.createElement("field");
            Text text = this.doc.createTextNode(Integer.toString(resultSet.getInt("unit")));
            field.appendChild(text);
            entry.appendChild(field);
            root.appendChild(entry);
        }
        this.resultSet.close();
	}
	/**
     * Удаляет данные из таблицы.
     * @throws SQLException ошибка SQL.
     */
	public void deleteData() throws SQLException {
		this.pStat = this.conn.prepareStatement("delete from jp2p8ch4;");
        this.pStat.execute();
	}
	/**
     * Добавляет данные в таблицу.
     * @param iters кщличество итераций.
     * @throws SQLException ошибка SQL.
     */
	public void insertData(int iters) throws SQLException {
		this.pStat = conn.prepareStatement("insert into jp2p8ch4 (unit) values (?);");
        this.conn.setAutoCommit(false);
        for (int a = 0; a < iters; a++) {
            this.pStat.setInt(1, a);
            this.pStat.addBatch();
        }
        this.pStat.executeBatch();
        this.conn.commit();
        this.pStat = this.conn.prepareStatement("select * from jp2p8ch4;");
        this.resultSet =  this.pStat.executeQuery();
	}
	/**
     * Парсит XML-файл.
     * @return результат вычислений.
     * @throws FileNotFoundException ошибка файл не найден.
     * @throws XMLStreamException ошибка xml-потока.
     */
	public int parseXML() throws FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream fis = new FileInputStream(this.dir + "2.xml");
        XMLStreamReader parser = factory.createXMLStreamReader(fis);
        String name = "";
        int event;
        int result = 0;
        while (parser.hasNext()) {
            event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                name = parser.getLocalName();
                if ("entry".equals(name)) {
                    int field = Integer.parseInt(parser.getAttributeValue(null, "field"));
                    result += field;
                }
            }
        }
        return result;
	}
	/**
     * Сохраняет XML в файл.
     * @throws TransformerConfigurationException ошибка конфигурирования преобразователя.
     * @throws TransformerException ошибка преобразователя.
     */
	public void saveXML() throws TransformerConfigurationException, TransformerException {
		this.transformerFactory = TransformerFactory.newInstance();
        this.transformer = this.transformerFactory.newTransformer();
        this.source = new DOMSource(this.doc);
        this.dir = this.srcPath + "/../../../../../src/main/java/ru/job4j/jdbc/";
        this.outFile = new StreamResult(new File(this.dir + "1.xml"));
        this.transformer.transform(this.source, this.outFile);
	}
	/**
     * Устанавливает соединения с бд.
     * @throws SQLException ошибка SQL.
     */
	public void setConnection() throws SQLException {
        this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", this.path));
	}
	/**
     * Преобразовать с помощью XSLT.
     * @throws TransformerConfigurationException ошибка конфигурирования преобразователя.
     * @throws TransformerException ошибка преобразователя.
     */
	public void xslt() throws TransformerConfigurationException, TransformerException {
		StreamSource xslStream = new StreamSource(this.dir + "1.xslt");
        this.transformer = this.transformerFactory.newTransformer(xslStream);
        this.outFile = new StreamResult(new File(this.dir + "2.xml"));
        this.transformer.transform(this.source, this.outFile);
	}
}