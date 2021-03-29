package com.github.olestxcode.jingle.impl;

import com.github.olestxcode.jingle.ContextController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JingleContextControllerTest {

    @Test
    void injectPrimary_shouldReturnInjectedObject_whenClassIsSame() {
        ContextController contextController = new JingleContextController();
        String hello = "Hello!";

        contextController.add(hello);

        Assertions.assertSame(hello, contextController.get(String.class));
        Assertions.assertEquals(hello, contextController.get(String.class));
    }

    @Test
    void injectPrimary_shouldReturnInjectedObject_whenClassIsSuper() {
        ContextController contextController = new JingleContextController();
        Integer integer = new Integer(1);

        contextController.add(integer);

        Assertions.assertEquals(integer, contextController.get(Number.class));
    }

    @Test
    void injectPrimary_shouldReturnInjectedObject_whenClassIsSub() {
        ContextController contextController = new JingleContextController();
        Number number = new Integer(1);

        contextController.add(number);

        Assertions.assertEquals(number, contextController.get(Integer.class));
    }

    @Test
    void injectSubordinate_shouldReturnInjectedObject_whenKeyIsCorrect() {
        ContextController contextController = new JingleContextController();
        String hello = "Hello!";

        contextController.add(hello, "hlo");

        Assertions.assertEquals(hello, contextController.get("hlo"));
    }
}
