package safa.safepaws.cloudinary;

import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryServiceImpl   {

    @Resource
    private Cloudinary cloudinary;


    public String uploadFile(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
