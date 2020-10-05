package ru.job4j.utils;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Реализует кодировщик паролей с использованием алгоритма BCrypt.
 * @version 2019-08-01
 * @since 2019-07-30
 */
public class CustomBCryptPasswordEncoder implements PasswordEncoder {
    /**
     * Cost factor - логарифм итераций алгоритма.
     * Целое число в диапазоне > 3 && < 32.
     */
    private final int costFactor;

    /**
     * Соль. Любой массив из 8 байтов.
     */
    private final byte[] salt;

    /**
     * Конструктор.
     * @param salt соль (любой набор из 8 байтов).
     * @param costFactor логарифм итераций алгоритма.
     */
    public CustomBCryptPasswordEncoder(byte[] salt, int costFactor) {
        this.costFactor = costFactor;
        this.salt = salt;
    }

    /**
     * Кодирует пароль.
     * @param rawPassword последовательность символов пароля.
     * @return хэш пароля.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return OpenBSDBCrypt.generate(rawPassword.toString().toCharArray(), this.salt, this.costFactor);
    }

    /**
     * Проверяет соответствие хэшей целевого пароля и пороля из хранилица.
     * @param rawPassword целевой пароль для проверки.
     * @param encodedPassword хэш пароля из хранилища.
     * @return true если хэши паролей одинаковые. Иначе false.
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return OpenBSDBCrypt.checkPassword(encodedPassword, rawPassword.toString().toCharArray());
    }
}
