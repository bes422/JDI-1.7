package com.epam.commons.linqinterfaces;
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

/**
 * Created by 12345 on 30.09.2014.
 */
@FunctionalInterface
public interface JFuncTTTTTR<TInput1, TInput2, TInput3, TInput4, TInput5, TResult> {
    TResult invoke(TInput1 val1, TInput2 val2, TInput3 val3, TInput4 val4, TInput5 val5);
}