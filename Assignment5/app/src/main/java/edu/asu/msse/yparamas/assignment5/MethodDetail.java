/* MethodDetail.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: Method detail class to provide the attributes of method call to the server
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 23, 2020
 */

package edu.asu.msse.yparamas.assignment5;

import android.app.Activity;

public class MethodDetail {

    public String method;
    public Object[] params;
    public Activity activity;
    public String url;
    public String resultAsJson;

    MethodDetail(Activity parent, String url, String method, Object[] params){
        this.method = method;
        this.activity = parent;
        this.url = url;
        this.params = params;
        this.resultAsJson = "{}";
    }
}
