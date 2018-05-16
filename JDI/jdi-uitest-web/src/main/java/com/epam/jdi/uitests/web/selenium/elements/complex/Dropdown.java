package com.epam.jdi.uitests.web.selenium.elements.complex;
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


import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.web.selenium.elements.GetElementType;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.base.Clickable;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import io.qameta.allure.Step;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;
import static java.lang.String.format;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Dropdown<TEnum extends Enum> extends Selector<TEnum> implements IDropDown<TEnum> {
	protected GetElementType element;
	protected GetElementType expander;

	public Dropdown() {
		super();
	}

	public Dropdown(By selectLocator) {
		super(selectLocator);
	}

	public Dropdown(By selectLocator, By optionsNamesLocator) {
		this(selectLocator, optionsNamesLocator, null);
	}

	public Dropdown(By selectLocator, By optionsNamesLocator, By allOptionsNamesLocator) {
		super(optionsNamesLocator, allOptionsNamesLocator);
		element = new GetElementType(selectLocator, this);
		expander = new GetElementType(selectLocator, this);
	}

	public static void setUp(BaseElement el, Field field) {
		if (!fieldHasAnnotation(field, JDropdown.class, IDropDown.class))
			return;
		((Dropdown) el).setUp(field.getAnnotation(JDropdown.class));
	}

	public Dropdown<TEnum> setUp(JDropdown jDropdown) {
		By root = findByToBy(jDropdown.root());
		By value = findByToBy(jDropdown.value());
		By list = findByToBy(jDropdown.list());
		By expand = findByToBy(jDropdown.expand());
		if (root == null)
			root = findByToBy(jDropdown.jRoot());
		if (value == null)
			value = findByToBy(jDropdown.jValue());
		if (list == null)
			list = findByToBy(jDropdown.jList());
		if (expand == null)
			expand = findByToBy(jDropdown.jExpand());

		if (root != null) {
			Element el = new Element(root);
			el.setParent(getParent());
			setParent(el);
			setAvatar(root);
		}
		if (value != null) {
			this.element = new GetElementType(value, this);
			if (expander == null) this.expander = element;
		}
		if (list != null)
			this.allLabels = new GetElementType(list, this);
		if (expand != null) {
			this.expander = new GetElementType(expand, this);
			if (element == null) this.element = expander;
		}
		return this;
	}

	protected Label element() {
		if (element == null)
			return getLocator() != null && !getLocator().equals("")
					? new GetElementType(getLocator(), this).get(Label.class)
					: null;
		return element.get(Label.class);
	}

	protected Clickable expander() {
		if (expander == null) {
			if (getLocator() != null && !getLocator().equals(""))
				return new GetElementType(getLocator(), this).get(Label.class);
			throw exception("'Expand' element for dropdown not defined");
		}
		return expander.get(Label.class);
	}

	protected void expandAction(String name) {
		if (element().isDisplayed()) {
			setWaitTimeout(0);
			if (!isDisplayedAction(name))
				restoreWaitTimeout();
			timer().wait(() -> {
				expander().click();
				return timer(1).wait(() -> isDisplayedAction(name));
			});
			restoreWaitTimeout();
		}
	}

	protected void expandAction(int index) {
		if (!isDisplayedAction(index))
			element().click();
	}

	@Override
	protected void selectAction(String name) {
		if (element().getLocator().toString().contains("select"))
			try {
				new Select(element().getWebElement()).selectByVisibleText(name);
				return;
			} catch (Exception ignore) {
			}
		expandAction(name);
		super.selectAction(name);
	}

	@Override
	protected void selectAction(int index) {
		if (element() != null) {
			expandAction(index);
			super.selectAction(index);
		} else
			new Select(getWebElement()).selectByIndex(index);
	}

	@Override
	protected boolean isDisplayedAction(String name) {
		WebElement element;
		try {
			element = allLabels.get(TextList.class).getElement(name);
		} catch (Exception | Error ex) {
			return false;
		}
		return element != null && element.isDisplayed();
	}

	@Override
	protected String getValueAction() {
		return getTextAction();
	}

	@Override
	protected String getSelectedAction() {
		return getTextAction();
	}

	/**
	 * Waits while Element becomes visible
	 */
	@Override
	public void waitDisplayed() {
		element().waitDisplayed();
	}

	/**
	 * Waits while Element becomes invisible
	 */
	@Override
	public void waitVanished() {
		element().waitVanished();
	}

	public void wait(Function<WebElement, Boolean> resultFunc) {
		element().wait(resultFunc);
	}

	public <R> R wait(Function<WebElement, R> resultFunc, Function<R, Boolean> condition) {
		return element().wait(resultFunc, condition);
	}

	public <R> R test(Function<WebElement, R> resultFunc, Function<R, Boolean> condition) {
		return resultFunc.apply(getWebElement());
	}

	public void wait(Function<WebElement, Boolean> resultFunc, int timeoutSec) {
		element().wait(resultFunc, timeoutSec);
	}

	public <R> R wait(Function<WebElement, R> resultFunc, Function<R, Boolean> condition, int timeoutSec) {
		return element().wait(resultFunc, condition, timeoutSec);
	}

	@Override
	protected List<String> getOptionsAction() {
		boolean isExpanded = isDisplayedAction(1);
		if (!isExpanded) element().click();
		List<String> result = super.getOptionsAction();
		if (!isExpanded) element().click();
		return result;
	}

	protected void clickAction() {
		element().click();
	}

	protected String getTextAction() {
		String result = "";
		if (element().getLocator().toString().contains("select")) try {
			result = new Select(element().getWebElement()).getFirstSelectedOption().getText();
		} catch (Exception ignore) {
		}
		return result != null && !result.equals("")
				? result
				: element().getText();
	}

	/**
	 * Expanding DropDown
	 */
	public final void expand() {
		actions.expand(() -> expandAction(1));
	}

	public final void expand(String name) {
		expand(getName(), name);
	}

	@Step("{elName} expand {name}")
	private void expand(String elName, String name) {
		actions.expand(() -> expandAction(name));
	}

	public final void expand(int index) {
		expand(getName(), index);
	}

	@Step("{elName} expand {index}")
	private void expand(String elName, int index) {
		actions.expand(() -> expandAction(index));
	}

	/**
	 * Closing DropDown
	 */
	public final void close() {
		close(getName());
	}

	@Step("{elName} - close")
	private void close(String elName) {
		if (isDisplayedAction(1)) element().click();
	}

	/**
	 * Click on Element
	 */
	public final void click() {
		click(getName());
	}

	@Step("Click on {name}")
	private void click(String name) {
		actions.click(this::clickAction);
	}

	/**
	 * @return Get Element’s text
	 */
	public final String getText() {
		return getText(getName());
	}

	@Step("{elName} - get text")
	private String getText(String elName) {
		return actions.getText(this::getTextAction);
	}

	/**
	 * @param text Specify expected text
	 * @return Wait while Element’s text contains expected text. Returns Element’s text
	 */
	public final String waitText(String text) {
		return waitText(getName(), text);
	}

	@Step("{elName} - wait text {text}")
	private String waitText(String elName, String text) {
		return actions.waitText(text, this::getTextAction);
	}

	/**
	 * @param regEx Specify expected regular expression Text
	 * @return Wait while Element’s text matches regEx. Returns Element’s text
	 */
	public final String waitMatchText(String regEx) {
		return waitMatchText(getName(), regEx);
	}

	@Step("{elName} - wait match text {regEx}")
	private String waitMatchText(String elName, String regEx) {
		return actions.waitMatchText(regEx, this::getTextAction);
	}

	/**
	 * @param attributeName Specify attribute name
	 * @param value         Specify attribute value
	 *                      Sets attribute value for Element
	 */
	public void setAttribute(String attributeName, String value) {
		element().setAttribute(attributeName, value);
	}

	public WebElement getWebElement() {
		return new GetElementType(getLocator(), this).get(Element.class).getWebElement();
	}

	public String getAttribute(String name) {
		return element().getAttribute(name);
	}

	/**
	 * @param name  Specify attribute name
	 * @param value Specify attribute value
	 *              Waits while attribute gets expected value. Return false if this not happens
	 */
	public void waitAttribute(String name, String value) {
		waitAttribute(getName(), name, value);
	}

	@Override
	public void waitContainsAttribute(String name, String value) {
		waitContainsAttribute(getName(), name, value);
	}

	@Step("{elName} Wait attribute {name} contains value {value}")
	private void waitContainsAttribute(String elName, String name, String value) {
		wait(el -> el.getAttribute(name).contains(value));
	}

	@Step("{elName} - wait attribute {name} has value {value}")
	private void waitAttribute(String elName, String name, String value) {
		element().waitAttribute(name, value);
	}

	public void removeAttribute(String attributeName) {
		removeAttribute(getName(),attributeName);
	}

	@Step("{elName} Remove attribute {attributeName}")
	private void removeAttribute(String elName, String attributeName) {
		invoker.doJAction(format("Remove Attribute '%s'", attributeName),
				() -> jsExecutor().executeScript("arguments[0].removeAttribute(arguments[1]);",getWebElement(), attributeName));
	}
}