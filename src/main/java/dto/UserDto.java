package dto;

import dto.exceptions.DtoErrorCode;
import dto.exceptions.DtoException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;

public class UserDto {
    private long id;
    private Object name, password;

    public UserDto(Object name, Object password) {
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    private byte[] hashSHA512(byte[] salt, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] hashPBKDF2(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return secretKeyFactory.generateSecret(spec).getEncoded();
    }

    public void setPassword(String password) throws DtoException {
        SecureRandom random = new SecureRandom(new byte[]{1, 2});
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        try {
            Base64.Encoder enc = Base64.getEncoder();
            this.password = enc.encodeToString(hashPBKDF2(salt, password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new DtoException(DtoErrorCode.ERROR_IN_PASSWORD_HASHING);
        }
    }

    public String getName() {
        return name.toString();
    }

    public String getPassword() {
        return password.toString();
    }

    public boolean validate() throws DtoException {
        if (name != null && !name.toString().isEmpty()) {
            if (password != null && !password.toString().isEmpty()) {
                setPassword(password.toString());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return name.equals(user.name) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
