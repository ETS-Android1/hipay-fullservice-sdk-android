package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.network.HttpResult;
import com.hipay.fullservice.core.utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nfillion on 08/03/16.
 */

public class UpdatePaymentCardOperation extends AbstractOperation {

    public UpdatePaymentCardOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    protected URL getRequestURL() {

        String baseURL;

        switch (ClientConfig.getInstance().getEnvironment()) {

            case Stage: baseURL = ClientConfig.SecureVaultClientBaseURLStage; break;
            case Production: baseURL = ClientConfig.SecureVaultClientBaseURLProduction; break;
            default: baseURL = null; break;
        }

        URL requestURL;

        try {
            requestURL = Utils.concatenatePath(new URL(baseURL), "token/update");

        } catch (URISyntaxException e) {

            e.printStackTrace();
            requestURL = null;

        } catch (MalformedURLException e) {

            e.printStackTrace();
            requestURL = null;
        }

        return requestURL;
    }

    @Override
    protected HttpURLConnection getHttpURLConnection() throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) this.getRequestURL().openConnection();

        urlConnection.setRequestMethod(this.getRequestType().getStringValue());
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Authorization", this.getAuthHeader());

        if (this.getBundle() != null) {

            String queryParams = this.getBundle().getString("queryParams");

            if (queryParams != null) {

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(queryParams);
                wr.flush();
                wr.close();
            }
        }

        return urlConnection;
    }

    @Override
    public HttpMethod getRequestType() {
        return HttpMethod.POST;
    }

    @Override
    protected String getSignature() {
        return null;
    }

    @Override
    protected HttpResult buildFromHttpResponse(HttpResult httpResult) {
        return httpResult;
    }

    @Override
    public String concatUrl() {
        return null;
    }

    @Override
    protected boolean isV2() {
        return false;
    }
}

