package pl.jklata.budgetapp.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class LogoUtils {

    public static byte[] getByteArrayFromLogoFile(MultipartFile logo) {
        byte[] byteArray = null;
        if (Objects.nonNull(logo) && !logo.isEmpty()) {
            try {
                byteArray = logo.getBytes();
            } catch (IOException e) {
                log.error("Get byte array from multipartFile went wrong.", e);
            }
        }
        return byteArray;
    }
}

