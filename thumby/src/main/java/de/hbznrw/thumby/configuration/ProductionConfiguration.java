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
package de.hbznrw.thumby.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Component
@Profile("production")
@PropertySource("file:/etc/thumby.properties")
@ConfigurationProperties(prefix = "thumby")
@Data
public class ProductionConfiguration {
	
	// the values comes from external thumby.properties file
	private String storageLocation;
	private List<String> whiteList; 
	private Resource pathToDefaultPic;
	private Resource pathToPdfPic;
	private Resource pathToZipPic;
	private Resource pathToVideoPic;
	private Resource pathToTextPic;
	private Resource pathToImagePic;
	private Resource pathToAudioPic;

}
