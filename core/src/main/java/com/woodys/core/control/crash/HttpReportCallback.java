package com.woodys.core.control.crash;

import java.io.File;

/**
 * @author: woodys
 * @date: 2016-06-17 16:32

 */
public interface HttpReportCallback {

    void uploadException2remote(File file);
}
