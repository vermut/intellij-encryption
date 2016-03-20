package com.ansible.parsing.vault;

import org.apache.commons.lang.text.StrBuilder;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;


/**
 * Created by admin on 20/03/16.
 */
public class VaultLib {
    public static final String VAULT_HEADER = "$ANSIBLE_VAULT";
    private final String vaultPassword;
    private String cipherName;
    private String version;

    public VaultLib(String vaultPassword) {
        this.vaultPassword = vaultPassword;
    }

    public void setCipherName(String cipherName) {
        this.cipherName = cipherName;
    }

    public String getCipherName() {
        return cipherName;
    }

    public String encrypt(String data) {
        int keylen = 128;
        if (cipherName.equals("AES256"))
            keylen = 256;

        ByteArrayOutputStream output = new ByteArrayOutputStream(data.length() * 4);
        try {
            AES.encrypt(keylen, vaultPassword.toCharArray(), new ByteArrayInputStream(data.getBytes("utf-8")), output);
        } catch (AES.InvalidKeyLengthException e) {
            System.out.println("e = " + e);
        } catch (AES.StrongEncryptionNotAvailableException e) {
            try {
                setCipherName("AES");
                AES.encrypt(128, vaultPassword.toCharArray(), new ByteArrayInputStream(data.getBytes("utf-8")), output);
            } catch (AES.InvalidKeyLengthException e1) {
                e1.printStackTrace();
            } catch (AES.StrongEncryptionNotAvailableException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("e = " + e);
        }

        return formatOutput(DatatypeConverter.printHexBinary(output.toByteArray()));
    }

    String formatOutput(String encData) {
        StrBuilder builder = new StrBuilder(encData.length());

        builder.appendWithSeparators(Arrays.asList(VAULT_HEADER, version, cipherName), ";");
        builder.append('\n');
        for (int n = 0; n < encData.length(); n += 80) {
            builder.append(encData, n, Math.min(80, encData.length() - n));
            builder.append('\n');
        }
        return builder.toString();
    }

    String splitHeader(String data) {
        String[] tmpdata = data.split("\n");
        String[] tmpheader = tmpdata[0].trim().split(";");
        this.version = tmpheader[1].trim();
        this.cipherName = tmpheader[2].trim();

        StringBuilder builder = new StringBuilder();
        for (int i = 1, tmpdataLength = tmpdata.length; i < tmpdataLength; i++) {
            String s = tmpdata[i];
            builder.append(s);
        }
        return builder.toString();
    }

    public String decrypt(String encData) {
        String data = splitHeader(encData);

        ByteArrayOutputStream output = new ByteArrayOutputStream(data.length() / 4);
        try {
            AES.decrypt(vaultPassword.toCharArray(), new ByteArrayInputStream(DatatypeConverter.parseHexBinary(data)), output);
        } catch (AES.StrongEncryptionNotAvailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AES.InvalidPasswordException e) {
            e.printStackTrace();
        } catch (AES.InvalidAESStreamException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
