/*******************************************************************************
 // Copyright (c) Microsoft Corporation.
 // All rights reserved.
 //
 // This code is licensed under the MIT License.
 //
 // Permission is hereby granted, free of charge, to any person obtaining a copy
 // of this software and associated documentation files(the "Software"), to deal
 // in the Software without restriction, including without limitation the rights
 // to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
 // copies of the Software, and to permit persons to whom the Software is
 // furnished to do so, subject to the following conditions :
 //
 // The above copyright notice and this permission notice shall be included in
 // all copies or substantial portions of the Software.
 //
 // THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 // IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 // FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
 // AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 // LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 // OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 // THE SOFTWARE.
 ******************************************************************************/
package com.randomlogic.rlpay.resources.api.azure.facade;


////import com.randomlogic.rlpay.application.config.SpringContext;
import com.randomlogic.rlpay.resources.api.azure.interfaces.IAuthenticate;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import com.randomlogic.rlpay.resources.api.azure.model.service.impl.AuthenticateSvcImpl;
import com.randomlogic.rlpay.resources.api.azure.model.service.interfaces.IAuthenticateSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Authenticate implements IAuthenticate
{
    @Autowired
    private IAuthenticateSvc service;

    @Override
    public void init (IAuthParams params)
    {
////        service = SpringContext.getBean (AuthenticateSvcImpl.class);
        service.init (params);
    }

    @Override
    public IAuthResponse authenticate()
    {
        return service.authenticate();
    }

    public void setService (IAuthenticateSvc service)
    {
        this.service = service;
    }
}
