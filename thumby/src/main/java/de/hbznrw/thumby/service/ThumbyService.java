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
package de.hbznrw.thumby.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.net.MediaType;

import de.hbznrw.thumby.configuration.ThumbyConfiguration;
import de.hbznrw.thumby.helper.ThumbnailGenerator;
import de.hbznrw.thumby.helper.URLUtil;
import de.hbznrw.thumby.model.TypedInputStream;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Service
public class ThumbyService {
    
    private static final Logger log = LoggerFactory.getLogger(ThumbyService.class);
	
	@Autowired
	private ThumbyConfiguration conf;
	
	@Autowired
	private URLUtil urlUtil;
	
	@Autowired
	private ThumbnailGenerator generator;
	
	
	public boolean isWhitelisted(String host) 
	{
		return conf.getWhiteList().stream().anyMatch(element -> element.equals(host));
    }

    public File uploadUrl(URL url, int size) 
    {
        TypedInputStream ts = null;
        try 
        {
            File thumbnail = this.createThumbnail( conf.getPathToDefaultPic().getInputStream(), 
            									   MediaType.PNG, size, url.toString() );
            ts = urlUtil.urlToInputStream(url);
            thumbnail = this.createThumbnail( ts.getIn(), 
            								  MediaType.parse(ts.getType()), 
            								  size, 
            								  url.toString() );
            return thumbnail;
        } catch (Exception e) 
        {
            throw new RuntimeException(e);
        } finally 
        {
            try 
            {
                if (ts != null && ts.getIn() != null) 
                    ts.getIn().close();
            } catch (Exception e) 
            {
                log.error("Problems to close connection! Maybe restart application "
                		+ "to prevent too many open connections.\nCaused when accessing: " + url);
            }
        }
    }

    public File createThumbnail(InputStream ts, MediaType contentType, int size, String name) throws IOException 
    {
        log.info("Content-Type: " + contentType);
        File out = generator.createThumbnail(ts, contentType, size, name);
        if (out == null)
            out = generator.createThumbnail( conf.getPathToDefaultPic().getInputStream(), 	
            								 MediaType.PNG, 
            								 size, 
            								 name );
        return out;
    }
	
}
