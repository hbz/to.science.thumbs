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
package de.hbznrw.thumby.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import de.hbznrw.thumby.model.Storage;
import de.hbznrw.thumby.service.ThumbyService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Controller
@Slf4j
public class MainController {
	
	@Autowired
	private Storage storage;
	
	@Autowired
	private ThumbyService service;
	
	@GetMapping(value="/",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public CompletableFuture<Object> getThumbnail( @RequestParam(required=false) String url, 
											       @RequestParam(defaultValue="150") Integer size,
											       @RequestParam(defaultValue="0") Integer refresh ) {
		return CompletableFuture.supplyAsync(  () -> {
			try {	
				if(url == null || url.isEmpty()) { 
					return (String) "upload";
				}
				URL urlAdress = new URL(url);
				if(!service.isWhitelisted(urlAdress.getHost())) {
					log.info("URLHost: " + urlAdress.getHost());
					return ResponseEntity.status(HttpStatus.FORBIDDEN)
										 .contentType(MediaType.APPLICATION_JSON)
										 .body("Thumby is not allowed to access this url!");
				}
				File result = (File) storage.get(urlAdress.toString() + size);
				if(refresh == 1 && result != null) { 
					result.delete();
					result = null;
				}
				if(result == null) {	
					result = service.uploadUrl(urlAdress, size);
					storage.set(urlAdress.toString() + size, result);
				}	
				return ResponseEntity.status(HttpStatus.OK)
						             .contentType(MediaType.IMAGE_JPEG)
						             .body(Files.readAllBytes(result.toPath()));
			
			} catch(IOException e) {
				return ResponseEntity.internalServerError().body(e.toString());
			}			
			
		});
	}

}
