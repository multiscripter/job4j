package ru.job4j.utils;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Класс Handler реализует вспомогательные методы.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-02
 * @since 2018-05-25
 */
public class Handler {
    /**
     * Кодировка.
     */
    private String enc;
    /**
     * Папка с картинками.
     */
    private String fotosPath;
    /**
     * Конструктор.
     * @param enc кодировка.
     * @param fotosPath путь к папке с картинками.
     */
    public Handler(String enc, String fotosPath) {
        this.enc = enc;
        this.fotosPath = fotosPath;
    }
    /**
     * Получает список имён файлов изображений.
     * @param dirName имя папки.
     * @return список имён файлов изображений.
     */
    public String[] getFotos(String dirName) {
        String[] names = new String[0];
        String path = String.format("%s/%s", this.fotosPath, dirName);
        path = Paths.get(path).normalize().toString();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            names = new String[files.length];
            for (int a = 0; a < files.length; a++) {
                names[a] = files[a].getName();
            }
        }
        return names;
    }
    /**
     * Обрабатывает данные формы.
     * @param servlet сервлет.
     * @param req запрос.
     * @param fields карта с данными полей формы кроме type="file".
     * @param files список с файлами из формы.
     * @throws Exception исключение.
     */
    public void handleFormData(ServletContext servlet, HttpServletRequest req, HashMap<String, String> fields, List<FileItem> files) throws Exception {
        // @see https://commons.apache.org/proper/commons-fileupload/using.html
        // Create a factory for disk-based file items.
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File repository = (File) servlet.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        // Create a new file upload handler.
        ServletFileUpload sfu = new ServletFileUpload(factory);
        // Parse the request.
        List<FileItem> items = sfu.parseRequest(req);
        // Process the uploaded items
        for (FileItem item : items) {
            if (item.isFormField()) {
                fields.put(item.getFieldName(), item.getString());
            } else {
                files.add(item);
            }
        }
    }
    /**
     * Обрабатывает загруженные фотографии.
     * @param id идентификатор объявления.
     * @param files список необработанных загруженных файлов.
     * @throws Exception исключение.
     */
    public void handleFotos(int id, List<FileItem> files) throws Exception {
        String dirName = String.format("%s/%d", this.fotosPath, id);
        File dir = new File(dirName);
        if (!dir.exists() && !dir.mkdir()) {
            throw new Exception(String.format("Folder %s not created.", dirName));
        }
        List<String> mimes = Arrays.asList("image/jpeg", "image/png");
        for (FileItem item : files) {
            String mime = item.getContentType();
            if (mimes.contains(mime)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(item.getName().getBytes(this.enc), 0, item.getName().length());

                String name = new BigInteger(1, md.digest()).toString(16);
                name = String.format("%s/%d/%s", this.fotosPath, id, name);
                if (mime.equals("image/jpeg")) {
                    name = name + ".jpg";
                } else if (mime.equals("image/png")) {
                    name = name + ".png";
                }
                File uploadedFile = new File(name);
                item.write(uploadedFile);
            }
        }
    }
}