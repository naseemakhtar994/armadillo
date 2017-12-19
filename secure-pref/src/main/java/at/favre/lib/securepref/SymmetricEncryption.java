package at.favre.lib.securepref;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author RISE GmbH (patrick.favre@rise-world.com)
 * @since 18.12.2017
 */

public interface SymmetricEncryption {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STRENGTH_HIGH, STRENGTH_VERY_HIGH})
    @interface KeyStrength {
    }

    /**
     * High Security which is equivalent to a AES key size of 128 bit
     */
    int STRENGTH_HIGH = 0;

    /**
     * Very high security which is equivalent to a AES key size of 256 bit
     * Note: This is usually not required.
     */
    int STRENGTH_VERY_HIGH = 1;

    byte[] encrypt(byte[] key, byte[] rawData) throws SymmetricEncryptionException;

    byte[] decrypt(byte[] key, byte[] encryptedData) throws SymmetricEncryptionException;
}
