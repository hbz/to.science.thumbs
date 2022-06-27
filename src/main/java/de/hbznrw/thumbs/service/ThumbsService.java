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
package de.hbznrw.thumbs.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.net.MediaType;

import de.hbznrw.thumbs.configuration.ThumbsProperties;
import de.hbznrw.thumbs.helper.ThumbnailGenerator;
import de.hbznrw.thumbs.helper.URLUtil;
import de.hbznrw.thumbs.model.TypedInputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Service
@Slf4j
public class ThumbsService {
	
	@Autowired
	private ThumbsProperties props;
	
	@Autowired
	private URLUtil urlUtil;
	
	@Autowired
	private ThumbnailGenerator generator;
	
	
	public boolean isWhitelisted(String host) {
		return props.getWhiteList().stream().anyMatch(element -> element.equals(host));
    }

    public File uploadUrl(URL url, int size) {
        TypedInputStream ts = null;
        try {
            File thumbnail = this.createThumbnail( props.getPathToDefaultPic().getInputStream(), 
            									   MediaType.PNG, size, url.toString() );
            ts = urlUtil.urlToInputStream(url);
            thumbnail = this.createThumbnail( ts.getIn(), 
            								  MediaType.parse(ts.getType()), 
            								  size, 
            								  url.toString() );
            return thumbnail;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ts != null && ts.getIn() != null) 
                    ts.getIn().close();
            } catch (Exception e) {
                log.error("Problems to close connection! Maybe restart application "
                		+ "to prevent too many open connections.\nCaused when accessing: " + url);
            }
        }
    }

    public File createThumbnail(InputStream ts, MediaType contentType, int size, String name) throws IOException {
        log.info("Content-Type: " + contentType);
        File out = generator.createThumbnail(ts, contentType, size, name);
        if (out == null)
            out = generator.createThumbnail( props.getPathToDefaultPic().getInputStream(), 	
            								 MediaType.PNG, 
            								 size, 
            								 name );
        return out;
    }
	
}
