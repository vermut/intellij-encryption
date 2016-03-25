package com.ansible.parsing.vault

import net.wedjaa.ansible.vault.crypto.VaultHandler
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException

/**
 * Created by admin on 20/03/16.
 */
class VaultLibTest {
    private var PASSWORD: String = "ansible"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val console = ConsoleAppender() //create appender
        //configure the appender
        val PATTERN = "%d [%p|%c|%C{1}] %m%n"
        console.setLayout(PatternLayout(PATTERN));
        console.setThreshold(Level.DEBUG);
        console.activateOptions();

        val root = Logger.getRootLogger()
        root.level = Level.DEBUG
        //add appender to any Logger (here is root)
        root.addAppender(console);
    }

    @Test(expected = IOException::class)
    fun test_encrypt_decrypt_aes() {
        val enc_data = "\$ANSIBLE_VAULT;1.1;AES\n53616c7465645f5fc107ce1ef4d7b455e038a13b053225776458052f8f8f332d554809d3f150bfa3\nfe3db930508b65e0ff5947e4386b79af8ab094017629590ef6ba486814cf70f8e4ab0ed0c7d2587e\n786a5a15efeb787e1958cbdd480d076c\n"
        val dec_data = VaultHandler.decrypt(enc_data, PASSWORD)
        assertEquals("decryption failed", "foobar", dec_data)

    }

    @Test
    fun test_encrypt_decrypt_aes256() {
        val enc_data = VaultHandler.encrypt("foobar", PASSWORD)
        println("data = " + enc_data)
        val dec_data = VaultHandler.decrypt(enc_data, PASSWORD)
        assertNotNull("encryption failed", enc_data);
        assertEquals("decryption failed", "foobar", dec_data)
    }

    @Test
    fun test_decrypt_sample_txt() {
        val dec_data = VaultHandler.decrypt(File("src/test/data/parser/sample.txt").readText(), PASSWORD)
        assertEquals("decryption failed", "foobar\n", dec_data)
    }
}