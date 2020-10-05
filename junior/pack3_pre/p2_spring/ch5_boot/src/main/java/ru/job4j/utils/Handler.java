package ru.job4j.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.servlet.ServletContext;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Класс Handler реализует вспомогательные методы.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-10
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
     * @param ctx контекст.
     * @param req запрос.
     * @param fields карта с данными полей формы кроме type="file".
     * @param files список с файлами из формы.
     * @throws Exception исключение.
     */
    public void handleFormData(ServletContext ctx, HttpServletRequest req, HashMap<String, String[]> fields, List<Part> files) throws Exception {
        File tmpDir = (File) ctx.getAttribute("javax.servlet.context.tempdir");
//        if (!tmpDir.exists()) {
//            tmpDir.mkdirs();
//        }
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            if (null != part.getContentType() && part.getContentType().startsWith("image")) {
                String name = part.getSubmittedFileName();
                InputStream is = part.getInputStream();
                byte[] buffer = new byte[(int) part.getSize()];
                is.read(buffer);
                File targetFile = new File(tmpDir + "/" + name);
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(buffer);
                files.add(part);
            }
        }

//        //parts.forEach(part -> System.out.println(part.getContentType()));
//
//        // @see https://commons.apache.org/proper/commons-fileupload/using.html
//        // Create a factory for disk-based file items.
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        //File repository = (File) ctx.getAttribute("javax.servlet.context.tempdir");
//        File repository = (File) ctx.getAttribute("FILES_TMP_DIR");
//        factory.setRepository(repository);
//        // Create a new file upload handler.
//        ServletFileUpload sfu = new ServletFileUpload(factory);
//        // Parse the request.
//        List<FileItem> items = sfu.parseRequest(req);
//        // Process the uploaded items
//        for (FileItem item : items) {
//            if (item.isFormField()) {
//                //fields.put(item.getFieldName(), item.getString());
//            } else {
//                files.add(item);
//            }
//        }
        // Получение полей формы.
        Enumeration paramNames = req.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = req.getParameterValues(paramName);
            fields.put(paramName, paramValues);
        }
    }
    /**
     * Обрабатывает загруженные фотографии.
     * @param id идентификатор объявления.
     * @param files список необработанных загруженных файлов.
     * @throws Exception исключение.
     */
    public void handleFotos(Long id, List<Part> files) throws Exception {
        String dirName = String.format("%s/%d", this.fotosPath, id);
        File dir = new File(dirName);
        if (!dir.exists() && !dir.mkdir()) {
            throw new Exception(String.format("Folder %s not created.", dirName));
        }
        List<String> mimes = Arrays.asList("image/jpeg", "image/png");
        for (Part item : files) {
            String mime = item.getContentType();
            if (mimes.contains(mime)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                String name = item.getSubmittedFileName();
                md.update(name.getBytes(this.enc), 0, name.length());

                name = new BigInteger(1, md.digest()).toString(16);
                name = String.format("%s/%d/%s", this.fotosPath, id, name);
                if (mime.equals("image/jpeg")) {
                    name = name + ".jpg";
                } else if (mime.equals("image/png")) {
                    name = name + ".png";
                }
                //File uploadedFile = new File(name);
                item.write(name);
            }
        }
    }
}