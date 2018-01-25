<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Payment Receipt</title>
    <link href="css/bill-pay-receipt.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="parent-container">
    <div class="header">
        <div class="header-text">
            <span class="page-title">Payment Confirmation</span>
        </div>
        <div class="header-logo">
            <img src="img/logo.jpg"/>
        </div>
    </div>
    <div id="successSection">
        <div class="container top-content">
            <div class="confirmation-heading">Payment Received</div>
            <div>Please note it takes up to <b>2 business days</b> to process your payment and update your My Account
                balance.
            </div>
        </div>
        <div class="container confirmation-bg label-text payment-fields">
        <#if supplyAddress??>
            <div class="row address-parent">
                <div class="label-text-title right address-child">
                    <strong class="">Supply Address</strong>
                </div>
                <div class="label-text-field left address-child" id="supplyAddress">${supplyAddress}</div>
            </div>
        </#if>
        <#if fuelType??>
            <div class="row">
                <div class="label-text-title right">
                    <strong class="right">Energy Type</strong>
                </div>
                <div class="label-text-field left" id="fuelType">${fuelType}</div>
            </div>
        </#if>
        <#if receiptNumber??>
            <div class="row">
                <div class="label-text-title right">
                    <strong class="right">Receipt Number</strong>
                </div>
                <div class="label-text-field left" id="receiptNumber">${receiptNumber}</div>
            </div>
        </#if>
        <#if accountNumber??>
            <div class="row">
                <div class="label-text-title right">
                    <strong class="right">Account Number</strong>
                </div>
                <div class="label-text-field left" id="accountNumber">${accountNumber}</div>
            </div>
        </#if>
        <#if paymentReceived??>
            <div class="row">
                <div class="label-text-title right">
                    <strong class="right">Payment Received</strong>
                </div>
                <div class="label-text-field left" id="paymentReceived">${paymentReceived}</div>
            </div>
        </#if>
        <#if paymentAmount??>
            <div class="row margin-top-20">
                <div class="label-text-title right">
                    <strong class="right">Payment Amount</strong>
                </div>
                <div class="label-text-field left" id="paymentAmount">${paymentAmount}</div>
            </div>
        </#if>
        </div>
    </div>
    <div class="footer">
        <span class="page-url">https://www.originenergy.com.au/business/my-account.html</span>
        <span class="page-no">page <span id="pagenumber"></span>/<span id="pagecount"></span></span>
    </div>
</div>
</body>
</html>
