package com.epam.jdi.uitests.web.selenium.driver;
/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.IOException;

import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;
import static org.openqa.selenium.os.WindowsUtils.killByName;

/**
 * Created by 12345 on 26.01.2015.
 */
public final class WebDriverUtils {

    private WebDriverUtils() {
    }

    //TODO Add OS type and current user check.
    //TODO Try to use C/C++ Library to work with processes.
    public static void killAllRunWebBrowsers() throws IOException {
        asserter.ignore(() -> killByName("chromedriver.exe"));
        asserter.ignore(() -> killByName("geckodriver.exe"));
        asserter.ignore(() -> killByName("IEDriverServer.exe"));
        asserter.ignore(() -> killByName("Command line server for the IE driver"));
        asserter.ignore(() -> killByName("MicrosoftWebDriver.exe"));
    }
}