package com.epam.jdi.uitests.web.selenium.elements.base;
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


import com.epam.jdi.uitests.core.interfaces.base.ISelect;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement extends ClickableText implements ISelect {
    public SelectElement() {
    }

    public SelectElement(By byLocator) {
        super(byLocator);
    }

    public SelectElement(WebElement webElement) {
        super(webElement);
    }

    protected boolean isSelectedAction() {
        return getWebElement().isSelected();
    }

    /**
     * Selects Element. Similar to click()
     */
    public void select() {
        select(getName());
    }

    @Step("Select {elName}")
    private void select(String elName) {
        click();
    }

    /**
     * Selects Element. Similar to click()
     */
    public boolean isSelected() {
        return isSelected(getName());
    }

    @Step("Is {elName} selected")
    private boolean isSelected(String elName) {
        return actions.isSelected(this::isSelectedAction);
    }
}