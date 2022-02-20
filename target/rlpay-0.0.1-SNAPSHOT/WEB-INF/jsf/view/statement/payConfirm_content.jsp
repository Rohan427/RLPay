        <div class="div_header">
            <h1>Payment</h1>
        </div>
        <div class="res_div_header">
                Confirmation
        </div>
        <div class="res_div_main">
            <div style="width: 500px">
                    <c:choose>
                        <c:when test="${userBean.profileToken ne '0'}" >
                <p>
                    Continue payment to account <c:out value="${userBean.accountNum}" />
                </p>
            </div>
        </div>

        <div class="res_div_main">
            <form method="post" action="<c:out value="${userBean.hostedUrl}" />">
                <input type="hidden" name="token" value="<c:out value="${token}" />" />
                
                <p>
                    <input type="submit"
                           value="Continue"
                           name="submit" 
                    />
                </p>
            </form> 
        </div>
                        </c:when>
                        <c:otherwise>
                <p>
                    Failed payment for account <c:out value="${userBean.accountNum}" />.
                </p>
            </div>
        </div>
                        </c:otherwise>
                    </c:choose>
            
        <p>
            <small>&nbsp;</small>
        </p>
