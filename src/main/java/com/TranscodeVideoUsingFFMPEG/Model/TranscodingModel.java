package com.TranscodeVideoUsingFFMPEG.Model;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranscodingModel {
	public List<VideoOutputProperties> outputs;
	public VideoDestination destination;
	@Override
	public String toString() {
		return "TranscodingModel [" + (outputs != null ? "outputs=" + outputs + ", " : "")
				+ (destination != null ? "destination=" + destination : "") + "]";
	}
	
	
	

}
