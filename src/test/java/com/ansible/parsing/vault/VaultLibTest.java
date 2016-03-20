package com.ansible.parsing.vault;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by admin on 20/03/16.
 */
public class VaultLibTest {
    private VaultLib vault;

 /*     def test_encrypt_decrypt_aes(self):
        if not HAS_AES or not HAS_COUNTER or not HAS_PBKDF2:
            raise SkipTest
        v = VaultLib('ansible')
        v.cipher_name = u'AES'
        # AES encryption code has been removed, so this is old output for
        # AES-encrypted 'foobar' with password 'ansible'.
        enc_data = b'$ANSIBLE_VAULT;1.1;AES\n53616c7465645f5fc107ce1ef4d7b455e038a13b053225776458052f8f8f332d554809d3f150bfa3\nfe3db930508b65e0ff5947e4386b79af8ab094017629590ef6ba486814cf70f8e4ab0ed0c7d2587e\n786a5a15efeb787e1958cbdd480d076c\n'
        dec_data = v.decrypt(enc_data)
        assert dec_data == b"foobar", "decryption failed"

    def test_encrypt_decrypt_aes256(self):
        if not HAS_AES or not HAS_COUNTER or not HAS_PBKDF2:
            raise SkipTest
        v = VaultLib('ansible')
        v.cipher_name = 'AES256'
        enc_data = v.encrypt(b"foobar")
        dec_data = v.decrypt(enc_data)
        assert enc_data != b"foobar", "encryption failed"
        assert dec_data == b"foobar", "decryption failed"
  */

    @Before
    public void setUp() throws Exception {
        vault = new VaultLib("ansible");
    }

    public void test_encrypt_decrypt_aes() {
        String enc_data = "$ANSIBLE_VAULT;1.1;AES\n53616c7465645f5fc107ce1ef4d7b455e038a13b053225776458052f8f8f332d554809d3f150bfa3\nfe3db930508b65e0ff5947e4386b79af8ab094017629590ef6ba486814cf70f8e4ab0ed0c7d2587e\n786a5a15efeb787e1958cbdd480d076c\n";
        String dec_data = vault.decrypt(enc_data);
        assertEquals("decryption failed", "foobar", dec_data);

    }

    @Test
    public void test_output_formatter() {
        String data = vault.formatOutput(new String(new char[163]).replace("\0", "ABCedf"));
        // System.out.println("data = " + data);
        assertEquals("formatter ate something", 1008, data.length());
    }

    @Test
    public void test_split_header() {
        String data = "$ANSIBLE_VAULT;9.9;TEST\nansible";
        String rdata = vault.splitHeader(data);
        String[] lines = rdata.split("\n");
        assertEquals("ansible", lines[0]);
        assertEquals("cipher name was not set", "TEST", vault.getCipherName());
        assertEquals("9.9", vault.getVersion());
    }


    @Test
    public void test_encrypt_decrypt_aes256() {
        vault.setCipherName("AES256");
        vault.setVersion("9.9");
        String enc_data = vault.encrypt("foobar");
        System.out.println("data = " + enc_data);
        String dec_data = vault.decrypt(enc_data);
        // assertNull("encryption failed", enc_data);
        assertEquals("decryption failed", "foobar", dec_data);
    }

    @Test
    public void manual_format() throws AES.StrongEncryptionNotAvailableException, AES.InvalidPasswordException, AES.InvalidAESStreamException, IOException {
        byte[] salt = DatatypeConverter.parseHexBinary("ed3496252ad601cf571ac38eab55544fd9de4fc160e0053e688e1da1fbb98f40");
        byte[] hmac = DatatypeConverter.parseHexBinary("c329dee4cbc4412294e077aca91d23c471b0cc8473967fe81dbc0c1832db0f88");
        byte[] data = DatatypeConverter.parseHexBinary("2812bc157abaa53f7a86e22f9ed253dd");

        AES.decryptTest(256, "ansible".toCharArray(), salt, hmac, new ByteArrayInputStream(data));
    }

    @Test
    public void test_decrypt_sample_txt() {
        String dec_data = vault.decrypt("$ANSIBLE_VAULT;1.1;AES256\n" +
                "65643334393632353261643630316366353731616333386561623535353434666439646534666331\n" +
                "3630653030353365363838653164613166626239386634300a633332396465653463626334343132\n" +
                "32393465303737616361393164323363343731623063633834373339363766653831646263306331\n" +
                "3833326462306638380a323831326263313537616261613533663761383665323266396564323533\n" +
                "6464\n");
        assertEquals("decryption failed", "foobar", dec_data);
    }


}