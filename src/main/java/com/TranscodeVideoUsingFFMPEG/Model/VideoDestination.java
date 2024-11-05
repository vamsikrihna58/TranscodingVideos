package com.TranscodeVideoUsingFFMPEG.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDestination {
	public String path;

	@Override
	public String toString() {
		return "VideoDestination [" + (path != null ? "path=" + path : "") + "]";
	}

	
	
	

}
