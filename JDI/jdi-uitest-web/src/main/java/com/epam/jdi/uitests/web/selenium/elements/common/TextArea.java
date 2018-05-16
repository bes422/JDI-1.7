package com.epam.jdi.uitests.web.selenium.elements.common;
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


import com.epam.jdi.uitests.core.interfaces.common.ITextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class TextArea extends TextField implements ITextArea {
    public TextArea() {
    }

    public TextArea(By byLocator) {
        super(byLocator);
    }

    public TextArea(WebElement webElement) {
        super(webElement);
    }

    /**
     * @param textLines Specify text lines (clear textArea before
     *                  Clear textarea and Input several lines of text in textarea
     */
    public final void inputLines(String... textLines) {
        inputLines(getName(), textLines);
    }
    @Step("{elName} - input lines {textLines}")
    private void inputLines(String elName, String... textLines) {
        actions.inputLines(this::clearAction, this::inputAction, textLines);
    }

    /**
     * @param textLine Specify text to add new line (without clearing previous)
     *                 Add text in textarea from new line
     */
    public final void addNewLine(String textLine) {
        addNewLine(getName(), textLine);
    }

    @Step("{elName} - add new line {textLine}")
    private void addNewLine(String elName, String textLine) {
        actions.addNewLine(textLine, this::inputAction);
    }

    /**
     * @return Get lines of text in textarea
     */
    public final String[] getLines() {
        return getLines(getName());
    }
    @Step("{elName} - get lines")
    private String[] getLines(String elName) {
        return actions.getLines(this::getTextAction);
    }
}