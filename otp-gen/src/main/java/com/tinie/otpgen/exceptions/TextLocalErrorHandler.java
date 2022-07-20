package com.tinie.otpgen.exceptions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tinie.otpgen.domain.TextLocalErrWarnItem;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Component
@Log4j2
public class TextLocalErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        var objectMapper = new ObjectMapper();
        var responseRoot = objectMapper.readTree(httpResponse.getBody());
        var hasError = false;
        if (responseRoot.has("errors")) {
            hasError = true;
            var errorsNode = ((ArrayNode) responseRoot.get("errors"));
            var reader = objectMapper.readerFor(new TypeReference<List<TextLocalErrWarnItem>>(){});
            var errorList = reader.readValue(errorsNode);
            log.error("TEXT LOCAL ERRORS: " + errorList);
        }
        if (responseRoot.has("warnings")) {
            var warningsNode = ((ArrayNode) responseRoot.get("warnings"));
            var reader = objectMapper.readerFor(new TypeReference<List<TextLocalErrWarnItem>>(){});
            var warningsList = reader.readValue(warningsNode);
            log.error("TEXT LOCAL WARNINGS: " + warningsList);
        }

        return hasError;
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        log.error("USING WRONG REST CONTROLLER ERROR HANDLER METHOD");
        throw new SMSFailedException("NOK", 0L);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {
        var urlQuery = url.getQuery();
        var splitQuery = urlQuery.split("&");
        var queryPairs = new HashMap<String, String>();
        for (String pair : splitQuery) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }

        throw new SMSFailedException("NOK", Long.parseLong(queryPairs.get("numbers")));
    }
}
