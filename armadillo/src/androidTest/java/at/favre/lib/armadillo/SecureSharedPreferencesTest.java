package at.favre.lib.armadillo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SecureSharedPreferencesTest extends ASecureSharedPreferencesTest {
    protected Armadillo.Builder create(String name, char[] pw) {
        return Armadillo.create(InstrumentationRegistry.getTargetContext(), name)
                .encryptionFingerprint(InstrumentationRegistry.getTargetContext())
                .password(pw);
    }

    @Test
    public void quickStartTest() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        SharedPreferences preferences = Armadillo.create(context, "myPrefs")
                .encryptionFingerprint(context)
                .build();

        preferences.edit().putString("key1", "string").apply();
        String s = preferences.getString("key1", null);
    }

    @Test
    public void advancedTest() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        String userId = "1234";
        SharedPreferences preferences = Armadillo.create(context, "myCustomPreferences")
                .password("mySuperSecretPassword".toCharArray()) //use user based password
                .securityProvider(Security.getProvider("BC")) //use bouncy-castle security provider
                .keyStretchingFunction(new PBKDF2KeyStretcher()) //use PBKDF2 as user password kdf
                .contentKeyDigest((providedMessage, usageName) -> sha256((usageName + providedMessage).getBytes(StandardCharsets.UTF_8))) //use sha256 as message digest
                .secureRandom(new SecureRandom()) //provide your own secure random for salt/iv generation
                .encryptionFingerprint(context, userId.getBytes(StandardCharsets.UTF_8)) //add the user id to fingerprint
                .build();

        preferences.edit().putString("key1", "string").apply();
        String s = preferences.getString("key1", null);
    }

    private String sha256(byte[] bytes) {
        return new String(bytes);
    }
}
