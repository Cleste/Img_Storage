package com.kirill.pimenov.services.implementations;

import com.kirill.pimenov.domain.GetUrl;
import com.kirill.pimenov.domain.Image;
import com.kirill.pimenov.exceptions.ImageNotFoundException;
import com.kirill.pimenov.exceptions.InvalidUrlException;
import com.kirill.pimenov.repositories.ImageRepository;
import com.kirill.pimenov.services.ImgService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
public class ImgServiceIml implements ImgService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ServletContext servletContext;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public ResponseEntity<InputStreamResource> loadImg(String name, String uploadPath) throws IOException, ImageNotFoundException {
        if (uploadPath == null)
            uploadPath = this.uploadPath;
        else
            uploadPath = this.uploadPath + uploadPath;
        if (name != null && !name.isEmpty()) {
            Image image = imageRepository
                    .findByName(name);
            if (image != null && !image.getName().isEmpty()) {
                File img = new File(uploadPath + "/" + image.getName());
                InputStreamResource resource = new InputStreamResource(new FileInputStream(img));
                MediaType mediaType = MediaType.parseMediaType(servletContext.getMimeType(img.getName()));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + img.getName())
                        .contentType(mediaType)
                        .contentLength(img.length())
                        .body(resource);
            } else throw new InvalidUrlException();
        } else throw new ImageNotFoundException();
    }

    public GetUrl saveImg(String url)
            throws InvalidUrlException, IOException {
        if (url != null && !url.isEmpty()) {
            String fileExtension = getFileExtension(url);
            if (fileExtension != null && (fileExtension.contains("png") || fileExtension.contains("jpeg") || fileExtension.contains("jpg"))) {
                String fileName = UUID.randomUUID().toString() + "." + fileExtension;
                downloadImg(url, fileName, 100);
                makeSmallImage(new File(uploadPath + fileName));
                Image image = new Image(fileName);
                GetUrl getUrl = new GetUrl();
                getUrl.setOriginal(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("img/" + fileName)
                        .toUriString());
                getUrl.setPreview(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("img/preview/" + fileName)
                        .toUriString());
                imageRepository.save(image);
                return getUrl;
            } else throw new InvalidUrlException();
        } else throw new InvalidUrlException();
    }

    public void downloadImg(String strURL, String fileName, int buffSize) {
        try {
            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = urlconn.getInputStream();
            File file = new File(uploadPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream writer = new FileOutputStream(file);
            byte[] buffer = new byte[buffSize];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void makeSmallImage(File img) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(img);
        BufferedImage scaledImage = Scalr.resize(bufferedImage, 100, 100);
        File newImage = new File(uploadPath + "preview/" + img.getName());
        if (!newImage.exists()) {
            newImage.createNewFile();
        }
        ImageIO.write(scaledImage, getFileExtension(newImage.getName()), newImage);
    }

    private static String getFileExtension(String mystr) {
        int index = mystr.lastIndexOf('.');
        return index == -1 ? null : mystr.substring(index + 1);
    }
}