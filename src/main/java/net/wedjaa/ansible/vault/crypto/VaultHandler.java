/*
 * Copyright 2016 - Fabio "MrWHO" Torchetti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.wedjaa.ansible.vault.crypto;

import net.wedjaa.ansible.vault.crypto.data.Util;
import net.wedjaa.ansible.vault.crypto.data.VaultInfo;
import net.wedjaa.ansible.vault.crypto.decoders.CypherFactory;
import net.wedjaa.ansible.vault.crypto.decoders.implementation.CypherAES256;
import net.wedjaa.ansible.vault.crypto.decoders.inter.CypherInterface;

import java.io.IOException;

/**
 * Created by mrwho on 03/06/15.
 */
public class VaultHandler {

    private final static String DEFAULT_CYPHER = CypherAES256.CYPHER_ID;

    public static String encrypt(byte[] cleartext, String password, String cypher) throws IOException {
        CypherInterface cypherInstance = CypherFactory.getCypher(cypher);
        byte[] vaultData = cypherInstance.encrypt(cleartext, password);
        String vaultDataString = new String(vaultData);
        String vaultPackage = cypherInstance.infoLine() + "\n" + vaultDataString;
        return vaultPackage;
    }

    public static String encrypt(byte[] cleartext, String password) throws IOException {
        return encrypt(cleartext, password, DEFAULT_CYPHER);
    }

    public static String encrypt(String clearTextValue, String password, String cypher) throws IOException {
        return encrypt(clearTextValue.getBytes(), password, cypher);
    }

    public static String encrypt(String clearText, String password) throws IOException {
        return encrypt(clearText, password, DEFAULT_CYPHER);
    }

    public static String decrypt(String encryptedValue, String password) throws IOException {
        return new String(decrypt(encryptedValue.getBytes(), password));
    }

    public static byte[] decrypt(byte[] encrypted, String password) throws IOException {
        VaultInfo vaultInfo = Util.getVaultInfo(encrypted);
        if (!vaultInfo.isEncryptedVault()) {
            throw new IOException("File is not an Ansible Encrypted Vault");
        }

        if (!vaultInfo.isValidVault()) {
            throw new IOException("The vault is not a format we can handle - check the cypher.");
        }

        byte[] encryptedData = Util.getVaultData(encrypted);

        return vaultInfo.getCypher().decrypt(encryptedData, password);
    }

}
