package com.shop.command;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface CartCommand {
    void execute(HttpServletRequest request) throws IOException;
}
