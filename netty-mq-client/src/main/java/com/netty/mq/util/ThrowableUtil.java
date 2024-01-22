package com.netty.mq.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * error
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public class ThrowableUtil {

    /**
     * parse error to string
     *
     * @param e
     * @return
     */
    public static String toString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String errorMsg = stringWriter.toString();
        return errorMsg;
    }

}
