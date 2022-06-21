/*Copyright (c) 2021 "hbz"

This file is part of thumby.

thumby is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.hbznrw.thumbs.helper;

import java.net.URI;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import de.hbznrw.thumbs.model.TypedInputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Component
@Slf4j
public class URLUtil {

    /*
     * This method will only encode an URL if it is not encoded already. It will
     * also replace '+'-encoded spaces with percent encoding.
     * 
     * First check if spaces are encoded with '+' signs. If so, replace it by
     * '%20' because this method is considered to be 'more correct'. So we want
     * generally use percent encoding. <p/> If the decoded form of the passed
     * url is equal to the direct string representation of the URL, it does not
     * harm to encode the URL. There will be no 'double encoding issue'
     * 
     */
    public URL saveEncode(URL url) {
        try {
            String passedUrl = url.toExternalForm().replaceAll("\\+", "%20");
            String decodeUrl = decode(passedUrl);
            if (passedUrl.equals(decodeUrl))
                return new URL(encode(passedUrl));
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String encode(String url) {
        try {
            URL u = new URL(url);
            URI uri = new URI(u.getProtocol(), 
            				  u.getUserInfo(), 
            				  IDN.toASCII(u.getHost()), 
            				  u.getPort(), 
            				  u.getPath(),
            				  u.getQuery(), 
            				  u.getRef() );
            String correctEncodedURL = uri.toASCIIString();
            return correctEncodedURL;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decode(String url) {
        try {
            URL u = new URL(url);
            String protocol = u.getProtocol();
            String userInfo = u.getUserInfo();
            String host = u.getHost() != null ? IDN.toUnicode(u.getHost()) : null;
            int port = u.getPort();
            String path = u.getPath() != null ? URLDecoder.decode(u.getPath(), StandardCharsets.UTF_8.name()) : null;
            String ref = u.getRef();
            String query = u.getQuery() != null ? URLDecoder.decode(u.getQuery(), StandardCharsets.UTF_8.name()) : null;

            protocol = protocol != null ? protocol + "://" : "";
            userInfo = userInfo != null ? userInfo : "";
            host = host != null ? host : "";
            String portStr = port != -1 ? ":" + port : "";
            path = path != null ? path : "";
            query = query != null ? "?" + query : "";
            ref = ref != null ? "#" + ref : "";

            return String.format("%s%s%s%s%s%s%s", protocol, userInfo, host, portStr, path, ref, query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public TypedInputStream urlToInputStream(URL url) {
        URL encodedUrl = saveEncode(url);
        HttpURLConnection con = null;
        TypedInputStream ts = new TypedInputStream();
        try {
            con = (HttpURLConnection) encodedUrl.openConnection();
            con.setInstanceFollowRedirects(false);
            con.connect();
            ts.setType(con.getContentType());
            int responseCode = con.getResponseCode();
            log.info("Get a " + responseCode + " from " + encodedUrl.toExternalForm());
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || responseCode == 307 || responseCode == 303) {
                String redirectUrl = con.getHeaderField("Location");
                try {
                    URL newUrl = new URL(redirectUrl);
                    log.info("Redirect to Location: " + newUrl);
                    return urlToInputStream(newUrl);
                } catch (MalformedURLException e) {
                    URL newUrl = new URL(encodedUrl.getProtocol() + "://" + encodedUrl.getHost() + redirectUrl);
                    log.info("Redirect to Location: " + newUrl);
                    return urlToInputStream(newUrl);
                }
            }
            ts.setIn(con.getInputStream());
            return ts;
        } catch (IOException e) {
            log.info("", e);
            throw new RuntimeException(e);
        }
	    
    }
    
}
