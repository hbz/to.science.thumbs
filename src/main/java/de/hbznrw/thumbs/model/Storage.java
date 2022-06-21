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
package de.hbznrw.thumbs.model;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.CRC32;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.io.Files;

import de.hbznrw.thumbs.configuration.ThumbsProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Component
@Slf4j
public class Storage {
	
	private ThumbsProperties conf;
	
	private static final long partitions = 100;
	
	@Autowired
	public Storage(ThumbsProperties conf) {
		this.conf = conf;
		log.info("Store content in: " + conf.getStorageLocation());
        for(int i = 0; i <= partitions; i++) {
            new File(conf.getStorageLocation() + File.separator + i).mkdirs(); 
        }
	}
	
	public File get(String key) {
		File target = findTarget(key);
        log.info("SEARCH "+ target);
        if (target.exists())
            return target;
        return null;
	}
	
	public void set(String key, File result) {
        File target = findTarget(key);
        log.info("CREATE "+ target);
        try {
            Files.copy(result, target);
        } catch (IOException e) {
            log.debug("",e);
            throw new RuntimeException("", e);
        }
    } 

    private File findTarget(String key) {
        String name = encode(key);
        String dirname = getDirName(name);
        return new File(conf.getStorageLocation() + File.separator + dirname + File.separator + name);
    }

    private String getDirName(String name) {
        /* CRC32 checksum calculation */
        CRC32 crc = new CRC32();
        crc.update(name.getBytes());
        long num = crc.getValue();
        long mod = num % partitions;
        String dirname = "" + mod;
        return dirname;
    }

    private String encode(String encodeMe) {
        return Base64.getUrlEncoder().encodeToString(encodeMe.getBytes());
    }
    /*
    private String decode(String decodeMe) {
        return new String(Base64.getUrlDecoder().decode(decodeMe));
    }
	*/  
	
}
