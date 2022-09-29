/*
 * MIT License
 *
 * Copyright (c) 2021 Thales DIS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.thalesgroup.tshpaysample.sdk;

public class SdkConstants {
 /*
    MG configuration API described in our documentation:
    https://thales-dis-dbp.stoplight.io/docs/tsh-hce-android/ZG9jOjM2MTc0MDEz-introduction
     */

    /*
    An ID which identifies the Wallet Provider uniquely.
     */
    public static final String WALLET_PROVIDER_ID = "WP_HCESANDBOX";

    /*
    The MG URL which the client will connect to.
     */
    public static final String MG_CONNECTION_URL = "https://hapi.dbp-stg.thalescloud.io/mg";

    /*
    The server URL which the client connect to retrieve the transaction history and notifications.
     */
    public static final String MG_TRANSACTION_HISTORY_CONNECTION_URL =  "https://hapi.dbp-stg.thalescloud.io/mg";

    /*
    The maximum time limit in milliseconds to connect to the server.
     */
    public static final int MG_CONNECTION_TIMEOUT = 30000; // <TO_BE_CONFIGURED>

    /*
    The maximum time limit in milliseconds to read the response from the server.
     */
    public static final int MG_CONNECTION_READ_TIMEOUT = 30000; // <TO_BE_CONFIGURED>

    /*
    Number of retries that the MG module can perform. Default value is set to 0.
     */
    public static final int MG_CONNECTION_RETRY_COUNT = 3; // <TO_BE_CONFIGURED>

    /*
    The interval in milliseconds before the MG module calls the server. Default value is set to 10000 ms.
     */
    public static final int MG_CONNECTION_RETRY_INTERVAL = 10000; // <TO_BE_CONFIGURED>

    /*
    Values used in sample app for calculation encrypted card data.
    Both of them are agreed during on-boarding phase. For more information please visit documentation:
    https://thales-dis-dbp.stoplight.io/docs/tsh-hce-android/ZG9jOjI5NDIzNDU0-onboarding
     */
    public static final String SUBJECT_IDENTIFIER = "022d11dfdbba94d85fe4cc97e46c71828d969c0f";
    public static final String PUBLIC_KEY = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100c56e1e17bda05f906b0f3a6c61464c12464102c2c5e70fce5aa6a0d382e0b0bc83d3d91330b55469c7f6d83dda8d4649a2290c144aea781ea1b3fed5658cd57e49b7f2a87eabfb7ff89f0aa49cb16d1fca3409e48215e23510d5112aae21fe2f2e9d4c28bde41c6ff7fcb92ba90c1c3d2ddca19e2d91db54f5c8b4b0d5747c995b9603fb4d0f8db204f1a86d7732065c631275df13bc5fb949daf4c3e0dbdc79f9ae6bf04bf3f981d1792e1311ae3b244e1983be9b53969cbc2753782d081f8e2f4864e46c4555f4658843a54e7c6589f9c9d4b961db767ed8f5c0273fc18aac13f3f341262378423c2d7c9360ba1d39aea02688583516cc864484d17bf6a4430203010001";
}
