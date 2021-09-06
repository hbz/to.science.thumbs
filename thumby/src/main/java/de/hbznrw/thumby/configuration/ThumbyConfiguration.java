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
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author Jan Schnasse & Alessio Pellerito
 */
@Component
@ConfigurationProperties(prefix = "thumby")
public class ThumbyConfiguration {
	
	private String storageLocation;
	private List<String> whiteList; 
	private Resource pathToDefaultPic;
	private Resource pathToPdfPic;
	private Resource pathToZipPic;
	private Resource pathToVideoPic;
	private Resource pathToTextPic;
	private Resource pathToImagePic;
	private Resource pathToAudioPic;
	
	// Getters and Setters
	
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public void setWhiteList(List<String> whiteList) {
		this.whiteList = whiteList;
	}
	public void setPathToDefaultPic(Resource pathToDefaultPic) {
		this.pathToDefaultPic = pathToDefaultPic;
	}
	public void setPathToPdfPic(Resource pathToPdfPic) {
		this.pathToPdfPic = pathToPdfPic;
	}
	public void setPathToZipPic(Resource pathToZipPic) {
		this.pathToZipPic = pathToZipPic;
	}
	public void setPathToVideoPic(Resource pathToVideoPic) {
		this.pathToVideoPic = pathToVideoPic;
	}
	public void setPathToTextPic(Resource pathToTextPic) {
		this.pathToTextPic = pathToTextPic;
	}
	public void setPathToImagePic(Resource pathToImagePic) {
		this.pathToImagePic = pathToImagePic;
	}
	public void setPathToAudioPic(Resource pathToAudioPic) {
		this.pathToAudioPic = pathToAudioPic;
	}
	public String getStorageLocation() {
		return storageLocation;
	}
	public List<String> getWhiteList() {
		return whiteList;
	}
	public Resource getPathToDefaultPic() {
		return pathToDefaultPic;
	}
	public Resource getPathToPdfPic() {
		return pathToPdfPic;
	}
	public Resource getPathToZipPic() {
		return pathToZipPic;
	}
	public Resource getPathToVideoPic() {
		return pathToVideoPic;
	}
	public Resource getPathToTextPic() {
		return pathToTextPic;
	}
	public Resource getPathToImagePic() {
		return pathToImagePic;
	}
	public Resource getPathToAudioPic() {
		return pathToAudioPic;
	}
}
